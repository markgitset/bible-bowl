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

    "a term in two lists resolves by CategoryPrecedence regardless of list order" {
        val data = singleVerseStudyData("Noah")
        // women outranks men; places outranks men — and the result is order-independent
        CategoryAnnotator("t", listOf(cat("men", "Noah"), cat("women", "Noah"))).compute(data)
            .valueContaining(0) shouldBe "women"
        CategoryAnnotator("t", listOf(cat("places", "Noah"), cat("men", "Noah"))).compute(data)
            .valueContaining(0) shouldBe "places"
    }

    "a longer match wins over a shorter overlapping one" {
        val data = singleVerseStudyData("by the Red Sea today")
        val map = CategoryAnnotator("t", listOf(cat("places", "Sea"), cat("places", "Red Sea"))).compute(data)
        val seaPos = data.text.indexOf("Sea")
        val redSea = data.text.indexOf("Red Sea")
        map.keyContaining(seaPos) shouldBe redSea..(redSea + "Red Sea".length - 1)
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
