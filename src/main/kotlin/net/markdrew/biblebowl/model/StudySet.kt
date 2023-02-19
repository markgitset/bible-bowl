package net.markdrew.biblebowl.model

data class StudySet(val name: String, val simpleName: String, val chapterRanges: List<ChapterRange>) {

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

    constructor(book: Book, simpleName: String) : this(book.fullName, simpleName, book.allChapters())

    operator fun contains(chapterRef: ChapterRef): Boolean = chapterRanges.any { chapterRef in it }

    fun chapterNamesWith2LetterCodes(): String {
        val books: List<String> = chapterRanges
            .map { it.start.book }.distinct()
            .map { "${it.fullName} (${it.twoLetter})" }
        return books.dropLast(1).joinToString(", ") + ", and " + books.last()
    }

    fun toChapter(lastChapterRef: ChapterRef): ChapterRange = (chapterRanges.first().start..lastChapterRef).also {
        require(!it.isEmpty()) {
            "The requested range ends ($lastChapterRef) before it begins (${chapterRanges.first().start})!"
        }
    }
}