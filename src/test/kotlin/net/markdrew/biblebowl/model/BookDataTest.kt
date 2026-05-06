package net.markdrew.biblebowl.model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class BookDataTest : FunSpec({

    test("hyphenated words parse as one word") {
        StudyData.wordsPattern.findAll("There were twenty-seven dogs.").map { it.value }.toList() shouldBe
            listOf("There", "were", "twenty-seven", "dogs")
    }
})