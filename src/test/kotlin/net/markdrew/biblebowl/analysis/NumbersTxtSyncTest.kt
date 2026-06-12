package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * Guards that the `numbers` category list ([WordList.NUMBERS], from word-lists/numbers.txt) stays in sync
 * with the readable, composable number patterns in [FindNumbers]. If the consts change, re-mirror them
 * into numbers.txt rather than letting the two drift.
 */
class NumbersTxtSyncTest : StringSpec({

    "numbers.txt mirrors the FindNumbers patterns exactly" {
        WordList.NUMBERS.dictionary shouldBe setOf(FRACTIONS, ORDINALS, MULTI_NUMBER_PATTERN, NUMERAL_PATTERN)
    }

    "WordList.NUMBERS matches numerals and spelled-out numbers" {
        val regexes = WordList.NUMBERS.regexSequence().toList()
        fun matched(s: String) = regexes.any { it.containsMatchIn(s) }
        matched("1,234") shouldBe true
        matched("five") shouldBe true
        matched("eighteen") shouldBe true
        matched("one-third") shouldBe true
        matched("twenty-first") shouldBe true
        matched("elephant") shouldBe false
    }
})
