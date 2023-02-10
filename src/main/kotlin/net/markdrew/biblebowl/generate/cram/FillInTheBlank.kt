package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeSet

data class FillInTheBlank(val clue: Excerpt, val answers: List<Excerpt>, val verseRef: VerseRef) {

    fun toCramCard(): Card {
        val blankRanges = DisjointRangeSet(answers.map { it.excerptRange })
        val blankedClueString: String = clue.formatRanges(blankRanges, blankOut()).normalizeWS()
        val answersString =
            if (answers.map { it.excerptText.lowercase() }.distinct().count() == 1) answers.first().excerptText
            else answers.joinToString("<br/>") { it.excerptText }
        return Card(
            blankedClueString,
            "$answersString<br/>(${verseRef.toChapterAndVerse()})"
        )
    }

    companion object {

    }
}