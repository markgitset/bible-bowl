package net.markdrew.biblebowl.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class ChapterRefKtTest : StringSpec({
    "ChapterRef(book, 0) throws" {
        shouldThrow<IllegalArgumentException> { ChapterRef(Book.GEN, 0) }
    }

    withData(
        nameFn = { (input, expected) -> """ChapterRef.parse("$input") = $expected""" },
        "Genesis 1" to ChapterRef(Book.GEN, 1),
        "Gen 3" to ChapterRef(Book.GEN, 3),
        "John 3" to ChapterRef(Book.JOH, 3),
        "Rev 22" to ChapterRef(Book.REV, 22),
    ) { (input, expected) ->
        ChapterRef.parse(input) shouldBe expected
    }

    "ChapterRef.parse with defaultBook" {
        ChapterRef.parse("3", Book.JOH) shouldBe ChapterRef(Book.JOH, 3)
    }

    "ChapterRef.parse bare chapter without defaultBook throws" {
        shouldThrow<IllegalArgumentException> { ChapterRef.parse("3") }
    }

    "ChapterRef.serialize round-trips via deserialize" {
        val ref = ChapterRef(Book.JOH, 3)
        ChapterRef.deserialize(ref.serialize()) shouldBe ref
    }

    "ChapterRef.format(FULL_BOOK_FORMAT) renders full book name" {
        ChapterRef(Book.JOH, 3).format(FULL_BOOK_FORMAT) shouldBe "John 3"
    }

    "ChapterRef.format(BRIEF_BOOK_FORMAT) renders abbreviated book name" {
        ChapterRef(Book.MAT, 5).format(BRIEF_BOOK_FORMAT) shouldBe "Matt 5"
    }

    "ChapterRange.format on a single-chapter range omits the end" {
        (ChapterRef(Book.JOH, 3)..ChapterRef(Book.JOH, 3)).format() shouldBe "John 3"
    }

    "ChapterRange.format on a multi-chapter range appends end chapter" {
        (ChapterRef(Book.JOH, 3)..ChapterRef(Book.JOH, 5)).format() shouldBe "John 3-5"
    }
})
