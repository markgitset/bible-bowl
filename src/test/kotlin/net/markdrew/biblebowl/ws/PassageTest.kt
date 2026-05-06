package net.markdrew.biblebowl.ws

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PassageTest : FunSpec({

    test("serialize") {
        val meta = PassageMeta("blah123", listOf(2, 3), listOf(9, 10), null, 23, listOf(3, 5), null)
        val passage = Passage("blah blah", 3..2_332, meta, "some more text")
        val serialized: String = passage.serialize()
        val deserialized = Passage.deserialize(serialized)
        deserialized shouldBe passage
    }
})