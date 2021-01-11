package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.cram.Card
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.*
import java.io.File
import java.nio.file.Paths

/**
 * Combines two [Card] lists such that no front values are duplicated--if duplicates are found, the last one
 * seen is kept.
 */
fun distinctCards(vararg cardLists: List<Card>): Collection<Card> = cardLists.asSequence().flatMap { cardList ->
    cardList.asSequence().map { it.front to it }
}.toMap().values

/**
 * Build list of one-section word cards (where the sections could be chapters or headings)
 */
private fun oneSectionWordCards(bookData: BookData,
                                rangeToSection: (IntRange) -> String): List<Card> =
    bookData.wordIndex
        .filterValues { ranges -> ranges.size > 1 } // remove one-time words
        .filterValues { ranges ->
            ranges.map(rangeToSection).distinct().count() == 1  // only entries all in same section
        }.map { (word, ranges) ->
            val section: String = rangeToSection(ranges.first())
            println("""%20s occurs %2d times in %s""".format(""""$word"""", ranges.size, section))
            val verseListString = ranges.joinToString {
                bookData.verses.valueEnclosing(it)?.toVerseRef()?.toChapterAndVerseString() ?: throw Exception()
            }
            val cardBack = listOf(section, "(${ranges.size} times: $verseListString)").joinToString("<br/>")
            Card(word, cardBack)
        }

fun main(args: Array<String>) {
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    // build one-chapter words
    val oneChapterWordCards: List<Card> = oneSectionWordCards(bookData) { range ->
        "Chapter " + (bookData.chapters.valueEnclosing(range)?.toString() ?: throw Exception())
    }

    // build one-heading words
    val oneHeadingWordCards: List<Card> = oneSectionWordCards(bookData) { range ->
        "Heading: " + (bookData.headings.valueEnclosing(range) ?: throw Exception())
    }

    // combine the two sets of cards
    val fewTimeWordCards: Collection<Card> = distinctCards(oneChapterWordCards, oneHeadingWordCards)
        .sortedBy { it.front }

    // write 'em out
    val outFile = File("output/$bookName", "$bookName-cram-few-time-words.tsv")
    CardWriter.writeCards(fewTimeWordCards, outFile)
    println("Wrote few-time words cards to $outFile")
}
