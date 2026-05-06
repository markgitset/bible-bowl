package net.markdrew.biblebowl.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Book.GEN
import net.markdrew.biblebowl.model.Book.LUK
import net.markdrew.biblebowl.model.Book.MAT

class VerseRefKtTest : FunSpec({

    test("format(NO_BOOK_FORMAT) fails on multi-book verse list") {
        shouldThrow<IllegalArgumentException> { multiBookRefs.format(NO_BOOK_FORMAT) }
    }

    test("format(FULL_BOOK_FORMAT) on multi-book verse list") {
        multiBookRefs.format(FULL_BOOK_FORMAT) shouldBe "Genesis 1:2,3; Matthew 5:2; 6:12,23,24,25; Luke 6:26,28"
    }

    test("format(BRIEF_BOOK_FORMAT) on multi-book verse list") {
        multiBookRefs.format(BRIEF_BOOK_FORMAT) shouldBe "Gen 1:2,3; Matt 5:2; 6:12,23,24,25; Luke 6:26,28"
    }

    test("format(NO_BOOK_FORMAT) on single-book verse list") {
        singleBookRefs.format(NO_BOOK_FORMAT) shouldBe "1:2,3; 5:2; 6:12,23,24,25,26,28"
    }

}) {
    companion object {
        private val singleBookRefs = listOf(
            MAT.verseRef(1, 2),
            MAT.verseRef(1, 3),
            MAT.verseRef(5, 2),
            MAT.verseRef(6, 12),
            MAT.verseRef(6, 23),
            MAT.verseRef(6, 24),
            MAT.verseRef(6, 25),
            MAT.verseRef(6, 26),
            MAT.verseRef(6, 28),
        )
        private val multiBookRefs = listOf(
            GEN.verseRef(1, 2),
            GEN.verseRef(1, 3),
            MAT.verseRef(5, 2),
            MAT.verseRef(6, 12),
            MAT.verseRef(6, 23),
            MAT.verseRef(6, 24),
            MAT.verseRef(6, 25),
            LUK.verseRef(6, 26),
            LUK.verseRef(6, 28),
        )
    }
}