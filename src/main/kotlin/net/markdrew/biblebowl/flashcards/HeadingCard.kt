package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange

/**
 * A normal forward-direction heading flashcard (heading title -> verse range(s))
 *
 * Headings that share a title across chapters collapse into one card with multiple [verseRanges].
 *
 * @param heading the heading text shown on the front of the card
 * @param verseRanges the verse ranges this heading applies to
 * @param indices 1-based indices of this heading in the study set
 * @param allHeadings every heading in the study set; used for back-of-card "N of M" labelling
 */
data class HeadingCard(
    val heading: String,
    val verseRanges: List<VerseRange>,
    val indices: List<Int>,
    val allHeadings: List<Heading>,
) {
    /** Chapter ranges derived from [verseRanges] */
    val chapterRanges = verseRanges.map { it.toChapterRange() }

    companion object {
        /** Builds one card per distinct heading title in [studyData], merging duplicate titles. */
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
