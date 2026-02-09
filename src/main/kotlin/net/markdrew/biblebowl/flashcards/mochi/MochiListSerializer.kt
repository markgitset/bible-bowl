package net.markdrew.biblebowl.flashcards.mochi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject

class MochiListSerializer<T>(private val elementSerializer: KSerializer<T>) : KSerializer<List<T>> {
    private val delegateSerializer = ListSerializer(elementSerializer)
    override val descriptor: SerialDescriptor = delegateSerializer.descriptor

    override fun serialize(encoder: Encoder, value: List<T>) {
        val jsonEncoder = encoder as? JsonEncoder 
            ?: throw IllegalStateException("This serializer only works with JSON")
        
        // Wrap the list in the required Mochi object structure
        val jsonObject = buildJsonObject {
            put("~#list", jsonEncoder.json.encodeToJsonElement(delegateSerializer, value))
        }
        jsonEncoder.encodeJsonElement(jsonObject)
    }

    override fun deserialize(decoder: Decoder): List<T> {
        val jsonDecoder = decoder as? JsonDecoder 
            ?: throw IllegalStateException("This serializer only works with JSON")
        
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val listElement = jsonObject["~#list"] ?: throw IllegalArgumentException("Missing ~#list key")
        
        return jsonDecoder.json.decodeFromJsonElement(delegateSerializer, listElement)
    }
}