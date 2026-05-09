package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.util.TreeMap

class FindNamesKtTest : StringSpec({
    fun assertFound(text: String, vararg expectedNameStrings: String) {
        val studyData = StudyData(
            StudySet(Book.GEN, "test"), text,
            DisjointRangeMap(), DisjointRangeMap(), DisjointRangeMap(), DisjointRangeMap(), TreeMap(), DisjointRangeSet()
        )
        var offset = 0
        val expected: List<Excerpt> = buildList {
            for (name in expectedNameStrings) {
                val start = text.indexOf(name, offset).also {
                    require(it >= 0) { "Name '$name' not found in test text — test is invalid" }
                }
                add(Excerpt(name, start until start + name.length))
                offset = start + name.length
            }
        }
        findNames(studyData).toList() shouldBe expected
    }

    "findNames finds a capitalized possessive word" {
        assertFound("And they were driven out from Pharaoh's presence.", "Pharaoh")
    }

//    "findNames finds mountain names" {
//        assertFound(
//            "And they climbed Mount Cat and Mount Ojok, near Ojok, before leaving.",
//            "Mount Cat", "Mount Ojok", "Ojok"
//        )
//    }

//    "findNames finds sea names" {
//        assertFound(
//            "And they swam across Cat Sea and Ojok Sea, near Ojok, before leaving. It's near the Sea of Chinnereth.",
//            "Cat Sea", "Ojok Sea", "Ojok", "Sea of Chinnereth"
//        )
//    }
})
