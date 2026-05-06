package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.VerseRef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FlashcardTest {

    private val htmlMarkup = HtmlStrategy()
    private val markdownMarkup = MarkdownStrategy()

    @Test
    fun `test EventFlashcard rendering`() {
        val range = VerseRange(VerseRef(Book.GEN, 1, 1), VerseRef(Book.GEN, 1, 5))
        val card = EventFlashcard("Creation", range, listOf("The Beginning", "Light"))
        
        assertEquals("Creation", card.renderFront(htmlMarkup))
        assertEquals("Genesis 1:1-5 (The Beginning AND Light)", card.renderBack(htmlMarkup))
    }

    @Test
    fun `test HeadingFlashcard rendering`() {
        val card = SimpleHeadingFlashcard("The Beginning", "Genesis 1:1-2:3")
        
        assertEquals("The Beginning", card.renderFront(htmlMarkup))
        assertEquals("Genesis 1:1-2:3", card.renderBack(htmlMarkup))
    }

    @Test
    fun `test VerseFlashcard rendering`() {
        val card = VerseFlashcard("Gen 1:1", "In the beginning...", "Creation")
        
        assertEquals("In the beginning...", card.renderFront(htmlMarkup))
        assertEquals("Creation<br/><b>Gen 1:1</b>", card.renderBack(htmlMarkup))
        
        val cardNoHeading = VerseFlashcard("Gen 1:1", "In the beginning...")
        assertEquals("<b>Gen 1:1</b>", cardNoHeading.renderBack(htmlMarkup))
    }

    @Test
    fun `test WordFlashcard rendering`() {
        val card = WordFlashcard("beginning", "In the <b><u>beginning</u></b>...", "Gen 1:1", "Creation")
        
        assertEquals("beginning", card.renderFront(htmlMarkup))
        assertEquals("Creation<br/><b>Gen 1:1</b><br/>In the <b><u>beginning</u></b>...", card.renderBack(htmlMarkup))
    }

    @Test
    fun `test FillInTheBlankFlashcard rendering`() {
        val card = FillInTheBlankFlashcard("In the [___]...", "beginning", "Gen 1:1")
        
        assertEquals("In the [___]...", card.renderFront(htmlMarkup))
        assertEquals("beginning<br/><b>(Gen 1:1)</b>", card.renderBack(htmlMarkup))
    }

    @Test
    fun `test FlashcardGenerator with DelimitedExporter`() {
        val cards = listOf(
            SimpleHeadingFlashcard("H1", "Ref1"),
            SimpleHeadingFlashcard("H2", "Ref2")
        )
        val generator = FlashcardGenerator(htmlMarkup, DelimitedExporter("\t"))
        val result = generator.generateDeck(cards)
        
        assertEquals("H1\tRef1\nH2\tRef2", result)
    }

    @Test
    fun `test MarkdownStrategy rendering`() {
        val card = VerseFlashcard("Gen 1:1", "In the beginning...", "Creation")
        
        assertEquals("Creation\n\n**Gen 1:1**", card.renderBack(markdownMarkup))
    }
}
