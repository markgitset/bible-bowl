package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.encloses

data class OldPracticeContent internal constructor(val bookData: BookData, val coveredChapters: ChapterRange) {

    init {
        require(bookData.chapterRange.encloses(coveredChapters)) {
            "Requested chapter range (${bookData.chapterRange}) must be within $coveredChapters!"
        }
    }

    val allChapters: Boolean = coveredChapters == bookData.chapterRange

    val coveredOffsets: IntRange = with(bookData.chapterIndex) {
        getValue(coveredChapters.start).first..getValue(coveredChapters.endInclusive).last
    }

    fun headings(): List<Heading> = bookData.headings(coveredChapters)

}