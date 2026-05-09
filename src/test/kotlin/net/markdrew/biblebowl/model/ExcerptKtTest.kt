package net.markdrew.biblebowl.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExcerptKtTest : StringSpec({
    // "quick brown" occupies absolute range 4..14 inside "The quick brown fox"
    val excerpt = Excerpt("quick brown", 4..14)

    "Excerpt.relative converts an absolute range to a local range" {
        excerpt.relative(4..8) shouldBe 0..4
    }

    "Excerpt.substring extracts text by absolute range" {
        excerpt.substring(4..8) shouldBe "quick"
    }

    "Excerpt.formatRange wraps the span at the given absolute range" {
        excerpt.formatRange(4..8) { "[$it]" } shouldBe "[quick] brown"
    }

    "Excerpt.disown strips a straight apostrophe-s suffix" {
        Excerpt("Pharaoh's", 5..13).disown() shouldBe Excerpt("Pharaoh", 5..11)
    }

    "Excerpt.disown strips a curly apostrophe-s suffix" {
        Excerpt("Pharaoh’s", 5..13).disown() shouldBe Excerpt("Pharaoh", 5..11)
    }

    "Excerpt.disown leaves a non-possessive excerpt unchanged" {
        val plain = Excerpt("Jordan", 0..5)
        plain.disown() shouldBe plain
    }

    "MatchResult.toExcerpt wraps a regex match" {
        val match = "quick".toRegex().find("The quick brown")!!
        match.toExcerpt() shouldBe Excerpt("quick", 4..8)
    }
})
