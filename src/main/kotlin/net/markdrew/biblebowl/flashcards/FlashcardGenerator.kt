package net.markdrew.biblebowl.flashcards

class FlashcardGenerator(
    private val richTextStrategy: RichTextStrategy,
    private val deckExporter: DeckExporter
) {
    fun generateDeck(domainCards: List<Flashcard>): String = generateDeck(domainCards.asSequence())
    fun generateDeck(domainCards: Sequence<Flashcard>): String {
        val renderedCards: Sequence<RenderedCard> = domainCards.map { card ->
            RenderedCard(
                front = card.renderFront(richTextStrategy),
                back = card.renderBack(richTextStrategy)
            )
        }
        return deckExporter.export(renderedCards.toList()) // FIXME
    }
}
