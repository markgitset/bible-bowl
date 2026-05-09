package net.markdrew.biblebowl.model

/** Packed integer of the form `book.number * BCV_FACTOR + chapter`; see [BCV_FACTOR] */
typealias AbsoluteChapterNum = Int

/** Decodes a packed [AbsoluteChapterNum] back into a [ChapterRef]. */
fun AbsoluteChapterNum.toChapterRef(): ChapterRef = ChapterRef.fromAbsoluteChapterNum(this)

/** An inclusive range of [ChapterRef]s, possibly spanning books */
typealias ChapterRange = ClosedRange<ChapterRef>

/**
 * Renders this chapter range as a string like "John 3-5".
 *
 * @throws IllegalArgumentException if the range is empty
 */
fun ChapterRange.format(bookFormat: BookFormat = FULL_BOOK_FORMAT, separator: String = "-"): String {
    require(!isEmpty()) { "Chapter range is empty!" }
    return start.format(bookFormat) + if (start == endInclusive) "" else "$separator${endInclusive.chapter}"
}

/** Converts this chapter range to a range over packed [AbsoluteChapterNum]s. */
fun ChapterRange.toAbsoluteRange(): IntRange = start.absoluteChapter..endInclusive.absoluteChapter

/** Returns the absolute-chapter range spanning the first and last [ChapterRef] in this collection. */
fun Collection<ChapterRef>.toAbsoluteRange(): IntRange = first().absoluteChapter..last().absoluteChapter

/**
 * A reference to a single chapter within a [Book]
 *
 * @param book the book this chapter belongs to
 * @param chapter the 1-based chapter number; must be positive
 * @throws IllegalArgumentException if [chapter] is less than 1
 */
data class ChapterRef(val book: Book, val chapter: Int) : Comparable<ChapterRef> {

    init {
        require(chapter > 0) { "Chapter number cannot be less than 1!" }
    }

    /** Convenience accessor for `book.fullName` */
    val bookName: String get() = book.fullName

    /** Packed integer encoding both [book] and [chapter]; see [BCV_FACTOR] */
    val absoluteChapter: AbsoluteChapterNum by lazy { BCV_FACTOR * book.number + chapter }

    override fun compareTo(other: ChapterRef): Int = absoluteChapter.compareTo(other.absoluteChapter)

    /** Returns a [VerseRef] for [verseNum] in this chapter. */
    fun verse(verseNum: Int): VerseRef = VerseRef(this, verseNum)

    /** Returns a [VerseRange] covering verses [fromVerseNum]..[toVerseNum] within this chapter. */
    fun verseRange(fromVerseNum: Int = 1, toVerseNum: Int = BCV_FACTOR - 1): VerseRange =
        verse(fromVerseNum)..verse(toVerseNum)

    /** Renders this chapter as a string like "John 3" using the given [bookFormat]. */
    fun format(bookFormat: BookFormat): String = "${bookFormat(book)} $chapter".trim()

    /** Returns a stable round-trippable string of the form "JOH3"; see [deserialize]. */
    fun serialize(): String = "${book.name}$chapter"

    override fun toString(): String = serialize()

    companion object {
        /**
         * Strict inverse of [serialize]; expects exactly the three-letter book code followed by the chapter number.
         *
         * Use [parse] for lenient user-input parsing.
         */
        fun deserialize(s: String): ChapterRef = ChapterRef(Book.valueOf(s.take(3)), s.drop(3).toInt())

        /** Decodes a packed [AbsoluteChapterNum] into a [ChapterRef]. */
        fun fromAbsoluteChapterNum(refNum: AbsoluteChapterNum): ChapterRef =
            ChapterRef(Book.fromNumber(refNum / BCV_FACTOR), refNum % BCV_FACTOR)

        /**
         * Lenient parser for inputs like "John 3", "Joh 3", or just "3" with a [defaultBook].
         *
         * @throws IllegalArgumentException if the string has more than two whitespace-separated parts, or if no
         *   [defaultBook] is supplied for a single-part input
         */
        fun parse(chapterRefString: String, defaultBook: Book? = null): ChapterRef {
            val parts: List<String> = chapterRefString.trim().split(" ")
            return when (parts.size) {
                1 -> ChapterRef(
                    requireNotNull(defaultBook) { "Unable to parse '$chapterRefString'" },
                    parts.single().toInt()
                )
                2 -> parse(parts.last(), Book.parse(parts.first()))
                else -> throw IllegalArgumentException("Unable to parse '$chapterRefString'")
            }
        }
    }

}

/**
 * Renders a sequence of chapter ranges, grouping consecutive ranges from the same book under one book label
 * (e.g. "John 1-3,5; Acts 1-2").
 *
 * @throws IllegalArgumentException if [bookFormat] is [NO_BOOK_FORMAT] and the ranges span multiple books
 */
fun Iterable<ChapterRange>.format(bookFormat: BookFormat): String {
    require(distinctBy { it.start.book }.size <= 1 || bookFormat != NO_BOOK_FORMAT) {
        "Don't use NO_BOOK_FORMAT for multi-book chapter ranges!"
    }
    val rangesByBook: Map<Book, List<ChapterRange>> = groupingBy { it.start.book }
        .aggregate { _, accumulator: MutableList<ChapterRange>?, chapterRange, _ ->
            accumulator?.apply { add(chapterRange) } ?: mutableListOf(chapterRange)
        }
    return rangesByBook.entries.joinToString("; ") { (book, inBookChapterRanges) ->
        "${bookFormat(book)} " + inBookChapterRanges.joinToString(",") { it.format(NO_BOOK_FORMAT) }
    }.trim() // trim() removes leading space in the NO_BOOK_FORMAT case
}
