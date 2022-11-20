package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.rangeLabel
import net.markdrew.chupacabra.core.intersect
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.math.roundToInt


fun main(args: Array<String>) {
    println(BANNER)
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookData = BookData.readData(book)

//    printReverseHeadings(bookData)
//
//    // write out cumulative sets (i.e., chapters 1 to N)
//    val newHeadingsPerSet = 10
//    val idealNumberOfSets = bookData.headingCharRanges.size / newHeadingsPerSet.toFloat()
//    //println("idealNumberOfSets = $idealNumberOfSets")
//    val newChaptersPerSet = (bookData.chapters.size / idealNumberOfSets).roundToInt()
//    //println("newChaptersPerSet = $newChaptersPerSet")
//    for (chunk in bookData.chapterRange.chunked(newChaptersPerSet)) {
////        println(1..chunk.last())
//        printHeadings(bookData, 1..chunk.last())
//    }
////    println()

    // write out exclusive sets (i.e., chapters N to M)
//    val nChunks = 4
//    val chunkSize = bookData.chapterRange.last / nChunks
//    for (chunk in bookData.chapterRange.chunked(chunkSize)) {
////        println(chunk.first()..chunk.last())
//        printHeadings(bookData, chunk.first()..chunk.last())
//    }

    printHeadings(bookData, 9..12)
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
    // NOTE: Headings may not be unique within a book! (e.g., "Jesus Heals Many" in Mat 8 and 15)
    val cramHeadingsPath = makePath(bookData, "cram-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        bookData.headings(chapterRange).groupBy {
            it.title
        }.forEach { (headingTitle, headingList) ->
            // format and write out the results
            val answerString = headingList.joinToString("<br/>OR<br/>") { heading ->
                heading.chapterRange.toList().joinToString(" & ")
            }
            writer.write(headingTitle, answerString)
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
                val headings: List<String> = bookData.headingCharRanges.valuesIntersectedBy(chapterRange)
                writer.write("${bookData.book.fullName} $chapter", headings.joinToString("<br/>"))
            }
    }
    println("Wrote data to: $cramHeadingsPath")
}
