package net.markdrew.biblebowl.flashcards2

class MarkdownStrategy : MarkupStrategy {
    override fun heading(text: String, level: Int) = "${"#".repeat(level)} $text\n"
    override fun bold(text: String) = "**$text**"
    override fun italic(text: String) = "*$text*"
    override fun newline() = "\n\n"
    override fun combine(vararg elements: String) = elements.joinToString("")
}

class HtmlStrategy : MarkupStrategy {
    override fun heading(text: String, level: Int) = "<h$level>$text</h$level>"
    override fun bold(text: String) = "<strong>$text</strong>"
    override fun italic(text: String) = "<em>$text</em>"
    override fun newline() = "<br/>"
    override fun combine(vararg elements: String) = elements.joinToString("")
}