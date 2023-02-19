package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.encloses
import net.markdrew.chupacabra.core.intersect

data class PracticeContent internal constructor(
    val studyData: StudyData,
    // spans lots of not covered chapters, but intersect with studyData.chapters to find actual chapters
    val coveredChapters: ChapterRange = studyData.chapterRange
) {

    init {
        require(!coveredChapters.isEmpty()) { "Requested chapter range is empty!" }
        require(studyData.chapterRange.encloses(coveredChapters)) {
            "Requested chapter range (${studyData.chapterRange}) must be within $coveredChapters!"
        }
    }

    val allChapters: Boolean = coveredChapters == studyData.chapterRange

    val coveredOffsets: IntRange = with(studyData.chapterIndex) {
        getValue(coveredChapters.start).first..getValue(coveredChapters.endInclusive).last
    }

    fun headings(): List<Heading> = studyData.headings(coveredChapters)

    fun coveredChaptersString(): String =
        studyData.studySet.chapterRanges
            .map { it.intersect(coveredChapters) }
            .filterNot { it.isEmpty() }
            .format(FULL_BOOK_FORMAT)
}