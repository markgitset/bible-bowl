package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Paths


val STOP_WORDS: Set<String> = setOf("the", "and", "of", "to", "a", "i", "who", "in", "on", "with", "for", "will",
    "you", "was", "is", "his", "he", "from", "that", "they", "are", "their", "it", "be", "like", "were", "have",
    "him", "them", "her", "not", "had", "has", "its", "your", "then", "but", "those", "no", "as", "what", "this",
    "by", "my", "so", "into", "or", "when", "came", "an", "these", "which", "there", "been", "am", "at", "nor", "shall",
    "let", "do", "she", "if", "also", "our", "about", "may", "where", "because", "o", "would", "whose", "here", "how",
    "could", "does", "me", "says", "said", "all", "out", "we", "went", "us")

fun main() {
    // word frequencies
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR))
    val wordIndex: List<WordIndexEntry> = buildWordIndex(studyData, STOP_WORDS, frequencyRange = 2..Int.MAX_VALUE)
    printWordFrequencies(wordIndex)
    printWordIndex(wordIndex)
}

data class WithCount<T>(val item: T, val count: Int)
typealias WordIndexEntryC = IndexEntry<String, WithCount<VerseRef>>
typealias WordIndexEntry = IndexEntry<String, VerseRef>
typealias VerseIndexEntry = IndexEntry<VerseRef, String>

private fun printWordIndex(buildWordIndex: List<WordIndexEntry>) {
    buildWordIndex
        .sortedBy { it.key }
        .forEach { (word, verseList) ->
            verseList.joinTo(System.out, prefix = "$word: ", postfix = "\n") { it.toChapterAndVerse() }
        }
}

fun buildWordIndex(
    bookData: StudyData,
    stopWords: Set<String> = setOf(),
    frequencyRange: IntRange? = null): List<WordIndexEntry> =
    bookData.wordIndex
        .filter { (word, ranges) ->
            word !in stopWords && frequencyRange?.contains(ranges.size) ?: true
        }.map { (word, ranges) ->
            WordIndexEntry(word, ranges.map { wordRange ->
                bookData.verseEnclosing(wordRange) ?: throw Exception("Couldn't find verse enclosing $wordRange!")
            })
        }

fun printWordFrequencies(indexEntries: List<IndexEntry<*, *>>) {
    indexEntries.sortedBy { it.values.size }.forEach { (key, refs) ->
        println("%45s = %5d".format(key, refs.size))
    }
}
