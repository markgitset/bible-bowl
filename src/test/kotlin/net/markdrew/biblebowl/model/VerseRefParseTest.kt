package net.markdrew.biblebowl.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class VerseRefParseTest : FunSpec({

    fun assertParsesTo(input: String, expected: VerseRef, defaultChapterRef: ChapterRef? = null) {
        val result = VerseRef.parse(input, defaultChapterRef)
        result shouldBe expected
    }

    fun assertParseFails(input: String, defaultChapterRef: ChapterRef? = null) {
        shouldThrow<IllegalArgumentException> {
            VerseRef.parse(input, defaultChapterRef)
        }
    }

    test("parse valid strings") {
        assertParsesTo("John 3:16", VerseRef(Book.JOH, 3, 16))
        assertParsesTo("Joh 3:16", VerseRef(Book.JOH, 3, 16))
        assertParsesTo("16", VerseRef(Book.JOH, 3, 16), ChapterRef(Book.JOH, 3))
        assertParsesTo("  John 3:16  ", VerseRef(Book.JOH, 3, 16)) // input with whitespace
        assertParsesTo("  John 3:16a  ", VerseRef(Book.JOH, 3, 16)) // part a (ignored)
        assertParsesTo("  John 3:16b  ", VerseRef(Book.JOH, 3, 16)) // part b (ignored)
        assertParsesTo("  John 3:16c  ", VerseRef(Book.JOH, 3, 16)) // part c?! (ignored)
    }

    test("parse invalid strings should fail") {
        assertParseFails("") // empty
        assertParseFails("16") // single-part no default
        assertParseFails("InvalidFormat")
        assertParseFails("John:3:16") // multiple colons
    }

})