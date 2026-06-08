package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.model.AbsoluteVerseNum
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import kotlin.math.max

/**
 * Parses the rendered text returned by the ESV API into [StudyData]
 *
 * Walks chapter passages line-by-line and incrementally appends them to a single buffer, keeping disjoint
 * range maps for verses, headings, chapters, paragraphs, and poetry, plus a sorted map from footnote anchor
 * offsets to footnote text. Recognizes ESV's text-format conventions: heading separator lines (`____`),
 * verse markers (`[N]`), poetry blocks (delimited by blank lines plus a trailing indent line), and
 * footnote callouts (`(N)`) with a "Footnotes" trailer.
 *
 * The intermediate `verses`/`headings`/etc. fields are public to make the parsing stages testable; in
 * normal use callers should just invoke [indexBook] and consume the resulting [StudyData].
 */
class EsvIndexer(val studySet: StudySet) {
    val buffer = StringBuilder()
    val verses = DisjointRangeMap<VerseRef>()
    val headings = DisjointRangeMap<String>()
    val chapters = DisjointRangeMap<ChapterRef>()
    val paragraphs = DisjointRangeMap<Int>() // char range -> number of indents (for poetry lines)
    val poetry = DisjointRangeSet()
    val footnotes = sortedMapOf<Int, String>() // char offset -> footnote text

    private var headingStart: Int = 0
    private var paragraphStart: Int = 0

    private lateinit var currentHeading: String

    private fun skipSpace(startAt: Int): Int {
        var newOffset = startAt
        while (newOffset < buffer.length && buffer[newOffset].isWhitespace()) newOffset++
        return newOffset
    }

    /** Indexes every chapter in [chapterPassages] in order and returns the resulting [StudyData]. */
    fun indexBook(chapterPassages: Sequence<Passage>): StudyData {
        chapterPassages.forEach {
            indexChapter(it)
        }
        endHeading()
        return StudyData(studySet, buffer.toString(), verses, headings, chapters, paragraphs, footnotes, poetry)
    }

    // It seems that there are [INDENT_POETRY_LINES] spaces on a line by themselves after each poetry block
    private val postPoetryLine = " ".repeat(INDENT_POETRY_LINES)

    private fun indexChapter(chapterPassage: Passage) {
        val chapterStart = buffer.length
        val currentChapter = chapterPassage.range.first.toVerseRef().chapterRef
        var nextLineIsHeader = false
        var inFootnotes = false
        val noteOffsetsByNoteNumber = mutableMapOf<Int, Int>()
        var prevLineWasBlank = false
        var potentialPoetryStart = -1
        var lineCount = 0
        for (line in chapterPassage.text.lines()) {
            lineCount += 1
            if (line.isBlank()) {
                if (prevLineWasBlank) // poetry section ends with 2 blank lines
                    poetry.add(skipSpace(potentialPoetryStart) until buffer.length)
                else if (potentialPoetryStart >= 0 && !inFootnotes) {
                    prevLineWasBlank = line == postPoetryLine
                    if (!prevLineWasBlank) potentialPoetryStart = -1
                    continue
                }
                potentialPoetryStart = -1
                prevLineWasBlank = false
                continue
            } else {
                if (prevLineWasBlank || potentialPoetryStart < 0) potentialPoetryStart = buffer.length
                prevLineWasBlank = false
            }
            if (line.trim().lowercase() == "footnotes") {
                inFootnotes = true
                continue
            }
            if (inFootnotes) {
                val footnote = Footnote.parse(line)
                val offset: Int = noteOffsetsByNoteNumber[footnote.noteNum]
                    ?: throw Exception("Didn't find reference to footnote in the text: $footnote")
                footnotes[offset] = footnote.noteText
                continue
            }
            if (nextLineIsHeader) {
                potentialPoetryStart = -1
                currentHeading = line.trim()
                headingStart = buffer.length
                nextLineIsHeader = false
                continue
            }
            if (line.trim().matches(HEADER_SEPARATOR_REGEX)) {
                potentialPoetryStart = -1
                nextLineIsHeader = true
                if (buffer.isNotEmpty()) endHeading()
                continue
            }
            paragraphStart = buffer.length// + line.indexOfFirst { !it.isWhitespace() }
            val indexOf = line.indexOf('[')
            // indexOf('[') != 0 indicates that this line is continuing the verse from the previous line
            if (indexOf < 0) { // could be -1 or > 0 -- either way we need to continue the verse
                continueVerse(line, noteOffsetsByNoteNumber)
            } else if (indexOf > 0) { // could be -1 or > 0 -- either way we need to continue the verse
                continueVerse(line.substring(0 until indexOf), noteOffsetsByNoteNumber)
            }

// TODO            TRYING TO FIGURE OUT HOW TO INCLUDE LAST SPACE OF A VERSE WITHIN THE VERSE'S RANGE

            VERSE_START_REGEX.findAll(line).forEach { match ->
                val (verseNum, verseText) = match.destructured
                startVerse(currentChapter, verseNum.toInt(), verseText, noteOffsetsByNoteNumber)
            }
            val spaceCount: Int = max(0, buffer.substring(paragraphStart).indexOfFirst { !it.isWhitespace() })
            val indentCount: Int = spaceCount / 4
            paragraphs[(paragraphStart + spaceCount) until buffer.length] = indentCount
            buffer.appendLine()
        }
        chapters[skipSpace(chapterStart) until buffer.trimEnd().length] = currentChapter
    }

    private fun endHeading() {
        headings[skipSpace(headingStart) until buffer.trimEnd().length] = currentHeading
        currentHeading = ""
    }

    private fun startVerse(
        chapter: ChapterRef,
        verseNum: AbsoluteVerseNum,
        text: String,
        noteOffsetsByNoteNumber: MutableMap<Int, Int>
    ) {
        val verseStart = buffer.length
        appendTextContainingFootnoteRefs(text, noteOffsetsByNoteNumber)
        verses[skipSpace(verseStart) until buffer.trimEnd().length] = chapter.verse(verseNum)
    }

    private fun appendTextContainingFootnoteRefs(text: String, noteOffsetsByNoteNumber: MutableMap<Int, Int>) {
        var lastStart = 0
        FOOTNOTE_REF_REGEX.findAll(text).forEach {
            buffer.append(text.substring(lastStart, it.range.first))
            val noteNumber = it.groupValues[1].toInt()

            // Handle leading footnotes (shifting past the following space to the start of the next word)
            val shiftNoteRight = (it.range.first == 0 || text[it.range.first - 1] == ' ')
                    && text.length > it.range.last + 1 && text[it.range.last + 1] == ' '

            // Handle regular footnotes (shifting past any trailing punctuation so they anchor after commas/periods)
            val textAfterFootnote = text.substring(it.range.last + 1)
            val punctuationShift = textAfterFootnote.takeWhile { char -> char in ".,;:?!'\"”’)]" }.length

            noteOffsetsByNoteNumber[noteNumber] = buffer.length + (if (shiftNoteRight) 1 else punctuationShift)
            lastStart = it.range.last + 1
        }
        buffer.append(text.substring(lastStart))
    }

    private fun continueVerse(text: String, noteOffsetsByNoteNumber: MutableMap<Int, Int>) {
        appendTextContainingFootnoteRefs(text, noteOffsetsByNoteNumber)
        val (currentVerseRange, verseRef) = verses.lastEntry() ?: return // null if no entries yet
        verses.putForcefully(currentVerseRange.first until buffer.trimEnd().length, verseRef)
    }

    companion object {
        private val HEADER_SEPARATOR_REGEX = """_+""".toRegex()
        private val VERSE_START_REGEX = """\[(\d+)] ([^\[]+)""".toRegex()
        private val FOOTNOTE_REF_REGEX = """\((\d+)\)""".toRegex()

        private data class Footnote(val noteNum: Int, val verseRef: String, val noteText: String) {
            companion object {
                private val FOOTNOTE_PARSE_REGEX = """\s*\((\d+)\)\s+(\d+:\d+)\s+(.*?)\s*""".toRegex()
                fun parse(line: String): Footnote {
                    val (noteNum, verseRef, noteText) =
                        FOOTNOTE_PARSE_REGEX.matchEntire(line)?.destructured
                            ?: throw Exception("Unable to parse footnote: $line")
                    return Footnote(noteNum.toInt(), verseRef, noteText)
                }
            }
        }
    }
}