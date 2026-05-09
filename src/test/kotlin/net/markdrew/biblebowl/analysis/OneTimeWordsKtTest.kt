package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.util.TreeMap

class OneTimeWordsKtTest : StringSpec({
    "oneTimeWords finds words that appear only once" {
        oneTimeWords(
            StudyData(
                StandardStudySet.GENESIS.set,
                "There were twenty-seven dogs there.",
                verses = DisjointRangeMap(),
                headingCharRanges = DisjointRangeMap(),
                chapters = DisjointRangeMap(),
                paragraphs = DisjointRangeMap(),
                footnotes = TreeMap(),
                poetry = DisjointRangeSet()
            )
        ) shouldBe listOf(6..9, 11..22, 24..27)
    }
})
