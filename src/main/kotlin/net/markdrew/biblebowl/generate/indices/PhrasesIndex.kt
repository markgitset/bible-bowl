package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNonLocalPhrasesIndex
import net.markdrew.biblebowl.analysis.buildPhrasesIndex
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Paths

fun main() {
    writePhrasesIndex(Book.DEFAULT, maxPhraseLength = 23)
    writeNonLocalPhrasesIndex(Book.DEFAULT, maxPhraseLength = 23)
}

fun formatVerseRefWithCount(ref: WithCount<VerseRef>): String =
    ref.item.toChapterAndVerse() + (if (ref.count > 1) """(\(\times\)${ref.count})""" else "")

private fun writePhrasesIndex(book: Book, maxPhraseLength: Int = 50) {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))
    val indexEntries: List<WordIndexEntryC> = buildPhrasesIndex(bookData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
            phraseIndexEntry.key.joinToString(" "),
            phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
        ) }
    val dir = File("$PRODUCTS_DIR/$bookName/indices").also { it.mkdirs() }
    val file = dir.resolve("$bookName-index-phrases.tex")
    file.writer().use { writer ->
        writeDoc(writer, "${book.fullName} Reoccurring Phrases",
            docPreface = "The following phrases appear at least twice in the book of ${book.fullName}.") {

            writeIndex(writer, indexEntries.sortedBy { it.key }, "Alphabetical",
                       columns = 2) { formatVerseRefWithCount(it) }
            writer.appendLine("""\newpage""")
            writeIndex(writer, indexEntries.sortedByDescending { it.values.size }, "In Order of Decreasing Frequency",
                       columns = 2) { formatVerseRefWithCount(it) }
        }
    }
    file.toPdf()
}

private fun writeNonLocalPhrasesIndex(book: Book, maxPhraseLength: Int = 50) {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))
    val indexEntries: List<WordIndexEntryC> = buildNonLocalPhrasesIndex(bookData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
            phraseIndexEntry.key.joinToString(" "),
            phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
        ) }
    val dir = File("$PRODUCTS_DIR/$bookName/indices").also { it.mkdirs() }
    val file = dir.resolve("$bookName-index-nonlocal-phrases.tex")
    file.writer().use { writer ->
        writeDoc(writer, "${book.fullName} Reoccurring Non-Local Phrases", docPreface = "The following phrases " +
                "appear in at least two different chapters in the book of ${book.fullName}."
        ) {

            writeIndex(writer, indexEntries.sortedBy { it.key }, "Alphabetical",
                       columns = 2) { formatVerseRefWithCount(it) }
            writer.appendLine("""\newpage""")
            writeIndex(writer, indexEntries.sortedByDescending { it.values.size }, "In Order of Decreasing Frequency",
                       columns = 2) { formatVerseRefWithCount(it) }
        }
    }
    file.toPdf()
}

