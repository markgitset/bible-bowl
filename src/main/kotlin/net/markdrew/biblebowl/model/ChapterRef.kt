package net.markdrew.biblebowl.model

typealias AbsoluteChapterNum = Int
fun AbsoluteChapterNum.toChapterRef(): ChapterRef = ChapterRef.fromAbsoluteChapterNum(this)

data class ChapterRef(val book: Book, val chapter: Int) : Comparable<ChapterRef> {
    val bookName: String get() = book.fullName
    val absoluteChapter: AbsoluteChapterNum by lazy { 1_000 * book.number + chapter }
    override fun compareTo(other: ChapterRef): Int = absoluteChapter.compareTo(other.absoluteChapter)

    fun verse(verseNum: Int): VerseRef = VerseRef(this, verseNum)

    fun toFullString(): String = "$bookName $chapter"

    companion object {
        fun fromAbsoluteChapterNum(refNum: AbsoluteChapterNum): ChapterRef =
            ChapterRef(Book.fromNumber(refNum / 1_000), refNum % 1_000)
    }

}
