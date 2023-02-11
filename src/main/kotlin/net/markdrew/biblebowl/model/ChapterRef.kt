package net.markdrew.biblebowl.model

typealias AbsoluteChapterNum = Int
fun AbsoluteChapterNum.toChapterRef(): ChapterRef = ChapterRef.fromAbsoluteChapterNum(this)

typealias ChapterRange = ClosedRange<ChapterRef>
fun ChapterRange.toString(separator: String): String {
    require(!isEmpty()) { "Chapter range is empty!" }
    return start.toFullString() + if (start == endInclusive) "" else "$separator${endInclusive.chapter}"
}

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

    fun toFullString(): String = "$bookName $chapter"
    fun toBriefString(): String = "${book.briefName} $chapter"

    // for serialization
    override fun toString(): String = "$book$chapter"

    companion object {
        // strict parsing for deserialization
        fun valueOf(s: String): ChapterRef = ChapterRef(Book.valueOf(s.take(3)), s.drop(3).toInt())

        fun fromAbsoluteChapterNum(refNum: AbsoluteChapterNum): ChapterRef =
            ChapterRef(Book.fromNumber(refNum / BCV_FACTOR), refNum % BCV_FACTOR)
    }

}
