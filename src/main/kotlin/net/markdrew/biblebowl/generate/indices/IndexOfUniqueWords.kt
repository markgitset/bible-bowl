package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.analysis.ChapterIndexEntry
import net.markdrew.biblebowl.analysis.VerseIndexEntry
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR_NAME))
//    writeOneTimeWordsIndex(studyData)
    writeOneTimeWordsHomework(studyData)
//    writeOneTimeWordsList(studyData)
}

private fun writeOneTimeWordsList(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val names: List<String> = oneTimeWords(studyData).map { studyData.excerpt(it).excerptText }.sorted()
    val dir = File("$PRODUCTS_DIR_NAME/$studyName/lists").also { it.mkdirs() }
    val file = dir.resolve("$studyName-list-unique-words.txt")
    file.writer().use { writer ->
        for (name in names) writer.appendLine(name)
    }
}

/** Writes the one-time-words index (alphabetical + by appearance) for [studyData] as a LaTeX PDF. */
fun writeOneTimeWordsIndex(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    oneTimeWordRanges: List<IntRange> = oneTimeWords(studyData),
) {
    val simpleName = studyData.studySet.simpleName
    val set = studyData.studySet
    val indexEntriesByWord: List<WordIndexEntry> = oneTimeWordsIndexByWord(studyData, oneTimeWordRanges)
    val indexEntriesByVerse: List<VerseIndexEntry> = oneTimeWordsIndexByVerse(studyData, oneTimeWordRanges)
    val dir = productsDir.resolve(simpleName, "indices").also { Files.createDirectories(it) }
    val file = dir.resolve("$simpleName-index-one-time-words.tex")
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(
            writer, "${set.name} One-Time Words",
            docPreface = "The following words only appear one time in ${set.longName}.",
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
    file.latexToPdf(keepTexFiles = true)
}

/** Writes the per-chapter one-time-words homework sheet as a LaTeX PDF. */
fun writeOneTimeWordsHomework(
    studyData: StudyData,
    productsDir: Path = defaultProductsPath,
    oneTimeWordRanges: List<IntRange> = oneTimeWords(studyData),
) {
    val simpleName = studyData.studySet.simpleName
    val set = studyData.studySet
    val indexEntriesByChapter: List<ChapterIndexEntry> = oneTimeWordsIndexByChapter(studyData, oneTimeWordRanges)
    val dir = productsDir.resolve(simpleName, "homework").also { Files.createDirectories(it) }
    val file: Path = dir.resolve("$simpleName-homework-one-time-words.tex")
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(
            writer, "${set.name} One-Time Words Homework",
            docPreface = "The following words only appear one time in ${set.longName}.",
            allowParagraphBreaks = false,
        ) {
            writeIndex(
                writer, indexEntriesByChapter.sortedBy { it.key }, "In Order of Appearance",
                columns = 4, formatKey = studyData.chapterRefFormat
            )
        }
    }
    file.latexToPdf(keepTexFiles = true)
}

private fun oneTimeWordsIndexByWord(studyData: StudyData, ranges: List<IntRange>): List<WordIndexEntry> = ranges.map {
    val ref: VerseRef = studyData.verseEnclosing(it) ?: throw Exception()
    IndexEntry(studyData.excerpt(it).excerptText, listOf(ref))
}

private fun oneTimeWordsIndexByVerse(studyData: StudyData, ranges: List<IntRange>): List<VerseIndexEntry> = ranges
    .groupBy {
        studyData.verseEnclosing(it) ?: throw Exception()
    }.map { (ref, wordRanges) ->
        IndexEntry(ref, wordRanges.map { studyData.excerpt(it).excerptText })
    }

private fun oneTimeWordsIndexByChapter(studyData: StudyData, ranges: List<IntRange>): List<ChapterIndexEntry> = ranges
    .groupBy {
        studyData.chapterEnclosing(it) ?: throw Exception()
    }.map { (ref, wordRanges) ->
        IndexEntry(ref, wordRanges.map { studyData.excerpt(it).excerptText })
    }
