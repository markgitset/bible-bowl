package net.markdrew.biblebowl.flashcards

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RichTextStrategyTest {

    @Test
    fun `test MarkdownStrategy`() {
        val strategy = MarkdownStrategy()
        assertEquals("# Heading 1\n", strategy.heading("Heading 1", 1))
        assertEquals("## Heading 2\n", strategy.heading("Heading 2", 2))
        assertEquals("**bold text**", strategy.bold("bold text"))
        assertEquals("*italic text*", strategy.italic("italic text"))
        assertEquals("__underlined text__", strategy.underline("underlined text"))
        assertEquals("\n\n", strategy.newline())
        assertEquals("part1part2part3", strategy.combine("part1", "part2", "part3"))
    }

    @Test
    fun `test HtmlStrategy`() {
        val strategy = HtmlStrategy()
        assertEquals("<h1>Heading 1</h1>", strategy.heading("Heading 1", 1))
        assertEquals("<h2>Heading 2</h2>", strategy.heading("Heading 2", 2))
        assertEquals("<b>bold text</b>", strategy.bold("bold text"))
        assertEquals("<i>italic text</i>", strategy.italic("italic text"))
        assertEquals("<u>underlined text</u>", strategy.underline("underlined text"))
        assertEquals("<br/>", strategy.newline())
        assertEquals("part1part2part3", strategy.combine("part1", "part2", "part3"))
    }
}
