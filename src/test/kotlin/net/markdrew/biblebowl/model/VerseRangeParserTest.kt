package net.markdrew.biblebowl.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class VerseRangeParseTest : FunSpec({

    fun parseValidRange(
        input: String,
        expStartBook: Book, expStartCh: Int, expStartVerse: Int,
        expEndBook: Book, expEndCh: Int, expEndVerse: Int,
    ) {
        VerseRange.parse(input) shouldBe (VerseRef(expStartBook, expStartCh, expStartVerse)..VerseRef(expEndBook, expEndCh, expEndVerse))
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

    fun parseInvalidRange(input: String) {
        shouldThrow<IllegalArgumentException> { VerseRange.parse(input) }
    }

    test("parse valid ranges") {
        parseValidRange("John 3:16", Book.JOH, 3, 16, 3, 16)
        parseValidRange("John 3:16-John 3:18", Book.JOH, 3, 16, 3, 18)
        parseValidRange("Genesis 1:1-Genesis 1:5", Book.GEN, 1, 1, 1, 5)
        parseValidRange("  Matthew 5:3 - Matthew 5:9  ", Book.MAT, 5, 3, 5, 9)
        parseValidRange("Genesis 1:1-Matthew 1:1", Book.GEN, 1, 1, Book.MAT, 1, 1)
        parseValidRange("Joshua 2:18-21", Book.JOS, 2, 18, Book.JOS, 2, 21)
        parseValidRange("Joshua 1:1-9", Book.JOS, 1, 1, Book.JOS, 1, 9)
        parseValidRange("Joshua 16:1-17:13", Book.JOS, 16, 1, Book.JOS, 17, 13)
    }

    test("parse invalid ranges") {
        parseInvalidRange("")
        parseInvalidRange("John 3:16-Genesis 1:1-Matthew 1:1")
        parseInvalidRange("John 3:16-NotAValidRef")
    }

})
