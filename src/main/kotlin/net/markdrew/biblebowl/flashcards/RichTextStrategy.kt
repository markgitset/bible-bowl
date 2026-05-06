package net.markdrew.biblebowl.flashcards

interface RichTextStrategy {
    fun heading(text: String, level: Int): String
    fun bold(text: String): String
    fun italic(text: String): String
    fun underline(text: String): String
    fun newline(): String
    fun combine(vararg elements: String): String
}

class MarkdownStrategy : RichTextStrategy {
    override fun heading(text: String, level: Int) = "${"#".repeat(level)} $text\n"
    override fun bold(text: String) = "**$text**"
    override fun italic(text: String) = "*$text*"
    override fun underline(text: String) = "__${text}__"
    override fun newline() = "\n\n"
    override fun combine(vararg elements: String) = elements.joinToString("")
}

class HtmlStrategy : RichTextStrategy {
    override fun heading(text: String, level: Int) = "<h$level>$text</h$level>"
    override fun bold(text: String) = "<b>$text</b>"
    override fun italic(text: String) = "<i>$text</i>"
    override fun underline(text: String) = "<u>$text</u>"
    override fun newline() = "<br/>"
    override fun combine(vararg elements: String) = elements.joinToString("")
}
