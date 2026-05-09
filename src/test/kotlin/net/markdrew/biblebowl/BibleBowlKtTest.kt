package net.markdrew.biblebowl

import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class BibleBowlKtTest : StringSpec({
    "rangeLabel with a single-element range uses the singular form" {
        rangeLabel("Dog", 42..42) shouldBe "Dog-42"
    }

    "rangeLabel with a multi-element range uses the plural form" {
        rangeLabel("Dog", 3..7) shouldBe "Dogs-3-7"
    }

    "rangeLabel with a custom explicit plural" {
        rangeLabel("Ox", 1..3, plural = "Oxen") shouldBe "Oxen-1-3"
    }

    "rangeLabel with a custom separator" {
        rangeLabel("Dog", 1..5, separator = " ") shouldBe "Dogs 1-5"
    }

    withData(
        nameFn = { (input, expected) -> """normalizeFileName("$input") = "$expected"""" },
        "Joshua Judges Ruth" to "joshua-judges-ruth",
        "GENESIS" to "genesis",
        "1 Kings" to "1-kings",
        "foo-bar" to "foo-bar",
        "Luke" to "luke",
    ) { (input, expected) ->
        normalizeFileName(input) shouldBe expected
    }
})
