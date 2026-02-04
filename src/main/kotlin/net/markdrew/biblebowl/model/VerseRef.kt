package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.generate.indices.formatWithCount

typealias AbsoluteVerseNum = Int
fun AbsoluteVerseNum.toVerseRef(): VerseRef = VerseRef.fromAbsoluteVerseNum(this)

data class VerseRef(val chapterRef: ChapterRef, val verse: Int) : Comparable<VerseRef> {

    init {
        require(verse > 0) { "Verse number cannot be less than 1!" }
    }

    val absoluteVerse: AbsoluteVerseNum by lazy { BCV_FACTOR * chapterRef.absoluteChapter + verse }

    val chapter: Int get() = chapterRef.chapter
    val book: Book get() = chapterRef.book
    val bookName: String get() = chapterRef.bookName

    constructor(book: Book, chapter: Int, verse: Int) : this(ChapterRef(book, chapter), verse)

    override fun compareTo(other: VerseRef): Int = absoluteVerse.compareTo(other.absoluteVerse)

    fun format(bookFormat: BookFormat = BRIEF_BOOK_FORMAT): String = "${chapterRef.format(bookFormat)}:$verse"

    override fun toString(): String = format()

    operator fun rangeTo(endInclusive: VerseRef) = VerseRange(this, endInclusive)

    companion object {
        fun fromAbsoluteVerseNum(refNum: AbsoluteVerseNum): VerseRef =
            VerseRef(ChapterRef.fromAbsoluteChapterNum(refNum / BCV_FACTOR), refNum % BCV_FACTOR)

        fun parse(verseRefString: String, defaultChapterRef: ChapterRef? = null): VerseRef {
            val parts: List<String> = verseRefString.split(":")
            return when (parts.size) {
                1 -> VerseRef(
                    requireNotNull(defaultChapterRef) { "Unable to parse '$verseRefString' as a verse ref" },
                    parts.single().dropLastWhile { !it.isDigit() }.toInt()
                )
                2 -> parse(parts.last(), ChapterRef.parse(parts.first(), defaultChapterRef?.book))
                else -> throw IllegalArgumentException("Unable to parse '$verseRefString' as a verse ref")
            }
        }
    }

}

fun Iterable<VerseRef>.format(bookFormat: BookFormat): String {
    require(distinctBy { it.book }.size <= 1 || bookFormat != NO_BOOK_FORMAT) {
        "Don't use NO_BOOK_FORMAT for multi-book verse lists!"
    }
    val versesByBook: Map<Book, List<VerseRef>> = groupingBy(VerseRef::book)
        .aggregate { _, accumulator: MutableList<VerseRef>?, verseRef, _ ->
            accumulator?.apply { add(verseRef) } ?: mutableListOf(verseRef)
        }
    return versesByBook.entries.joinToString("; ") { (book, inBookVerseRefs) ->
        inBookVerseRefs.groupingBy(VerseRef::chapterRef)
            .aggregate { _, accumulator: StringBuilder?, verseRef, _ ->
                if (accumulator == null) StringBuilder(verseRef.format(NO_BOOK_FORMAT))
                else accumulator.append(",").append(verseRef.verse)
            }
            .values
            .joinToString("; ", prefix = bookFormat(book) + " ")
        }.trim() // trim() removes leading space in the NO_BOOK_FORMAT case
}

fun Iterable<WithCount<VerseRef>>.formatWithCounts(bookFormat: BookFormat): String {
    require(distinctBy { it.item.book }.size <= 1 || bookFormat != NO_BOOK_FORMAT) {
        "Don't use NO_BOOK_FORMAT for multi-book verse lists!"
    }
    val versesByBook: Map<Book, List<WithCount<VerseRef>>> = groupingBy { it.item.book }
        .aggregate { _, accumulator: MutableList<WithCount<VerseRef>>?, verseRef, _ ->
            accumulator?.apply { add(verseRef) } ?: mutableListOf(verseRef)
        }
    return versesByBook.entries.joinToString("; ") { (book, inBookVerseRefs) ->
        inBookVerseRefs.groupingBy { it.item.chapterRef }
            .aggregate { _, accumulator: StringBuilder?, verseRef, _ ->
                if (accumulator == null) {
                    StringBuilder(formatWithCount(verseRef.item.format(NO_BOOK_FORMAT), verseRef.count))
                } else {
                    accumulator.append(",\\linebreak[0]")
                        .append(formatWithCount(verseRef.item.verse.toString(), verseRef.count))
                }
            }
            .values
            .joinToString("; ", prefix = bookFormat(book) + "~")
        }.trim() // trim() removes leading space in the NO_BOOK_FORMAT case
}

fun main() {
    val bcv = VerseRef.fromAbsoluteVerseNum(66013001)
    println(bcv)
    println(bcv.absoluteVerse)
    println(66160101.toVerseRef())
}
