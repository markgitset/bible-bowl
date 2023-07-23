package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildWordIndex
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.io.File

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
//    val revStopWords = setOf("he", "from", "his", "is", "you", "was", "will", "for", "with", "on", "in", "who", "i",
//                              "a", "to", "of", "and", "the")
    writeFullIndex(studyData, STOP_WORDS)

//    // For Maria
//    for (studySet in setOf(StudySet(Book.EXO, "exodus"), StudySet(Book.LEV, "lev"), StudySet(Book.NUM, "num"))) {
//        writeFullIndex(StudyData.readData(studySet), STOP_WORDS)
//    }
}

fun writeFullIndex(studyData: StudyData, stopWords: Set<String> = STOP_WORDS) {
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
    val dir = File("$PRODUCTS_DIR/$simpleName/indices").also { it.mkdirs() }
    val file = dir.resolve("$simpleName-index-full.tex")
    file.writer().use { writer ->
        writeDoc(writer, "$setName Word Index",
            docPreface = "The following is a complete index of all words in $longName, " +
                    """except for these:\\\\${stopWords.sorted().joinToString()}.""") {

            val index: List<WordIndexEntryC> =
                indexEntries.filterNot { it.key in stopWords }.sortedBy { it.key.lowercase() }
            writeIndex(writer, index, columns = 3, formatValue = studyData.verseRefFormat.noBreak().withCount())

            writer.appendLine("""\newpage""")

            val freqs: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
                .filter { it.values.single() > 1 }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(writer, freqs, "Words in $setName in Order of Increasing Frequency",
                       indexPreface = "Each word here occurs in $longName " +
                               "the number of times shown next to it.  One-time words are omitted for brevity.",
                       columns = 5)
        }
    }
    file.toPdf(keepTexFiles = true)
}
