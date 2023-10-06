package net.markdrew.biblebowl.flashcards.mailmerge

import net.markdrew.biblebowl.flashcards.Card
import net.markdrew.chupacabra.core.NonCloseableWriter.Companion.stdout
import java.io.Closeable
import java.io.File
import java.io.Writer
import java.nio.charset.Charset
import java.nio.file.Path

class MailMergeCardWriter(
    private val cardsPerPage: Int,
    private val cardsPerRow: Int,
    private val writer: Writer = stdout(),
    private val delimiter: Char = DEFAULT_DELIMITER
) : Closeable {

    private var cardBuffer = mutableListOf<Card>()

    constructor(
        cardsPerPage: Int,
        cardsPerRow: Int,
        file: File,
        delimiter: Char = DEFAULT_DELIMITER,
        charset: Charset = Charsets.UTF_8
    ) : this(cardsPerPage, cardsPerRow, file.also { it.parentFile.mkdirs() }.writer(charset), delimiter)

    constructor(
        cardsPerPage: Int,
        cardsPerRow: Int,
        path: Path,
        delimiter: Char = DEFAULT_DELIMITER,
        charset: Charset = Charsets.UTF_8
    ) : this(cardsPerPage, cardsPerRow, path.toFile(), delimiter, charset)

    init {
        (1..cardsPerPage).joinTo(writer, delimiter.toString()) { "Card$it" }
        writer.appendLine()
    }

    fun write(cards: Iterable<Card>) {
        cards.forEach { write(it) }
    }

    fun write(card: Card) {
        cardBuffer += card
        if (cardBuffer.size >= cardsPerPage) flushBuffer()
    }

    fun write(front: Any, back: Any, hint: Any? = null) {
        write(Card(front.toString(), back.toString(), hint.toString()))
    }

    private fun <E> MutableList<E>.pad(length: Int, value: E): List<E> {
        while (size < length) add(value)
        return this
    }

    private fun flushBuffer() {
        val cards: List<Card> = cardBuffer//.pad(cardsPerPage, Card.EMPTY)

        cards.joinTo(writer, delimiter.toString()) { """"${it.front}"""" }
        writer.appendLine()

        // backs are trickier because we want to put the back on the back of the page in a position corresponding to its
        // correct front
        cards.windowed(cardsPerRow, cardsPerRow)
            .map { it.reversed() }
            .flatten()
            .joinTo(writer, delimiter.toString()) { """"${it.front}"""" }
        writer.appendLine()

        cardBuffer.clear()
    }

    override fun close() {
        flushBuffer()
        writer.close()
    }

    companion object {
        const val DEFAULT_DELIMITER = ','

        fun writeCards(
            cardsPerPage: Int,
            cardsPerRow: Int,
            cards: Iterable<Card>,
            file: File,
            delimiter: Char = DEFAULT_DELIMITER,
            charset: Charset = Charsets.UTF_8
        ) {
            MailMergeCardWriter(cardsPerPage, cardsPerRow, file, delimiter, charset).use { it.write(cards) }
        }
    }
}