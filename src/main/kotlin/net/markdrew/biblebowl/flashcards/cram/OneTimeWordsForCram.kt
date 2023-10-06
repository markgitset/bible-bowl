package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.encloses
import java.io.File
import java.nio.file.Paths

fun highlightVerse(target: String, verse: String): String =
    verse.replace(Regex("""\b$target\b"""), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    val oneTimeWords: List<IntRange> = oneTimeWords(studyData)
    writeCramOneTimeWords(studyData, oneTimeWords)

//    val stepByNChapters = 10
//    val oneTimeWords: List<IntRange> = oneTimeWords(studyData)
//    val chapterChunks: List<ChapterRange> = studyData.chapters.values.chunked(stepByNChapters) { it.first()..it.last() }
//    for (chapterRange in chapterChunks) {
//        writeCramOneTimeWords(studyData, oneTimeWords, chapterRange)
//    }
}

fun writeCramOneTimeWords(
    studyData: StudyData,
    oneTimeWords: List<IntRange>,
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val simpleName = studyData.studySet.simpleName
    val scopeString = studyData.chapterRangeOrEmpty("-chapters-", chapterRange)
    val uniqueWordsFile = File("$PRODUCTS_DIR/$simpleName/cram", "$simpleName-cram-one-words$scopeString.tsv")
    CardWriter(uniqueWordsFile).use { writer ->
        writeCards(writer, oneTimeWords, studyData, chapterRange)
    }
    println("Wrote $uniqueWordsFile")
}

private fun writeCards(
    writer: CardWriter,
    oneTimeWords: List<IntRange>,
    studyData: StudyData,
    chapterRange: ChapterRange?
) {
    val words: List<IntRange> =
        if (chapterRange == null) oneTimeWords
        else oneTimeWords.filter { studyData.charRangeFromChapterRange(chapterRange).encloses(it) }
    words.forEach { wordRange ->
        val (verseRange, verseRef) = studyData.verses.entryEnclosing(wordRange) ?: throw Exception()
        val verseText: String = studyData.text.substring(verseRange)
        val word = studyData.text.substring(wordRange)
        val highlightedVerse = highlightVerse(word, verseText.normalizeWS())
        val heading = studyData.headingCharRanges.valueEnclosing(wordRange)
        val verseRefString = verseRef.format(FULL_BOOK_FORMAT)
        val answer = "$heading<br/><b>$verseRefString</b><br/>$highlightedVerse"
        writer.write(word, answer, hint = highlightedVerse)
    }
}
