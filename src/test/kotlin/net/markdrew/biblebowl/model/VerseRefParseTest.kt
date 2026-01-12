package net.markdrew.biblebowl.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class VerseRefParseTest {

    @Test
    fun `parse valid strings`() {
        assertParsesTo("John 3:16", VerseRef(Book.JOH, 3, 16))
        assertParsesTo("Joh 3:16", VerseRef(Book.JOH, 3, 16))
        assertParsesTo("16", VerseRef(Book.JOH, 3, 16), ChapterRef(Book.JOH, 3))
        assertParsesTo("  John 3:16  ", VerseRef(Book.JOH, 3, 16)) // input with whitespace
        assertParsesTo("  John 3:16a  ", VerseRef(Book.JOH, 3, 16)) // part a (ignored)
        assertParsesTo("  John 3:16b  ", VerseRef(Book.JOH, 3, 16)) // part b (ignored)
        assertParsesTo("  John 3:16c  ", VerseRef(Book.JOH, 3, 16)) // part c?! (ignored)
    }

    @Test
    fun `parse invalid strings should fail`() {
        assertParseFails("") // empty
        assertParseFails("16") // single-part no default
        assertParseFails("InvalidFormat")
        assertParseFails("John:3:16") // multiple colons
    }

    private fun assertParsesTo(input: String, expected: VerseRef, defaultChapterRef: ChapterRef? = null) {
        val result = VerseRef.parse(input, defaultChapterRef)
        assertEquals(expected, result) { "Expected '$input' to yield $expected, but got $result" }
    }

    private fun assertParseFails(input: String, defaultChapterRef: ChapterRef? = null) {
        assertThrows(IllegalArgumentException::class.java) {
            VerseRef.parse(input, defaultChapterRef)
        }
    }
}