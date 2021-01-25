package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.toVerseRef
import java.nio.file.Paths

fun main(vararg args: String) {
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookData = BookData.readData(Paths.get("output"), book)
    fun verseOfPosition(position: Int): String =
        bookData.verses.valueContaining(position)?.toVerseRef()?.toChapterAndVerse().orEmpty()
    bookData.sentences.filter { bookData.verseEnclosing(it) == null }
        .forEach { println("${verseOfPosition(it.first)}..${verseOfPosition(it.last)}") }
}