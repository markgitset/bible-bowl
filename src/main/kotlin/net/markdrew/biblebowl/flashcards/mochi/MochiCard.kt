@file:OptIn(ExperimentalSerializationApi::class)

package net.markdrew.biblebowl.flashcards.mochi

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Represents a card object in a Mochi flashcard deck.
 *
 * @param content The contents of the card.
 * @param deck-id An :id of the parent Deck (only necessary for cards in the top level :cards vector).
 * @param id Optional keyword. (0-9A-Za-z) Should be globally unique, at least 8 characters.
 * @param name Optional string. The name will show up when linking to this Card from markdown, as well as in other parts of
 * the UI.
 * @param pos Optional string. /[0-9A-Za-z]+/ The order that this card will be displayed, relative to others in the deck page,
 * as well as during learning. The cards will be sorted lexicographically.
 * @param reviews Optional vector of Reviews.
 * @param fields Optional map of field ids to values. Values are usually strings, but if the field is of type :boolean, then
 * the value should be a boolean.
 */
@Serializable
class MochiCard(
    @SerialName("~:content") val content: String,
    @SerialName("~:deck-id") val deckId: String? = null,
    val id: String? = null,
    val name: String? = null
) {
}
//:version - The number 2.
//Optional
//
//:decks - A vector of Decks.
//:cards - A vector of Cards.
//NOTE: Cards added to this field must contain a valid :deck-id.
//:templates - A vector of Templates.
@Serializable
class MochiFile(
    @SerialName("~:decks") @Serializable(with = MochiListSerializer::class) val decks: List<MochiDeck>,
    @SerialName("~:version") @EncodeDefault(EncodeDefault.Mode.ALWAYS) val version: Int = 2,
)
//:name - A string.
//Optional
//
//:id - A keyword. (0-9A-Za-z) Should be globally unique, at least 8 characters.
//:cards - A vector of Cards.
//:parent-id - An :id of another Deck. If provided, this deck will be nested under the parent deck in the sidebar.
@Serializable
class MochiDeck(
    @SerialName("~:name") val name: String,
    val id: String? = null,
    @SerialName("~:cards") @Serializable(with = MochiListSerializer::class) val cards: List<MochiCard>? = null,
    @SerialName("parent-id") val parentId: String? = null
) {
}
fun main() {
    val data = MochiFile(
        listOf(
            MochiDeck("Test Deck",
                cards = listOf(
                    MochiCard("Hello World!"),
                    MochiCard("Bye World!"),
                )
            )
        )
    )

    val path: Path = Paths.get("test.mochi")
    writeMochiFile(path, data)
}

private fun writeMochiFile(path: Path, data: MochiFile) {
    val json = Json {
        prettyPrint = true

//        encodeDefaults = true
    }
    println(json.encodeToString(data))
    ZipOutputStream(Files.newOutputStream(path)).use { zipOut ->
        zipOut.putNextEntry(ZipEntry("data.json"))
        json.encodeToStream(data, zipOut)
        zipOut.closeEntry()
    }
    println("Wrote $path")
}
