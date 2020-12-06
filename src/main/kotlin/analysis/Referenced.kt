package analysis

interface Referenced {
    val reference: String
}

data class ReferencedWord(override val reference: String, val word: String) : Referenced
data class ReferencedVerse(override val reference: String, val verse: String) : Referenced
data class ReferencedWordSequence(override val reference: String, val words: List<String>) : Referenced
