package net.markdrew.biblebowl.model

data class BookChapter(val book: Int, val chapter: Int)

data class BookChapterVerse(val book: Int, val chapter: Int, val verse: Int) : Comparable<BookChapterVerse> {

    companion object {

        fun fromRefNum(refNum: Int): BookChapterVerse = with(refNum.toString()) {
            BookChapterVerse(take(2).toInt(), substring(2..4).toInt(), takeLast(3).toInt())
        }

    }

    fun bookName(): String = BOOKS_LIST[book - 1]
    
    fun toRefNum(): Int = "%02d%03d%03d".format(book, chapter, verse).toInt()

    fun sameBook(bcv: BookChapterVerse): Boolean = bcv.book == book

    override fun compareTo(other: BookChapterVerse): Int = toRefNum().compareTo(other.toRefNum())

    fun toFullString(): String = "${bookName()} $chapter:$verse"
    fun toChapterAndVerseString(): String = "$chapter:$verse"
}

fun Int.toBookChapterVerse(): BookChapterVerse = BookChapterVerse.fromRefNum(this)

fun main(args: Array<String>) {
    val bcv: BookChapterVerse = BookChapterVerse.fromRefNum(66013001)
    println(bcv)
    println(bcv.toRefNum())
}

data class VerseWithText(val canonicalRef: String, val text: String)

val BOOKS_LIST = listOf(
    "Genesis",
    "Exodus",
    "Leviticus",
    "Numbers",
    "Deuteronomy",
    "Joshua",
    "Judges",
    "Ruth",
    "1 Samuel",
    "2 Samuel",
    "1 Kings",
    "2 Kings",
    "1 Chronicles",
    "2 Chronicles",
    "Ezra",
    "Nehemiah",
    "Esther",
    "Job",
    "Psalms",
    "Proverbs",
    "Ecclesiastes",
    "Song of Solomon",
    "Isaiah",
    "Jeremiah",
    "Lamentations",
    "Ezekiel",
    "Daniel",
    "Hosea",
    "Joel",
    "Amos",
    "Obadiah",
    "Jonah",
    "Micah",
    "Nahum",
    "Habakkuk",
    "Zephaniah",
    "Haggai",
    "Zechariah",
    "Malachi",
    "Matthew",
    "Mark",
    "Luke",
    "John",
    "Acts",
    "Romans",
    "1 Corinthians",
    "2 Corinthians",
    "Galatians",
    "Ephesians",
    "Philippians",
    "Colossians",
    "1 Thessalonians",
    "2 Thessalonians",
    "1 Timothy",
    "2 Timothy",
    "Titus",
    "Philemon",
    "Hebrews",
    "James",
    "1 Peter",
    "2 Peter",
    "1 John",
    "2 John",
    "3 John",
    "Jude",
    "Revelation"
)