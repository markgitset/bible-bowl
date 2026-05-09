package net.markdrew.biblebowl.model

/**
 * A section heading from the ESV text, scoped to the verses it covers
 *
 * @param title the heading text as it appears in the ESV
 * @param verseRange the verses this heading spans
 * @param index the 1-based position of this heading within its [StudySet]
 * @param maxIndex the total number of headings in the study set; used to display "N of M" labels
 */
data class Heading(val title: String, val verseRange: VerseRange, val index: Int, val maxIndex: Int) {
    /** The chapter range covered by [verseRange] */
    val chapterRange: ChapterRange by lazy { verseRange.toChapterRange() }
}
