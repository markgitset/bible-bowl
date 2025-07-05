package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.ChapterRef
import java.nio.file.Path
import java.time.LocalDateTime
import kotlin.io.path.createDirectories
import kotlin.io.path.isReadable
import kotlin.io.path.readText
import kotlin.io.path.writeText

class PassageStore(
    private val baseDir: Path = getDefaultCacheDir()
) {
    companion object {
        private fun getDefaultCacheDir(): Path {
            // Replace this with actual default dir logic if needed
            return Path.of(System.getProperty("user.home"), ".tbb", "passage-cache")
        }
    }

    fun read(chapterRef: ChapterRef): Passage? {
        val dir = cachePathFor(chapterRef, null).parent
        if (!dir.toFile().exists()) return null
        val prefix = "${chapterRef.chapter}_"
        val mostRecentFile: Path? = dir.toFile().listFiles { file ->
            file.name.startsWith(prefix)
        }?.maxByOrNull { it.name.substringAfter(prefix) }
            ?.toPath()
        return if (mostRecentFile?.isReadable() == true) Passage.deserialize(mostRecentFile.readText()) else null
    }

    fun write(chapterRef: ChapterRef, passage: Passage) {
        val path = cachePathFor(chapterRef, LocalDateTime.now())
        path.parent.createDirectories()
        path.writeText(passage.serialize())
    }

    fun mostRecentSerialized(chapterRef: ChapterRef): String? {
        return read(chapterRef)?.serialize()
    }

//    fun bookByChapters(studySet: StudySet): Sequence<Passage> =
//        studySet.chapterRanges.asSequence().flatMap { rangeByChapters(it) }

    private fun cachePathFor(chapterRef: ChapterRef, timestamp: LocalDateTime?): Path {
        // This is based on the pattern seen in the original retrieveCachedPassage
        val subdir = baseDir.resolve(chapterRef.book.toString())
        val ts = timestamp?.toString()?.replace(":", "-") ?: ""
        val fileName = if (ts.isBlank()) "${chapterRef.chapter}_" else "${chapterRef.chapter}_$ts"
        return subdir.resolve(fileName)
    }
}
