package net.markdrew.biblebowl.model

interface Referenced {
    val reference: VerseRef
}

data class ReferencedWord(override val reference: VerseRef, val word: String) : Referenced
data class ReferencedVerse(override val reference: VerseRef, val verse: String) : Referenced
data class ReferencedWordSequence(override val reference: VerseRef, val words: List<String>) : Referenced

fun List<ReferencedVerse>.toVerseMap(): Map<VerseRef, String> =
    this.map { (ref, verse) -> ref to verse }.toMap()
