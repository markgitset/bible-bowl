package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.model.Book.GEN
import net.markdrew.biblebowl.model.Book.LUK
import net.markdrew.biblebowl.model.Book.MAT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VerseRefKtTest {

    @Test
    fun `format(NO_BOOK_FORMAT) fails on multi-book verse list`() {
        assertThrows<IllegalArgumentException> { multiBookRefs.format(NO_BOOK_FORMAT) }
    }

    @Test
    fun `format(FULL_BOOK_FORMAT) on multi-book verse list`() {
        assertEquals("Genesis 1:2,3; Matthew 5:2; 6:12,23,24,25; Luke 6:26,28", multiBookRefs.format(FULL_BOOK_FORMAT))
    }

    @Test
    fun `format(BRIEF_BOOK_FORMAT) on multi-book verse list`() {
        assertEquals("Gen 1:2,3; Matt 5:2; 6:12,23,24,25; Luke 6:26,28", multiBookRefs.format(BRIEF_BOOK_FORMAT))
    }

    @Test
    fun `format(NO_BOOK_FORMAT) on single-book verse list`() {
        assertEquals("1:2,3; 5:2; 6:12,23,24,25,26,28", singleBookRefs.format(NO_BOOK_FORMAT))
    }

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