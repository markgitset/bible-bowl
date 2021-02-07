package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.enclose
import java.nio.file.Paths
import java.util.*

typealias PhraseIndexEntry = IndexEntry<List<String>, VerseRef>

fun <E> List<E>.indexOfSublist(subList: List<E>): Int = Collections.indexOfSubList(this, subList)

fun main() {
    // word frequencies
    val bookData = BookData.readData(Paths.get("output"), Book.REV)
    val maxPhraseLength = 25
    var phraseIndex: MutableList<PhraseIndexEntry>

    println(23)
    phraseIndex = buildPhraseIndex(bookData, nWords = 23, stopWords = STOP_WORDS).toMutableList()
//    printPhraseFrequencies(phraseIndex)

    for (i in (22 downTo 2)) {
        println(i)
        val phraseIndex2 = buildPhraseIndex(bookData, nWords = i, stopWords = STOP_WORDS).filter { entry ->
            phraseIndex.none { it.key.indexOfSublist(entry.key) >= 0 && it.values.size == entry.values.size }
        }
        phraseIndex.addAll(phraseIndex2)
//        printPhraseFrequencies(phraseIndex)
    }
    printPhraseFrequencies(phraseIndex)

}

private fun printWordIndex(buildWordIndex: List<WordIndexEntry>) {
    buildWordIndex
        .sortedBy { it.key }
        .forEach { (word, verseList) ->
            verseList.joinTo(System.out, prefix = "$word: ", postfix = "\n") { it.toChapterAndVerse() }
        }
}

private fun buildPhraseIndex(bookData: BookData,
                             nWords: Int,
                             stopWords: Set<String> = setOf(),
                             frequencyRange: IntRange = 2..Int.MAX_VALUE,
                             minGoWords: Int = 2): List<PhraseIndexEntry> {
    val windowed: Sequence<Pair<List<String>, IntRange>> = bookData.words.asSequence()
        .map { bookData.excerpt(it) }
        .windowed(nWords) { excerpts ->
            val words = excerpts.map { it.excerptText.toLowerCase() }
            if (words.count { it !in stopWords } < minGoWords) null
            else
                words to
                    excerpts.map { it.excerptRange }.reduce { r1, r2 -> r1.enclose(r2) }
        }.filterNotNull()
    val index: Map<List<String>, List<IntRange>> = windowed.groupBy({ it.first }, { it.second })
    return index
        .filter { (phrase, ranges) ->
            /*phrase !in stopWords &&*/ frequencyRange.contains(ranges.size)
        }.map { (phrase, ranges) ->
            PhraseIndexEntry(phrase, ranges.map { phraseRange ->
                bookData.verseEnclosing(phraseRange.first..phraseRange.first) ?: throw Exception("Couldn't find verse enclosing $phraseRange!")
            })
        }
}

private fun printPhraseFrequencies(indexEntries: List<PhraseIndexEntry>) {
    indexEntries.sortedBy { it.key.size }.forEach { (phrase, refs) ->
        println("%5d = %s".format(refs.size, phrase.joinToString(" ")))
    }
}
