package net.markdrew.biblebowl.flashcards.mochi

/**
 * A complete, parsed Mochi archive
 *
 * @param data the parsed Transit JSON data containing decks, templates, and cards
 * @param mediaFiles a map of file paths to their raw byte contents (e.g. "image.png" -> [bytes])
 */
data class MochiArchive(
    val data: MochiData,
    val mediaFiles: Map<String, ByteArray> = emptyMap()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MochiArchive
        if (data != other.data) return false
        // Map equality with ByteArray values requires deep content comparison
        if (mediaFiles.keys != other.mediaFiles.keys) return false
        return mediaFiles.all { (k, v) -> v.contentEquals(other.mediaFiles[k]) }
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + mediaFiles.entries.sumOf { (k, v) -> k.hashCode() xor v.contentHashCode() }
        return result
    }
}
