package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.intersect

data class PracticeContent internal constructor(
    val studyData: StudyData,
    val coveredChapters: List<ChapterRef> = studyData.chapterRefs
) {
    @Deprecated("Doesn't work great for multi-book sets")
    constructor(studyData: StudyData, coveredChapters: ClosedRange<ChapterRef>)
            : this(studyData, studyData.chapterRefs.filter { it in coveredChapters })

    init {
        require(coveredChapters.isNotEmpty()) { "Requested chapter range is empty!" }
        require(studyData.chapterRefs.containsAll(coveredChapters)) {
            "These requested chapters are not in range: ${coveredChapters - studyData.chapterRefs.toSet()})!"
        }
    }

    /** True if this [PracticeContent] covers a whole [StudySet] */
    val allChapters: Boolean = coveredChapters == studyData.chapterRange

    val coveredOffsets: IntRange = with(studyData.chapterIndex) {
        getValue(coveredChapters.first()).first..getValue(coveredChapters.last()).last
    }

    fun headings(): List<Heading> = studyData.headings(coveredChapters)

    fun coveredChaptersString(): String = studyData.studySet.chapterRanges
        .map { it.intersect(coveredChapters.first()..coveredChapters.last()) }
        .filterNot { it.isEmpty() }
        .format(FULL_BOOK_FORMAT)
}