package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.TreeMap

internal class OneTimeWordsKtTest {

    @Test
    fun oneTimeWords() {
        assertEquals(
            listOf(6..9, 11..22, 24..27),
            oneTimeWords(
                StudyData(
                    StandardStudySet.GENESIS.set,
                    "There were twenty-seven dogs there.",
                    verses = DisjointRangeMap(),
                    headingCharRanges = DisjointRangeMap(),
                    chapters = DisjointRangeMap(),
                    paragraphs = DisjointRangeSet(),
                    footnotes = TreeMap(),
                    poetry = DisjointRangeSet()
                )
            )
        )
    }

}
