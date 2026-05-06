package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import net.markdrew.biblebowl.model.Book.REV
import net.markdrew.biblebowl.model.VerseRef

class PhraseAnalysisKtTest : FunSpec({

    test("subsumes") {
        val pie1 = PhraseIndexEntry(
            listOf("before", "the", "throne"),
            listOf(VerseRef(REV, 1, 1), VerseRef(REV, 1, 12), VerseRef(REV, 12, 1))
        )
        val pie2 = PhraseIndexEntry(
            listOf("before", "the", "throne", "and"),
            listOf(VerseRef(REV, 1, 1), VerseRef(REV, 12, 1))
        )
        val pie3 = PhraseIndexEntry(
            listOf("the", "throne", "and"),
            listOf(VerseRef(REV, 1, 1), VerseRef(REV, 12, 1))
        )
        pie1.subsumes(pie2).shouldBeTrue()
        pie2.subsumes(pie1).shouldBeFalse()
        pie3.subsumes(pie2).shouldBeFalse()
        pie2.subsumes(pie3).shouldBeTrue()
    }

})