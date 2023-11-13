package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.generate.excerpt
import net.markdrew.biblebowl.generate.indices.noBreak
import net.markdrew.biblebowl.generate.indices.withCount
import net.markdrew.biblebowl.generate.text.AnnotatedDoc
import net.markdrew.biblebowl.generate.text.toAnnotatedDoc
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.ws.downloadAndIndex
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.enclose
import net.markdrew.chupacabra.core.encloses
import net.markdrew.chupacabra.core.intersect
import net.markdrew.chupacabra.core.length
import net.markdrew.chupacabra.core.toDisjointRangeSet
import java.io.File
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths
import java.util.SortedMap
import kotlin.io.path.notExists

typealias CharOffset = Int

class StudyData(
    val studySet: StudySet,
    val text: String,
    val verses: DisjointRangeMap<VerseRef>,
    val headingCharRanges: DisjointRangeMap<String>,
    val chapters: DisjointRangeMap<ChapterRef>,
    val paragraphs: DisjointRangeMap<Int>, // char range to number of indents (for poetry lines)
    val footnotes: SortedMap<CharOffset, String>,
    val poetry: DisjointRangeSet,
) {

    val books: DisjointRangeMap<Book> by lazy {
        chapters.entries
            .groupingBy { (_, chapterRef) -> chapterRef.book }
            .aggregate { _, accumulator: IntRange?, (charRange), _ -> accumulator?.enclose(charRange) ?: charRange }
            .entries.associateTo(DisjointRangeMap()) { (book, charRange) -> charRange to book }
    }

    val verseIndex: Map<VerseRef, IntRange> by lazy {
        verses.entries.associate { (range, refNum) -> refNum to range }
    }

    /** Character ranges by chapter number */
    val chapterIndex: Map<ChapterRef, IntRange> by lazy {
        chapters.entries.associate { (range, chapterNum) -> chapterNum to range }
    }

    val wordIndex: Map<String, List<IntRange>> by lazy {
        words.groupBy { wordRange -> text.substring(wordRange).lowercase() }
    }

    val headings: List<Heading> by lazy {
        headingCharRanges.map { (headingCharRange, headingTitle) ->
            val verseRefs: List<VerseRef> = verses.valuesIntersectedBy(headingCharRange)
            Heading(headingTitle, verseRefs.first()..verseRefs.last())
        }
    }

    /**
     * Sentences (or sentence parts) that are guaranteed to NOT span more than one verse
     */
    val oneVerseSentParts: DisjointRangeMap<VerseRef> by lazy { verses.maskedBy(sentences) }

    val sentences: DisjointRangeSet by lazy { identifySentences(text) }

    val words: DisjointRangeSet by lazy { findAll(wordsPattern) }

    val isMultiBook: Boolean by lazy { books.size > 1 }

    val verseRefFormat: (VerseRef) -> String by lazy {
        if (isMultiBook) { verseRef: VerseRef -> verseRef.format(BRIEF_BOOK_FORMAT) }
        else { verseRef: VerseRef -> verseRef.format(NO_BOOK_FORMAT) }.noBreak()//.withCount()
    }

    val compactVerseRefListFormat: (List<VerseRef>) -> String by lazy {
        if (isMultiBook) { verseRefs: List<VerseRef> -> verseRefs.format(BRIEF_BOOK_FORMAT) }
        else { verseRefs: List<VerseRef> -> verseRefs.joinToString { verseRefFormat(it) } }
    }

    val compactWithCountVerseRefListFormat: (List<WithCount<VerseRef>>) -> String by lazy {
        if (isMultiBook) { verseRefs: List<WithCount<VerseRef>> -> verseRefs.formatWithCounts(BRIEF_BOOK_FORMAT) }
        else { verseRefs: List<WithCount<VerseRef>> -> verseRefs.joinToString(transform = verseRefFormat.withCount()) }
    }

    fun writeData(outPath: Path) {
        val outDir = outPath.resolve(studySet.simpleName)
        writeText(outDir.resolve(studySet.simpleName + ".txt"))
        writeVerseIndex(outDir.resolve(fileName(VERSE)))
        writeHeadingsIndex(outDir.resolve(fileName(HEADING)))
        writeChaptersIndex(outDir.resolve(fileName(CHAPTER)))
        writeParagraphsIndex(outDir.resolve(fileName(PARAGRAPH)))
        writeFootnotesIndex(outDir.resolve(fileName(FOOTNOTE)))
        writePoetryIndex(outDir.resolve(fileName(POETRY, plural = false)))
    }

    private fun fileName(unit: AnalysisUnit, plural: Boolean = true): String =
        indexFileName(studySet, unit, plural)

    private fun writeText(outPath: Path) {
        val outFile: File = outPath.toFile()
        outFile.parentFile.mkdirs()
        outFile.writeText(text)
        println("Wrote text to: $outPath")
    }

    private fun writeVerseIndex(outPath: Path) {
        writeIterable(outPath, verses.entries) { (range, verseRef) ->
            println("${range.first}\t${range.last}\t${verseRef.absoluteVerse}")
        }
    }

    private fun writeHeadingsIndex(outPath: Path) {
        writeIterable(outPath, headingCharRanges.entries) { (range, heading) ->
            println("${range.first}\t${range.last}\t$heading")
        }
    }

    private fun writeChaptersIndex(outPath: Path) {
        writeIterable(outPath, chapters.entries) { (range, chapterRef) ->
            println("${range.first}\t${range.last}\t${chapterRef.serialize()}")
        }
    }

    private fun writeParagraphsIndex(outPath: Path) {
        writeIterable(outPath, paragraphs.entries) { (range, indents) ->
            println("${range.first}\t${range.last}\t$indents")
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
        val verseRef: VerseRef? = verses.valueEnclosing(range)
        if (verseRef != null) return verseRef.format(NO_BOOK_FORMAT)

        val chapter: ChapterRef? = chapters.valueEnclosing(range)
        val heading: String? = headingCharRanges.valueEnclosing(range)
        if (heading != null) return "Chapter ${chapter?.chapter}: $heading"

        if (chapter != null) return "Chapter ${chapter.chapter}"

        return null
    }

    /** All chapter refs in this data, in Bible order */
    val chapterRefs: List<ChapterRef> = chapters.values.toList()

    /** The range of chapters in this book */
    val chapterRange: ChapterRange by lazy {
        val values = chapterRefs
        val min = values.minOrNull() ?: throw Exception("Should never happen!")
        val max = values.maxOrNull() ?: throw Exception("Should never happen!")
        min..max
    }

    val numberOfChapters: Int = chapters.size

    fun chapterRangeOfNChapters(nChapters: Int, offset: Int = 0): ChapterRange =
        with (chapterRefs.drop(offset).take(nChapters)) { first()..last() }

    /**
     * Returns the character range corresponding to the given chapter range
     */
    fun charRangeFromChapterRange(selectedChaptersRange: ChapterRange): IntRange {
        val chapters = chapterRange.intersect(selectedChaptersRange) // ensure a valid chapter range
        return chapterIndex.getValue(chapters.start).first..chapterIndex.getValue(chapters.endInclusive).last
    }

    /**
     * Returns the character range from the beginning through [lastChapter], or the whole book if [lastChapter] is null
     */
    fun charRangeThroughChapter(lastChapter: Int?): IntRange =
        if (lastChapter == null) text.indices
        else 0..chapters.entries.drop(lastChapter - 1).first().key.last

    /**
     * Returns chapter [[Heading]]s that intersect the given chapter range
     */
    @Deprecated("Doesn't work great for multi-book sets")
    fun headings(chapterRange: ChapterRange): List<Heading> =
        headings.filter { it.verseRange.start.chapterRef in chapterRefs }

    /**
     * Returns chapter [[Heading]]s that intersect the given chapter range
     */
    fun headings(chapterRefs: Collection<ChapterRef>): List<Heading> =
        headings.filter { it.chapterRange.start in chapterRefs }

    /**
     * Returns the given chapter number (with optional prefix/suffix) if it is less than the last chapter of the book,
     * otherwise an empty string
     */
    fun maxChapterOrEmpty(prefix: String = "", maxChapter: Int?, suffix: String = ""): String =
        maxChapter?.takeIf { it < chapterRange.endInclusive.chapter }?.let { prefix + it + suffix }.orEmpty()

    /**
     * Returns the given chapter range (with optional prefix/suffix) if it is less than all chapters of the book,
     * otherwise an empty string
     */
    fun chapterRangeOrEmpty(prefix: String = "", range: ChapterRange?, suffix: String = ""): String =
        range?.takeIf { it != chapterRange }
            ?.let { prefix + it + suffix }
            .orEmpty()

    /**
     * For multi-book sets, these are relative chapters, not necessarily book chapters
     */
    fun relativeChapterRange(first: Int, last: Int): ChapterRange =
        with (chapterRefs.toList()) { this[first - 1]..this[last - 1] }

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
        verses.entries.map { (range, verseRef) -> ReferencedVerse(verseRef, text.substring(range)) }

    fun getVerse(verseRef: VerseRef): String = text.substring(verseIndex.getValue(verseRef))
    fun verseEnclosing(charRange: IntRange): VerseRef? = verses.valueEnclosing(charRange)
    fun verseContaining(charOffset: CharOffset): VerseRef? = verses.valueContaining(charOffset)

    /**
     * Returns all [VerseRef]s containing the words of the given phrase (ignoring case and punctuation)
     */
//    fun versesContainingPhrase(phraseCharOffsets: IntRange): List<VerseRef> {
//        val phraseWords: List<String> = words.enclosedBy(phraseCharOffsets).map { text.substring(it).lowercase() }
//        phraseWords.
//    }

    fun findAll(vararg patterns: Regex): DisjointRangeSet =
        patterns.fold(DisjointRangeSet()) { drs, pattern ->
            pattern.findAll(text).map { it.range }.forEach { r ->
                // if two (or more) patterns overlap, and one encloses the other, keep only the longer range
                val intersections: DisjointRangeSet = drs.rangesIntersectedBy(r)
                if (intersections.isNotEmpty()) { // can have zero, one, or more intersections
                    val maxR: IntRange = sequenceOf(r, *intersections.toTypedArray()).maxBy { it.length() }
                    drs.addForcefully(maxR)
                } else drs.add(r) // success when no intersections
            }
            drs
        }

    @Deprecated("doesn't work well with multi-book sets")
    fun practice(throughChapter: Int?): PracticeContent {
        if (throughChapter == null) return practice(chapterRange)
        require(throughChapter > 0)
        val firstChapterRef: ChapterRef = chapters.values.first()
        val lastChapterRef: ChapterRef = chapters.values.drop(throughChapter - 1).first()
        return practice(firstChapterRef..lastChapterRef)
    }

    @Deprecated("doesn't work well with multi-book sets")
    fun practice(chapters: ChapterRange = chapterRange): PracticeContent = practice(chapters.endInclusive)

    fun practice(throughChapter: ChapterRef?): PracticeContent =
        if (throughChapter == null) PracticeContent(this)
        else PracticeContent(this, chapterRefs.take(chapterRefs.indexOf(throughChapter) + 1))

    companion object {

        internal val wordsPattern = """\d{1,3}(?:,\d{3})+|[-\w]+(?:[’']s)?""".toRegex()

        private fun <T> writeIterable(outPath: Path, iterable: Iterable<T>, formatFun: PrintWriter.(T) -> Unit) {
            outPath.toFile().printWriter().use { pw ->
                iterable.forEach { pw.formatFun(it) }
            }
            println("Wrote data to: $outPath")
        }

        private fun indexFileName(studySet: StudySet, unit: AnalysisUnit, plural: Boolean = true): String =
            "${studySet.simpleName}-${unit.name.lowercase()}${if (plural) "s" else ""}.tsv"

//        private fun textFileName(studySet: StudySet): String = book.name.lowercase() + ".txt"

        private fun readVerses(inPath: Path): DisjointRangeMap<VerseRef> = inPath.toFile().useLines { linesSeq ->
            linesSeq.map { line ->
                val (first, last, verseRefNum) = line.split('\t').map { it.toInt() }
                first..last to verseRefNum.toVerseRef()
            }.toMap(DisjointRangeMap())
        }

        private fun readParagraphs(inPath: Path): DisjointRangeMap<Int> =
            inPath.toFile().useLines { linesSeq ->
                linesSeq.map { line ->
                    val (first, last, indents) = line.split('\t')
                    first.toInt()..last.toInt() to indents.toInt()
                }.toMap(DisjointRangeMap())
            }

        private fun readChapters(inPath: Path): DisjointRangeMap<ChapterRef> =
            inPath.toFile().useLines { linesSeq ->
                linesSeq.map { line ->
                    val (first, last, chapterRef) = line.split('\t')
                    first.toInt()..last.toInt() to ChapterRef.deserialize(chapterRef)
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

        fun readData(studySet: StudySet = StandardStudySet.DEFAULT, inPath: Path = Paths.get(DATA_DIR)): StudyData {
            val bookDir = inPath.resolve(studySet.simpleName)
            if (bookDir.notExists()) {
                downloadAndIndex(studySet)
            }
            val text = bookDir.resolve(studySet.simpleName + ".txt").toFile().readText()
            val verses = readVerses(bookDir.resolve(indexFileName(studySet, VERSE)))
            val headings = readHeadings(bookDir.resolve(indexFileName(studySet, HEADING)))
            val chapters = readChapters(bookDir.resolve(indexFileName(studySet, CHAPTER)))
            val paragraphs = readParagraphs(bookDir.resolve(indexFileName(studySet, PARAGRAPH)))
            val footnotes = readFootnotes(bookDir.resolve(indexFileName(studySet, FOOTNOTE)))
            val poetry = readDisjointRangeSet(bookDir.resolve(indexFileName(studySet, POETRY, plural = false)))
            return StudyData(studySet, text, verses, headings, chapters, paragraphs, footnotes, poetry)
        }

    }

}

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR))
//    for (w in StudyData.words) {
//        if (StudyData.chapterIndex[8]!!.encloses(w))
//            println(StudyData.excerpt(w))
//    } // worked
//    for (w in oneTimeWords(StudyData)) {
//        if (StudyData.chapterIndex[8]!!.encloses(w))
//            println(StudyData.excerpt(w))
//    } // worked

    val annotatedDoc: AnnotatedDoc<AnalysisUnit> = studyData.toAnnotatedDoc(
        CHAPTER, HEADING, VERSE, POETRY, PARAGRAPH, FOOTNOTE
    ).apply {
        setAnnotations(AnalysisUnit.UNIQUE_WORD, DisjointRangeSet(oneTimeWords(studyData)))
    }
    for (tr in annotatedDoc.textRuns()) {
        if (studyData.chapterIndex[ChapterRef(Book.MAT, 8)]!!.encloses(tr.range)) {
            println("${studyData.excerpt(tr.range).excerptText} $tr")
        }
    }
}