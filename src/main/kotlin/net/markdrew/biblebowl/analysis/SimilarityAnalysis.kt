package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.StringSubsequenceKernel
import net.markdrew.chupacabra.core.levenshtein
import java.util.TreeMap
import kotlin.math.abs
import kotlin.math.min

fun main() {
    // phrase frequencies
    val bookData = BookData.readData()
    val similaritiesIndex: Map<VerseRef, TopNMap<Double, VerseRef>> = buildSimilaritiesIndex(bookData)
    printSimilarities(similaritiesIndex)
}

class TopNMap<K, V>(val topN: Int) : TreeMap<K, V>() {
    override fun put(key: K, value: V): V? {
        val result: V? = super.put(key, value)
        if (size > topN) remove(firstKey())
        return result
    }
}

fun buildSimilaritiesIndex(bookData: BookData): Map<VerseRef, TopNMap<Double, VerseRef>> {
    val topNSize = 3
    val ssk = StringSubsequenceKernel()
    val verses: List<ReferencedVerse> = bookData.verseList()
    val tmpMap: Map<VerseRef, TopNMap<Double, VerseRef>> = buildMap {
        for (v in verses) {
            put(v.reference, TopNMap(topNSize))
        }
    }
    for (i in verses.indices) {
        println(verses[i].reference.toFullString())
        val topNMap: TopNMap<Double, VerseRef> = tmpMap.getValue(verses[i].reference)
        for (j in verses.indices.drop(i + 1)) {
//            topNMap[ssk.normalizedKernel(2, verses[i].verse, verses[j].verse)] = verses[j].reference
            val lengthDiff = abs(verses[i].verse.length - verses[j].verse.length)
            val minLength = min(verses[i].verse.length, verses[j].verse.length)
            topNMap[1 - (levenshtein(verses[i].verse, verses[j].verse) - lengthDiff)/minLength.toDouble()] = verses[j].reference
        }
    }
    return tmpMap
}

private fun printSimilarities(indexEntries: Map<VerseRef, TopNMap<Double, VerseRef>>) {
    indexEntries.toSortedMap().forEach { (ref, topNMap) ->
        println(ref.toFullString())
        topNMap.forEach { (similarity, simRef) ->
            println("\t%5.2f  %s".format(similarity, simRef.toFullString()))
        }
    }
}
