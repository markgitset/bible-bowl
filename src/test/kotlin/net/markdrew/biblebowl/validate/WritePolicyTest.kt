package net.markdrew.biblebowl.validate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.analysis.singleVerseStudyData
import net.markdrew.biblebowl.model.StudyData

class WritePolicyTest : StringSpec({

    fun cand(data: StudyData, range: IntRange) = Candidate(range, data.text.substring(range), null)

    "unanimous category writes only the list (no overrides)" {
        val data = singleVerseStudyData("Moses Moses")
        val decisions = listOf(0..4, 6..10).map { OccurrenceDecision(cand(data, it), WordList.MEN) }
        val plan = WritePolicy.plan(data, decisions, currentListCategories = emptyList(), listEntry = "Moses")
        plan.listAdds shouldContainExactly listOf(WordList.MEN to "Moses")
        plan.listRemoves.shouldBeEmpty()
        plan.overrides.shouldBeEmpty()
        plan.verdict shouldBe "men"
    }

    "unanimous none removes the entry from its current list (a gated change)" {
        val data = singleVerseStudyData("Selah")
        val decisions = listOf(OccurrenceDecision(cand(data, 0..4), null))
        val plan = WritePolicy.plan(data, decisions, currentListCategories = listOf(WordList.MEN), listEntry = "Selah")
        plan.listAdds.shouldBeEmpty()
        plan.listRemoves shouldContainExactly listOf(WordList.MEN to "Selah")
        plan.overrides.shouldBeEmpty()
        plan.touchesSharedLists shouldBe true
        plan.verdict shouldBe "none"
    }

    "mixed occurrences put the majority in the list and the exception in an override" {
        val data = singleVerseStudyData("Noah Noah Noah")
        val ranges = listOf(0..3, 5..8, 10..13)
        val decisions = listOf(
            OccurrenceDecision(cand(data, ranges[0]), WordList.MEN),
            OccurrenceDecision(cand(data, ranges[1]), WordList.WOMEN), // the exception
            OccurrenceDecision(cand(data, ranges[2]), WordList.MEN),
        )
        val plan = WritePolicy.plan(data, decisions, currentListCategories = emptyList(), listEntry = "Noah")
        plan.listAdds shouldContainExactly listOf(WordList.MEN to "Noah")
        plan.overrides.size shouldBe 1
        plan.overrides.single().category shouldBe "women"
        plan.overrides.single().text shouldBe "Noah"
        plan.overrides.single().occurrence shouldBe 2 // the 2nd "Noah" in the verse
        plan.verdict shouldBe "split:men"
    }

    "marking a regex-matched form 'none' writes an exclusion override, not just a verdict" {
        // "bear" resolves to animals via a regex entry, so it's not a literal list member -> nothing to
        // remove. The exclusion must be enforced per-occurrence or the verdict re-surfaces as drift.
        val data = singleVerseStudyData("a bear")
        val occ = Candidate(2..5, "bear", WordList.ANIMALS)
        val plan = WritePolicy.plan(data, listOf(OccurrenceDecision(occ, null)),
            currentListCategories = emptyList(), listEntry = "bear")
        plan.listAdds.shouldBeEmpty()
        plan.listRemoves.shouldBeEmpty()
        plan.overrides shouldHaveSize 1
        plan.overrides.single().category shouldBe null // exclusion ("-")
        plan.overrides.single().text shouldBe "bear"
        plan.verdict shouldBe "none"
    }

    "reassigning a whole group to another category moves it (add new, remove old)" {
        val data = singleVerseStudyData("Gibeon")
        val decisions = listOf(OccurrenceDecision(cand(data, 0..5), WordList.PLACES))
        val plan = WritePolicy.plan(data, decisions, currentListCategories = listOf(WordList.MEN), listEntry = "Gibeon")
        plan.listAdds shouldContainExactly listOf(WordList.PLACES to "Gibeon")
        plan.listRemoves shouldContainExactly listOf(WordList.MEN to "Gibeon")
        plan.verdict shouldBe "places"
    }
})
