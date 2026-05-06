package net.markdrew.biblebowl.flashcards2

// The standardized output of the rendering phase
data class RenderedCard(val front: String, val back: String)

// The strategy for file generation
interface DeckExporter {
    fun export(cards: List<RenderedCard>): String
}

class DelimitedExporter(private val separator: String = ",") : DeckExporter {
    override fun export(cards: List<RenderedCard>): String {
        val builder = StringBuilder()
        builder.appendLine("Front${separator}Back")
        
        cards.forEach { card ->
            // Basic CSV escaping
            val safeFront = card.front.replace("\"", "\"\"")
            val safeBack = card.back.replace("\"", "\"\"")
            builder.appendLine("\"$safeFront\"$separator\"$safeBack\"")
        }
        return builder.toString()
    }
}

class XmlExporter : DeckExporter {
    override fun export(cards: List<RenderedCard>): String {
        // Implementation for wrapping cards in <deck> and <card> tags
        return "<xml>...</xml>" 
    }
}