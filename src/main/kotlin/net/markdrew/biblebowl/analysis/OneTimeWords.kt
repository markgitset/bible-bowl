package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap

fun oneTimeWords(bookData: BookData): List<IntRange> = bookData.words
    .groupBy { bookData.text.substring(it).lowercase() }
    .filterValues { it.size == 1 }.values.flatten()

fun oneTimeWords(studyData: StudyData): List<IntRange> = studyData.words
    .groupBy { studyData.text.substring(it).lowercase() }
    .filterValues { it.size == 1 }.values.flatten()

data class OneSectionWord<T>(
    val word: String,
    val wordRanges: List<IntRange>,
    val section: T,
    val sectionRange: IntRange
)

/**
 * Build list of one-section words (where the sections could be chapters or headings)
 */
fun <T : Any> oneSectionWords(
    bookData: BookData,
    sectionMap: DisjointRangeMap<T>
): List<OneSectionWord<T>> = bookData.wordIndex
    .filterValues { ranges -> ranges.size > 1 } // remove one-time words
    .filterValues { ranges ->
        ranges.map { sectionMap.valueEnclosing(it) }.distinct().count() == 1  // only entries all in same section
    }.map { (word, ranges) ->
        val (sectionRange, section) = sectionMap.entryEnclosing(ranges.first()) ?: throw Exception()
        OneSectionWord(word, ranges, section, sectionRange)
    }
