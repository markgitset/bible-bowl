package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.encloses

data class OldPracticeContent internal constructor(val studyData: StudyData, val coveredChapters: ChapterRange) {

    init {
        require(studyData.chapterRange.encloses(coveredChapters)) {
            "Requested chapter range (${studyData.chapterRange}) must be within $coveredChapters!"
        }
    }

    val allChapters: Boolean = coveredChapters == studyData.chapterRange

    val coveredOffsets: IntRange = with(studyData.chapterIndex) {
        getValue(coveredChapters.start).first..getValue(coveredChapters.endInclusive).last
    }

    fun headings(): List<Heading> = studyData.headings(coveredChapters)

}