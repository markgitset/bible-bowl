package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.toVerseRef
import java.io.File
import java.nio.file.Paths

fun highlightVerse(target: String, verse: String): String =
    verse.replace(Regex.fromLiteral(target), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    val uniqueWordsFile = File("output/$bookName", "$bookName-cram-one-time-words.tsv")
    CardWriter(uniqueWordsFile).use { writer ->
        oneTimeWords(bookData)
            .forEach { wordRange ->
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

}
