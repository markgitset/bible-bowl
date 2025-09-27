package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.toChapterRange

/**
 * For normal, forward-direction headings flashcards
 *
 * @param verseRanges The verse ranges that the heading applies to
 * @param indices The 1-based indices of this heading in the study set
 */
data class HeadingCard(
    val heading: String,
    val verseRanges: List<VerseRange>,
    val indices: List<Int>,
    val allHeadings: List<Heading>,
) {
    val chapterRanges = verseRanges.map { it.toChapterRange() }

    companion object {
        @Deprecated("Shouldn't need this with Typst")
        val EMPTY: HeadingCard = HeadingCard("", emptyList(), emptyList(), emptyList())

        fun fromStudyData(studyData: StudyData): List<HeadingCard> =
            studyData.headings.groupBy { it.title }.map { (headingTitle, headingList) ->
                HeadingCard(
                    headingTitle,
                    headingList.map { it.verseRange },
                    headingList.map { it.index },
                    studyData.headings
                )
            }
    }
}
