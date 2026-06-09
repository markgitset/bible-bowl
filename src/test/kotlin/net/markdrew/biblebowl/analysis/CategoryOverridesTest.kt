package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet

/** Minimal StudyData whose whole text is one verse (Genesis 1:1), enough to resolve overrides. */
internal fun singleVerseStudyData(text: String, verse: VerseRef = VerseRef(Book.GEN, 1, 1)): StudyData =
    StudyData(
        studySet = StudySet("Test", "test", ChapterRef(Book.GEN, 1)..ChapterRef(Book.GEN, 1)),
        text = text,
        verses = DisjointRangeMap(mapOf(text.indices to verse)),
        headingCharRanges = DisjointRangeMap(),
        chapters = DisjointRangeMap(mapOf(text.indices to ChapterRef(Book.GEN, 1))),
        paragraphs = DisjointRangeMap(),
        footnotes = sortedMapOf(),
        poetry = DisjointRangeSet(),
    )

class CategoryOverridesTest : StringSpec({

    val verse = VerseRef(Book.GEN, 1, 1)

    "resolves verse + text to the matching character range" {
        val data = singleVerseStudyData("the king of Tirzah and Noah")
        val resolved = CategoryOverrides.resolve(listOf(CategoryOverride(verse, "Noah", "women")), data)
        resolved shouldHaveSize 1
        data.text.substring(resolved.single().range) shouldBe "Noah"
        resolved.single().category shouldBe "women"
    }

    "occurrence index selects the nth occurrence" {
        val data = singleVerseStudyData("Noah and Noah and Noah")
        val resolved = CategoryOverrides.resolve(listOf(CategoryOverride(verse, "Noah", "places", occurrence = 2)), data)
        resolved shouldHaveSize 1
        // second "Noah" starts at index 9
        resolved.single().range.first shouldBe 9
    }

    "a null category (exclusion) is preserved through resolution" {
        val data = singleVerseStudyData("an ox and a Noah")
        val resolved = CategoryOverrides.resolve(listOf(CategoryOverride(verse, "ox", category = null)), data)
        resolved.single().category shouldBe null
    }

    "a stale override (text absent) resolves to nothing" {
        val data = singleVerseStudyData("nothing to see here")
        CategoryOverrides.resolve(listOf(CategoryOverride(verse, "Zedekiah", "men")), data).shouldBeEmpty()
    }

    "loads and parses the committed josh-judg-ruth override file" {
        val overrides = CategoryOverrides.load(StandardStudySet.JOSHUA_JUDGES_RUTH.set)
        overrides shouldHaveSize 1
        overrides.single().text shouldBe "Noah"
        overrides.single().category shouldBe "women"
        overrides.single().verse shouldBe VerseRef.parse("Joshua 17:3")
    }
})
