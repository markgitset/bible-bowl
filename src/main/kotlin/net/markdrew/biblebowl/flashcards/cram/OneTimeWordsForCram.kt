package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.DelimitedExporter
import net.markdrew.biblebowl.flashcards.FlashcardGenerator
import net.markdrew.biblebowl.flashcards.HtmlStrategy
import net.markdrew.biblebowl.flashcards.WordFlashcard
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.encloses
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun highlightVerse(target: String, verse: String): String =
    verse.replace(Regex("""\b$target\b"""), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME))

    val oneTimeWords: List<IntRange> = oneTimeWords(studyData)
    writeCramOneTimeWords(studyData, oneTimeWords)
}

fun writeCramOneTimeWords(
    studyData: StudyData,
    oneTimeWords: List<IntRange>,
    productsDir: Path = defaultProductsPath,
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val simpleName = studyData.studySet.simpleName
    val scopeString = studyData.chapterRangeOrEmpty("-chapters-", chapterRange)
    val uniqueWordsFile = productsDir.resolve(simpleName, "cram", "$simpleName-cram-one-words$scopeString.tsv")
    
    val words: List<IntRange> = oneTimeWords.filter { studyData.charRangeFromChapterRange(chapterRange).encloses(it) }
    
    val flashcards = words.map { wordRange ->
        val (verseRange, verseRef) = studyData.verses.entryEnclosing(wordRange) ?: throw Exception()
        val verseText: String = studyData.text.substring(verseRange)
        val word = studyData.text.substring(wordRange)
        val highlightedVerse = highlightVerse(word, verseText.normalizeWS())
        val heading = studyData.headingCharRanges.valueEnclosing(wordRange)
        val verseRefString = verseRef.format(FULL_BOOK_FORMAT)
        WordFlashcard(word, highlightedVerse, verseRefString, heading)
    }

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter("\t"))
    val deck = generator.generateDeck(flashcards)
    Files.createDirectories(uniqueWordsFile.parent)
    Files.writeString(uniqueWordsFile, deck)
    
    println("Wrote $uniqueWordsFile")
}
