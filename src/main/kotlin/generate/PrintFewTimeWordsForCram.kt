package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.readHeadingsIndex
import net.markdrew.biblebowl.analysis.readVersesIndex
import net.markdrew.biblebowl.analysis.verseToWordList
import net.markdrew.biblebowl.cram.Card
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.BookChapterVerse
import net.markdrew.biblebowl.model.ReferencedVerse
import java.io.File

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
private fun oneSectionWordCards(sectionType: String,
                                wordIndex: Map<String, List<BookChapterVerse>>,
                                refToSection: (BookChapterVerse) -> String): List<Card> =
    wordIndex
        .filterValues { refs -> refs.size > 1 } // remove one-time words
        .filterValues { refs -> refs.map(refToSection).distinct().count() == 1 } // only entries all in same section
        .map { (word, refs) ->
            val section: String = refToSection(refs.first())
            println("""%20s occurs %2d times in $sectionType:  %s""".format(""""$word"""", refs.size, section))
            Card(word, listOf(
                sectionType.toUpperCase(),
                section,
                "(${refs.size} times: " + refs.joinToString { "${it.chapter}:${it.verse}" } + ")"
            ).joinToString("<br/>"))
        }

fun main(args: Array<String>) {
    val bookName = args.getOrNull(0) ?: "rev"
    val referencedVerses: List<ReferencedVerse> = readVersesIndex(bookName)
    val wordIndex = referencedVerses.flatMap { verseToWordList(it) }.groupBy(
        { (_, word) -> word.toLowerCase() },
        { (ref, _) -> ref }
    )

    // build one-chapter words
    val oneChapterWordCards: List<Card> = oneSectionWordCards("chapter", wordIndex) { it.chapter.toString() }

    // build one-heading words
    val headingsIndex: Map<BookChapterVerse, String> = readHeadingsIndex(bookName)
    val oneHeadingWordCards: List<Card> = oneSectionWordCards("heading", wordIndex) { headingsIndex.getValue(it) }

    // combine the two sets of cards
    val fewTimeWordCards: Collection<Card> = distinctCards(oneChapterWordCards, oneHeadingWordCards)
        .sortedBy { it.front }

    // write 'em out
    val outFile = File("output/$bookName", "$bookName-cram-few-time-words.tsv")
    CardWriter.writeCards(fewTimeWordCards, outFile)
    println("Wrote few-time words cards to $outFile")
}
