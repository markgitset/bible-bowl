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

    printReverseHeadings(bookData)

    // compute all the heading-to-chapters mappings for the whole book
    val headingsToChapters: Map<String, List<List<Int>>> = computeHeadingsToChapters(bookData)

    // write out cumulative sets (i.e., chapters 1 to N)
    val newHeadingsPerSet = 10
    val idealNumberOfSets = bookData.headings.size / newHeadingsPerSet.toFloat()
    //println("idealNumberOfSets = $idealNumberOfSets")
    val newChaptersPerSet = (bookData.chapters.size / idealNumberOfSets).roundToInt()
    //println("newChaptersPerSet = $newChaptersPerSet")
    for (chunk in bookData.chapterRange.chunked(newChaptersPerSet)) {
//        println(1..chunk.last())
        printHeadings(bookData, headingsToChapters, 1..chunk.last())
    }
//    println()

    // write out exclusive sets (i.e., chapters N to M)
    val nChunks = 4
    val chunkSize = bookData.chapterRange.last / nChunks
    for (chunk in bookData.chapterRange.chunked(chunkSize)) {
//        println(chunk.first()..chunk.last())
        printHeadings(bookData, headingsToChapters, chunk.first()..chunk.last())
    }
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

private fun printHeadings(
    bookData: BookData,
    headingsToChapters: Map<String, List<List<Int>>>,
    chapterRange: IntRange = bookData.chapterRange
) {
    // NOTE: Headings may not be unique within a book! (e.g., "Jesus Heals Many" in Mat 8 and 15)
    val cramHeadingsPath = makePath(bookData, "cram-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        headingsToChapters.mapValues { (_, chapterLists: List<List<Int>>) ->
            // remove any chapterLists that don't start in the requested chapter range
            chapterLists.filter { it.first() in chapterRange }
        }.filterValues { chapterLists: List<List<Int>> ->
            // remove any heading entries whose chapterLists are now empty
            chapterLists.isNotEmpty()
        }.forEach { (heading, chapterLists) ->
            // format and write out the results
            val answerString = chapterLists.joinToString("<br/>OR<br/>") { it.joinToString(" & ") }
            writer.write(heading, answerString)
        }
    }
    println("Wrote data to: $cramHeadingsPath")
}

/**
 * Compute a map of headings to a list of lists of chapter numbers.
 *
 * Outer list contains an entry for each occurrence of a heading, and the inner list is for headings that span more
 * than one chapter.  Most headings will only be in one chapter and will look something like: listOf(listOf(3)).
 *
 * A heading that spans chapters 3 and 4 will look like: listOf(listOf(3, 4)).
 *
 * In rare cases, a heading appears twice within a book.  For example, "Jesus Heals Many" is in Mat 8 and 15, and would
 * look like: listOf(listOf(8), listOf(15)).
 */
private fun computeHeadingsToChapters(bookData: BookData): Map<String, List<List<Int>>> =
    bookData.headings
        .map { (headingRange, heading) ->
            val chapters: List<Int> = bookData.chapters.valuesIntersectedBy(headingRange)
            heading to chapters
        }.fold(mutableMapOf<String, List<List<Int>>>()) { map, headingToChapterPair ->
            map.apply {
                merge(headingToChapterPair.first, listOf(headingToChapterPair.second)) { oldVal, newVal ->
                    oldVal + newVal
                }
            }
        }

private fun printReverseHeadings(bookData: BookData, chapterRange: IntRange = bookData.chapterRange) {
    val cramHeadingsPath = makePath(bookData, "cram-reverse-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        bookData.chapters
            .filterValues { it in chapterRange }
            .map { (chapterRange, chapter) ->
                val headings: List<String> = bookData.headings.valuesIntersectedBy(chapterRange)
                writer.write("${bookData.book.fullName} $chapter", headings.joinToString("<br/>"))
            }
    }
    println("Wrote data to: $cramHeadingsPath")
}
