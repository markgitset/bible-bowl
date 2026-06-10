package net.markdrew.biblebowl.validate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.analysis.singleVerseStudyData
import net.markdrew.chupacabra.core.DisjointRangeMap

class CandidateScannerTest : StringSpec({

    val data = singleVerseStudyData("Moses saw Egypt")
    val resolved = DisjointRangeMap(mapOf(0..4 to "men")) // "Moses" -> men; "Egypt" uncategorized

    "scans selected matches plus uncategorized capitalized words, grouped and pre-annotated" {
        val groups = CandidateScanner.scan(data, setOf(WordList.MEN), resolved)
        val byText = groups.associateBy { it.text }
        byText.keys shouldContainExactly setOf("Moses", "Egypt") // "saw" is lowercase, not a candidate
        byText.getValue("Moses").proposed shouldBe WordList.MEN
        byText.getValue("Egypt").proposed shouldBe null          // capitalized but no category yet
    }

    "skips forms already marked done" {
        val groups = CandidateScanner.scan(data, setOf(WordList.MEN), resolved, doneForms = setOf("Moses"))
        groups.map { it.text } shouldContainExactly listOf("Egypt")
    }

    "search collects word occurrences matching the query" {
        val groups = CandidateScanner.search(data, "egy", asRegex = false, resolvedCategories = resolved)
        groups.map { it.text } shouldContainExactly listOf("Egypt")
    }

    "regexMatches finds multi-word list-regex entries over the whole text" {
        val multi = singleVerseStudyData("the men of Israel went up against the men of Ai")
        val found = CandidateScanner.regexMatches(multi, "men of Israel", DisjointRangeMap())
        found.map { it.text } shouldContainExactly listOf("men of Israel") // not "men of Ai"
    }
})
