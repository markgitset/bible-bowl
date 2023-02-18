package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.VerseIndexEntry
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Paths

fun main() {
    writeOneTimeWordsIndex(StandardStudySet.DEFAULT)
}

fun writeOneTimeWordsIndex(studySet: StudySet): File {
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))
    return writeOneTimeWordsIndex(studyData)
}

fun writeOneTimeWordsIndex(studyData: StudyData): File {
    val simpleName = studyData.studySet.simpleName
    val name = studyData.studySet.name
    val indexEntriesByWord: List<WordIndexEntry> = oneTimeWordsIndexByWord(studyData)
    val indexEntriesByVerse: List<VerseIndexEntry> = oneTimeWordsIndexByVerse(studyData)
    val dir = File("$PRODUCTS_DIR/$simpleName/indices").also { it.mkdirs() }
    val file = dir.resolve("$simpleName-index-one-time-words.tex")
    file.writer().use { writer ->
        writeDoc(
            writer, "$name One-Time Words",
            docPreface = "The following words only appear one time in the whole book of $name.",
            allowParagraphBreaks = false,
        ) {

            writeIndex(
                writer, indexEntriesByWord.sortedBy { it.key.lowercase() }, "Alphabetical",
                columns = 4, formatValue = studyData.verseRefFormat.noBreak()
            )
            writer.appendLine("""\newpage""")
            writeIndex(
                writer, indexEntriesByVerse.sortedBy { it.key }, "In Order of Appearance",
                columns = 4, formatKey = studyData.verseRefFormat
            )
        }
    }
    return file.toPdf(keepTexFiles = true)
}

private fun oneTimeWordsIndexByWord(studyData: StudyData): List<WordIndexEntry> = oneTimeWords(studyData).map {
    val ref: VerseRef = studyData.verseEnclosing(it) ?: throw Exception()
    IndexEntry(studyData.excerpt(it).excerptText, listOf(ref))
}

private fun oneTimeWordsIndexByVerse(studyData: StudyData): List<VerseIndexEntry> = oneTimeWords(studyData)
    .groupBy {
        studyData.verseEnclosing(it) ?: throw Exception()
    }.map { (ref, wordRanges) ->
        IndexEntry(ref, wordRanges.map { studyData.excerpt(it).excerptText })
    }
