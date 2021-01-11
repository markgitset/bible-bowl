package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.toVerseRef
import java.nio.file.Paths


val stopWords: Set<String> = setOf("the", "and", "of", "to", "a", "i", "who", "in", "on", "with", "for", "will",
    "you", "was", "is", "his", "he", "from", "that", "they", "are", "their", "it", "be", "like", "were", "have",
    "him", "them", "her", "not", "had", "has", "its", "your", "then", "but", "those", "no", "as", "what", "this",
    "by", "my", "so", "into", "or", "when", "came", "an", "these", "which", "there", "been", "am", "at", "nor", "shall",
    "let", "do", "she", "if", "also", "our", "about", "may", "where", "because", "o", "would", "whose", "here", "how",
    "could", "does")

fun main() {
    // word frequencies
    val bookData = BookData.readData(Paths.get("output"), Book.REV)
    printWordFrequencies(bookData)
    printWordIndex(bookData, stopWords)
}

private fun printWordIndex(bookData: BookData, stopWords: Set<String>) {
    bookData.wordIndex
        .filterKeys { it !in stopWords }
        .filterValues { it.size in 2..5 } // 2- to 5-time words
        .entries.sortedBy { it.key }
        .forEach { (word, rangeList) ->
            rangeList.joinTo(System.out, prefix = "$word: ", postfix = "\n") { wordRange ->
                bookData.verses.valueEnclosing(wordRange)?.toVerseRef()?.toChapterAndVerseString() ?: "ERR"
            }
        }
}

private fun printWordFrequencies(bookData: BookData) {
    val text = bookData.text
    val groupBy: Map<String, Int> = bookData.words
        .filterNot { stopWords.contains(text.substring(it).toLowerCase()) }
        .groupingBy { text.substring(it).toLowerCase() }.eachCount()
        .filterValues { it > 1 }
    groupBy.asSequence().sortedBy { it.value }.forEach { (word, count) ->
        println("%12s = %5d".format(word, count))
    }
}
