package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNamesIndex
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.model.IndexEntry
import net.markdrew.biblebowl.typst.typstToPdf
import net.markdrew.biblebowl.typst.writeDoc
import net.markdrew.biblebowl.typst.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT)
    writeNamesList(studyData)
    writeNamesIndex(studyData)
}

private fun writeNamesList(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val names: Sequence<String> = findNames(studyData).map { it.excerptText }.sorted().distinct()
    val dir = defaultProductsPath.resolve("$studyName/lists").toFile().also { it.mkdirs() }
    val file = dir.resolve("$studyName-list-names.txt")
    file.writer().use { writer ->
        for (name in names) writer.appendLine(name)
    }
    println("Wrote $file")
}

/** Writes the names index (alphabetical + frequency) for [studyData] as a Typst PDF. */
fun writeNamesIndex(
    studyData: StudyData,
    productsDir: Path = defaultProductsPath,
    nameRanges: List<IntRange> = findNames(studyData).map { it.excerptRange }.toList(),
) {
    val studyName = studyData.studySet.simpleName
    val exceptNames: Array<String> = arrayOf()
    val indexEntries: List<WordIndexEntryC> = buildNamesIndex(studyData, nameRanges)
        .map { wordIndexEntry ->
            WordIndexEntryC(
                wordIndexEntry.key,
                wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) -> WithCount(verseRef, count) }
            )
        }
    val dir = productsDir.resolve(studyName, "indices").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-index-names.typ")
    val name = studyData.studySet.name
    val longName = studyData.studySet.longName
    val docPrefaceExceptions =
        if (exceptNames.isEmpty()) "" else ", except for these: ${exceptNames.sorted().joinToString()}"
    val docPreface = "The following is a complete index of all names in $longName$docPrefaceExceptions."
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(writer, "$name Names Index", docPreface) {

            val index: List<WordIndexEntryC> = indexEntries.sortedBy { it.key.lowercase() }
            writeIndex(writer, index, columns = 3,
                formatValues = studyData.compactWithCountVerseRefListFormat,
            )

            writer.appendLine("#pagebreak()")
            writer.appendLine()

            val freqs: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
                .filter { it.values.single() > 1 }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(writer, freqs, "Names in $name in Order of Increasing Frequency",
                       indexPreface = "Each name here occurs in $longName " +
                               "the number of times shown next to it. One-time names are omitted for brevity.",
                       columns = 5)
        }
    }
    file.typstToPdf(keepTypFiles = true)
}
