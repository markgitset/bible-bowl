package net.markdrew.biblebowl.model

/** Anything that carries a [VerseRef] for the verse it came from */
interface Referenced {
    /** The verse this item is anchored to */
    val reference: VerseRef
}

/** A single word tagged with the verse it appears in */
data class ReferencedWord(override val reference: VerseRef, val word: String) : Referenced

/** A verse's full text tagged with its reference */
data class ReferencedVerse(override val reference: VerseRef, val verse: String) : Referenced

/** A sequence of words (e.g. a phrase) tagged with the verse it appears in */
data class ReferencedWordSequence(override val reference: VerseRef, val words: List<String>) : Referenced

/** Indexes verses by reference, dropping the [ReferencedVerse] wrapper. */
fun List<ReferencedVerse>.toVerseMap(): Map<VerseRef, String> =
    this.map { (ref, verse) -> ref to verse }.toMap()
