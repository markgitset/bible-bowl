package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths

fun main(vararg args: String) {
    val bookName: String = args.getOrNull(0) ?: "gen"
    val book: Book = Book.valueOf(bookName.toUpperCase())
    val client = EsvClient(
        includePassageReferences = false,
        includeShortCopyright = false,
        includePassageHorizontalLines = false
    )
    val indexer = BookIndexer(book)
    val bookData: BookData = indexer.indexBook(client.bookByChapters(book))
    bookData.writeData(Paths.get("data"))
}

