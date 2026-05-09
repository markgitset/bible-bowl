package net.markdrew.biblebowl.ws

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PassageTest : StringSpec({
    "Passage serializes and deserializes round-trip" {
        val meta = PassageMeta("blah123", listOf(2, 3), listOf(9, 10), null, 23, listOf(3, 5), null)
        val passage = Passage("blah blah", 3..2_332, meta, "some more text")
        Passage.deserialize(passage.serialize()) shouldBe passage
    }
})
