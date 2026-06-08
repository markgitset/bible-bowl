package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNumbersIndex
import net.markdrew.biblebowl.analysis.findNumbers
import net.markdrew.biblebowl.fileForProduct
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    writeNumbersIndex(StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR_NAME)))
}

/** Writes the numbers index (alphabetical + frequency) for [studyData] as a LaTeX PDF. */
fun writeNumbersIndex(studyData: StudyData, stopWords: Set<String> = STOP_WORDS,
                      productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
                      numberRanges: List<IntRange> = findNumbers(studyData.text).map { it.excerptRange }.toList()) {
    val indexEntries: List<WordIndexEntryC> = buildNumbersIndex(studyData, numberRanges).map { wordIndexEntry ->
        WordIndexEntryC(
            wordIndexEntry.key,
            wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) ->
                WithCount(verseRef, count)
            }
        )
    }.filterNot { it.key in stopWords }
    writeLatexIndex(studyData, indexEntries, "Number", productsDir = productsDir)
}

/**
 * Writes a generic alphabetical+frequency LaTeX index for [indexEntries] under [productsDir]/<simpleName>/indices/
 *
 * Used as a shared backbone by the various per-category index writers.
 *
 * @param singularIndexType label for one entry (e.g. "Number")
 * @param pluralIndexType label for many entries (defaults to [singularIndexType] + "s")
 */
fun writeLatexIndex(
    studyData: StudyData,
    indexEntries: List<WordIndexEntryC>,
    singularIndexType: String,
    pluralIndexType: String = "${singularIndexType}s",
    productsDir: Path,
) {
    val file = fileForProduct(studyData, "indices", "index-$pluralIndexType.tex", productsDir)
    writeLatexIndex(studyData, pluralIndexType, indexEntries, singularIndexType, file)
}

/** Path-typed overload of [writeLatexIndex] that delegates to the [File] version. */
fun writeLatexIndex(
    studyData: StudyData,
    pluralIndexType: String,
    indexEntries: List<WordIndexEntryC>,
    singularIndexType: String,
    file: Path
) {
    writeLatexIndex(studyData, pluralIndexType, indexEntries, singularIndexType, file.toFile())
}

/**
 * Writes the alphabetical and frequency sections of an index to [file] as LaTeX, then compiles to PDF.
 */
fun writeLatexIndex(
    studyData: StudyData,
    pluralIndexType: String,
    indexEntries: List<WordIndexEntryC>,
    singularIndexType: String,
    file: File
) {
    val fullName = studyData.studySet.name
    val longName = studyData.studySet.longName
    val docPreface = "The following is a complete index of all ${pluralIndexType.lowercase()} in $longName"
    file.writer().use { writer ->
        writeDoc(writer, "$fullName $pluralIndexType Index", docPreface) {

            val index: List<WordIndexEntryC> = indexEntries.sortedBy { it.key.lowercase() }
            writeIndex(
                writer, index, columns = 3,
                //formatValue = studyData.verseRefFormat.noBreak().withCount(),
                formatValues = studyData.compactWithCountVerseRefListFormat,
            )

            writer.appendLine("""\newpage""")

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
    file.latexToPdf(keepTexFiles = true)
}

