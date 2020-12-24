package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.readHeadingsIndex
import net.markdrew.biblebowl.analysis.readVersesIndex
import net.markdrew.biblebowl.analysis.verseToWordList
import net.markdrew.biblebowl.cram.Card
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.BookChapterVerse
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.ReferencedWord
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
private fun oneSectionWordCards(
    sectionType: String, referencedVerses: List<ReferencedVerse>, refToSection: (BookChapterVerse) -> String
): List<Card> {
    val wordSections: List<Pair<ReferencedWord, String>> = referencedVerses.flatMap { verseToWordList(it) }
        .map { it to refToSection(it.reference) }
    val wordToSectionsList: Map<String, List<String>> = wordSections.groupBy(
        { (refWord, _) -> refWord.word.toLowerCase() },
        { (_, section) -> section }
    )
    val filteredWordToSections: Map<String, List<String>> = wordToSectionsList
        .filterValues { sectionList -> sectionList.size > 1 } // remove one-time words
        .filterValues { sectionList -> sectionList.distinct().count() == 1 } // only keep entries all in same section
    return filteredWordToSections.entries.sortedBy { (_, sections) -> sections.size }.map { (word, sections) ->
        println("""%20s occurs %2d times in $sectionType:  "%s""""
            .format(""""$word"""", sections.size, sections.first())
        )
        Card(word, sectionType.toUpperCase() + "<br/>" + sections.first() + "<br/>(${sections.size} times)")
    }
}

fun main(args: Array<String>) {
    val bookName = args.getOrNull(0) ?: "rev"
    val referencedVerses: List<ReferencedVerse> = readVersesIndex(bookName)

    // build one-chapter words
    val oneChapterWordCards: List<Card> = oneSectionWordCards("chapter", referencedVerses) { it.chapter.toString() }

    // build one-heading words
    val headingsIndex: Map<BookChapterVerse, String> = readHeadingsIndex(bookName)
    val oneHeadingWordCards: List<Card> = oneSectionWordCards("heading", referencedVerses) { headingsIndex.getValue(it) }

    // combine the two sets of cards
    val fewTimeWordCards: Collection<Card> = distinctCards(oneChapterWordCards, oneHeadingWordCards)
        .sortedBy { it.front }

    // write 'em out
    val outFile = File("output/$bookName", "$bookName-cram-few-time-words.tsv")
    CardWriter.writeCards(fewTimeWordCards, outFile)
    println("Wrote few-time words cards to $outFile")
}
