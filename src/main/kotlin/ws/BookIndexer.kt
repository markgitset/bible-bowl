package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet

private data class Footnote(val noteNum: Int, val verseRef: String, val noteText: String) {
    companion object {
        fun parse(line: String): Footnote {
            val (noteNum, verseRef, noteText) =
                """\s*\((\d+)\)\s+(\d+:\d+)\s+(.*?)\s*""".toRegex().matchEntire(line)?.destructured
                    ?: throw Exception("Unable to parse footnote: $line")
            return Footnote(noteNum.toInt(), verseRef, noteText)
        }
    }
}

class BookIndexer(val book: Book) {
    val buffer = StringBuilder()
    val verses = DisjointRangeMap<Int>()
    val headings = DisjointRangeMap<String>()
    val chapters = DisjointRangeMap<Int>()
    val paragraphs = DisjointRangeSet()
    val poetry = DisjointRangeSet()
    val footnotes = sortedMapOf<Int, String>()

    private var headingStart: Int = 0
    private var paragraphStart: Int = 0

    private lateinit var currentHeading: String

    fun indexBook(chapterPassages: Sequence<Passage>): BookData {
        chapterPassages.forEach { indexChapter(it) }
        endHeading()
        return BookData(book, buffer.toString(), verses, headings, chapters, paragraphs, footnotes, poetry)
    }

    private fun indexChapter(chapterPassage: Passage) {
        val chapterStart = buffer.length
        val currentChapter = chapterPassage.range.first.toVerseRef().chapter
        val lines: List<String> = chapterPassage.text.lines()
        var nextLineIsHeader = false
        var inFootnotes = false
        val noteOffsetsByNoteNumber = mutableMapOf<Int, Int>()
        var inPoetry = false
        var potentialPoetryStart = -1
        for (line in lines) {
            if (line.isBlank()) {
                if (inPoetry)
                    poetry.add(potentialPoetryStart until buffer.length)
                potentialPoetryStart = -1
                inPoetry = false
                continue
            } else {
                if (potentialPoetryStart < 0) potentialPoetryStart = buffer.length
                else
                    inPoetry = true
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
            if (line.trim().matches("""_+""".toRegex())) {
                potentialPoetryStart = -1
                nextLineIsHeader = true
                if (buffer.isNotEmpty()) endHeading()
                continue
            }
            paragraphStart = buffer.length
            val indexOf = line.indexOf('[')
            // indexOf('[') != 0 indicates that this line is continuing the verse from the previous line
            if (indexOf < 0) { // could be -1 or > 0 -- either way we need to continue the verse
                continueVerse(line, noteOffsetsByNoteNumber)
            } else if (indexOf > 0) { // could be -1 or > 0 -- either way we need to continue the verse
                continueVerse(line.substring(0 until indexOf), noteOffsetsByNoteNumber)
            }
            """\[(\d+)] ([^\[]+)""".toRegex().findAll(line).forEach { match ->
                val (verseNum, verseText) = match.destructured
                startVerse(currentChapter, verseNum.toInt(), verseText, noteOffsetsByNoteNumber)
            }
            paragraphs.add(paragraphStart until buffer.length)
            buffer.appendLine()
        }
        chapters[chapterStart until buffer.trimEnd().length] = currentChapter
    }

    private fun endHeading() {
        headings[headingStart until buffer.trimEnd().length] = currentHeading
        currentHeading = ""
    }

    private fun startVerse(chapter: Int, verseNum: Int, text: String, noteOffsetsByNoteNumber: MutableMap<Int, Int>) {
        val verseStart = buffer.length
        appendTextContainingFootnoteRefs(text, noteOffsetsByNoteNumber)
        verses[verseStart until buffer.trimEnd().length] = VerseRef(book, chapter, verseNum).toRefNum()
    }

    private fun appendTextContainingFootnoteRefs(text: String, noteOffsetsByNoteNumber: MutableMap<Int, Int>) {
        var lastStart = 0
        """\((\d+)\)""".toRegex().findAll(text).forEach {
            buffer.append(text.substring(lastStart, it.range.first))
            noteOffsetsByNoteNumber[it.groupValues[1].toInt()] = buffer.length
            lastStart = it.range.last + 1
        }
        buffer.append(text.substring(lastStart))
    }

    private fun continueVerse(text: String, noteOffsetsByNoteNumber: MutableMap<Int, Int>) {
        appendTextContainingFootnoteRefs(text, noteOffsetsByNoteNumber)
        val (currentVerseRange, verseRef) = verses.lastEntry() ?: return // null if no entries yet
        verses.putForcefully(currentVerseRange.first until buffer.trimEnd().length, verseRef)
    }

}
