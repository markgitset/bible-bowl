package net.markdrew.biblebowl.flashcards2

// 1. The Implementation (How to format)
interface MarkupStrategy {
    fun heading(text: String, level: Int): String
    fun bold(text: String): String
    fun italic(text: String): String
    fun newline(): String
    fun combine(vararg elements: String): String
}

// 2. The Abstraction (The Cards)
sealed interface Flashcard {
    fun renderFront(markup: MarkupStrategy): String
    fun renderBack(markup: MarkupStrategy): String
}

// 3. Concrete Cards
data class WordCard(
    val word: String,
    val definition: String,
    val partOfSpeech: String
) : Flashcard {
    override fun renderFront(markup: MarkupStrategy) = 
        markup.heading(word, 2)
        
    override fun renderBack(markup: MarkupStrategy) = markup.combine(
        markup.italic(partOfSpeech),
        markup.newline(),
        definition
    )
}

data class VerseCard(
    val reference: String,
    val text: String
) : Flashcard {
    override fun renderFront(markup: MarkupStrategy) = 
        markup.bold(reference)
        
    override fun renderBack(markup: MarkupStrategy) = 
        text 
}