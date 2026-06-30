package net.markdrew.biblebowl.analysis

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length

private val log: KLogger = KotlinLogging.logger {}

/**
 * Unified category detector: tags each text range with one **category id** (a [String]) drawn from an
 * ordered list of `(categoryId, regexes)` rules, then applies per-occurrence human [overrides].
 *
 * This is the single resolution every consumer (text highlights, word-list indices) reads, so they all
 * agree and all honor overrides. Resolution per occurrence:
 *  1. **Override wins** — a [CategoryOverride] reclassifies the occurrence, excludes it (category `-`),
 *     or adds one that no list matched.
 *  2. else the **single** matching category.
 *  3. else (a term in more than one list) the higher-precedence category wins per [CategoryPrecedence],
 *     and the ambiguity is logged so a human can add an override.
 *
 * Overlapping (not identical) matches are deconflicted by length — the longer match wins, equal-length
 * ties broken by [CategoryPrecedence]. The override file content is part of [defDigest], so edits
 * invalidate the cache.
 */
class CategoryAnnotator(
    override val name: String,
    private val categories: List<Pair<String, Set<Regex>>>,
    private val overrides: List<CategoryOverride> = emptyList(),
) : AnnotationSource<String> {

    override val defDigest: String = annotationDigest(
        *(categories.flatMap { (category, regexes) -> listOf(category) + regexes.map { it.pattern }.sorted() } +
            overrides.map { "${it.verse}\t${it.text}\t${it.category}\t${it.occurrence}" }).toTypedArray()
    )

    override fun compute(studyData: StudyData): DisjointRangeMap<String> {
        val matches = DisjointRangeMap<String>()
        // Every category that matched a given exact range — used only to flag unresolved ambiguity.
        val matchedCategories = HashMap<IntRange, MutableSet<String>>()

        for ((category, patterns) in categories) {
            for (range in studyData.findAll(*patterns.toTypedArray())) {
                matchedCategories.getOrPut(range) { mutableSetOf() }.add(category)
                val longestOverlap = matches.intersectedBy(range).maxByOrNull { it.key.length() }
                when {
                    longestOverlap == null -> matches[range] = category
                    range.length() > longestOverlap.key.length() -> matches.putForcefully(range, category) // longer wins
                    range.length() < longestOverlap.key.length() -> Unit // a longer match already covers this
                    // equal length: the higher-precedence category wins (deterministic, order-independent)
                    CategoryPrecedence.wins(category, longestOverlap.value) -> matches.putForcefully(range, category)
                }
            }
        }

        val overriddenRanges = applyOverrides(studyData, matches)
        warnUnresolvedConflicts(studyData, matches, matchedCategories, overriddenRanges)
        return matches
    }

    /** Applies resolved overrides in place; returns the override ranges that were touched. */
    private fun applyOverrides(studyData: StudyData, matches: DisjointRangeMap<String>): Set<IntRange> {
        val overriddenRanges = mutableSetOf<IntRange>()
        for ((range, category) in CategoryOverrides.resolve(overrides, studyData)) {
            for (displaced in matches.intersectedBy(range).keys.toList()) matches.remove(displaced)
            if (category != null) matches[range] = category
            overriddenRanges += range
        }
        return overriddenRanges
    }

    private fun warnUnresolvedConflicts(
        studyData: StudyData,
        matches: DisjointRangeMap<String>,
        matchedCategories: Map<IntRange, Set<String>>,
        overriddenRanges: Set<IntRange>,
    ) {
        for ((range, cats) in matchedCategories) {
            if (cats.size <= 1 || range in overriddenRanges) continue
            val verse = studyData.verseEnclosing(range)?.format() ?: "?"
            val resolved = matches.valueEnclosing(range) ?: "(none)"
            log.warn {
                "Ambiguous \"${studyData.excerpt(range).excerptText}\" ($verse) is in categories " +
                    "${cats.sorted()}; resolved to '$resolved' by precedence. Add a category override to disambiguate."
            }
        }
    }

    override fun encodeValue(value: String): String = value
    override fun decodeValue(cell: String): String = cell
}
