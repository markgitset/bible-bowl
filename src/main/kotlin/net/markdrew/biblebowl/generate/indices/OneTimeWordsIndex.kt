package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.VerseIndexEntry
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Paths

fun main() {
    writeOneTimeWordsIndex(Book.DEFAULT)
}

private fun writeOneTimeWordsIndex(book: Book) {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))
    val indexEntriesByWord: List<WordIndexEntry> = oneTimeWordsIndexByWord(bookData)
    val indexEntriesByVerse: List<VerseIndexEntry> = oneTimeWordsIndexByVerse(bookData)
    val file = File("$PRODUCTS_DIR/$bookName/indices", "$bookName-index-one-time-words.tex")
    file.writer().use { writer ->
        writeDoc(writer, "${book.fullName} One-Time Words",
            docPreface = "The following words only appear one time in the whole book of ${book.fullName}.") {

            writeIndex(writer, indexEntriesByWord.sortedBy { it.key.lowercase() }, "Alphabetical",
                       columns = 4, formatValue = VerseRef::toChapterAndVerse)
            writer.appendLine("""\newpage""")
            writeIndex(writer, indexEntriesByVerse.sortedBy { it.key }, "In Order of Appearance",
                       columns = 4, formatKey = VerseRef::toChapterAndVerse)
        }
    }
    file.toPdf()
}

private fun oneTimeWordsIndexByWord(bookData: BookData): List<WordIndexEntry> = oneTimeWords(bookData).map {
    val ref: VerseRef = bookData.verseEnclosing(it) ?: throw Exception()
    IndexEntry(bookData.excerpt(it).excerptText, listOf(ref))
}

private fun oneTimeWordsIndexByVerse(bookData: BookData): List<VerseIndexEntry> = oneTimeWords(bookData)
    .groupBy {
        bookData.verseEnclosing(it) ?: throw Exception()
    }.map { (ref, wordRanges) ->
        IndexEntry(ref, wordRanges.map { bookData.excerpt(it).excerptText })
    }
