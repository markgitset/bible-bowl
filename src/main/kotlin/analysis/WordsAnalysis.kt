package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.ReferencedWord
import java.nio.file.Paths


/**
 * Tokenizes a [ReferencedVerse] into a list of [ReferencedWord]s
 */
fun verseToWordList(refVerse: ReferencedVerse): List<ReferencedWord> =
        refVerse.verse.split(*" \t\r\n.‘!“”:;?()—".toCharArray())   // split around various punctuation
            .flatMap { it.split(""",(?!\d)""".toRegex()) } // split around , not followed by a digit
            .flatMap { it.split("""’(?!s)""".toRegex()) }  // split around ’ not followed by an s
            .filter { it.isNotBlank() }
            .map { ReferencedWord(refVerse.reference, it) }

fun shortenReference(reference: String): String = reference.substringAfter(' ')

val stopWords: List<String> = listOf("the", "and", "of", "to", "a", "i", "who", "in", "on", "with", "for", "will",
    "you", "was", "is", "his", "he", "from", "that", "they", "are", "their", "it", "be", "like", "were", "have",
    "him", "them", "her", "not", "had", "has", "its", "your", "then", "but", "those", "no", "as", "what", "this",
    "by", "my", "so", "into", "or", "when", "came", "an", "these", "which", "there", "been", "am", "at")

fun main() {
    // word frequencies
    val bookData = BookData.readData(Paths.get("output"), Book.REV)
    val versesIndex: List<ReferencedVerse> = bookData.verseList()
    val words: List<String> = versesIndex.flatMap { verseToWordList(it) }
        .map { it.word }
        .filterNot { stopWords.contains(it.toLowerCase()) }
    val groupBy: Map<String, Int> = words.groupingBy { it.toLowerCase() }.eachCount()
        .filter { (_, count) -> count > 1 }
    groupBy.asSequence().sortedBy { it.value }.forEach { (word, count) ->
        println("%12s = %5d".format(word, count))
    }

}
