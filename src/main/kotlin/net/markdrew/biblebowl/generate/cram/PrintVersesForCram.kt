package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.toVerseRef
import java.nio.file.Paths


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val cramFile = Paths.get("$PRODUCTS_DIR/$bookName/cram").resolve("$bookName-cram-verses.tsv")
    CardWriter(cramFile).use {
        bookData.verses.forEach { (range, verseRefNum) ->
            val verseText = bookData.text.substring(range).normalizeWS()
            it.write(verseText,
                "${bookData.headings.valueEnclosing(range)}<br/>${verseRefNum.toVerseRef().toFullString()}")
        }
    }
    println("Wrote data to: $cramFile")
}