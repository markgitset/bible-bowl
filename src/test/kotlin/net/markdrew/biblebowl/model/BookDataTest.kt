package net.markdrew.biblebowl.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BookDataTest : StringSpec({
    "wordsPattern treats hyphenated tokens as a single word" {
        StudyData.wordsPattern.findAll("There were twenty-seven dogs.")
            .map { it.value }.toList() shouldBe listOf("There", "were", "twenty-seven", "dogs")
    }
})
