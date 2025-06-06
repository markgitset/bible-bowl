package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.RAW_DATA_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.createDirectories
import kotlin.io.path.isReadable
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class PassageCache(
    private val forceDownload: Boolean = false,
    private val esvClient: EsvClient = EsvClient(),
    private val timeBetweenChapters: Duration = 1.seconds,
) {
    fun getPassage(chapterRef: ChapterRef): Passage {
        val cached: Passage? = if (forceDownload) null else retrieveCachedPassage(chapterRef)
        if (cached != null) {
            println("Using cached $chapterRef")
            return cached
        }
        Thread.sleep(timeBetweenChapters.inWholeMilliseconds)
        val passage: Passage = esvClient.queryPassage(chapterRef)
        updateCachedPassage(chapterRef, passage)
        return passage
    }

    private fun retrieveCachedPassage(chapterRef: ChapterRef): Passage? {
        val dir = cachePathFor(chapterRef, null).parent
        if (!dir.toFile().exists()) return null
        val prefix = "${chapterRef.chapter}_"
        val mostRecentFile = dir.toFile().listFiles { file ->
            file.name.startsWith(prefix)
        }?.maxByOrNull { it.name.substringAfter(prefix) }
            ?.toPath()
        return if (mostRecentFile?.isReadable() == true) Passage.deserialize(mostRecentFile.readText()) else null
    }

    private fun updateCachedPassage(chapterRef: ChapterRef, passage: Passage) {
        val existingPassage = retrieveCachedPassage(chapterRef)
        if (existingPassage != null && existingPassage.serialize() == passage.serialize()) {
            println("Content unchanged for $chapterRef, skipping cache update")
            return
        }
        println("Updating cache for $chapterRef...")
        val path = cachePathFor(chapterRef, LocalDateTime.now())
        path.parent.createDirectories()
        path.writeText(passage.serialize())
    }

    private fun cachePathFor(chapterRef: ChapterRef, timestamp: LocalDateTime?): Path {
        val bookDirName: String = "%02d-${chapterRef.bookName}".format(chapterRef.book.number)
        val fileName = if (timestamp != null) {
            "${chapterRef.chapter}_${timestamp.format(dateFormatter)}"
        } else {
            "${chapterRef.chapter}_"  // Just the prefix for searching
        }
        return Paths.get(RAW_DATA_DIR).resolve(bookDirName).resolve(fileName)
    }

    fun bookByChapters(book: Book): Sequence<Passage> = sequence {
        var chapterNum: Int? = 1
        while (chapterNum != null) {
            val chapter = getPassage(ChapterRef(book, chapterNum))
            yield(chapter)
            val next = chapter.meta.nextChapter?.firstOrNull()?.toVerseRef()
            chapterNum = if (next?.book == book) next.chapter else null
        }
    }

    fun bookByChapters(studySet: StudySet): Sequence<Passage> =
        studySet.chapterRanges.asSequence().flatMap { rangeByChapters(it) }

    private fun rangeByChapters(chapterRange: ChapterRange): Sequence<Passage> {
        var chapterRef: ChapterRef? = chapterRange.start
        return generateSequence {
            chapterRef?.let {
                val chapter: Passage = getPassage(it)
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
        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
    }
}