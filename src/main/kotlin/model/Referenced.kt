package net.markdrew.biblebowl.model

interface Referenced {
    val reference: BookChapterVerse
}

data class ReferencedWord(override val reference: BookChapterVerse, val word: String) : Referenced
data class ReferencedVerse(override val reference: BookChapterVerse, val verse: String) : Referenced
data class ReferencedWordSequence(override val reference: BookChapterVerse, val words: List<String>) : Referenced
