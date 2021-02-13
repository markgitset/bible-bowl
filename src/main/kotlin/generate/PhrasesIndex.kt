package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.*
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Paths

fun main() {
    writePhrasesIndex(Book.REV, maxPhraseLength = 23)
}

private fun formatVerseRefWithCount(ref: WithCount<VerseRef>): String =
    ref.item.toChapterAndVerse() + (if (ref.count > 1) "(\\times ${ref.count})" else "")

private fun writePhrasesIndex(book: Book, maxPhraseLength: Int = 50) {
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)
    val indexEntries: List<WordIndexEntryC> = buildPhrasesIndex(bookData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
            phraseIndexEntry.key.joinToString(" "),
            phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
        ) }
    val file = File("output/$bookName", "$bookName-index-phrases.tex")
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
    println("Wrote $file")
}

