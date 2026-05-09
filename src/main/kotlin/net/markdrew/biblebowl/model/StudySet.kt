package net.markdrew.biblebowl.model

/**
 * The Bible portion in scope for a given study cycle (one or more chapter ranges, possibly across books)
 *
 * [simpleName] is used as a directory name throughout the pipeline, so it is constrained to lowercase letters,
 * digits, and hyphens. [chapterRanges] must be listed in ascending Biblical order with no overlap.
 *
 * @param name human-readable name (e.g. "Joshua, Judges, and Ruth")
 * @param simpleName short slug used for output directories (e.g. "josh-judg-ruth")
 * @param chapterRanges chapter ranges covered, in ascending Biblical order
 * @param longName a phrase suitable for use mid-sentence (e.g. "the books of Joshua, Judges, and Ruth")
 * @throws IllegalArgumentException if [simpleName] starts with a hyphen, contains illegal characters, or if
 *   [chapterRanges] are not in ascending order
 */
data class StudySet(
    val name: String,
    val simpleName: String,
    val chapterRanges: List<ChapterRange>,
    val longName: String = "the book of $name"
) {

    init {
        require(simpleName.first() != '-') { "simpleName can't start with a hyphen, but was: $simpleName" }
        require(simpleName.all { it.isDigit() || it.isLetter() && it.isLowerCase() || it == '-' }) {
            "simpleName must be lowercase and all letters, numbers, and/or hyphens, but was: $simpleName"
        }
        require(chapterRanges.zipWithNext().all { (a, b) -> a.endInclusive < b.start }) {
            "Chapter ranges must be in ascending Biblical order!"
        }
    }

    constructor(name: String, simpleName: String, vararg chapterRanges: ChapterRange)
            : this(name, simpleName, chapterRanges.asList())

    /** Builds a single-book study set from [book], deriving [simpleName] from the brief name when not given. */
    constructor(book: Book, simpleName: String = book.briefName.filterNot { it.isWhitespace() }.lowercase()) :
            this(book.fullName, simpleName, book.allChapters())

    /** Returns true if [chapterRef] falls within any of this set's [chapterRanges]. */
    operator fun contains(chapterRef: ChapterRef): Boolean = chapterRanges.any { chapterRef in it }

    /**
     * Renders the distinct books in this set as "Full Name (TL), …, and Full Name (TL)" using each book's
     * two-letter code.
     */
    fun chapterNamesWith2LetterCodes(): String {
        val books: List<String> = chapterRanges
            .map { it.start.book }.distinct()
            .map { "${it.fullName} (${it.twoLetter})" }
        return books.dropLast(1).joinToString(", ") + ", and " + books.last()
    }

    /**
     * Returns a [ChapterRange] from the start of this set through [lastChapterRef].
     *
     * Useful for "cumulative practice" generation where only the chapters studied so far are in scope.
     *
     * @throws IllegalArgumentException if [lastChapterRef] precedes the first chapter of this set
     */
    fun toChapter(lastChapterRef: ChapterRef): ChapterRange = (chapterRanges.first().start..lastChapterRef).also {
        require(!it.isEmpty()) {
            "The requested range ends ($lastChapterRef) before it begins (${chapterRanges.first().start})!"
        }
    }
}
