package net.markdrew.biblebowl.generate.text

import io.kotest.core.spec.style.FunSpec
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet

class BibleTextKtTest : FunSpec({

    test("annotatedDoc should replace shorter overlapping highlights") {
        val studyData = StudyData(
            studySet = StudySet("Test", "test", ChapterRef(Book.GEN, 1) ..ChapterRef(Book.GEN, 3)),
            text = "This is a test sentence with some words.",
            verses = DisjointRangeMap(mapOf(0..39 to VerseRef(Book.GEN, 1, 1))),
            headingCharRanges = DisjointRangeMap(),
            chapters = DisjointRangeMap(mapOf(0..39 to ChapterRef(Book.GEN, 1))),
            paragraphs = DisjointRangeMap(),
            footnotes = sortedMapOf(),
            poetry = DisjointRangeSet()
        )
        val opts = TextOptions(
            customHighlights = mapOf(
                "first" to setOf("test sentence".toRegex()),
                "second" to setOf("sentence with some".toRegex()),
            )
        )
        BibleTextRenderer.annotatedDoc(studyData, opts)
    }

    test("annotatedDoc should skip longer overlapping highlights") {
        val studyData = StudyData(
            studySet = StudySet("Test", "test", ChapterRef(Book.GEN, 1) ..ChapterRef(Book.GEN, 3)),
            text = "This is a test sentence with some words.",
            verses = DisjointRangeMap(mapOf(0..39 to VerseRef(Book.GEN, 1, 1))),
            headingCharRanges = DisjointRangeMap(),
            chapters = DisjointRangeMap(mapOf(0..39 to ChapterRef(Book.GEN, 1))),
            paragraphs = DisjointRangeMap(),
            footnotes = sortedMapOf(),
            poetry = DisjointRangeSet()
        )
        val opts = TextOptions(
            customHighlights = mapOf(
                "second" to setOf("sentence with some".toRegex()),
                "first" to setOf("test sentence".toRegex()),
            )
        )
        BibleTextRenderer.annotatedDoc(studyData, opts)
    }

})
