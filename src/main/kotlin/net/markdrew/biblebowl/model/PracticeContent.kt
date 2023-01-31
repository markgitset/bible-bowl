package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.encloses

data class PracticeContent internal constructor(
    val bookData: BookData,
    val coveredChapters: IntRange,
) {

    init {
        require(bookData.chapterRange.encloses(coveredChapters)) {
            "Requested chapter range (${bookData.chapterRange}) must be within $coveredChapters!"
        }
    }

    val allChapters: Boolean = coveredChapters == bookData.chapterRange

    val coveredOffsets: IntRange = with(bookData.chapterIndex) {
        getValue(coveredChapters.first).first..getValue(coveredChapters.last).last
    }

    fun headings(): List<Heading> = bookData.headings(coveredChapters)

}