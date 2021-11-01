package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.generate.findNumbers
import net.markdrew.biblebowl.model.Excerpt
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FindNumbersKtTest {

    @Test
    fun `findNumbers finds hyphenated ordinals`() {
        assertEquals(
            listOf(
                Excerpt("twenty-seven", 11..22),
                Excerpt("twenty-seventh", 40..53),
            ),
            findNumbers("There were twenty-seven dogs and he was twenty-seventh.").toList()
        )
    }

}
