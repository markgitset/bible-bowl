package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.TreeMap

internal class OneTimeWordsKtTest {

    @Test
    fun oneTimeWords() {
        assertEquals(
            listOf(6..9, 11..22, 24..27),
            oneTimeWords(
                BookData(
                    Book.GEN,
                    "There were twenty-seven dogs there.",
                    verses = DisjointRangeMap(),
                    headings = DisjointRangeMap(),
                    chapters = DisjointRangeMap(),
                    paragraphs = DisjointRangeSet(),
                    footnotes = TreeMap(),
                    poetry = DisjointRangeSet()
                )
            )
        )
    }

}
