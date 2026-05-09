package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.RAW_DATA_DIR_NAME
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultRawDataPath
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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.SortedMap
import kotlin.io.path.notExists
import kotlin.io.path.useLines

/** A character offset into [StudyData.text] */
typealias CharOffset = Int

/** A character offset range over [StudyData.text] */
typealias CharOffsetRange = IntRange

/**
 * The fully-indexed in-memory representation of a [StudySet]'s ESV text
 *
 * Generators consume [StudyData] rather than re-parsing text. The full study set is one joined string
 * ([text]); structural features (verses, headings, chapters, paragraphs, poetry, footnotes) are attached as
 * disjoint character-offset annotations from the chupacabra library. Many derived views are exposed as `lazy`
 * properties so callers pay only for what they use.
 *
 * [readData] reconstructs an instance from a previously persisted directory; [writeData] writes one to disk
 * in the same TSV-based format.
 *
 * @param studySet which Bible portion this data represents
 * @param text the joined ESV text of every chapter in [studySet], in canonical order
 * @param verses per-verse character ranges over [text]
 * @param headingCharRanges per-heading character ranges over [text], mapped to the heading title
 * @param chapters per-chapter character ranges over [text]
 * @param paragraphs per-paragraph character ranges over [text]; the value is the indent depth (0 for prose,
 *   non-zero for poetry lines)
 * @param footnotes character offsets at which a footnote anchor sits, mapped to the footnote text
 * @param poetry character ranges over [text] that are typeset as poetry
 */
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

    /** Character ranges grouped by [Book]; one entry per book contained in [studySet] */
    val books: DisjointRangeMap<Book> by lazy {
        chapters.entries
            .groupingBy { (_, chapterRef) -> chapterRef.book }
            .aggregate { _, accumulator: IntRange?, (charRange), _ -> accumulator?.enclose(charRange) ?: charRange }
            .entries.associateTo(DisjointRangeMap()) { (book, charRange) -> charRange to book }
    }

    /** Inverse of [verses]: character range for a given [VerseRef] */
    val verseIndex: Map<VerseRef, IntRange> by lazy {
        verses.entries.associate { (range, refNum) -> refNum to range }
    }

    /** Inverse of [chapters]: character range for a given [ChapterRef] */
    val chapterIndex: Map<ChapterRef, IntRange> by lazy {
        chapters.entries.associate { (range, chapterNum) -> chapterNum to range }
    }

    /** Lowercased word -> every character range where that word occurs */
    val wordIndex: Map<String, List<IntRange>> by lazy {
        words.groupBy { wordRange -> text.substring(wordRange).lowercase() }
    }

    /** Headings as ordered domain objects, each carrying its 1-based index and the total heading count */
    val headings: List<Heading> by lazy {
        val maxIndex: Int = headingCharRanges.size
        headingCharRanges.entries.mapIndexed { index, (headingCharRange, headingTitle) ->
            val verseRefs: List<VerseRef> = verses.valuesIntersectedBy(headingCharRange)
            Heading(headingTitle, verseRefs.first()..verseRefs.last(), index + 1, maxIndex)
        }
    }

    /** Sentences (or sentence fragments) guaranteed not to span more than one verse, keyed by that verse */
    val oneVerseSentParts: DisjointRangeMap<VerseRef> by lazy { verses.maskedBy(sentences) }

    /** All sentence ranges in [text], computed from a US-English [java.text.BreakIterator] */
    val sentences: DisjointRangeSet by lazy { identifySentences(text) }

    /** Every word range in [text], using [wordsPattern] */
    val words: DisjointRangeSet by lazy { findAll(wordsPattern) }

    /** True if [studySet] spans more than one [Book] */
    val isMultiBook: Boolean by lazy { books.size > 1 }

    /** Renders a [VerseRef] for inline display, including the book name only when [isMultiBook] */
    val verseRefFormat: (VerseRef) -> String by lazy {
        if (isMultiBook) { verseRef: VerseRef -> verseRef.format(BRIEF_BOOK_FORMAT) }
        else { verseRef: VerseRef -> verseRef.format(NO_BOOK_FORMAT) }.noBreak()
    }

    /** Renders a [ChapterRef] for inline display, including the book name only when [isMultiBook] */
    val chapterRefFormat: (ChapterRef) -> String by lazy {
        if (isMultiBook) { chapterRef: ChapterRef -> chapterRef.format(BRIEF_BOOK_FORMAT) }
        else { chapterRef: ChapterRef -> chapterRef.format(NO_BOOK_FORMAT) }.noBreak()
    }

    /** Renders a list of [VerseRef]s compactly (e.g. "Mat 4:5,6; 6:8,10") */
    val compactVerseRefListFormat: (List<VerseRef>) -> String by lazy {
        if (isMultiBook) { verseRefs: List<VerseRef> -> verseRefs.format(BRIEF_BOOK_FORMAT) }
        else { verseRefs: List<VerseRef> -> verseRefs.joinToString { verseRefFormat(it) } }
    }

    /** Like [compactVerseRefListFormat] but appending a frequency count to each verse */
    val compactWithCountVerseRefListFormat: (List<WithCount<VerseRef>>) -> String by lazy {
        if (isMultiBook) { verseRefs: List<WithCount<VerseRef>> -> verseRefs.formatWithCounts(BRIEF_BOOK_FORMAT) }
        else { verseRefs: List<WithCount<VerseRef>> -> verseRefs.joinToString(transform = verseRefFormat.withCount()) }
    }

    /**
     * Persists this [StudyData] under `<outPath>/<studySet.simpleName>/`, writing the joined text and one TSV
     * file per annotation kind. Round-trips with [readData].
     */
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
        val timestamp: String = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(
            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        )
        outFile.writeText("# Downloaded on $timestamp\n$text")
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
     * Returns a label for the smallest named range (verse, heading, or chapter) that encloses [range].
     *
     * For example, a range inside a single verse returns the verse reference; a range covering a few verses
     * within one heading returns the heading; a range across an unnamed span returns null.
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

    /** Every chapter in [studySet], in canonical Bible order */
    val chapterRefs: List<ChapterRef> = chapters.values.toList()

    /** The first-to-last chapter range covered by this data */
    val chapterRange: ChapterRange by lazy {
        val values = chapterRefs
        val min = values.minOrNull() ?: throw Exception("Should never happen!")
        val max = values.maxOrNull() ?: throw Exception("Should never happen!")
        min..max
    }

    /** Returns the character range over [text] that corresponds to [selectedChaptersRange]. */
    fun charRangeFromChapterRange(selectedChaptersRange: ChapterRange): IntRange {
        val chapters = chapterRange.intersect(selectedChaptersRange) // ensure a valid chapter range
        return chapterIndex.getValue(chapters.start).first..chapterIndex.getValue(chapters.endInclusive).last
    }

    /** Returns the character range from the start of [text] through the end of [lastChapter]; if null, returns all of [text]. */
    fun charRangeThroughChapter(lastChapter: Int?): IntRange =
        if (lastChapter == null) text.indices
        else 0..chapters.entries.drop(lastChapter - 1).first().key.last

    /** Returns the [Heading]s whose chapter falls within [chapterRange]. */
    fun headings(chapterRange: ChapterRange): List<Heading> =
        headings.filter { it.verseRange.start.chapterRef in chapterRange }

    /** Returns the [Heading]s whose first chapter is one of [chapterRefs]. */
    fun headings(chapterRefs: Collection<ChapterRef>): List<Heading> =
        headings.filter { it.chapterRange.start in chapterRefs }

    /**
     * Renders [maxChapter] as `prefix$it$suffix` if it falls strictly inside this study set's chapter range,
     * or returns an empty string otherwise (so callers can splice it into messages without an `if`).
     */
    fun maxChapterOrEmpty(prefix: String = "", maxChapter: Int?, suffix: String = ""): String =
        maxChapter?.takeIf { it < chapterRange.endInclusive.chapter }?.let { prefix + it + suffix }.orEmpty()

    /**
     * Renders [range] as `prefix$it$suffix` if it is a strict subset of this study set's full chapter range,
     * or returns an empty string otherwise.
     */
    fun chapterRangeOrEmpty(prefix: String = "", range: ChapterRange?, suffix: String = ""): String =
        range?.takeIf { it != chapterRange }
            ?.let { prefix + it + suffix }
            .orEmpty()

    /**
     * Returns the [first]..[last] chapter range using 1-based positions within this study set's chapters.
     *
     * For multi-book sets these are relative positions, not per-book chapter numbers.
     */
    fun relativeChapterRange(first: Int, last: Int): ChapterRange =
        with (chapterRefs.toList()) { this[first - 1]..this[last - 1] }

    /** Returns the sentence range that fully contains [range], or null if none does. */
    fun enclosingSentence(range: IntRange): IntRange? = sentences.enclosing(range)

    /** Returns the surrounding sentence as an [Excerpt], or null if no sentence fully contains [range]. */
    fun sentenceContext(range: IntRange): Excerpt? = enclosingSentence(range)?.let { excerpt(it) }

    /** Slices [text] into an [Excerpt] over the given character [range]. */
    fun excerpt(range: IntRange): Excerpt = text.excerpt(range)

    /** Returns the number of [words] entirely contained in [range]. */
    fun wordCount(range: IntRange): Int = words.enclosedBy(range).size

//    fun splitLong(textRange: IntRange, maxLengthGoal: Int = 22): List<IntRange> {
//        if (wordCount(textRange) <= maxLengthGoal) return listOf(textRange)
//        TODO()
//        return listOf(textRange)
//    }

    /** Like [enclosingSentence] but only returns sentences/sentence parts that don't cross verse boundaries. */
    fun enclosingSingleVerseSentence(range: IntRange): IntRange? = oneVerseSentParts.keyEnclosing(range)

    /** [enclosingSingleVerseSentence] as an [Excerpt]. */
    fun singleVerseSentenceContext(range: IntRange): Excerpt? =
        enclosingSingleVerseSentence(range)?.let { excerpt(it) }

    /** Materializes every verse as a [ReferencedVerse]. */
    fun verseList(): List<ReferencedVerse> =
        verses.entries.map { (range, verseRef) -> ReferencedVerse(verseRef, text.substring(range)) }

    /** Returns the text of the given [verseRef]. */
    fun getVerse(verseRef: VerseRef): String = text.substring(verseIndex.getValue(verseRef))

    /** Returns the verse that fully contains [charRange], or null if none does. */
    fun verseEnclosing(charRange: CharOffsetRange): VerseRef? = verses.valueEnclosing(charRange)

    /**
     * Returns the heading that fully contains [verseRef], or null if [verseRef] straddles a heading boundary
     * (which can happen for verses that span more than one heading).
     */
    fun headingEnclosing(verseRef: VerseRef): String? = headingCharRanges.valueEnclosing(verseIndex.getValue(verseRef))

    /**
     * Returns the heading that fully contains [verseRange], or null if [verseRange] straddles a heading
     * boundary.
     */
    fun headingEnclosing(verseRange: VerseRange): String? =
        headingCharRanges.valueEnclosing(verseRangeToCharRange(verseRange))

    /**
     * Returns every heading that intersects [verseRef].
     *
     * Usually exactly one, but some verses span more than one heading.
     */
    fun headingsIntersecting(verseRef: VerseRef): List<String> =
        headingCharRanges.valuesIntersectedBy(verseIndex.getValue(verseRef))

    /**
     * Returns every heading that intersects [verseRange].
     *
     * Usually exactly one, but some verse ranges span more than one heading.
     */
    fun headingsIntersecting(verseRange: VerseRange): List<String> =
        headingCharRanges.valuesIntersectedBy(verseRangeToCharRange(verseRange))

    private fun verseRangeToCharRange(verseRange: VerseRange): IntRange =
        verseIndex.getValue(verseRange.start).first..verseIndex.getValue(verseRange.endInclusive).last

    /** Returns the chapter that fully contains [charRange], or null if none does. */
    fun chapterEnclosing(charRange: CharOffsetRange): ChapterRef? = chapters.valueEnclosing(charRange)

    /** Returns the verse containing [charOffset], or null if [charOffset] sits between verses. */
    fun verseContaining(charOffset: CharOffset): VerseRef? = verses.valueContaining(charOffset)

    /**
     * Returns all [VerseRef]s containing the words of the given phrase (ignoring case and punctuation)
     */
//    fun versesContainingPhrase(phraseCharOffsets: IntRange): List<VerseRef> {
//        val phraseWords: List<String> = words.enclosedBy(phraseCharOffsets).map { text.substring(it).lowercase() }
//        phraseWords.
//    }

    /**
     * Runs every regex in [patterns] against [text] and returns the union of their match ranges.
     *
     * When two patterns produce overlapping matches, the longer range wins (the shorter is discarded).
     */
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

    /**
     * Returns a [PracticeContent] view of this data, optionally limited to the chapters up through
     * [throughChapter] (inclusive) for cumulative practice.
     */
    fun practice(throughChapter: ChapterRef? = null): PracticeContent =
        if (throughChapter == null) PracticeContent(this)
        else PracticeContent(this, chapterRefs.take(chapterRefs.indexOf(throughChapter) + 1))

    companion object {

        /**
         * Pattern for tokenizing words: matches a comma-grouped number (e.g. "1,234") or a hyphenated word
         * with an optional possessive ending
         */
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

        /**
         * Loads previously persisted [StudyData] for [studySet] from [dataDir].
         *
         * If the per-study-set directory doesn't exist, falls back to downloading and indexing from the ESV
         * API (using [rawDataDir] as the download cache). Set [forceDownload] true to bypass any existing
         * raw cache and re-fetch from the API.
         */
        fun readData(
            studySet: StudySet = StandardStudySet.DEFAULT,
            dataDir: Path = defaultDataPath,
            rawDataDir: Path = defaultRawDataPath,
            forceDownload: Boolean = false
        ): StudyData {
            val bookDir = dataDir.resolve(studySet.simpleName)
            if (bookDir.notExists()) {
                downloadAndIndex(studySet, dataDir, rawDataDir, forceDownload)
            }
            val text = bookDir.resolve(studySet.simpleName + ".txt").useLines { lines ->
                // Ignore leading comments (that start with '#')
                lines.dropWhile { it.startsWith("#") }.joinToString("\n")
            }
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
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR_NAME), Paths.get(RAW_DATA_DIR_NAME))
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