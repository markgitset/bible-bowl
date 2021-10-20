package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.encloses
import java.io.File
import java.nio.file.Paths

fun highlightVerse(target: String, verse: String): String =
    verse.replace(Regex("""\b$target\b"""), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    println(BANNER)
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val stepByNChapters = 10
    val oneTimeWords: List<IntRange> = oneTimeWords(bookData)
    for (lastChapter in bookData.chapterRange) {
        if (lastChapter % stepByNChapters == 0 || lastChapter == bookData.chapterRange.last) {
            writeFile(bookData, oneTimeWords, lastChapter)
        }
    }
}

private fun writeFile(
    bookData: BookData,
    oneTimeWords: List<IntRange>,
    lastChapter: Int
) {
    val bookName = bookData.book.name.lowercase()
    val scopeString = bookData.maxChapterOrEmpty("-chapters-1-", lastChapter)
    val uniqueWordsFile = File("$PRODUCTS_DIR/$bookName/cram", "$bookName-cram-one-words$scopeString.tsv")
    CardWriter(uniqueWordsFile).use { writer ->
        writeCards(writer, oneTimeWords, bookData, throughChapter = lastChapter)
    }
    println("Wrote $uniqueWordsFile")
}

private fun writeCards(
    writer: CardWriter,
    oneTimeWords: List<IntRange>,
    bookData: BookData,
    throughChapter: Int?
) {
    val words: List<IntRange> =
        if (throughChapter == null) oneTimeWords
        else oneTimeWords.filter { bookData.charRangeThroughChapter(throughChapter).encloses(it) }
    words.forEach { wordRange ->
        val (verseRange, verseRefNum) = bookData.verses.entryEnclosing(wordRange) ?: throw Exception()
        val verseText: String = bookData.text.substring(verseRange)
        val word = bookData.text.substring(wordRange)
        val highlightedVerse = highlightVerse(word, verseText.normalizeWS())
        val heading = bookData.headings.valueEnclosing(wordRange)
        val verseRefString = verseRefNum.toVerseRef().toFullString()
        val answer = "$heading<br/><b>$verseRefString</b><br/>$highlightedVerse"
        writer.write(word, answer, hint = highlightedVerse)
    }
}
