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

    fun headings(): List<Heading> = bookData.headings(coveredChapters)

}