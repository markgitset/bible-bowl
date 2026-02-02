package net.markdrew.biblebowl.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class VerseRangeParseTest {

    @Test
    fun `parse valid ranges`() {
        parseValidRange("John 3:16", Book.JOH, 3, 16, 3, 16)
        parseValidRange("John 3:16-John 3:18", Book.JOH, 3, 16, 3, 18)
        parseValidRange("Genesis 1:1-Genesis 1:5", Book.GEN, 1, 1, 1, 5)
        parseValidRange("  Matthew 5:3 - Matthew 5:9  ", Book.MAT, 5, 3, 5, 9)
        parseValidRange("Genesis 1:1-Matthew 1:1", Book.GEN, 1, 1, Book.MAT, 1, 1)
        parseValidRange("Joshua 2:18-21", Book.JOS, 2, 18, Book.JOS, 2, 21)
        parseValidRange("Joshua 1:1-9", Book.JOS, 1, 1, Book.JOS, 1, 9)
        parseValidRange("Joshua 16:1-17:13", Book.JOS, 16, 1, Book.JOS, 17, 13)
    }

    @Test
    fun `parse invalid ranges`() {
        parseInvalidRange("")
        parseInvalidRange("John 3:16-Genesis 1:1-Matthew 1:1")
        parseInvalidRange("John 3:16-NotAValidRef")
    }

    fun parseValidRange(
        input: String,
        expBook: Book,
        expStartChapter: Int,
        expStartVerse: Int,
        expEndChapter: Int,
        expEndVerse: Int,
    ) {
        parseValidRange(input, expBook, expStartChapter, expStartVerse, expBook, expEndChapter, expEndVerse)
    }

    fun parseValidRange(
        input: String,
        expStartBook: Book, expStartCh: Int, expStartVerse: Int,
        expEndBook: Book, expEndCh: Int, expEndVerse: Int,
    ) {
        assertEquals(
            VerseRef(expStartBook, expStartCh, expStartVerse)..VerseRef(expEndBook, expEndCh, expEndVerse),
            VerseRange.parse(input)
        )
    }

    fun parseInvalidRange(input: String) {
        assertThrows(IllegalArgumentException::class.java) { VerseRange.parse(input) }
    }
}
