package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.latex.IndexEntry
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.enclose
import java.nio.file.Paths
import java.util.*

typealias PhraseIndexEntry = IndexEntry<List<String>, VerseRef>

fun <E> List<E>.indexOfSublist(subList: List<E>): Int = Collections.indexOfSubList(this, subList)
fun <E> List<E>.containsSublist(subList: List<E>): Boolean = this.indexOfSublist(subList) >= 0

fun main() {
    // phrase frequencies
    val bookData = BookData.readData(Book.DEFAULT, Paths.get(DATA_DIR))
    val phrasesIndex = buildPhrasesIndex(bookData, maxPhraseLength = 23)
    printPhraseFrequencies(phrasesIndex)
}

/**
 * Returns true if this phrase subsumes the [other] phrase. Alternatively, if the words of the [other] phrase are a
 * sublist of this phrase and it's found in the same number of places as this phrase.
 */
internal fun PhraseIndexEntry.subsumes(other: PhraseIndexEntry): Boolean =
    // if the words of the other phrase are a sublist of this phrase (other is shorter) AND
    this.key.containsSublist(other.key) &&
            // it's found in the same number of places as this phrase OR
            this.values.size == other.values.size ||
    // if the words of this phrase are a sublist of the other phrase (other is longer) AND
    other.key.containsSublist(this.key) &&
            // this phrase is only lacking stopwords from the other phrase AND
            other.key.toSet().subtract(this.key).all { it in STOP_WORDS } &&
            // it's found in a superset of the other phrase's places
            other.values.toSet().subtract(this.values).isEmpty()

fun buildNonLocalPhrasesIndex(bookData: BookData, maxPhraseLength: Int): List<PhraseIndexEntry> =
    buildPhrasesIndex(bookData, maxPhraseLength).filter { pie ->
        pie.values.map { ref ->
            val range: IntRange = bookData.verseIndex[ref.absoluteVerse] ?: throw Exception()
            bookData.chapters.valueEnclosing(range) ?: throw Exception()
        }.distinct().size > 1
    }

fun buildPhrasesIndex(bookData: BookData, maxPhraseLength: Int): List<PhraseIndexEntry> {
    val phrasesIndex = mutableListOf<PhraseIndexEntry>()
    for (nWords in maxPhraseLength downTo 2) {
        val nGramIndex = buildNGramIndex(bookData, nWords, stopWords = STOP_WORDS).filter { nGram ->
            phrasesIndex.none { phraseEntry -> phraseEntry.subsumes(nGram) }
        }
        phrasesIndex.addAll(nGramIndex)
    }
    // TODO fix this, but need to filter again here because now we're allowing shorter phrases to "subsume" longer ones
    return phrasesIndex.filter { entry -> phrasesIndex.none { it != entry && it.subsumes(entry) } }
}

fun buildNGramIndex(bookData: BookData,
                    nWords: Int,
                    stopWords: Set<String> = setOf(),
                    frequencyRange: IntRange = 2..Int.MAX_VALUE,
                    minGoWords: Int = 2): List<PhraseIndexEntry> {
    val windowed: Sequence<Pair<List<String>, IntRange>> = bookData.words.asSequence()
        .map { bookData.excerpt(it) }
        .windowed(nWords) { excerpts ->
            val words = excerpts.map { it.excerptText.lowercase() }
            if (words.count { it !in stopWords } < minGoWords) null
            else words to excerpts.map { it.excerptRange }.reduce { r1, r2 -> r1.enclose(r2) }
        }.filterNotNull()
    val index: Map<List<String>, List<IntRange>> = windowed.groupBy({ it.first }, { it.second })
    return index.filter { (phrase, ranges) ->
        /*phrase !in stopWords &&*/ ranges.size in frequencyRange
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
