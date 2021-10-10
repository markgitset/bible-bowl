package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.analysis.PhraseIndexEntry
import net.markdrew.biblebowl.analysis.subsumes
import net.markdrew.biblebowl.model.Book.REV
import net.markdrew.biblebowl.model.VerseRef
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PhraseAnalysisKtTest {

    @Test
    fun subsumes() {
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
        assertTrue(pie1.subsumes(pie2))
        assertFalse(pie2.subsumes(pie1))
        assertFalse(pie3.subsumes(pie2))
        assertTrue(pie2.subsumes(pie3))
    }

}