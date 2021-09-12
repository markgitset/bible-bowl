package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Path
import java.nio.file.Paths


fun main(args: Array<String>) {
    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(Paths.get(DATA_DIR), book)

    val cramHeadingsPath = Paths.get("$PRODUCTS_DIR/$bookName").resolve("$bookName-cram-headings.tsv")

    printHeadings(cramHeadingsPath, bookData)
    //printHeadings(PrintWriter(System.out), headingIndex)
    println("Wrote data to: $cramHeadingsPath")

    val cramReverseHeadingsPath = Paths.get("$PRODUCTS_DIR/$bookName").resolve("$bookName-cram-reverse-headings.tsv")
    printReverseHeadings(cramReverseHeadingsPath, bookData)
    //printHeadings(PrintWriter(System.out), headingIndex)
    println("Wrote data to: $cramReverseHeadingsPath")
}

private fun printHeadings(cramHeadingsPath: Path, bookData: BookData) {
    CardWriter(cramHeadingsPath).use { writer ->
        bookData.headings.map { (headingRange, heading) ->
            val chapters: List<Int> = bookData.chapters.valuesIntersectedBy(headingRange)
            writer.write(heading, chapters.joinToString(" & "))
        }
    }
}

private fun printReverseHeadings(cramHeadingsPath: Path, bookData: BookData) {
    CardWriter(cramHeadingsPath).use { writer ->
        bookData.chapters.map { (chapterRange, chapter) ->
            val headings: List<String> = bookData.headings.valuesIntersectedBy(chapterRange)
            writer.write("Chapter $chapter", headings.joinToString("<br/>"))
        }
    }
}
