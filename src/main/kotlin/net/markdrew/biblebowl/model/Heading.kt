package net.markdrew.biblebowl.model

data class Heading(val title: String, val verseRange: VerseRange) {
    val chapterRange: ChapterRange by lazy { verseRange.toChapterRange() }
}