package net.markdrew.biblebowl.flashcards.mochi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Top-level Mochi archive payload: decks, templates, and schema metadata
 *
 * @param decks the decks in the archive
 * @param templates card templates referenced by [Card.templateId]
 * @param version Mochi's data-format version
 * @param schemaVersion Mochi's internal schema version
 */
@Serializable
data class MochiData(
    val decks: List<Deck> = emptyList(),
    val templates: List<Template> = emptyList(),
    val version: Int = 2,
    @SerialName("schema/version") val schemaVersion: Int = 34,
)

/** A Mochi card template (front/back layout, fields, etc.) */
@Serializable
data class Template(
    val id: String,
    val name: String,
    val content: String = "",
    val pos: String? = null
)

/**
 * A Mochi deck of cards
 *
 * @param settings catch-all for Mochi's denormalized per-deck Clojure map (settings.pages.deck.…)
 */
@Serializable
data class Deck(
    val name: String,
    val id: String? = null,
    val sort: Int? = null,
    val settings: Map<String, JsonElement>? = null,
    val cards: List<Card> = emptyList()
)

/**
 * A single Mochi card with content and review-state metadata
 *
 * Date fields use Mochi's custom Transit `~#dt` tag and are exposed here as Long timestamps. Several
 * fields are coerced into sets at the Transit layer (see [MochiTransitFormat]).
 */
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
