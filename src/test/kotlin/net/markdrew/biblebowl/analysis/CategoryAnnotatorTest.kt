package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.VerseRef

private val VERSE = VerseRef(Book.GEN, 1, 1)
private fun cat(id: String, vararg words: String) = id to words.map { Regex("""\b$it\b""") }.toSet()

class CategoryAnnotatorTest : StringSpec({

    "a single matching category tags the occurrence" {
        val data = singleVerseStudyData("Moses spoke")
        val map = CategoryAnnotator("t", listOf(cat("men", "Moses"))).compute(data)
        map.valueContaining(0) shouldBe "men"
    }

    "a term in two lists resolves to the later category by precedence" {
        val data = singleVerseStudyData("Noah")
        val map = CategoryAnnotator("t", listOf(cat("men", "Noah"), cat("women", "Noah"))).compute(data)
        map.valueContaining(0) shouldBe "women"
    }

    "an override reclassifies the occurrence, beating precedence" {
        val data = singleVerseStudyData("Noah")
        val map = CategoryAnnotator(
            "t",
            listOf(cat("men", "Noah"), cat("women", "Noah")),
            overrides = listOf(CategoryOverride(VERSE, "Noah", "places")),
        ).compute(data)
        map.valueContaining(0) shouldBe "places"
    }

    "an excluding override removes the occurrence entirely" {
        val data = singleVerseStudyData("Noah")
        val map = CategoryAnnotator(
            "t",
            listOf(cat("men", "Noah")),
            overrides = listOf(CategoryOverride(VERSE, "Noah", category = null)),
        ).compute(data)
        map.valueContaining(0).shouldBeNull()
    }

    "an override can add a category no list detected" {
        val data = singleVerseStudyData("Noah Moses")
        val map = CategoryAnnotator(
            "t",
            listOf(cat("men", "Moses")),
            overrides = listOf(CategoryOverride(VERSE, "Noah", "women")),
        ).compute(data)
        map.valueContaining(0) shouldBe "women"            // "Noah" added by override
        map.valueContaining(data.text.indexOf("Moses")) shouldBe "men"
    }
})
