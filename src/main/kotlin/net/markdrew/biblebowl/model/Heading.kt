package net.markdrew.biblebowl.model

/**
 * Represents a heading in the study set.
 *
 * @param index The 1-based index of the heading in the study set.
 */
data class Heading(val title: String, val verseRange: VerseRange, val index: Int, val maxIndex: Int) {
    val chapterRange: ChapterRange by lazy { verseRange.toChapterRange() }
}