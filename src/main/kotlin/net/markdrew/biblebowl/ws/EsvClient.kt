package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import retrofit2.Call
import retrofit2.Response
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isReadable
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * High-level wrapper around [EsvService] that fetches one chapter at a time and caches the JSON response on disk
 *
 * Cached chapters live under `<rawDataDir>/<NN-Book>/<chapter>` where `NN` is the book's 1-based canonical
 * number. A cached chapter is reused on subsequent runs unless `forceDownload` is true. When fetching from
 * the live API the client sleeps `timeBetweenChapters` between requests to stay polite to ESV.
 *
 * The remaining constructor parameters mirror the ESV "passage/text" formatting options; see [EsvService.text]
 * for details. Defaults here are tuned for downstream parsing by [EsvIndexer].
 *
 * @param rawDataDir local directory used for the chapter-level JSON cache
 * @param token ESV API token; defaults to the `ESV_API_TOKEN` environment variable
 */
class EsvClient(
    private val rawDataDir: Path = defaultRawDataPath,
    private val includePassageReferences: Boolean = false,
    private val includeFirstVerseNumbers: Boolean = true,
    private val includeVerseNumbers: Boolean = true,
    private val includeFootnotes: Boolean = true,
    private val includeFootnoteBody: Boolean = true,
    private val includeShortCopyright: Boolean = false,
    private val includeCopyright: Boolean = false,
    private val includePassageHorizontalLines: Boolean = false,
    private val includeHeadingHorizontalLines: Boolean = true,
    private val horizontalLineLength: Int = 55,
    private val includeHeadings: Boolean = true,
    private val includeSelahs: Boolean = true,
    private val indentUsing: String = "space",
    private val indentParagraphs: Int = 2,
    private val indentPoetry: Boolean = true,
    private val indentPoetryLines: Int = INDENT_POETRY_LINES,
    private val indentDeclares: Int = 40,
    private val indentPsalmDoxology: Int = 30,
    private val lineLength: Int = 0,
    token: String? = System.getenv("ESV_API_TOKEN"),
    private val esvService: EsvService = EsvService.create(token),
) {

    /** Builds (but does not execute) the underlying Retrofit [Call] for [query]. */
    fun query(query: String): Call<PassageText> = esvService.text(query,
        includePassageReferences,
        includeFirstVerseNumbers,
        includeVerseNumbers,
        includeFootnotes,
        includeFootnoteBody,
        includeShortCopyright,
        includeCopyright,
        includePassageHorizontalLines,
        includeHeadingHorizontalLines,
        horizontalLineLength,
        includeHeadings,
        includeSelahs,
        indentUsing,
        indentParagraphs,
        indentPoetry,
        indentPoetryLines,
        indentDeclares,
        indentPsalmDoxology,
        lineLength
    )

    private fun queryPassage(singleQuery: String): Passage {
        require(',' !in singleQuery) { "singleQuery may not contain a comma!" }
        println("query = $singleQuery")
        val call: Call<PassageText> = query(singleQuery)
        val response: Response<PassageText> = call.execute()
        return response.body()?.single() ?: throw Exception(response.errorBody()?.string())
    }

    /**
     * Streams every chapter of [book] from the API, one [Passage] per chapter, walking via the API's
     * `next_chapter` link until the next chapter is in a different book.
     *
     * Does NOT use the on-disk cache; intended for ad-hoc whole-book pulls.
     */
    fun bookByChapters(book: Book): Sequence<Passage> {
        var chapterNum: Int? = 1
        return generateSequence {
            if (chapterNum == null) null
            else {
                val chapter = queryPassage("$book$chapterNum")
                val nextChapterV1: VerseRef? = chapter.meta.nextChapter?.first()?.toVerseRef()
                chapterNum = if (nextChapterV1?.book == book) nextChapterV1.chapter else null
                chapter
            }
        }
    }

    /**
     * Streams every chapter in every range of [studySet] in canonical order, using the on-disk cache when
     * available
     *
     * @param forceDownload if true, ignores the cache and re-fetches every chapter from the API
     * @param timeBetweenChapters delay between live API calls to avoid overloading the ESV service; not
     *   applied when responses come from the cache
     */
    fun bookByChapters(
        studySet: StudySet,
        forceDownload: Boolean,
        timeBetweenChapters: Duration = 1.seconds,
    ): Sequence<Passage> =
        studySet.chapterRanges.asSequence().flatMap {
            rangeByChapters(it, forceDownload, timeBetweenChapters)
        }

    private fun retrievePassage(
        chapterRef: ChapterRef,
        timeBetweenChapters: Duration,
        forceDownload: Boolean,
    ): Passage {
        val cached: Passage? = if (forceDownload) null else retrieveCachedPassage(chapterRef)
        if (cached != null) {
            println("Using cached $chapterRef")
            return cached
        }

        Thread.sleep(timeBetweenChapters.inWholeMilliseconds)
        val passage: Passage = retrievePassageFromService(chapterRef)
        updateCachedPassage(chapterRef, passage)
        return passage
    }

    private fun retrievePassageFromService(chapterRef: ChapterRef): Passage {
        println("Querying $chapterRef...")
        return queryPassage(chapterRefToQuery(chapterRef))
    }

    private fun retrieveCachedPassage(chapterRef: ChapterRef): Passage? {
        //println("Checking cache for $chapterRef...")
        val path = cachePathFor(chapterRef)
        return if (path.isReadable()) Passage.deserialize(path.readText()) else null
    }

    private fun updateCachedPassage(chapterRef: ChapterRef, passage: Passage) {
        println("Updating cache for $chapterRef...")
        val path = cachePathFor(chapterRef)
        path.parent.createDirectories()
        path.writeText(passage.serialize())
    }

    private fun cachePathFor(chapterRef: ChapterRef): Path {
        val bookDirName: String = "%02d-${chapterRef.bookName}".format(chapterRef.book.number)
        return rawDataDir.resolve(bookDirName).resolve(chapterRef.chapter.toString())
    }

    private fun rangeByChapters(
        chapterRange: ChapterRange,
        forceDownload: Boolean,
        timeBetweenChapters: Duration,
    ): Sequence<Passage> {
        var chapterRef: ChapterRef? = chapterRange.start
        return generateSequence {
            chapterRef?.let {
                val chapter: Passage = retrievePassage(it, timeBetweenChapters, forceDownload)
                val nextChapterV1: VerseRef? = chapter.meta.nextChapter?.first()?.toVerseRef()
                if (nextChapterV1 == null) null
                else {
                    chapterRef = if (nextChapterV1.chapterRef in chapterRange) nextChapterV1.chapterRef else null
                    chapter
                }
            }
        }
    }

    companion object {

        private fun chapterRefToQuery(chapterRef: ChapterRef): String = with(chapterRef.verseRange()) {
            "${start.absoluteVerse}-${endInclusive.absoluteVerse}"
        }

        /** Builds a default-configured [EsvClient] using [token] (defaults to the `ESV_API_TOKEN` env var). */
        fun create(token: String? = System.getenv("ESV_API_TOKEN")): EsvClient = EsvClient(token = token)

    }

}
