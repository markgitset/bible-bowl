package net.markdrew.biblebowl.ws

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * kotlinx.serialization serializer for [IntRange] using a "first,last" string encoding
 *
 * Registered as a contextual serializer in [PassageText.json] so [Passage.range] round-trips through JSON.
 */
object IntRangeSerializer : KSerializer<IntRange> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntRange", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: IntRange) {
        encoder.encodeString("${value.first},${value.last}")
    }

    override fun deserialize(decoder: Decoder): IntRange =
        decoder.decodeString().split(",").map(String::toInt).let { (start, endInclusive) ->
            start..endInclusive
        }
}
