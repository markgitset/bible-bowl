package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.rangeFirstLastComparator

/**
 * Builds a name index from precomputed name [ranges] (e.g. from a shared annotation cache).
 *
 * Groups occurrences by lowercased text; pass the ranges of a proper-name category (e.g. `other`) from the
 * unified [WordList.categoryAnnotator] resolution to index exactly what was highlighted.
 */
fun buildNamesIndex(studyData: StudyData, ranges: List<IntRange>): List<WordIndexEntry> =
    ranges.map { studyData.excerpt(it) }
        .groupBy { it.excerptText.lowercase() }
        .map { (_, excerpts) ->
            WordIndexEntry(excerpts.first().excerptText, excerpts.map {
                studyData.verseEnclosing(it.excerptRange) ?: throw Exception()
            })
        }

/**
 * Returns every proper-name occurrence in [studyData] as an [Excerpt], in canonical Bible order.
 *
 * Names are no longer heuristically detected: a word is a name only because it is in a proper-name
 * [WordList] (men, women, places, people-groups, angels-demons, or the catch-all `other`). This reads the
 * one unified [WordList.categoryAnnotator] resolution, so it honors per-occurrence overrides. The `divine`
 * category is omitted (those are handled separately everywhere); [exceptNames] drops occurrences whose text
 * (case-insensitively) is listed.
 */
fun findNames(studyData: StudyData, vararg exceptNames: String): Sequence<Excerpt> {
    val except: Set<String> = exceptNames.map { it.lowercase() }.toSet()
    val resolved = AnnotationStore(studyData, cacheDir = null).get(WordList.categoryAnnotator(studyData.studySet))
    return resolved.asSequence()
        .filter { (_, token) -> WordList.byToken(token).let { it?.areNames == true && it != WordList.DIVINE } }
        .map { (range, _) -> studyData.excerpt(range) }
        .filter { it.excerptText.lowercase() !in except }
        .sortedWith(compareBy(rangeFirstLastComparator) { it.excerptRange })
}

/** Prints each distinct name with its occurrence count, sorted by ascending frequency. */
fun printNameFrequencies(nameExcerpts: Sequence<Excerpt>) {
    nameExcerpts.groupBy { it.excerptText }
        .map { (name, excerpts) -> excerpts.size to name }
        .sortedBy { it.first }
        .forEachIndexed { i, (count, name) ->
            println("%3d  %15s %15s".format(i + 1, count, name))
        }
}
