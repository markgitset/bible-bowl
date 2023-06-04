package net.markdrew.biblebowl.ws

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PassageTest {

    @Test
    fun serialize() {
        val meta = PassageMeta("blah123", listOf(2, 3), listOf(9, 10), null, 23, listOf(3, 5), null)
        val passage = Passage("blah blah", 3..2_332, meta, "some more text")
        val serialized: String = passage.serialize()
        val deserialized = Passage.deserialize(serialized)
        assertEquals(passage, deserialized)
    }
}