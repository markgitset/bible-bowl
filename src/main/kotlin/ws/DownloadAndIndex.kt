package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths

fun main(vararg args: String) {
    val bookName: String = args.getOrNull(0) ?: "rev"
    val book: Book = Book.valueOf(bookName.toUpperCase())
    val client = EsvClient(includePassageReferences = false,
        includeFootnotes = false,
        includeFootnoteBody = false,
        includeShortCopyright = false,
        includePassageHorizontalLines = false)
    val indexer = BookIndexer(book)
    val bookData: BookData = indexer.indexBook(client.bookByChapters(book))
//    val text = indexer.text
//    println(text)
//    println()
//    indexer.verses.entries.forEach { (range, verseRef) -> println("$verseRef - ${text.substring(range)}") }
//    println()
//    indexer.paragraphs.forEach { range -> println("'${text.substring(range)}'") }
//    println()
//    indexer.headings.forEach { (range, heading) -> println("\n$heading:\n'${text.substring(range)}'") }
//    println()
//    indexer.chapters.forEach { (range, chapter) -> println("\nChapter $chapter:\n'${text.substring(range)}'") }
//    println()
    bookData.writeData(Paths.get("output"))
}

