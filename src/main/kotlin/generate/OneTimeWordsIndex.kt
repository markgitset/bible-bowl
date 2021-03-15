package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Paths

fun main() {
    writeOneTimeWordsIndex(Book.GEN)
}

private fun writeOneTimeWordsIndex(book: Book) {
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)
    val indexEntries: List<WordIndexEntry> = oneTimeWordsIndex(bookData)
    val file = File("output/$bookName", "$bookName-index-one-time-words.tex")
    file.writer().use { writer ->
        writeDoc(writer, "${book.fullName} One-Time Words",
            docPreface = "The following words only appear one time in the whole book of ${book.fullName}.") {

            writeIndex(writer, indexEntries.sortedBy { it.key.toLowerCase() }, "Alphabetical",
                       columns = 4, formatValue = VerseRef::toChapterAndVerse)
            writer.appendLine("""\newpage""")
            writeIndex(writer, indexEntries.sortedBy { it.values.single() }, "In Order of Appearance",
                       columns = 4, formatValue = VerseRef::toChapterAndVerse)
        }
    }
    println("Wrote $file")
}

private fun oneTimeWordsIndex(bookData: BookData): List<WordIndexEntry> = oneTimeWords(bookData).map {
    val ref: VerseRef = bookData.verseEnclosing(it) ?: throw Exception()
    IndexEntry(bookData.excerpt(it).excerptText, listOf(ref))
}

