package net.markdrew.biblebowl.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class VerseRefParseTest : StringSpec({
    data class ValidCase(val input: String, val expected: VerseRef, val default: ChapterRef? = null)

    withData(
        nameFn = { """VerseRef.parse("${it.input}") = ${it.expected}""" },
        ValidCase("John 3:16", VerseRef(Book.JOH, 3, 16)),
        ValidCase("Joh 3:16", VerseRef(Book.JOH, 3, 16)),
        ValidCase("16", VerseRef(Book.JOH, 3, 16), ChapterRef(Book.JOH, 3)),
        ValidCase("  John 3:16  ", VerseRef(Book.JOH, 3, 16)),
        ValidCase("  John 3:16a  ", VerseRef(Book.JOH, 3, 16)),
        ValidCase("  John 3:16b  ", VerseRef(Book.JOH, 3, 16)),
        ValidCase("  John 3:16c  ", VerseRef(Book.JOH, 3, 16)),
    ) { (input, expected, default) ->
        VerseRef.parse(input, default) shouldBe expected
    }

    withData(
        nameFn = { """VerseRef.parse("$it") throws""" },
        "", "16", "InvalidFormat", "John:3:16"
    ) { input ->
        shouldThrow<IllegalArgumentException> { VerseRef.parse(input) }
    }
})
