package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Book.REV
import net.markdrew.biblebowl.model.VerseRef

class PhraseAnalysisKtTest : StringSpec({
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

    data class SubsumesCase(val a: PhraseIndexEntry, val b: PhraseIndexEntry, val expected: Boolean, val label: String)

    withData(
        nameFn = { it.label },
        SubsumesCase(pie1, pie2, true, "longer phrase subsumes shorter phrase with same prefix"),
        SubsumesCase(pie2, pie1, false, "shorter phrase does not subsume longer phrase"),
        SubsumesCase(pie3, pie2, false, "phrase with different prefix does not subsume"),
        SubsumesCase(pie2, pie3, true, "phrase subsumes same-suffix phrase missing its head"),
    ) { (a, b, expected) ->
        a.subsumes(b) shouldBe expected
    }
})
