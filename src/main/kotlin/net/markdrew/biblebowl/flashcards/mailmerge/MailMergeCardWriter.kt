package net.markdrew.biblebowl.flashcards.mailmerge

import net.markdrew.chupacabra.core.NonCloseableWriter.Companion.stdout
import java.io.Closeable
import java.io.File
import java.io.Writer
import java.nio.charset.Charset
import java.nio.file.Path

class MailMergeCardWriter(
    private val cardsPerPage: Int,
    private val writer: Writer = stdout(),
    private val delimiter: Char = DEFAULT_DELIMITER
) : Closeable {

    private var cardBuffer = mutableListOf<Map<String, String>>()

    constructor(
        cardsPerPage: Int,
        file: File,
        delimiter: Char = DEFAULT_DELIMITER,
        charset: Charset = Charsets.UTF_8
    ) : this(cardsPerPage, file.also { it.parentFile.mkdirs() }.writer(charset), delimiter)

    constructor(
        cardsPerPage: Int,
        path: Path,
        delimiter: Char = DEFAULT_DELIMITER,
        charset: Charset = Charsets.UTF_8
    ) : this(cardsPerPage, path.toFile(), delimiter, charset)

    init {
        (1..cardsPerPage).asSequence()
            .flatMap { sequenceOf("Front$it", "Back$it") }
            .joinTo(writer, delimiter.toString())
        writer.appendLine()
    }

    fun write(cards: Iterable<Map<String, String>>) {
        cards.forEach { write(it) }
    }

    fun write(card: Map<String,String>) {
        cardBuffer += card
        if (cardBuffer.size >= cardsPerPage) flushBuffer()
    }

    private fun <E> MutableList<E>.pad(length: Int, value: E): List<E> {
        while (size < length) add(value)
        return this
    }

    private fun writeCard(index: Int, card: Map<String, String>) {
        card.entries
    }

    private fun flushBuffer() {
        val cards: List<Map<String, String>> = cardBuffer//.pad(cardsPerPage, Map<String, String>.EMPTY)

        cards.flatMapIndexed { (i, fields) -> fields. // equenceOf(""""${fields.kit.front}"""", """"${it.back}"""") }.joinTo(writer, delimiter.toString())
        writer.appendLine()

        // backs are trickier because we want to put the back on the back of the page in a position corresponding to its
        // correct front
//        cards.windowed(cardsPerRow, cardsPerRow)
//            .map { it.reversed() }
//            .flatten()
//            .joinTo(writer, delimiter.toString()) { """"${it.back}"""" }
//        writer.appendLine()

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
            cards: Iterable<Map<String, String>>,
            file: File,
            delimiter: Char = DEFAULT_DELIMITER,
            charset: Charset = Charsets.UTF_8
        ) {
            MailMergeCardWriter(cardsPerPage, file, delimiter, charset).use { it.write(cards) }
        }
    }
}