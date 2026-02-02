package net.markdrew.biblebowl.model

data class VerseRange(
    override val start: VerseRef,
    override val endInclusive: VerseRef,
) : ClosedRange<VerseRef> {
    fun toChapterRange(): ChapterRange = start.chapterRef..endInclusive.chapterRef
    fun format(bookFormat: BookFormat, separator: String = "-", compact: Boolean = true): String {
        val endString: String = when {
            compact && endInclusive.chapterRef == start.chapterRef -> endInclusive.verse.toString()
            compact && endInclusive.book == start.book -> endInclusive.format(NO_BOOK_FORMAT)
            else -> endInclusive.format(bookFormat)
        }
        return "${start.format(bookFormat)}$separator${endString}"
    }
    override fun toString() = "[" + format(THREE_LETTER_BOOK_FORMAT) + "]"

    companion object {
        fun parse(verseRangeString: String): VerseRange {
            val start: VerseRef
            val end: VerseRef = try {
                val split: List<String> = verseRangeString.split("-", "–")
                require(split.size <= 2) { "Too many range indicators (hyphens)!" }
                start = VerseRef.parse(split.first())
                if (split.size == 1) start else VerseRef.parse(split[1], start.chapterRef)
            } catch (e: Exception) {
                throw IllegalArgumentException("Unable to parse '$verseRangeString' as a verse range!", e)
            }
            return start .. end
        }
    }
}
