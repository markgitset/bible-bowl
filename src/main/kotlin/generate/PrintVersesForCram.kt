package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    val cramFile = Paths.get("output/$bookName").resolve("$bookName-cram-verses.tsv")
    CardWriter(cramFile).use {
        bookData.verses.forEach { (range, verseRefNum) ->
            val verseText = bookData.text.substring(range).normalizeWS()
            it.write(verseText,
                "${bookData.headings.valueEnclosing(range)}<br/>${verseRefNum.toVerseRef().toFullString()}")
        }
    }
    println("Wrote data to: $cramFile")
}
