package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.verseToWordList
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.cram.normalizeWhitespace
import net.markdrew.biblebowl.model.*
import net.markdrew.biblebowl.model.BookData
import java.io.File
import java.nio.file.Paths

fun highlightVerse(target: String, verse: String): String =
    verse.replace(Regex.fromLiteral(target), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    val referencedVerses: List<ReferencedVerse> = bookData.verseList()

    val referencedWords: List<ReferencedWord> = referencedVerses.flatMap { verseToWordList(it) }
    val wordsIndex: Map<String, List<ReferencedWord>> = referencedWords.groupBy({ it.word.toLowerCase() }, { it })

    val uniqueWordsFile = File("output/$bookName", "$bookName-cram-one-time-words.tsv")
    CardWriter(uniqueWordsFile).use { writer ->
        wordsIndex.values.filter { it.size == 1 }.flatten().forEach { (reference, word) ->
            val verseRange = bookData.verseIndex.getValue(reference.toRefNum())
            val verseText: String = bookData.text.substring(verseRange)
            val highlightedVerse = highlightVerse(word, normalizeWhitespace(verseText))
            val heading = bookData.headings.valueEnclosing(verseRange)
            val answer = "$heading<br/><b>${reference.toFullString()}</b><br/>$highlightedVerse"
            writer.write(word, answer, highlightedVerse)
        }
    }

}
