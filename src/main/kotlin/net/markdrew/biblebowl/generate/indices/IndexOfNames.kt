package net.markdrew.biblebowl.indices

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNamesIndex
import net.markdrew.biblebowl.generate.formatVerseRefWithCount
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.io.File
import java.nio.file.Paths

fun main() {
//    val revStopWords = setOf("he", "from", "his", "is", "you", "was", "will", "for", "with", "on", "in", "who", "i",
//                              "a", "to", "of", "and", "the")
    writeNamesIndex(Book.DEFAULT, STOP_WORDS)
}

private fun writeNamesIndex(book: Book, stopWords: Set<String>) {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))
    val indexEntries: List<WordIndexEntryC> = buildNamesIndex(bookData)
        .map { wordIndexEntry ->
            WordIndexEntryC(
                wordIndexEntry.key,
                wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) -> WithCount(verseRef, count) }
            )
        }
    val file = File("$PRODUCTS_DIR/$bookName/indices", "$bookName-index-names.tex")
    file.writer().use { writer ->
        writeDoc(writer, "${book.fullName} Names Index",
            docPreface = "The following is a complete index of all names in the whole book of ${book.fullName}"//, " +
                    //"""except for these:\\\\${stopWords.sorted().joinToString()}."""
            ) {

            val index: List<WordIndexEntryC> =
                indexEntries.filterNot { it.key in stopWords }.sortedBy { it.key.lowercase() }
            writeIndex(writer, index, columns = 3) { formatVerseRefWithCount(it) }

            writer.appendLine("""\newpage""")

            val freqs: List<IndexEntry<String, Int>> = indexEntries
                .map { IndexEntry(it.key, listOf(it.values.sumOf { withCount -> withCount.count })) }
                .filter { it.values.single() > 1 }
                .sortedWith(compareBy({ it.values.single() }, { it.key }))
            writeIndex(writer, freqs, "Names in ${book.fullName} in Order of Increasing Frequency",
                       indexPreface = "Each name here occurs in the book of ${book.fullName} " +
                               "the number of times shown next to it.  One-time names are omitted for brevity.",
                       columns = 5)
        }
    }
    file.toPdf()
}
