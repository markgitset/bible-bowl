package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.RAW_DATA_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.isReadable
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * @param includePassageReferences Include a line at the top displaying the reference of the passage.
 * Must be true or false. Default true.
 * @param includeFirstVerseNumbers Display the verse number at the beginnings of chapters, e.g. [23:1].
 * Must be true or false. Default true.
 * @param includeVerseNumbers Display verse numbers in brackets, e.g. [4]. Must be true or false. Default true.
 * @param includeFootnotes Display callouts to footnotes in the text. Must be true or false. Default true.
 * @param includeFootnoteBody Display footnotes at the end of the text. Must be true or false. Default true.
 * @param includeShortCopyright Include (ESV) at the end of the text. This fulfills your copyright display
 * requirements. Must be true or false. Default true.
 * @param includeCopyright Include a longer copyright at the end of the text. Mutually exclusive with
 * include_short_copyright. This fulfills your copyright display requirements. Must be true or false. Default false.
 * @param includePassageHorizontalLines Include a visual line of equal signs (====) above the beginning of each
 * passage. Must be true or false. Default true.
 * @param includeHeadingHorizontalLines Include a visual line of underscores (____) above each section heading.
 * Must be true or false. Default true.
 * @param horizontalLineLength Control the length of the line for include_passage_horizontal_lines and
 * include_heading_horizontal_lines. Must be an integer. Default 55.
 * @param includeHeadings Include extra-textual section headings, e.g. The Creation of the World.
 * Must be true or false. Default true.
 * @param includeSelahs Include Selah in certain Psalms. Must be true or false. Default true.
 * @param indentUsing Should indentation use spaces or tabs? Must be 'space' or 'tab'. Default 'space'.
 * @param indentParagraphs How many indentation characters to begin a paragraph with. Must be an integer.
 * Default 2.
 * @param indentPoetry Should poetry lines be indented? Must be true or false. Default true.
 * @param indentPoetryLines How many indentation characters to use for a poetry indentation level.
 * Must be an integer. Default 4.
 * @param indentDeclares How many indentation characters to use for Declares the LORD in some of the prophets.
 * Must be an integer. Default 40.
 * @param indentPsalmDoxology How many indentation characters to use for Psalm doxologies. Must be an integer.
 * Default 30.
 * @param lineLength How long may a line be before it is wrapped? Use 0 for unlimited line lengths.
 * Must be an integer. Default 0.
 */
class EsvClient(
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
    private val esvService: EsvService = EsvService.create()) {

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
        return Paths.get(RAW_DATA_DIR).resolve(bookDirName).resolve(chapterRef.chapter.toString())
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

        fun create(): EsvClient = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EsvClient::class.java)

    }

}