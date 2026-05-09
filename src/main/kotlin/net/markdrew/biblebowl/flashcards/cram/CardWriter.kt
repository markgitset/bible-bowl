package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.flashcards.Card
import net.markdrew.chupacabra.core.NonCloseableWriter.Companion.stdout
import java.io.Closeable
import java.io.File
import java.io.Writer
import java.nio.charset.Charset
import java.nio.file.Path

/**
 * Writes flashcards to a Cram-compatible delimited file (front + back + optional hint per row)
 *
 * Cram's import format is tab-delimited by default; pass a different [delimiter] for CSV-style output.
 *
 * @param writer destination writer; defaults to stdout
 * @param delimiter character separating front/back/hint fields; defaults to TAB
 */
class CardWriter(private val writer: Writer = stdout(), private val delimiter: Char = DEFAULT_DELIMITER) : Closeable {

    constructor(file: File,
                delimiter: Char = DEFAULT_DELIMITER,
                charset: Charset = Charsets.UTF_8)
            : this(file.also { it.parentFile.mkdirs() }.writer(charset), delimiter)

    constructor(path: Path,
                delimiter: Char = DEFAULT_DELIMITER,
                charset: Charset = Charsets.UTF_8) : this(path.toFile(), delimiter, charset)

    /** Writes one row from positional fields; null [hint] is omitted. */
    fun write(front: Any, back: Any, hint: Any? = null) {
        writer.append(front.toString()).append(delimiter)
        writer.append(back.toString())
        hint?.let { writer.append(delimiter).append(it.toString()) }
        writer.appendLine()
    }

    /** Writes one row per [Card] in [cards]. */
    fun write(cards: Iterable<Card>) {
        cards.forEach { write(it) }
    }

    /** Writes one row from [card]'s fields. */
    fun write(card: Card) {
        with(card) { write(front, back, hint) }
    }

    override fun close() {
        writer.close()
    }

    companion object {
        const val DEFAULT_DELIMITER = '\t'

        /** Convenience: opens [file], writes [cards], and closes the writer. */
        fun writeCards(cards: Iterable<Card>,
                       file: File,
                       delimiter: Char = DEFAULT_DELIMITER,
                       charset: Charset = Charsets.UTF_8) {
            CardWriter(file, delimiter, charset).use { it.write(cards) }
        }
    }
}
