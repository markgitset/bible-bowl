package net.markdrew.biblebowl.model

typealias AbsoluteChapterNum = Int
fun AbsoluteChapterNum.toChapterRef(): ChapterRef = ChapterRef.fromAbsoluteChapterNum(this)

typealias ChapterRange = ClosedRange<ChapterRef>
fun ChapterRange.format(bookFormat: BookFormat = FULL_BOOK_FORMAT, separator: String = "-"): String {
    require(!isEmpty()) { "Chapter range is empty!" }
    return start.format(bookFormat) + if (start == endInclusive) "" else "$separator${endInclusive.chapter}"
}
fun ChapterRange.toAbsoluteRange(): IntRange = start.absoluteChapter..endInclusive.absoluteChapter
fun Collection<ChapterRef>.toAbsoluteRange(): IntRange = first().absoluteChapter..last().absoluteChapter

data class ChapterRef(val book: Book, val chapter: Int) : Comparable<ChapterRef> {

    init {
        require(chapter > 0) { "Chapter number cannot be less than 1!" }
    }

    val bookName: String get() = book.fullName
    val absoluteChapter: AbsoluteChapterNum by lazy { BCV_FACTOR * book.number + chapter }

    override fun compareTo(other: ChapterRef): Int = absoluteChapter.compareTo(other.absoluteChapter)

    fun verse(verseNum: Int): VerseRef = VerseRef(this, verseNum)

    fun verseRange(fromVerseNum: Int = 1, toVerseNum: Int = BCV_FACTOR - 1): VerseRange =
        verse(fromVerseNum)..verse(toVerseNum)

    fun format(bookFormat: BookFormat): String = "${bookFormat(book)} $chapter".trim()
    fun serialize(): String = "${book.name}$chapter"
    override fun toString(): String = serialize()

    companion object {
        // strict parsing for deserialization
        fun deserialize(s: String): ChapterRef = ChapterRef(Book.valueOf(s.take(3)), s.drop(3).toInt())

        fun fromAbsoluteChapterNum(refNum: AbsoluteChapterNum): ChapterRef =
            ChapterRef(Book.fromNumber(refNum / BCV_FACTOR), refNum % BCV_FACTOR)
    }

}

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
