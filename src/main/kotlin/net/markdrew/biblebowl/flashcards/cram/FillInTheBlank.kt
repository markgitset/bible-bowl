package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.flashcards.Card
import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeSet

/**
 * A fill-in-the-blank flashcard built from a clue excerpt with one or more answer spans blanked out
 *
 * @param clue the clue excerpt (typically a single-verse sentence fragment)
 * @param answers the spans to blank out within [clue]; their text is also shown on the back
 * @param verseRef verse the clue is drawn from; printed on the back for confirmation
 */
data class FillInTheBlank(val clue: Excerpt, val answers: List<Excerpt>, val verseRef: VerseRef) {

    /** Renders this as a [Card] suitable for [CardWriter]. */
    fun toCramCard(): Card {
        val blankRanges = DisjointRangeSet(answers.map { it.excerptRange })
        val blankedClueString: String = clue.formatRanges(blankRanges, blankOut()).normalizeWS()
        val answersString =
            if (answers.map { it.excerptText.lowercase() }.distinct().count() == 1) answers.first().excerptText
            else answers.joinToString("<br/>") { it.excerptText }
        return Card(
            blankedClueString,
            "$answersString<br/>(${verseRef.format(FULL_BOOK_FORMAT)})"
        )
    }

    companion object {

    }
}
