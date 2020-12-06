package analysis

import java.io.File

/**
 * Read verses from TSV file of (verse)TAB(reference) to a Map<ref,verse>
 */
fun readVerses(versesFile: File): List<ReferencedVerse> = versesFile.readLines(Charsets.UTF_8).map { line ->
    val (verse, ref) = line.split('\t', limit = 2)
    ReferencedVerse(ref, verse)
}

fun List<ReferencedVerse>.toVerseMap(): Map<String, String> = this.map { (ref, verse) -> ref to verse }.toMap()

fun verseToWordList(refVerse: ReferencedVerse): List<ReferencedWord> =
        refVerse.verse.split(*" \t\r\n.‘!“”:;?()—".toCharArray())   // split around various punctuation
                .flatMap { it.split(""",(?!\d)""".toRegex()) } // split around , not followed by a digit
                .flatMap { it.split("""’(?!s)""".toRegex()) }  // split around ’ not followed by an s
                .map { ReferencedWord(refVerse.reference, it) }

fun shortenReference(reference: String): String = reference.substringAfter(' ')