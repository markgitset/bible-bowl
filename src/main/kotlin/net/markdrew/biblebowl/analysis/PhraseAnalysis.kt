package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.model.IndexEntry
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.enclose
import java.nio.file.Paths
import java.util.Collections

/** An index entry whose key is a phrase (list of words) and whose values are the verses it appears in */
typealias PhraseIndexEntry = IndexEntry<List<String>, VerseRef>

/** Returns the start index of [subList] in this list, or -1 if not found. */
fun <E> List<E>.indexOfSublist(subList: List<E>): Int = Collections.indexOfSubList(this, subList)

/** Returns true if [subList] appears as a contiguous run within this list. */
fun <E> List<E>.containsSublist(subList: List<E>): Boolean = this.indexOfSublist(subList) >= 0

fun main() {
    // phrase frequencies
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR_NAME))
    val phrasesIndex = buildPhrasesIndex(studyData, maxPhraseLength = 23)
    printPhraseFrequencies(phrasesIndex)
}

/**
 * Returns true if this phrase subsumes [other], either because [other] is a longer phrase that contains this
 * one as a contiguous sublist with the same occurrence set, or because [other] is a shorter phrase that
 * differs only by missing stop words and appears in a subset of this phrase's verses
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

/**
 * Like [buildPhrasesIndex] but filtered to phrases that appear in more than one chapter ("non-local"
 * phrases worth surfacing in cross-chapter indices).
 */
fun buildNonLocalPhrasesIndex(studyData: StudyData, maxPhraseLength: Int): List<PhraseIndexEntry> =
    buildPhrasesIndex(studyData, maxPhraseLength).filter { pie ->
        pie.values.map { ref ->
            val range: IntRange = studyData.verseIndex[ref] ?: throw Exception()
            studyData.chapters.valueEnclosing(range) ?: throw Exception()
        }.distinct().size > 1
    }

/**
 * Mines recurring phrases up to [maxPhraseLength] words long
 *
 * Walks the study data with windows from [maxPhraseLength] down to 2, dropping any n-gram whose phrase is
 * subsumed by a previously-found longer phrase, then runs a final dedup pass to remove leftover overlaps
 * created when shorter phrases are allowed to subsume longer ones via the stop-word rule in [subsumes].
 */
fun buildPhrasesIndex(studyData: StudyData, maxPhraseLength: Int): List<PhraseIndexEntry> {
    val phrasesIndex = mutableListOf<PhraseIndexEntry>()
    for (nWords in maxPhraseLength downTo 2) {
        val nGramIndex = buildNGramIndex(studyData, nWords, stopWords = STOP_WORDS).filter { nGram ->
            phrasesIndex.none { phraseEntry -> phraseEntry.subsumes(nGram) }
        }
        phrasesIndex.addAll(nGramIndex)
    }
    // TODO fix this, but need to filter again here because now we're allowing shorter phrases to "subsume" longer ones
    return phrasesIndex.filter { entry -> phrasesIndex.none { it != entry && it.subsumes(entry) } }
}

/**
 * Builds an index of word [nWords]-grams whose occurrence count falls within [frequencyRange]
 *
 * @param stopWords words that don't count toward [minGoWords]
 * @param frequencyRange n-grams with occurrence counts outside this range are dropped
 * @param minGoWords minimum number of non-stop words an n-gram must contain to be considered
 */
fun buildNGramIndex(
    studyData: StudyData,
    nWords: Int,
    stopWords: Set<String> = setOf(),
    frequencyRange: IntRange = 2..Int.MAX_VALUE,
    minGoWords: Int = 2): List<PhraseIndexEntry> {
    val windowed: Sequence<Pair<List<String>, IntRange>> = studyData.words.asSequence()
        .map { studyData.excerpt(it) }
        .windowed(nWords) { excerpts ->
            val words = excerpts.map { it.excerptText.lowercase() }
            if (words.count { it !in stopWords } < minGoWords) null
            else words to excerpts.map { it.excerptRange }.reduce { r1, r2 -> r1.enclose(r2) }
        }.filterNotNull()
    val index: Map<List<String>, List<IntRange>> = windowed.groupBy({ it.first }, { it.second })
    return index.filter { (_ /*phrase*/, ranges) ->
        /*phrase !in stopWords &&*/ ranges.size in frequencyRange
    }.map { (phrase, ranges) ->
        PhraseIndexEntry(phrase, ranges.map { phraseRange ->
            studyData.verseEnclosing(phraseRange.first..phraseRange.first) ?: throw Exception("Couldn't find verse enclosing $phraseRange!")
        })
    }
}

private fun printPhraseFrequencies(indexEntries: List<PhraseIndexEntry>) {
    indexEntries.sortedBy { it.key.size }.forEach { (phrase, refs) ->
        println("%5d = %s".format(refs.size, phrase.joinToString(" ")))
    }
}
