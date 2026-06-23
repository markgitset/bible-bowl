package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNumbersIndex
import net.markdrew.biblebowl.analysis.findNumbers
import net.markdrew.biblebowl.fileForProduct
import net.markdrew.biblebowl.model.IndexEntry
import net.markdrew.biblebowl.typst.typstToPdf
import net.markdrew.biblebowl.typst.writeDoc
import net.markdrew.biblebowl.typst.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    writeNumbersIndex(StudyData.readData(StandardStudySet.DEFAULT))
}

/** Writes the numbers index (alphabetical + frequency) for [studyData] as a Typst PDF. */
fun writeNumbersIndex(studyData: StudyData, stopWords: Set<String> = STOP_WORDS,
                      productsDir: Path = defaultProductsPath,
                      numberRanges: List<IntRange> = findNumbers(studyData.text).map { it.excerptRange }.toList()) {
    val indexEntries: List<WordIndexEntryC> = buildNumbersIndex(studyData, numberRanges).map { wordIndexEntry ->
        WordIndexEntryC(
            wordIndexEntry.key,
            wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) ->
                WithCount(verseRef, count)
            }
        )
    }.filterNot { it.key in stopWords }
    writeTypstIndex(studyData, indexEntries, "Number", productsDir = productsDir)
}

/**
 * Writes a generic alphabetical+frequency Typst index for [indexEntries] under [productsDir]/<simpleName>/indices/
 *
 * Used as a shared backbone by the various per-category index writers.
 *
 * @param singularIndexType label for one entry (e.g. "Number")
 * @param pluralIndexType label for many entries (defaults to [singularIndexType] + "s")
 */
fun writeTypstIndex(
    studyData: StudyData,
    indexEntries: List<WordIndexEntryC>,
    singularIndexType: String,
    pluralIndexType: String = "${singularIndexType}s",
    productsDir: Path,
) {
    val file = fileForProduct(studyData, "indices", "index-$pluralIndexType.typ", productsDir)
    writeTypstIndex(studyData, pluralIndexType, indexEntries, singularIndexType, file)
}

/**
 * Writes the alphabetical and frequency sections of an index to [file] as Typst, then compiles to PDF.
 */
fun writeTypstIndex(
    studyData: StudyData,
    pluralIndexType: String,
    indexEntries: List<WordIndexEntryC>,
    singularIndexType: String,
    file: Path
) {
    val fullName = studyData.studySet.name
    val longName = studyData.studySet.longName
    val docPreface = "The following is a complete index of all ${pluralIndexType.lowercase()} in $longName"
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(writer, "$fullName $pluralIndexType Index", docPreface) {

            val index: List<WordIndexEntryC> = indexEntries.sortedBy { it.key.lowercase() }
            writeIndex(
                writer, index, columns = 3,
                formatValues = studyData.compactWithCountVerseRefListFormat,
            )

            writer.appendLine("#pagebreak()")
            writer.appendLine()

            val frequencies: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(
                writer, frequencies, "$pluralIndexType in $fullName in Order of Increasing Frequency",
                indexPreface = "Each ${singularIndexType.lowercase()} here occurs in $longName " +
                        "the number of times shown next to it.",
                columns = 4
            )
        }
    }
    file.typstToPdf(keepTypFiles = true)
}
