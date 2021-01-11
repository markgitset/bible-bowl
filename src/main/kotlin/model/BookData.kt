package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.model.AnalysisUnit.*
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.toDisjointRangeSet
import java.io.PrintWriter
import java.nio.file.Path

class BookData(val book: Book,
               val text: String,
               val verses: DisjointRangeMap<Int>,
               val headings: DisjointRangeMap<String>,
               val chapters: DisjointRangeMap<Int>,
               val paragraphs: DisjointRangeSet) {

    val verseIndex: Map<Int, IntRange> by lazy {
        verses.entries.map { (range, refNum) -> refNum to range }.toMap()
    }

    val chapterIndex: Map<Int, IntRange> by lazy {
        chapters.entries.map { (range, chapterNum) -> chapterNum to range }.toMap()
    }

    val wordIndex: Map<String, List<IntRange>> by lazy {
        words.groupBy { wordRange -> text.substring(wordRange).toLowerCase() }
    }

    val sentences: DisjointRangeSet by lazy { identifySentences(text) }

    val words: DisjointRangeSet by lazy {
        """\d{1,3}(?:,\d{3})+|\w+(?:[’']s)?""".toRegex().findAll(text).map { it.range }.toDisjointRangeSet()
    }

    fun writeData(outPath: Path) {
        val outDir = outPath.resolve(book.name.toLowerCase())
        writeText(outDir.resolve(textFileName(book)))
        writeVerseIndex(outDir.resolve(fileName(VERSE)))
        writeHeadingsIndex(outDir.resolve(fileName(HEADING)))
        writeChaptersIndex(outDir.resolve(fileName(CHAPTER)))
        writeParagraphsIndex(outDir.resolve(fileName(PARAGRAPH)))
    }

    private fun fileName(unit: AnalysisUnit): String = indexFileName(book, unit)

    private fun writeText(outPath: Path) {
        outPath.toFile().writeText(text)
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

    /**
     * Returns a name/description of the smallest, named range (verse, heading, or chapter) that encloses the given
     * range.  E.g., given a single would return a verse reference string, or given a few verses in the same chapter
     * heading, would return that chapter heading.
     */
    fun smallestNamedRange(range: IntRange): String? {
        val verseRefNum: Int? = verses.valueEnclosing(range)
        if (verseRefNum != null) return verseRefNum.toVerseRef().toChapterAndVerseString()

        val chapter: Int? = chapters.valueEnclosing(range)
        val heading: String? = headings.valueEnclosing(range)
        if (heading != null) return "Chapter $chapter: $heading"

        if (chapter != null) return "Chapter $chapter"

        return null
    }

    fun verseList(): List<ReferencedVerse> =
        verses.entries.map { (range, verseRefNum) -> ReferencedVerse(verseRefNum.toVerseRef(), text.substring(range)) }

    fun getVerse(verseReference: VerseRef): String = text.substring(verseIndex.getValue(verseReference.toRefNum()))

    companion object {

        private fun <T> writeIterable(outPath: Path, iterable: Iterable<T>, formatFun: PrintWriter.(T) -> Unit) {
            outPath.toFile().printWriter().use { pw ->
                iterable.forEach { pw.formatFun(it) }
            }
            println("Wrote data to: $outPath")
        }

        private fun indexFileName(book: Book, unit: AnalysisUnit): String =
            "${book.name.toLowerCase()}-${unit.name.toLowerCase()}s.tsv"

        private fun textFileName(book: Book): String = book.name.toLowerCase() + ".txt"

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

        private fun readParagraphs(inPath: Path): DisjointRangeSet = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (first, last) = line.split('\t').map { it.toInt() }
                first..last
            }.toDisjointRangeSet()
        }

        fun readData(inPath: Path, book: Book): BookData {
            val bookDir = inPath.resolve(book.name.toLowerCase())
            val text = bookDir.resolve(textFileName(book)).toFile().readText()
            val verses = readVerses(bookDir.resolve(indexFileName(book, VERSE)))
            val headings = readHeadings(bookDir.resolve(indexFileName(book, HEADING)))
            val chapters = readChapters(bookDir.resolve(indexFileName(book, CHAPTER)))
            val paragraphs = readParagraphs(bookDir.resolve(indexFileName(book, PARAGRAPH)))
            return BookData(book, text, verses, headings, chapters, paragraphs)
        }

    }
}