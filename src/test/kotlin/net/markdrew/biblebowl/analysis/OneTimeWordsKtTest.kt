package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.util.TreeMap

internal class OneTimeWordsKtTest : FunSpec({

    test("oneTimeWords") {
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
