package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.toChapterRange

/**
 * For normal, forward-direction headings flashcards
 */
data class HeadingCard(
    val heading: String,
    val verseRanges: List<VerseRange>
) {
    val chapterRanges = verseRanges.map { it.toChapterRange() }

    companion object {
        val EMPTY: HeadingCard = HeadingCard("", emptyList())

        fun fromStudyData(studyData: StudyData): List<HeadingCard> =
            studyData.headings.groupBy { it.title }.map { (headingTitle, headingList) ->
                HeadingCard(headingTitle, headingList.map { it.verseRange })
            }
    }
}
