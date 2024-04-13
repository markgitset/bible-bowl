package net.markdrew.biblebowl.flashcards.anki

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.FRACTIONS
import net.markdrew.biblebowl.analysis.MULTI_NUMBER_PATTERN
import net.markdrew.biblebowl.analysis.NUMBER_REGEX
import net.markdrew.biblebowl.analysis.NUMERAL_PATTERN
import net.markdrew.biblebowl.analysis.ORDINALS
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.flashcards.cram.highlightVerse
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.encloses
import java.io.File
import java.io.PrintWriter
import java.nio.file.Paths
import kotlin.text.RegexOption.IGNORE_CASE

//fun highlightVerse(target: String, verse: String): String =
//    verse.replace(Regex("""\b$target\b"""), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.firstOrNull())
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    val oneTimeWordRanges: List<IntRange> = oneTimeWords(studyData)
    val oneTimeWords: List<String> = oneTimeWordRanges.map { studyData.text.substring(it) }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges)
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "possessives") { it.any { c -> c in "'’" } }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "fractions") {
        it.matches(FRACTIONS.toRegex(IGNORE_CASE))
    }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "ordinals") { it.matches(ORDINALS.toRegex(IGNORE_CASE)) }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "non-numerals") { it.matches(MULTI_NUMBER_PATTERN.toRegex(IGNORE_CASE)) }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "sig-digs-one") {
        it.matches(NUMERAL_PATTERN.toRegex(IGNORE_CASE)) && it.count { c -> c in "123456789" } == 1
    }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "sig-digs-two") {
        it.matches(NUMERAL_PATTERN.toRegex(IGNORE_CASE)) && it.count { c -> c in "123456789" } == 2
    }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "sig-digs-three") {
        it.matches(NUMERAL_PATTERN.toRegex(IGNORE_CASE)) && it.count { c -> c in "123456789" } == 3
    }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "sig-digs-more") {
        it.matches(NUMERAL_PATTERN.toRegex(IGNORE_CASE)) && it.count { c -> c in "123456789" } > 3
    }
    val names: Set<String> = findNames(studyData).map { it.excerptText }.toSet()
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "names") { it in names }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "all-numbers") { it.matches(NUMBER_REGEX) }
    writeAnkiOneTimeWords(studyData, oneTimeWordRanges, predicateName = "other") {
        !it.matches(NUMBER_REGEX) && it !in names && it.none { c -> c in "'’" }
    }
//    removeAndDescribe("Everything else", words) { true }
    println("%,6d Total one time words".format(oneTimeWords.size))

//    val stepByNChapters = 10
//    val oneTimeWords: List<IntRange> = oneTimeWords(studyData)
//    val chapterChunks: List<ChapterRange> = studyData.chapters.values.chunked(stepByNChapters) { it.first()..it.last() }
//    for (chapterRange in chapterChunks) {
//        writeCramOneTimeWords(studyData, oneTimeWords, chapterRange)
//    }
}

fun removeAndDescribe(label: String, words: List<String>, samples: Int = 20, predicate: (String) -> Boolean): List<String> {
    val (removed, kept) = words.partition(predicate)
    println("%,6d $label".format(removed.size))

    removed.chunked(10).forEach {
        print("          ")
        println(it.joinToString("") { word -> "%-15s".format(word) })
    }
    return kept
}

fun writeAnkiOneTimeWords(
    studyData: StudyData,
    oneTimeWords: List<IntRange>,
    chapterRange: ChapterRange = studyData.chapterRange,
    predicateName: String = "",
    predicate: (String) -> Boolean = { true },
) {
    val simpleName = studyData.studySet.simpleName
    val scopeString = studyData.chapterRangeOrEmpty("-chapters-", chapterRange)
    val predicateString = if (predicateName.isNotEmpty()) "-$predicateName" else ""
    val uniqueWordsFile = File(
        "$PRODUCTS_DIR/$simpleName/anki",
        "$simpleName-anki-one-words$scopeString$predicateString.tsv"
    ).also { it.parentFile.mkdirs() }
    uniqueWordsFile.printWriter().use { writer ->
        writeCards(writer, oneTimeWords, studyData, chapterRange, predicate)
    }
    println("Wrote $uniqueWordsFile")
}

private fun writeCards(
    writer: PrintWriter,
    oneTimeWords: List<IntRange>,
    studyData: StudyData,
    chapterRange: ChapterRange?,
    predicate: (String) -> Boolean = { true }
) {
    val words: List<IntRange> =
        if (chapterRange == null) oneTimeWords
        else oneTimeWords.filter { studyData.charRangeFromChapterRange(chapterRange).encloses(it) }
    words.forEach { wordRange ->
        val word: String = studyData.text.substring(wordRange)
        if (predicate(word)) {
            val (verseRange, verseRef) = studyData.verses.entryEnclosing(wordRange) ?: throw Exception()
            val verseText: String = studyData.text.substring(verseRange)
            val highlightedVerse = highlightVerse(word, verseText.normalizeWS())
            val heading = studyData.headingCharRanges.valueEnclosing(wordRange)
            val verseRefString = verseRef.format(FULL_BOOK_FORMAT)
            writer.println(listOf(word, highlightedVerse, heading, verseRefString).joinToString("\t"))
        }
    }
}
