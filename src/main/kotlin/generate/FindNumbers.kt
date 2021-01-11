package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.cram.normalizeWhitespace
import net.markdrew.biblebowl.model.*
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    printMatches("""\d+(?:,\d\d\d)""", bookData)
    printMatches("""\d+(?:,\d\d\d)""", bookData)
//    val referencedVerses: List<ReferencedVerse> = bookData.verseList()
//
//    val referencedWords: List<ReferencedWord> = referencedVerses.flatMap { verseToWordList(it) }
//    val wordsIndex: Map<String, List<ReferencedWord>> = referencedWords.groupBy({ it.word.toLowerCase() }, { it })
//
//    val uniqueWordsFile = File("output/$bookName", "$bookName-cram-one-time-words.tsv")
//    CardWriter(uniqueWordsFile).use { writer ->
//        wordsIndex.values.filter { it.size == 1 }.flatten().forEach { (reference, word) ->
//            val verseRange = bookData.verseIndex.getValue(reference.toRefNum())
//            val verseText: String = bookData.text.substring(verseRange)
//            val highlightedVerse = highlightVerse(word, normalizeWhitespace(verseText))
//            val heading = bookData.headings.valueEnclosing(verseRange)
//            val answer = "$heading<br/><b>${reference.toFullString()}</b><br/>$highlightedVerse"
//            writer.write(word, answer, highlightedVerse)
//        }
//    }

}

private fun printMatches(pattern: String, bookData: BookData) {
    pattern.toRegex().findAll(bookData.text).forEach {
        val r = it.range
        val context: String = normalizeWhitespace(bookData.text.substring(r.first - 10..r.last + 10))
        println("%15s %15s    %s".format(r, it.value, context))
    }
}
