package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.printNameFrequencies
import net.markdrew.biblebowl.analysis.printNameMatches
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.chupacabra.core.encloses
import java.nio.file.Paths

fun main(args: Array<String>) {

    println(BANNER)
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val nameExcerpts: Sequence<Excerpt> = findNames(bookData, "god", "jesus", "christ")
    printNameFrequencies(nameExcerpts)
    printNameMatches(nameExcerpts, bookData)

    val stepByNChapters = 10
    for (lastChapter in 1..bookData.chapterRange.endInclusive.chapter) {
        if (lastChapter % stepByNChapters == 0 || lastChapter == bookData.chapterRange.endInclusive.chapter) {
            writeFile(bookData, nameExcerpts, lastChapter)
        }
    }
//
//    val cramNameBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName/cram").resolve("$bookName-cram-name-blanks.tsv")
//    CardWriter(cramNameBlanksPath).use {
//        it.write(toCards(nameExcerpts, bookData))
//    }

}

private fun writeFile(
    bookData: BookData,
    nameExcerpts: Sequence<Excerpt>,
    lastChapter: Int,
) {
    val bookName = bookData.book.name.lowercase()
    val scopeString = bookData.maxChapterOrEmpty("-chapters-1-", lastChapter)
    val cramNameBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName/cram")
        .resolve("$bookName-cram-name-blanks$scopeString.tsv")
    val validCharRange: IntRange = bookData.charRangeThroughChapter(lastChapter)
    CardWriter(cramNameBlanksPath).use {
        it.write(toCards(nameExcerpts.filter { validCharRange.encloses(it.excerptRange) }, bookData))
    }
    println("Wrote $cramNameBlanksPath")
}

private fun toCards(nameExcerpts: Sequence<Excerpt>, bookData: BookData): List<Card> =
    nameExcerpts.groupBy { excerpt ->
        bookData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception()
    }.map { (sentRange, nameExcerpts) ->
        FillInTheBlank(
            sentRange,
            nameExcerpts,
            bookData.verseEnclosing(sentRange.excerptRange) ?: throw Exception()
        ).toCramCard()
    }
