package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet

class BookIndexer(val book: Book) {
    val buffer = StringBuilder()
    val verses = DisjointRangeMap<Int>()
    val headings = DisjointRangeMap<String>()
    val chapters = DisjointRangeMap<Int>()
    val paragraphs = DisjointRangeSet()

    private var headingStart: Int = 0
    private var chapterStart: Int = 0
    private var paragraphStart: Int = 0

    private var currentChapter: Int = 0
    private lateinit var currentHeading: String

    fun indexBook(chapterPassages: Sequence<Passage>): BookData {
        chapterPassages.forEach { indexChapter(it) }
        endHeading()
        return BookData(book, buffer.toString(), verses, headings, chapters, paragraphs)
    }

    fun indexChapter(chapterPassage: Passage) {
        chapterStart = buffer.length
        currentChapter = chapterPassage.range.first.toVerseRef().chapter
        val lines: List<String> = chapterPassage.text.lines()
        var nextLineIsHeader = false
        for (line in lines) {
            if (line.isBlank()) {
                continue
            }
            if (nextLineIsHeader) {
                currentHeading = line.trim()
                headingStart = buffer.length
                nextLineIsHeader = false
                continue
            }
            if (line.trim().matches("""_+""".toRegex())) {
                nextLineIsHeader = true
                if (buffer.isNotEmpty()) endHeading()
                continue
            }
            paragraphStart = buffer.length
            val indexOf = line.indexOf('[')
            if (indexOf < 0) { // could be -1 or > 0 -- either way we need to continue the verse
                continueVerse(line)
            } else if (indexOf > 0) { // could be -1 or > 0 -- either way we need to continue the verse
                continueVerse(line.substring(0 until indexOf))
            }
            """\[(\d+)] ([^\[]+)""".toRegex().findAll(line).forEach { match ->
                val (verseNum, verseText) = match.destructured
                startVerse(verseNum.toInt(), verseText)
            }
            paragraphs.add(paragraphStart until buffer.length)
            buffer.appendLine()
        }
        chapters[chapterStart until buffer.trimEnd().length] = currentChapter
    }

    fun endHeading() {
        headings[headingStart until buffer.trimEnd().length] = currentHeading
        currentHeading = ""
    }

    fun startVerse(verseNum: Int, text: String) {
        val verseStart = buffer.length
        buffer.append(text)
        verses[verseStart until buffer.trimEnd().length] = VerseRef(book, currentChapter, verseNum).toRefNum()
    }

    fun continueVerse(text: String) {
        buffer.append(text)
        val (currentVerseRange, verseRef) = verses.lastEntry() ?: return // null if no entries yet
        verses.putForcefully(currentVerseRange.first until buffer.trimEnd().length, verseRef)
    }
}

