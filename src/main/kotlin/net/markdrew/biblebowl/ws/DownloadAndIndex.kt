package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths

fun main(vararg args: String) {
    val bookName: String? = args.getOrNull(0)
    val book: Book = bookName?.let { Book.valueOf(it.uppercase()) } ?: Book.DEFAULT
    val client = EsvClient(
        includePassageReferences = false,
        includeShortCopyright = false,
        includePassageHorizontalLines = false,
        indentPoetryLines = INDENT_POETRY_LINES,
    )
    val indexer = BookIndexer(book)
    val bookData: BookData = indexer.indexBook(client.bookByChapters(book))
    bookData.writeData(Paths.get(DATA_DIR))
}

