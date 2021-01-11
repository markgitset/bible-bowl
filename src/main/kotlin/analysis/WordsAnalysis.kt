package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths


val stopWords: List<String> = listOf("the", "and", "of", "to", "a", "i", "who", "in", "on", "with", "for", "will",
    "you", "was", "is", "his", "he", "from", "that", "they", "are", "their", "it", "be", "like", "were", "have",
    "him", "them", "her", "not", "had", "has", "its", "your", "then", "but", "those", "no", "as", "what", "this",
    "by", "my", "so", "into", "or", "when", "came", "an", "these", "which", "there", "been", "am", "at")

fun main() {
    // word frequencies
    val bookData = BookData.readData(Paths.get("output"), Book.REV)
    val text = bookData.text
    val groupBy: Map<String, Int> = bookData.words
        .filterNot { stopWords.contains(text.substring(it).toLowerCase()) }
        .groupingBy { text.substring(it).toLowerCase() }.eachCount()
        .filterValues { it > 1 }
    groupBy.asSequence().sortedBy { it.value }.forEach { (word, count) ->
        println("%12s = %5d".format(word, count))
    }

}
