package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.TreeMap

internal class FindNamesKtTest {

    @Test
    fun `findNames finds a capitalized, possessive word`() {
        assertFound("And they were driven out from Pharaoh’s presence.", "Pharaoh")
    }

    @Test
    fun `findNames finds a mountains`() {
        assertFound(
            "And they climbed Mount Cat and Mount Ojok, near Ojok, before leaving.",
            "Mount Cat", "Mount Ojok", "Ojok"
        )
    }

    @Test
    fun `findNames finds seas`() {
        assertFound(
            "And they swam across Cat Sea and Ojok Sea, near Ojok, before leaving. It's near the Sea of Chinnereth.",
            "Cat Sea", "Ojok Sea", "Ojok", "Sea of Chinnereth"
        )
    }

    private fun assertFound(text: String, vararg expectedNameStrings: String) {
        val studySet = StudySet(Book.GEN, "test")
        val studyData = StudyData(studySet, text, DisjointRangeMap(), DisjointRangeMap(), DisjointRangeMap(),
            DisjointRangeMap(), TreeMap(), DisjointRangeSet())
        var offset = 0
        val expected: List<Excerpt> = buildList {
            for (name in expectedNameStrings) {
                val startOffset = text.indexOf(name, offset)
                if (startOffset < 0) {
                    fail { "Expected name string '$name' not found in given text--test is not valid!" }
                }
                val endOffset = startOffset + name.length
                add(Excerpt(name, startOffset until endOffset))
                offset = endOffset
            }
        }
        val found: List<Excerpt> = findNames(studyData).toList()
        assertEquals(expected, found)
    }

}
