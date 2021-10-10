package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.rangeLabel
import net.markdrew.chupacabra.core.intersect
import java.nio.file.Path
import java.nio.file.Paths


fun main(args: Array<String>) {
    println(BANNER)
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookData = BookData.readData(book)

    printHeadings(bookData)
    printReverseHeadings(bookData)

//    for (i in bookData.chapterRange) {
//        printHeadings(bookData, 1..i)
//        printReverseHeadings(bookData, 1..i)
//    }
}

private fun makePath(bookData: BookData, fileType: String, chapterRange: IntRange): Path {
    val bookName = bookData.book.name.lowercase()
    val actualChapterRange = chapterRange.intersect(bookData.chapterRange)
    val suffix =
        if (actualChapterRange == bookData.chapterRange) ""
        else rangeLabel("-chapter", actualChapterRange)
    val dir: Path = Paths.get("$PRODUCTS_DIR/$bookName/cram").also { it.toFile().mkdirs() }
    return dir.resolve("$bookName-$fileType$suffix.tsv")
}

private fun printHeadings(bookData: BookData, chapterRange: IntRange = bookData.chapterRange) {
    val cramHeadingsPath = makePath(bookData, "cram-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        bookData.headings
            .intersectedBy(bookData.charRangeFromChapterRange(chapterRange))
            .map { (headingRange, heading) ->
                val chapters: List<Int> = bookData.chapters.valuesIntersectedBy(headingRange)
                writer.write(heading, chapters.joinToString(" & "))
            }
    }
    println("Wrote data to: $cramHeadingsPath")
}

private fun printReverseHeadings(bookData: BookData, chapterRange: IntRange = bookData.chapterRange) {
    val cramHeadingsPath = makePath(bookData, "cram-reverse-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        bookData.chapters
            .filterValues { it in chapterRange }
            .map { (chapterRange, chapter) ->
                val headings: List<String> = bookData.headings.valuesIntersectedBy(chapterRange)
                writer.write("Chapter $chapter", headings.joinToString("<br/>"))
            }
    }
    println("Wrote data to: $cramHeadingsPath")
}
