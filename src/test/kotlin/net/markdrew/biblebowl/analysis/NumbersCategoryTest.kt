package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * Checks the [WordList.NUMBERS] category, whose patterns are sourced directly from [FindNumbers] (the
 * single source — there is no numbers.txt mirror).
 */
class NumbersCategoryTest : StringSpec({

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
