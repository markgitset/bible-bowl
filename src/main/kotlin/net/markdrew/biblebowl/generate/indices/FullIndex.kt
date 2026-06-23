package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildWordIndex
import net.markdrew.biblebowl.model.IndexEntry
import net.markdrew.biblebowl.typst.typstToPdf
import net.markdrew.biblebowl.typst.writeDoc
import net.markdrew.biblebowl.typst.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
    writeFullIndex(studyData, STOP_WORDS)
}

/**
 * Writes the complete word index (alphabetical + frequency) for [studyData] as a Typst PDF
 *
 * Excludes [stopWords] from the alphabetical section. The frequency section omits one-time words.
 */
fun writeFullIndex(studyData: StudyData, stopWords: Set<String> = STOP_WORDS, productsDir: Path = defaultProductsPath) {
    val indexEntries: List<WordIndexEntryC> = buildWordIndex(studyData)
        .map { wordIndexEntry ->
            WordIndexEntryC(
                wordIndexEntry.key,
                wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) -> WithCount(verseRef, count) }
            )
        }
    val simpleName = studyData.studySet.simpleName
    val setName = studyData.studySet.name
    val longName = studyData.studySet.longName
    val dir = productsDir.resolve(simpleName, "indices").also { Files.createDirectories(it) }
    val file = dir.resolve("$simpleName-index-full.typ")
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(writer, "$setName Word Index",
            docPreface = "The following is a complete index of all words in $longName, " +
                    "except for these: ${stopWords.sorted().joinToString()}.") {

            val index: List<WordIndexEntryC> =
                indexEntries.filterNot { it.key in stopWords }.sortedBy { it.key.lowercase() }
            writeIndex(writer, index, columns = 3,
                formatValues = studyData.compactWithCountVerseRefListFormat,
            )

            writer.appendLine("#pagebreak()")
            writer.appendLine()

            val freqs: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
                .filter { it.values.single() > 1 }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(writer, freqs, "Words in $setName in Order of Increasing Frequency",
                       indexPreface = "Each word here occurs in $longName " +
                               "the number of times shown next to it. One-time words are omitted for brevity.",
                       columns = 5)
        }
    }
    file.typstToPdf(keepTypFiles = true)
}
