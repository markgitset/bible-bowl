package net.markdrew.biblebowl.flashcards2

class FlashcardGenerator(
    private val markupStrategy: MarkupStrategy,
    private val deckExporter: DeckExporter
) {
    fun generateDeck(domainCards: List<Flashcard>): String {
        // 1. Convert domain objects to rendered strings using the Bridge
        val renderedCards = domainCards.map { card ->
            RenderedCard(
                front = card.renderFront(markupStrategy),
                back = card.renderBack(markupStrategy)
            )
        }
        
        // 2. Export the rendered strings to the target file format
        return deckExporter.export(renderedCards)
    }
}