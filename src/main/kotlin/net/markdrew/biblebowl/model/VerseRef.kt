package net.markdrew.biblebowl.model

typealias AbsoluteVerseNum = Int
fun AbsoluteVerseNum.toVerseRef(): VerseRef = VerseRef.fromAbsoluteVerseNum(this)

data class VerseRef(val chapterRef: ChapterRef, val verse: Int) : Comparable<VerseRef> {

    constructor(book: Book, chapter: Int, verse: Int) : this(ChapterRef(book, chapter), verse)

    val absoluteVerse: AbsoluteVerseNum by lazy { 1_000 * chapterRef.absoluteChapter + verse }

    val chapter: Int get() = chapterRef.chapter
    val book: Book get() = chapterRef.book
    val bookName: String get() = chapterRef.bookName

    override fun compareTo(other: VerseRef): Int = absoluteVerse.compareTo(other.absoluteVerse)

    fun toFullString(): String = "${chapterRef.toFullString()}:$verse"
    fun toChapterAndVerse(): String = "${chapterRef.chapter}:$verse"

    companion object {
        fun fromAbsoluteVerseNum(refNum: AbsoluteVerseNum): VerseRef =
            VerseRef(ChapterRef.fromAbsoluteChapterNum(refNum / 1_000), refNum % 1_000)
    }

}

fun main() {
    val bcv = VerseRef.fromAbsoluteVerseNum(66013001)
    println(bcv)
    println(bcv.absoluteVerse)
}
