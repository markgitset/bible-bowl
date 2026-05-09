package net.markdrew.biblebowl.generate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Excerpt

class FormattingKtTest : StringSpec({
    "normalizeWS collapses runs of internal whitespace to a single space" {
        "hello  world".normalizeWS() shouldBe "hello world"
    }

    "normalizeWS strips leading and trailing whitespace" {
        "  hello world  ".normalizeWS() shouldBe "hello world"
    }

    "normalizeWS converts tabs and newlines to spaces" {
        "hello\t\nworld".normalizeWS() shouldBe "hello world"
    }

    "String.excerpt wraps the substring with its absolute range" {
        "hello world".excerpt(6..10) shouldBe Excerpt("world", 6..10)
    }

    "formatRange applies the transform to the substring at the given range" {
        formatRange("hello world", 6..10) { it.uppercase() } shouldBe "hello WORLD"
    }

    "wrapWith(begin, end) produces a formatter that wraps its input" {
        wrapWith("[", "]")("hello") shouldBe "[hello]"
    }

    "wrapWith(single) wraps symmetrically" {
        wrapWith("*")("hello") shouldBe "*hello*"
    }

    "blankOut() replaces with ten underscores by default" {
        blankOut()("any text") shouldBe "__________"
    }

    "blankOut(char, length) replaces with the given char repeated length times" {
        blankOut("-", 5)("text") shouldBe "-----"
    }
})
