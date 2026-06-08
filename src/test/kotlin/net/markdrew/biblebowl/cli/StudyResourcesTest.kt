package net.markdrew.biblebowl.cli

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class StudyResourcesTest : StringSpec({

    // A small fixed registry so the test doesn't depend on the real generator wiring.
    fun resource(slug: String, category: ResourceCategory) =
        StudyResource(slug, category, slug) { _, _, _ -> }

    val registry: List<StudyResource> = listOf(
        resource("text", ResourceCategory.TEXT),
        resource("names-index", ResourceCategory.INDICES),
        resource("numbers-index", ResourceCategory.INDICES),
        resource("cram-verses", ResourceCategory.FLASHCARDS),
        resource("practice", ResourceCategory.PRACTICE),
    )

    fun slugs(tokens: List<String>) = resolveSelection(tokens, registry).map { it.slug }

    "empty token list selects every resource" {
        slugs(emptyList()) shouldBe registry.map { it.slug }
    }

    "a single slug selects just that resource" {
        slugs(listOf("names-index")) shouldBe listOf("names-index")
    }

    "a category token expands to all of its resources" {
        slugs(listOf("indices")) shouldBe listOf("names-index", "numbers-index")
    }

    "category matching is case-insensitive" {
        slugs(listOf("INDICES")) shouldBe listOf("names-index", "numbers-index")
    }

    "mixed category and slug tokens combine, in registry order" {
        slugs(listOf("flashcards", "text")) shouldBe listOf("text", "cram-verses")
    }

    "overlapping tokens are de-duplicated" {
        slugs(listOf("indices", "names-index", "names-index")) shouldBe listOf("names-index", "numbers-index")
    }

    "an unknown token throws, naming the offending value" {
        val e = shouldThrow<IllegalArgumentException> { resolveSelection(listOf("bogus"), registry) }
        e.message!! shouldContain "bogus"
    }
})
