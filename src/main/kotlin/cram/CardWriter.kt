package net.markdrew.biblebowl.cram

import net.markdrew.chupacabra.core.NonCloseableWriter.Companion.stdout
import java.io.Closeable
import java.io.File
import java.io.Writer
import java.nio.charset.Charset

class CardWriter(private val writer: Writer = stdout(), private val delimiter: Char = DEFAULT_DELIMITER) : Closeable {

    constructor(file: File,
                delimiter: Char = DEFAULT_DELIMITER,
                charset: Charset = Charsets.UTF_8) : this(file.writer(charset), delimiter)

    fun write(front: String, back: String, hint: String? = null) {
        writer.append(front).append(delimiter)
        writer.append(back)
        hint?.let { writer.append(delimiter).append(it) }
        writer.appendLine()
    }

    fun write(cards: Iterable<Card>) {
        cards.forEach { write(it) }
    }

    fun write(card: Card) {
        with(card) { write(front, back, hint) }
    }

    override fun close() {
        writer.close()
    }

    companion object {
        const val DEFAULT_DELIMITER = '\t'

        fun writeCards(cards: Iterable<Card>,
                       file: File,
                       delimiter: Char = DEFAULT_DELIMITER,
                       charset: Charset = Charsets.UTF_8) {
            CardWriter(file, delimiter, charset).use { it.write(cards) }
        }
    }
}