package net.markdrew.biblebowl.flashcards

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExporterTest {

    @Test
    fun `test DelimitedExporter`() {
        val cards = listOf(
            RenderedCard("Front 1", "Back 1"),
            RenderedCard("Front 2", "Back 2")
        )
        val exporter = DelimitedExporter(",")
        val result = exporter.export(cards)
        assertEquals("Front 1,Back 1\nFront 2,Back 2", result)
    }

    @Test
    fun `test AnkiExporter`() {
        val cards = listOf(
            RenderedCard("Front 1", "Back 1"),
            RenderedCard("Front 2", "Back 2")
        )
        val exporter = AnkiExporter()
        val result = exporter.export(cards)
        assertEquals("Front 1\tBack 1\nFront 2\tBack 2", result)
    }
}
