package net.markdrew.biblebowl.ws

data class BookChapter(val book: Int, val chapter: Int)

data class BookChapterVerse(val book: Int, val chapter: Int, val verse: Int) {

    companion object {

        fun fromRefNum(refNum: Int): BookChapterVerse = with(refNum.toString()) {
            BookChapterVerse(take(2).toInt(), substring(2..4).toInt(), takeLast(3).toInt())
        }

    }

    fun toRefNum(): Int = "%02d%03d%03d".format(book, chapter, verse).toInt()

    fun sameBook(bcv: BookChapterVerse): Boolean = bcv.book == book

}

fun main(args: Array<String>) {
    val bcv: BookChapterVerse = BookChapterVerse.fromRefNum(66013001)
    println(bcv)
    println(bcv.toRefNum())
}

data class VerseWithText(val canonicalRef: String, val text: String)