package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap

/** Returns every character range whose word appears exactly once in [studyData] (a "hapax"). */
fun oneTimeWords(studyData: StudyData): List<IntRange> = studyData.wordIndex
    .filterValues { it.size == 1 }.values.flatten()

/**
 * A word that appears multiple times but only within one section (e.g. one chapter or one heading)
 *
 * @param word the lowercased word
 * @param wordRanges every character range where the word occurs
 * @param section the section that contains all of [wordRanges]
 * @param sectionRange the character range of [section] in [StudyData.text]
 */
data class OneSectionWord<T>(
    val word: String,
    val wordRanges: List<IntRange>,
    val section: T,
    val sectionRange: IntRange
)

/**
 * Builds the list of words that occur more than once but only within a single section
 *
 * "Section" is whatever the keys of [sectionMap] represent — typically [net.markdrew.biblebowl.model.ChapterRef]
 * or heading title strings.
 */
fun <T : Any> oneSectionWords(
    studyData: StudyData,
    sectionMap: DisjointRangeMap<T>,
): List<OneSectionWord<T>> = studyData.wordIndex
    .filterValues { ranges -> ranges.size > 1 } // remove one-time words
    .filterValues { ranges ->
        ranges.map { sectionMap.valueEnclosing(it) }.distinct().count() == 1  // only entries all in same section
    }.map { (word, ranges) ->
        val (sectionRange, section) = sectionMap.entryEnclosing(ranges.first()) ?: throw Exception()
        OneSectionWord(word, ranges, section, sectionRange)
    }
