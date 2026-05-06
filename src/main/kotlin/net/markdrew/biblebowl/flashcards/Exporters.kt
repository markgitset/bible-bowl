package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.flashcards.mochi.Card
import net.markdrew.biblebowl.flashcards.mochi.Deck
import net.markdrew.biblebowl.flashcards.mochi.MochiArchive
import net.markdrew.biblebowl.flashcards.mochi.MochiData

data class RenderedCard(val front: String, val back: String)

interface DeckExporter {
    fun export(cards: List<RenderedCard>): String
}

class DelimitedExporter(private val separator: String = "\t") : DeckExporter {
    override fun export(cards: List<RenderedCard>): String {
        return cards.joinToString("\n") { card ->
            "${card.front}$separator${card.back}"
        }
    }
}

class AnkiExporter : DeckExporter {
    override fun export(cards: List<RenderedCard>): String {
        return cards.joinToString("\n") { card ->
            "${card.front}\t${card.back}"
        }
    }
}

class MochiExporter(private val deckName: String) {
    fun exportToArchive(cards: List<RenderedCard>): MochiArchive {
        val mochiCards = cards.map { card ->
            Card(content = "${card.front}\n---\n${card.back}")
        }
        val data = MochiData(decks = listOf(Deck(name = deckName, cards = mochiCards)))
        return MochiArchive(data)
    }
}
