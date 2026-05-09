package net.markdrew.biblebowl.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class VerseRangeParseTest : StringSpec({
    data class ValidRange(
        val input: String,
        val startBook: Book, val startCh: Int, val startV: Int,
        val endBook: Book, val endCh: Int, val endV: Int,
    )

    withData(
        nameFn = { """VerseRange.parse("${it.input}")""" },
        ValidRange("John 3:16", Book.JOH, 3, 16, Book.JOH, 3, 16),
        ValidRange("John 3:16-John 3:18", Book.JOH, 3, 16, Book.JOH, 3, 18),
        ValidRange("Genesis 1:1-Genesis 1:5", Book.GEN, 1, 1, Book.GEN, 1, 5),
        ValidRange("  Matthew 5:3 - Matthew 5:9  ", Book.MAT, 5, 3, Book.MAT, 5, 9),
        ValidRange("Genesis 1:1-Matthew 1:1", Book.GEN, 1, 1, Book.MAT, 1, 1),
        ValidRange("Joshua 2:18-21", Book.JOS, 2, 18, Book.JOS, 2, 21),
        ValidRange("Joshua 1:1-9", Book.JOS, 1, 1, Book.JOS, 1, 9),
        ValidRange("Joshua 16:1-17:13", Book.JOS, 16, 1, Book.JOS, 17, 13),
    ) { (input, startBook, startCh, startV, endBook, endCh, endV) ->
        VerseRange.parse(input) shouldBe
            VerseRef(startBook, startCh, startV)..VerseRef(endBook, endCh, endV)
    }

    withData(
        nameFn = { """VerseRange.parse("$it") throws""" },
        "", "John 3:16-Genesis 1:1-Matthew 1:1", "John 3:16-NotAValidRef"
    ) { input ->
        shouldThrow<IllegalArgumentException> { VerseRange.parse(input) }
    }
})
