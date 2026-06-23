package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNonLocalPhrasesIndex
import net.markdrew.biblebowl.analysis.buildPhrasesIndex
import net.markdrew.biblebowl.model.IndexEntry
import net.markdrew.biblebowl.typst.typstToPdf
import net.markdrew.biblebowl.typst.writeDoc
import net.markdrew.biblebowl.typst.writeIndex
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT)
    writePhrasesIndex(studyData, maxPhraseLength = 23)
    writeNonLocalPhrasesIndex(studyData, maxPhraseLength = 23)
}

/** Returns a formatter that replaces spaces with Unicode non-breaking spaces so the output won't line-wrap. */
fun <T> ((T) -> String).noBreak(): (T) -> String = { ref -> this(ref).replace(' ', '\u00a0') }

/** Lifts a `(T) -> String` formatter to a `(WithCount<T>) -> String` by appending the count when > 1. */
fun <T> ((T) -> String).withCount(): (WithCount<T>) -> String =
    { ref -> formatWithCount(this(ref.item), ref.count) }

/** Returns [s] with `(×N)` appended when [count] > 1, suitable for Typst. */
fun formatWithCount(s: String, count: Int): String =
    s + (if (count > 1) " (×$count)" else "")

/** Specialized [formatWithCount] for a [VerseRef] rendered without a book name. */
fun formatVerseRefWithCount(ref: WithCount<VerseRef>): String =
    ref.item.format(NO_BOOK_FORMAT) + (if (ref.count > 1) " (×${ref.count})" else "")

private fun writePhrasesIndex(studyData: StudyData, maxPhraseLength: Int = 50) {
    val indexEntries: List<WordIndexEntryC> = buildPhrasesIndex(studyData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
                phraseIndexEntry.key.joinToString(" "),
                phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
            )
        }
    val simpleName = studyData.studySet.simpleName
    val dir = defaultProductsPath.resolve("$simpleName/indices").toFile().also { it.mkdirs() }
    val file = dir.resolve("$simpleName-index-phrases.typ")
    val fullName = studyData.studySet.name
    file.writer().use { writer ->
        writeDoc(
            writer, "$fullName Reoccurring Phrases",
            docPreface = "The following phrases appear at least twice in $fullName."
        ) {

            writeIndex(
                writer,
                indexEntries.sortedBy { it.key },
                "Alphabetical",
                columns = 2,
                formatValue = ::formatVerseRefWithCount,
            )
            writer.appendLine("#pagebreak()")
            writer.appendLine()
            writeIndex(
                writer,
                indexEntries.sortedByDescending { it.values.size },
                "In Order of Decreasing Frequency",
                columns = 2,
                formatValue = ::formatVerseRefWithCount,
            )
        }
    }
    file.toPath().typstToPdf(keepTypFiles = true)
}

/**
 * Writes the non-local phrases index (alphabetical + frequency) for [studyData] as a Typst PDF
 *
 * "Non-local" = phrases that recur across more than one chapter; see [buildNonLocalPhrasesIndex].
 */
fun writeNonLocalPhrasesIndex(studyData: StudyData, productsDir: Path = defaultProductsPath,
                              maxPhraseLength: Int = 50) {
    val indexEntries: List<WordIndexEntryC> = buildNonLocalPhrasesIndex(studyData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
                phraseIndexEntry.key.joinToString(" "),
                phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
            )
        }
    val simpleName = studyData.studySet.simpleName
    val dir = productsDir.resolve(simpleName, "indices").also { Files.createDirectories(it) }
    val file = dir.resolve("$simpleName-index-nonlocal-phrases.typ")
    val fullName = studyData.studySet.name
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(
            writer, "$fullName Reoccurring Non-Local Phrases", docPreface = "The following phrases " +
                    "appear in at least two different chapters in $fullName."
        ) {

            writeIndex(
                writer, indexEntries.sortedBy { it.key }, "Alphabetical",
                columns = 2, formatValue = studyData.verseRefFormat.noBreak().withCount()
            )
            writer.appendLine("#pagebreak()")
            writer.appendLine()
            writeIndex(
                writer, indexEntries.sortedByDescending { it.values.size }, "In Order of Decreasing Frequency",
                columns = 2, formatValue = studyData.verseRefFormat.noBreak().withCount()
            )
        }
    }
    file.typstToPdf(keepTypFiles = true)
}
