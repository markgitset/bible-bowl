package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.intersect

/**
 * The slice of [StudyData] eligible for question generation in a practice round
 *
 * Restricting to a subset of [StudyData.chapterRefs] supports cumulative practice (only chapters studied so
 * far). When [coveredChapters] equals all of [studyData]'s chapters, [allChapters] is true.
 *
 * @param studyData the underlying indexed study data
 * @param coveredChapters the chapters in scope, in Bible order; must be a subset of [studyData]'s chapters
 * @throws IllegalArgumentException if [coveredChapters] is empty or contains chapters outside [studyData]
 */
@ConsistentCopyVisibility
data class PracticeContent internal constructor(
    val studyData: StudyData,
    val coveredChapters: List<ChapterRef> = studyData.chapterRefs
) {
    @Deprecated("Doesn't work great for multi-book sets")
    constructor(studyData: StudyData, coveredChapters: ClosedRange<ChapterRef>)
            : this(studyData, studyData.chapterRefs.filter { it in coveredChapters })

    constructor(studyData: StudyData, singleChapter: ChapterRef)
            : this(studyData, listOf(singleChapter))

    init {
        require(coveredChapters.isNotEmpty()) { "Requested chapter range is empty!" }
        require(studyData.chapterRefs.containsAll(coveredChapters)) {
            "These requested chapters are not in range: ${coveredChapters - studyData.chapterRefs.toSet()})!"
        }
    }

    /** True if this [PracticeContent] covers the whole [StudySet] */
    val allChapters: Boolean = coveredChapters == studyData.chapterRange

    /** Character offset range over [StudyData.text] spanning every covered chapter */
    val coveredOffsets: IntRange = with(studyData.chapterIndex) {
        getValue(coveredChapters.first()).first..getValue(coveredChapters.last()).last
    }

    /** Headings whose chapter falls within [coveredChapters] */
    fun headings(): List<Heading> = studyData.headings(coveredChapters)

    /** Renders the covered chapters as a human-readable phrase, e.g. "Joshua 1-5; Judges 1-3". */
    fun coveredChaptersString(): String = studyData.studySet.chapterRanges
        .map { it.intersect(coveredChapters.first()..coveredChapters.last()) }
        .filterNot { it.isEmpty() }
        .format(FULL_BOOK_FORMAT)
}
