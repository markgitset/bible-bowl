package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNumbersIndex
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.io.File
import java.nio.file.Paths

fun main() {
    writeNumbersIndex(StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR)))
}

fun writeNumbersIndex(studyData: StudyData, stopWords: Set<String> = STOP_WORDS) {
    val indexEntries: List<WordIndexEntryC> = buildNumbersIndex(studyData).map { wordIndexEntry ->
        WordIndexEntryC(
            wordIndexEntry.key,
            wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) ->
                WithCount(verseRef, count)
            }
        )
    }
    val fullName = studyData.studySet.name
    val longName = studyData.studySet.longName
    val simpleName = studyData.studySet.simpleName
    val dir = File("$PRODUCTS_DIR/$simpleName/indices").also { it.mkdirs() }
    val file = dir.resolve("$simpleName-index-numbers.tex")
    val docPreface = "The following is a complete index of all numbers in $longName"
    file.writer().use { writer ->
        writeDoc(writer, "$fullName Numbers Index", docPreface) {

            val index: List<WordIndexEntryC> =
                indexEntries.filterNot { it.key in stopWords }.sortedBy { it.key.lowercase() }
            writeIndex(writer, index, columns = 3,
                //formatValue = studyData.verseRefFormat.noBreak().withCount(),
                formatValues = studyData.compactWithCountVerseRefListFormat,
            )

            writer.appendLine("""\newpage""")

            val freqs: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
//                .filter { it.values.single() > 1 }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(writer, freqs, "Numbers in $fullName in Order of Increasing Frequency",
                       indexPreface = "Each number here occurs in $longName " +
                               "the number of times shown next to it.",
                       columns = 4)
        }
    }
    file.latexToPdf(keepTexFiles = true)
//    println("Wrote $file")
}
