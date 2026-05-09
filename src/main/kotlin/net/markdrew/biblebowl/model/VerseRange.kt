package net.markdrew.biblebowl.model

/**
 * An inclusive range of verses, possibly spanning chapter or book boundaries
 *
 * @param start the first verse in the range
 * @param endInclusive the last verse in the range
 */
data class VerseRange(
    override val start: VerseRef,
    override val endInclusive: VerseRef,
) : ClosedRange<VerseRef> {

    /** Returns the chapter range covered by this verse range. */
    fun toChapterRange(): ChapterRange = start.chapterRef..endInclusive.chapterRef

    /**
     * Renders this range as a string like "John 3:16" or "John 3:16-18", suppressing redundant book/chapter parts
     * on the right side when [compact] is true and the start and end share that context.
     */
    fun format(bookFormat: BookFormat, separator: String = "-", compact: Boolean = true): String {
        if (endInclusive == start) return start.format(bookFormat)
        val endString: String = when {
            compact && endInclusive.chapterRef == start.chapterRef -> endInclusive.verse.toString()
            compact && endInclusive.book == start.book -> endInclusive.format(NO_BOOK_FORMAT)
            else -> endInclusive.format(bookFormat)
        }
        return "${start.format(bookFormat)}$separator${endString}"
    }

    override fun toString() = "[" + format(THREE_LETTER_BOOK_FORMAT) + "]"

    companion object {
        /**
         * Parses a verse range string like "John 3:16-18" or "Joh 3:16–4:2".
         *
         * Accepts both ASCII hyphen ("-") and Unicode en-dash ("–") as range separators. A bare verse with no
         * separator is treated as a one-verse range.
         *
         * @throws IllegalArgumentException if the string can't be parsed as one or two valid [VerseRef]s
         */
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
