package net.markdrew.biblebowl.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BookDataTest {

    @Test
    fun `hyphenated words parse as one word`() {
        assertEquals(
            listOf("There", "were", "twenty-seven", "dogs"),
            StudyData.wordsPattern.findAll("There were twenty-seven dogs.").map { it.value }.toList()
        )
    }
}