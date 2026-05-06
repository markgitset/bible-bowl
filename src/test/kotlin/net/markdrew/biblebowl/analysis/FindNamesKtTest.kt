package net.markdrew.biblebowl.analysis

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.util.TreeMap

internal class FindNamesKtTest : FunSpec({

    fun assertFound(text: String, vararg expectedNameStrings: String) {
        val studySet = StudySet(Book.GEN, "test")
        val studyData = StudyData(studySet, text, DisjointRangeMap(), DisjointRangeMap(), DisjointRangeMap(),
            DisjointRangeMap(), TreeMap(), DisjointRangeSet())
        var offset = 0
        val expected: List<Excerpt> = buildList {
            for (name in expectedNameStrings) {
                val startOffset = text.indexOf(name, offset)
                if (startOffset < 0) {
                    fail("Expected name string '$name' not found in given text--test is not valid!")
                }
                val endOffset = startOffset + name.length
                add(Excerpt(name, startOffset until endOffset))
                offset = endOffset
            }
        }
        val found: List<Excerpt> = findNames(studyData).toList()
        found shouldBe expected
    }

    test("findNames finds a capitalized, possessive word") {
        assertFound("And they were driven out from Pharaoh’s presence.", "Pharaoh")
    }

//    test("findNames finds a mountains") {
//        assertFound(
//            "And they climbed Mount Cat and Mount Ojok, near Ojok, before leaving.",
//            "Mount Cat", "Mount Ojok", "Ojok"
//        )
//    }

//    test("findNames finds seas") {
//        assertFound(
//            "And they swam across Cat Sea and Ojok Sea, near Ojok, before leaving. It's near the Sea of Chinnereth.",
//            "Cat Sea", "Ojok Sea", "Ojok", "Sea of Chinnereth"
//        )
//    }

})
