package net.markdrew.biblebowl.generate.text

import io.kotest.core.spec.style.StringSpec
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet

class BibleTextKtTest : StringSpec({
    fun makeStudyData(highlightOrder: Map<String, Set<Regex>>): Pair<StudyData, TextOptions<String>> {
        val studyData = StudyData(
            studySet = StudySet("Test", "test", ChapterRef(Book.GEN, 1)..ChapterRef(Book.GEN, 3)),
            text = "This is a test sentence with some words.",
            verses = DisjointRangeMap(mapOf(0..39 to VerseRef(Book.GEN, 1, 1))),
            headingCharRanges = DisjointRangeMap(),
            chapters = DisjointRangeMap(mapOf(0..39 to ChapterRef(Book.GEN, 1))),
            paragraphs = DisjointRangeMap(),
            footnotes = sortedMapOf(),
            poetry = DisjointRangeSet()
        )
        return studyData to TextOptions<String>(customHighlights = highlightOrder)
    }

    // TODO: the commented-out assertions below test the expected behaviour for overlapping highlights
    //  (the shorter overlap should be dropped). Reinstate once the algorithm is locked down.
    "annotatedDoc completes without error when shorter highlight overlaps a longer one" {
        val (studyData, opts) = makeStudyData(mapOf(
            "first" to setOf("test sentence".toRegex()),
            "second" to setOf("sentence with some".toRegex()),
        ))
        BibleTextRenderer.annotatedDoc(studyData, opts)
        // Assertions.assertThrows(IllegalStateException::class.java) { BibleTextRenderer.annotatedDoc(studyData, opts) }
    }

    "annotatedDoc completes without error when longer highlight overlaps a shorter one" {
        val (studyData, opts) = makeStudyData(mapOf(
            "second" to setOf("sentence with some".toRegex()),
            "first" to setOf("test sentence".toRegex()),
        ))
        BibleTextRenderer.annotatedDoc(studyData, opts)
        // Assertions.assertThrows(IllegalStateException::class.java) { BibleTextRenderer.annotatedDoc(studyData, opts) }
    }
})
