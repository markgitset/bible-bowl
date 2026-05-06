package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.generate.indices.formatWithCount

/**
 * Typealias for an integer representing an absolute verse number, calculated across all books.
 */
typealias AbsoluteVerseNum = Int

/**
 * Converts an [AbsoluteVerseNum] to a corresponding [VerseRef].
 *
 * @return The [VerseRef] constructed from this absolute verse number.
 */
fun AbsoluteVerseNum.toVerseRef(): VerseRef = VerseRef.fromAbsoluteVerseNum(this)

/**
 * Represents a reference to a specific verse in a specific chapter of a specific book.
 *
 * @property chapterRef The underlying reference to the chapter containing this verse.
 * @property verse The 1-based verse number.
 */
data class VerseRef(val chapterRef: ChapterRef, val verse: Int) : Comparable<VerseRef> {

    init {
        require(verse > 0) { "Verse number cannot be less than 1!" }
    }

    /**
     * An absolute verse number calculated based on a BCV_FACTOR.
     * It allows fast numerical comparisons between two VerseRefs.
     */
    val absoluteVerse: AbsoluteVerseNum by lazy { BCV_FACTOR * chapterRef.absoluteChapter + verse }

    /**
     * The chapter number of the reference.
     */
    val chapter: Int get() = chapterRef.chapter

    /**
     * The book of the reference.
     */
    val book: Book get() = chapterRef.book

    /**
     * The full name of the book for this reference.
     */
    val bookName: String get() = chapterRef.bookName

    /**
     * Constructs a VerseRef given a [Book], chapter number, and verse number.
     *
     * @param book The book of the Bible.
     * @param chapter The 1-based chapter number.
     * @param verse The 1-based verse number.
     */
    constructor(book: Book, chapter: Int, verse: Int) : this(ChapterRef(book, chapter), verse)

    override fun compareTo(other: VerseRef): Int = absoluteVerse.compareTo(other.absoluteVerse)

    /**
     * Formats the verse reference as a string, using the specified [BookFormat].
     *
     * @param bookFormat The format to use for the book name.
     * @return A formatted string (e.g., "Gen 1:1").
     */
    fun format(bookFormat: BookFormat = BRIEF_BOOK_FORMAT): String = "${chapterRef.format(bookFormat)}:$verse"

    override fun toString(): String = format()

    /**
     * Creates a [VerseRange] covering the span from this verse to another verse.
     *
     * @param endInclusive The last verse to include in the range.
     * @return A [VerseRange] between this verse and [endInclusive].
     */
    operator fun rangeTo(endInclusive: VerseRef) = VerseRange(this, endInclusive)

    companion object {
        /**
         * Parses an [AbsoluteVerseNum] back into a human-readable [VerseRef].
         *
         * @param refNum The absolute verse number.
         * @return The corresponding [VerseRef].
         */
        fun fromAbsoluteVerseNum(refNum: AbsoluteVerseNum): VerseRef =
            VerseRef(ChapterRef.fromAbsoluteChapterNum(refNum / BCV_FACTOR), refNum % BCV_FACTOR)

        /**
         * Parses a string (e.g. "Genesis 1:1" or "1:1" if context is provided) into a VerseRef.
         *
         * @param verseRefString The string to parse.
         * @param defaultChapterRef Optional fallback chapter reference context.
         * @return The parsed [VerseRef].
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
