package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.generate.indices.formatWithCount

/** Packed integer of the form `book.number * BCV_FACTOR^2 + chapter * BCV_FACTOR + verse`; see [BCV_FACTOR] */
typealias AbsoluteVerseNum = Int

/** Decodes a packed [AbsoluteVerseNum] back into a [VerseRef]. */
fun AbsoluteVerseNum.toVerseRef(): VerseRef = VerseRef.fromAbsoluteVerseNum(this)

/**
 * A reference to a single verse: a [chapterRef] plus a verse number
 *
 * Verse numbers must be positive. Two [VerseRef]s compare by their [absoluteVerse] so they can be sorted in
 * Bible order regardless of book.
 *
 * @throws IllegalArgumentException if [verse] is less than 1
 */
data class VerseRef(val chapterRef: ChapterRef, val verse: Int) : Comparable<VerseRef> {

    init {
        require(verse > 0) { "Verse number cannot be less than 1!" }
    }

    /** Packed integer encoding [chapterRef] and [verse]; see [BCV_FACTOR] */
    val absoluteVerse: AbsoluteVerseNum by lazy { BCV_FACTOR * chapterRef.absoluteChapter + verse }

    val chapter: Int get() = chapterRef.chapter
    val book: Book get() = chapterRef.book
    val bookName: String get() = chapterRef.bookName

    constructor(book: Book, chapter: Int, verse: Int) : this(ChapterRef(book, chapter), verse)

    override fun compareTo(other: VerseRef): Int = absoluteVerse.compareTo(other.absoluteVerse)

    /** Renders this verse as a string like "John 3:16" using the given [bookFormat]. */
    fun format(bookFormat: BookFormat = BRIEF_BOOK_FORMAT): String = "${chapterRef.format(bookFormat)}:$verse"

    override fun toString(): String = format()

    /** Builds an inclusive [VerseRange] from this verse to [endInclusive]. */
    operator fun rangeTo(endInclusive: VerseRef) = VerseRange(this, endInclusive)

    companion object {
        /** Decodes a packed [AbsoluteVerseNum] into a [VerseRef]. */
        fun fromAbsoluteVerseNum(refNum: AbsoluteVerseNum): VerseRef =
            VerseRef(ChapterRef.fromAbsoluteChapterNum(refNum / BCV_FACTOR), refNum % BCV_FACTOR)

        /**
         * Lenient parser for inputs like "John 3:16", "Joh 3:16", or just "16" with a [defaultChapterRef].
         *
         * Trailing letter parts (e.g. "16a", "16b") are ignored. Leading/trailing whitespace is tolerated.
         *
         * @throws IllegalArgumentException if the string can't be parsed, or if no [defaultChapterRef] is
         *   supplied for a bare verse number
         */
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

/**
 * Renders a list of verse references compactly, e.g. "Mat 4:5,6; 6:8,10; Joh 3:16".
 *
 * Refs from the same chapter collapse to comma-separated verse numbers; refs from the same book collapse under
 * one book label.
 *
 * @throws IllegalArgumentException if [bookFormat] is [NO_BOOK_FORMAT] and the list spans multiple books
 */
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

/**
 * Like [Iterable.format] but appends a frequency count to each verse, suitable for word-index entries.
 *
 * Uses standard wrapping-friendly comma separators between collapsed verses.
 *
 * @throws IllegalArgumentException if [bookFormat] is [NO_BOOK_FORMAT] and the list spans multiple books
 */
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
                    accumulator.append(", ")
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
