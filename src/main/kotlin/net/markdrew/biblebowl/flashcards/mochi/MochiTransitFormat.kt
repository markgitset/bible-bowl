package net.markdrew.biblebowl.flashcards.mochi

import com.cognitect.transit.Keyword
import com.cognitect.transit.TaggedValue
import com.cognitect.transit.TransitFactory
import com.cognitect.transit.Writer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull
import java.io.InputStream
import java.io.OutputStream
import java.util.Date

object MochiTransitFormat {
    @PublishedApi
    internal val json = Json {
        ignoreUnknownKeys = true // Safely ignores duplicated/denormalized Mochi map fields
        encodeDefaults = true
        explicitNulls = false
    }

    // Schema hints to accurately recreate Transit types from primitive JsonElements
    private val SET_KEYS = setOf("tags", "references", "cloze/indexes")
    private val DATE_KEYS = setOf("created-at", "updated-at", "date")
    private val KEYWORD_VAL_KEYS = setOf("id", "deck-id", "template-id")
    private val KEYWORD_SET_KEYS = setOf("references")

    /**
     * Reads a Transit JSON InputStream and decodes it into your Kotlin models.
     */
    inline fun <reified T> decodeFromStream(inputStream: InputStream): T {
        val reader = TransitFactory.reader(TransitFactory.Format.JSON, inputStream)
        val transitObj = reader.read<Any>()
        val jsonElement = transitToJsonElement(transitObj)
        return json.decodeFromJsonElement(jsonElement)
    }

    /**
     * Encodes your Kotlin models and writes them as Transit JSON to an OutputStream.
     */
    inline fun <reified T> encodeToStream(value: T, outputStream: OutputStream) {
        val jsonElement = json.encodeToJsonElement(value)
        val transitObj: Any? = jsonToTransit(null, jsonElement)
        val writer: Writer<Any?> = TransitFactory.writer(TransitFactory.Format.JSON, outputStream)
        writer.write(transitObj)
    }

    // --- Internal AST Conversions ---

    @PublishedApi
    internal fun transitToJsonElement(obj: Any?): JsonElement {
        return when (obj) {
            null -> JsonNull
            is String -> JsonPrimitive(obj)
            is Number -> JsonPrimitive(obj)
            is Boolean -> JsonPrimitive(obj)
            is Keyword -> {
                val ns = obj.namespace
                val name = obj.name
                JsonPrimitive(if (ns != null) "$ns/$name" else name)
            }

            is Date -> JsonPrimitive(obj.time)
            is TaggedValue<*> -> {
                when (obj.tag) {
                    "dt" -> {
                        // Extract the timestamp from Mochi's custom ~#dt tag
                        val rep = obj.rep
                        if (rep is Number) JsonPrimitive(rep.toLong())
                        else JsonPrimitive(rep.toString().toLongOrNull() ?: 0L)
                    }
                    // For ~#list, ~#set or other unknown tags, extract the inner value recursively
                    else -> transitToJsonElement(obj.rep)
                }
            }

            is Map<*, *> -> {
                val map = mutableMapOf<String, JsonElement>()
                for ((k, v) in obj) {
                    val keyStr = when (k) {
                        is Keyword -> {
                            val ns = k.namespace
                            val name = k.name
                            if (ns != null) "$ns/$name" else name
                        }

                        else -> k.toString()
                    }
                    map[keyStr] = transitToJsonElement(v)
                }
                JsonObject(map)
            }

            is Iterable<*> -> JsonArray(obj.map { transitToJsonElement(it) })
            else -> JsonPrimitive(obj.toString())
        }
    }

    @PublishedApi
    internal fun jsonToTransit(parentKey: String?, element: JsonElement): Any? {
        if (element is JsonNull) return null

        if (element is JsonPrimitive) {
            if (element.isString) {
                val content = element.content
                if (parentKey in KEYWORD_VAL_KEYS || parentKey in KEYWORD_SET_KEYS) {
                    return TransitFactory.keyword(content)
                }
                return content
            }
            if (parentKey in DATE_KEYS) {
                // Force it back into Mochi's custom ~#dt tag instead of a standard Date
                return TransitFactory.taggedValue("dt", element.long)
            }
            return element.booleanOrNull ?: element.longOrNull ?: element.doubleOrNull ?: element.content
        }

        if (element is JsonArray) {
            val items = element.map { jsonToTransit(parentKey, it) }
            // If it's a set key, convert to Set so Transit encodes it as ~#set
            if (parentKey in SET_KEYS) {
                return items.toSet()
            }

            // For standard lists, check if Mochi requires ~#list strictly.
            // If you get errors writing arrays later, you can wrap this in TransitFactory.taggedValue("list", items)
            return items.toList()
        }

        if (element is JsonObject) {
            val map = mutableMapOf<Keyword, Any?>()
            for ((k, v) in element) {
                map[TransitFactory.keyword(k)] = jsonToTransit(k, v)
            }
            return map
        }
        return null
    }
}