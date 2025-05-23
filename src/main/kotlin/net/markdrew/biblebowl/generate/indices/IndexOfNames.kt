package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNamesIndex
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.io.File
import java.nio.file.Paths

fun main() {
//    val revStopWords = setOf("he", "from", "his", "is", "you", "was", "will", "for", "with", "on", "in", "who", "i",
//                              "a", "to", "of", "and", "the")
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR))
    writeNamesList(studyData)
    writeNamesIndex(studyData)
}

private fun writeNamesList(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val names: Sequence<String> = findNames(studyData).map { it.excerptText }.sorted().distinct()
    val dir = File("$PRODUCTS_DIR/$studyName/lists").also { it.mkdirs() }
    val file = dir.resolve("$studyName-list-names.txt")
    file.writer().use { writer ->
        for (name in names) writer.appendLine(name)
    }
    println("Wrote $file")
}

fun writeNamesIndex(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val exceptNames: Array<String> = arrayOf()
    val indexEntries: List<WordIndexEntryC> = buildNamesIndex(studyData, *exceptNames)
        .map { wordIndexEntry ->
            WordIndexEntryC(
                wordIndexEntry.key,
                wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) -> WithCount(verseRef, count) }
            )
        }
    val dir = File("$PRODUCTS_DIR/$studyName/indices").also { it.mkdirs() }
    val file = dir.resolve("$studyName-index-names.tex")
    val name = studyData.studySet.name
    val longName = studyData.studySet.longName
    val docPrefaceExceptions =
        if (exceptNames.isEmpty()) "" else ", except for these: ${exceptNames.sorted().joinToString()}"
    val docPreface = "The following is a complete index of all names in $longName$docPrefaceExceptions."
    file.writer().use { writer ->
        writeDoc(writer, "$name Names Index", docPreface) {

            val index: List<WordIndexEntryC> = indexEntries.sortedBy { it.key.lowercase() }
            writeIndex(writer, index, columns = 3,
                //formatValue = studyData.verseRefFormat.noBreak().withCount(),
                formatValues = studyData.compactWithCountVerseRefListFormat,
            )

            writer.appendLine("""\newpage""")

            val freqs: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
                .filter { it.values.single() > 1 }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(writer, freqs, "Names in $name in Order of Increasing Frequency",
                       indexPreface = "Each name here occurs in $longName " +
                               "the number of times shown next to it.  One-time names are omitted for brevity.",
                       columns = 5)
        }
    }
    file.latexToPdf(keepTexFiles = true)
}
