package net.markdrew.biblebowl.model

data class Chapter(val book: Int, val chapter: Int)

data class VerseRef(val book: Book, val chapter: Int, val verse: Int) : Comparable<VerseRef> {

    companion object {

        fun fromRefNum(refNum: Int): VerseRef =
            VerseRef(Book.fromNumber(refNum / 1_000_000), refNum % 1_000_000 / 1_000, refNum % 1_000)

        fun fromShortRefNum(refNum: Int): VerseRef =
            VerseRef(Book.fromNumber(refNum / 10_000), refNum % 10_000 / 100, refNum % 100)

    }

    fun bookName(): String = book.fullName
    
    fun toRefNum(): Int = 1_000 * (1_000 * book.number + chapter) + verse
    fun toShortRefNum(): Int = 100 * (100 * book.number + chapter) + verse

    fun sameBook(bcv: VerseRef): Boolean = bcv.book == book

    override fun compareTo(other: VerseRef): Int = toRefNum().compareTo(other.toRefNum())

    fun toFullString(): String = "${bookName()} $chapter:$verse"
    fun toChapterAndVerse(): String = "$chapter:$verse"
//    fun toTextPosition(): TextPosition =
}

data class TextPosition(val verseRef: VerseRef, val charOffset: Int = 0) {
    fun toInt(): Int = 1_000 * verseRef.toShortRefNum() + charOffset
    companion object {
        fun fromInt(positionInt: Int): TextPosition = TextPosition(
            VerseRef.fromShortRefNum(positionInt / 1_000),
            positionInt % 1_000
        )
    }
}

data class TextRange(val start: TextPosition, val end: TextPosition) {
    fun toIntRange(): IntRange = start.toInt() .. end.toInt()
    companion object {
        fun fromIntRange(range: IntRange): TextRange = TextRange(
            TextPosition.fromInt(range.first),
            TextPosition.fromInt(range.last)
        )
    }
}

fun Int.toVerseRef(): VerseRef = VerseRef.fromRefNum(this)

fun main() {
    val bcv: VerseRef = VerseRef.fromRefNum(66013001)
    println(bcv)
    println(bcv.toRefNum())
    val pos = TextPosition(bcv, 12)
    println(pos)
    println(pos.toInt())
    println(TextPosition.fromInt(pos.toInt()))
}
