package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.generate.excerpt
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.intersect
import net.markdrew.chupacabra.core.toDisjointRangeSet
import java.io.File
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths
import java.util.SortedMap

class BookData(val book: Book,
               val text: String,
               val verses: DisjointRangeMap<Int>,
               val headings: DisjointRangeMap<String>,
               val chapters: DisjointRangeMap<Int>,
               val paragraphs: DisjointRangeSet,
               val footnotes: SortedMap<Int, String>,
               val poetry: DisjointRangeSet,
) {

    val verseIndex: Map<Int, IntRange> by lazy {
        verses.entries.associate { (range, refNum) -> refNum to range }
    }

    val chapterIndex: Map<Int, IntRange> by lazy {
        chapters.entries.associate { (range, chapterNum) -> chapterNum to range }
    }

    val wordIndex: Map<String, List<IntRange>> by lazy {
        words.groupBy { wordRange -> text.substring(wordRange).lowercase() }
    }

    /**
     * Sentences (or sentence parts) that are guaranteed to NOT span more than one verse
     */
    val oneVerseSentParts: DisjointRangeMap<Int> by lazy { verses.maskedBy(sentences) }

    val sentences: DisjointRangeSet by lazy { identifySentences(text) }

    val words: DisjointRangeSet by lazy { parseWords(text).map { it.range }.toDisjointRangeSet() }

    fun writeData(outPath: Path) {
        val outDir = outPath.resolve(book.name.lowercase())
        writeText(outDir.resolve(textFileName(book)))
        writeVerseIndex(outDir.resolve(fileName(VERSE)))
        writeHeadingsIndex(outDir.resolve(fileName(HEADING)))
        writeChaptersIndex(outDir.resolve(fileName(CHAPTER)))
        writeParagraphsIndex(outDir.resolve(fileName(PARAGRAPH)))
        writeFootnotesIndex(outDir.resolve(fileName(FOOTNOTE)))
        writePoetryIndex(outDir.resolve(fileName(POETRY, plural = false)))
    }

    private fun fileName(unit: AnalysisUnit, plural: Boolean = true): String = indexFileName(book, unit, plural)

    private fun writeText(outPath: Path) {
        val outFile: File = outPath.toFile()
        outFile.parentFile.mkdirs()
        outFile.writeText(text)
        println("Wrote text to: $outPath")
    }

    private fun writeVerseIndex(outPath: Path) {
        writeIterable(outPath, verses.entries) { (range, verseRefNum) ->
            println("${range.first}\t${range.last}\t${verseRefNum}")
        }
    }

    private fun writeHeadingsIndex(outPath: Path) {
        writeIterable(outPath, headings.entries) { (range, heading) ->
            println("${range.first}\t${range.last}\t$heading")
        }
    }

    private fun writeChaptersIndex(outPath: Path) {
        writeIterable(outPath, chapters.entries) { (range, chapter) ->
            println("${range.first}\t${range.last}\t$chapter")
        }
    }

    private fun writeParagraphsIndex(outPath: Path) {
        writeIterable(outPath, paragraphs) { range ->
            println("${range.first}\t${range.last}")
        }
    }

    private fun writePoetryIndex(outPath: Path) {
        writeIterable(outPath, poetry) { range ->
            println("${range.first}\t${range.last}")
        }
    }

    private fun writeFootnotesIndex(outPath: Path) {
        writeIterable(outPath, footnotes.entries) { (offset, noteText) ->
            println("${offset}\t${noteText}")
        }
    }

    /**
     * Returns a name/description of the smallest, named range (verse, heading, or chapter) that encloses the given
     * range.  E.g., given a single would return a verse reference string, or given a few verses in the same chapter
     * heading, would return that chapter heading.
     */
    fun smallestNamedRange(range: IntRange): String? {
        val verseRefNum: Int? = verses.valueEnclosing(range)
        if (verseRefNum != null) return verseRefNum.toVerseRef().toChapterAndVerse()

        val chapter: Int? = chapters.valueEnclosing(range)
        val heading: String? = headings.valueEnclosing(range)
        if (heading != null) return "Chapter $chapter: $heading"

        if (chapter != null) return "Chapter $chapter"

        return null
    }

    /** The range of chapters in this book */
    val chapterRange: IntRange by lazy {
        val values = chapters.values
        val min = values.minOrNull() ?: throw Exception("Should never happen!")
        val max = values.maxOrNull() ?: throw Exception("Should never happen!")
        min..max
    }

    /**
     * @return the character range corresponding to the given chapter range
     */
    fun charRangeFromChapterRange(selectedChaptersRange: IntRange): IntRange {
        val chapters = chapterRange.intersect(selectedChaptersRange) // ensure a valid chapter range
        return chapterIndex.getValue(chapters.first).first..chapterIndex.getValue(chapters.last).last
    }

    /**
     * @return the character range from the beginning through [lastChapter], or the whole book if [lastChapter] is null
     */
    fun charRangeThroughChapter(lastChapter: Int?): IntRange =
        if (lastChapter == null) text.indices
        else charRangeFromChapterRange(1..lastChapter)

    /**
     * @return the given chapter number (with optional prefix/suffix) if it is less than the last chapter of the book,
     * otherwise an empty string
     */
    fun maxChapterOrEmpty(prefix: String = "", maxChapter: Int?, suffix: String = ""): String =
        maxChapter?.takeIf { it < chapterRange.last }?.let { prefix + it + suffix }.orEmpty()

    fun enclosingSentence(range: IntRange): IntRange? = sentences.enclosing(range)
    fun sentenceContext(range: IntRange): Excerpt? = enclosingSentence(range)?.let { excerpt(it) }

    fun excerpt(range: IntRange): Excerpt = text.excerpt(range)

    fun wordCount(range: IntRange): Int = words.enclosedBy(range).size

    fun splitLong(textRange: IntRange, maxLengthGoal: Int = 22): List<IntRange> {
        if (wordCount(textRange) <= maxLengthGoal) return listOf(textRange)
        TODO()
        return listOf(textRange)
    }

    fun enclosingSingleVerseSentence(range: IntRange): IntRange? = oneVerseSentParts.keyEnclosing(range)
    fun singleVerseSentenceContext(range: IntRange): Excerpt? =
        enclosingSingleVerseSentence(range)?.let { excerpt(it) }

    fun verseList(): List<ReferencedVerse> =
        verses.entries.map { (range, verseRefNum) -> ReferencedVerse(verseRefNum.toVerseRef(), text.substring(range)) }

    fun getVerse(verseReference: VerseRef): String = text.substring(verseIndex.getValue(verseReference.toRefNum()))
    fun verseEnclosing(range: IntRange): VerseRef? = verses.valueEnclosing(range)?.toVerseRef()
    fun verseContaining(offset: Int): VerseRef? = verses.valueContaining(offset)?.toVerseRef()

    companion object {

        internal fun parseWords(text: String): Sequence<MatchResult> =
            """\d{1,3}(?:,\d{3})+|[-\w]+(?:[’']s)?""".toRegex().findAll(text)

        private fun <T> writeIterable(outPath: Path, iterable: Iterable<T>, formatFun: PrintWriter.(T) -> Unit) {
            outPath.toFile().printWriter().use { pw ->
                iterable.forEach { pw.formatFun(it) }
            }
            println("Wrote data to: $outPath")
        }

        private fun indexFileName(book: Book, unit: AnalysisUnit, plural: Boolean = true): String =
            "${book.name.lowercase()}-${unit.name.lowercase()}${if (plural) "s" else ""}.tsv"

        private fun textFileName(book: Book): String = book.name.lowercase() + ".txt"

        private fun readVerses(inPath: Path): DisjointRangeMap<Int> = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (first, last, verseRefNum) = line.split('\t').map { it.toInt() }
                first..last to verseRefNum
            }.toMap(DisjointRangeMap())
        }

        private fun readChapters(inPath: Path): DisjointRangeMap<Int> = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (first, last, chapterNum) = line.split('\t').map { it.toInt() }
                first..last to chapterNum
            }.toMap(DisjointRangeMap())
        }

        private fun readHeadings(inPath: Path): DisjointRangeMap<String> = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (first, last, heading) = line.split('\t')
                first.toInt()..last.toInt() to heading
            }.toMap(DisjointRangeMap())
        }

        private fun readDisjointRangeSet(inPath: Path): DisjointRangeSet = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (first, last) = line.split('\t').map { it.toInt() }
                first..last
            }.toDisjointRangeSet()
        }

        private fun readFootnotes(inPath: Path): SortedMap<Int, String> = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (offset, noteText) = line.split('\t')
                offset.toInt() to noteText
            }.toMap().toSortedMap()
        }

        fun readData(book: Book, inPath: Path = Paths.get(DATA_DIR)): BookData {
            val bookDir = inPath.resolve(book.name.lowercase())
            val text = bookDir.resolve(textFileName(book)).toFile().readText()
            val verses = readVerses(bookDir.resolve(indexFileName(book, VERSE)))
            val headings = readHeadings(bookDir.resolve(indexFileName(book, HEADING)))
            val chapters = readChapters(bookDir.resolve(indexFileName(book, CHAPTER)))
            val paragraphs = readDisjointRangeSet(bookDir.resolve(indexFileName(book, PARAGRAPH)))
            val footnotes = readFootnotes(bookDir.resolve(indexFileName(book, FOOTNOTE)))
            val poetry = readDisjointRangeSet(bookDir.resolve(indexFileName(book, POETRY, plural = false)))
            return BookData(book, text, verses, headings, chapters, paragraphs, footnotes, poetry)
        }

    }

}
