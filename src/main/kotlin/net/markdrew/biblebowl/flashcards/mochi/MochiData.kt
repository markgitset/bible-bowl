package net.markdrew.biblebowl.flashcards.mochi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MochiData(
    val decks: List<Deck> = emptyList(),
    val templates: List<Template> = emptyList(),
    val version: Int = 2,
    @SerialName("schema/version") val schemaVersion: Int = 34,
)

@Serializable
data class Template(
    val id: String,
    val name: String,
    val content: String = "",
    val pos: String? = null
)

@Serializable
data class Deck(
    val name: String,
    val id: String? = null,
    val sort: Int? = null,
    // Catch-all for complex, denormalized Clojure maps (like settings.pages.deck...)
    val settings: Map<String, JsonElement>? = null, 
    val cards: List<Card> = emptyList()
)

@Serializable
data class Card(
    val content: String,
    val id: String? = null,
    @SerialName("deck-id") val deckId: String? = null,
    @SerialName("template-id") val templateId: String? = null,
    val name: String? = null,
    val pos: String? = null,
    
    @SerialName("new?") val isNew: Boolean = false,
    @SerialName("needs-rereview?") val needsRereview: Boolean = false,
    
    // Mochi uses ~#dt for dates, our adapter exposes them as Longs
    @SerialName("created-at") val createdAt: Long? = null,
    @SerialName("updated-at") val updatedAt: Long? = null,
    
    // Mochi mandates these be sets (~#set)
    val tags: Set<String> = emptySet(),
    val references: Set<String> = emptySet(),
    
    val reviews: List<JsonElement> = emptyList(),
    @SerialName("reverse-reviews") val reverseReviews: List<JsonElement> = emptyList(),
    @SerialName("reverse/needs-rereview?") val reverseNeedsRereview: Boolean = false,
    
    @SerialName("cloze/reviews") val clozeReviews: Map<String, JsonElement> = emptyMap(),
    @SerialName("cloze/indexes") val clozeIndexes: Set<String> = emptySet(),
    @SerialName("cloze/needs-rereview?") val clozeNeedsRereview: Map<String, Boolean> = emptyMap()
)