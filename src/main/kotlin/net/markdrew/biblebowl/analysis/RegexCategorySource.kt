package net.markdrew.biblebowl.analysis

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.length

private val log: KLogger = KotlinLogging.logger {}

/**
 * Tags text ranges with a caller-defined **category id** based on regex matches — the color-agnostic
 * sibling of [RegexSetSource]. The value stored per range is the category id (a plain [String]); how a
 * category is ultimately colored/styled is a rendering concern handled downstream, not here.
 *
 * [categories] is an ordered list of `(categoryId, patterns)`. When two patterns match overlapping
 * ranges, the longer match wins; insertion order (i.e. [categories] order) breaks ties, so that order is
 * load-bearing and part of [defDigest]. Displaced shorter matches are logged.
 */
class RegexCategorySource(
    override val name: String,
    private val categories: List<Pair<String, Set<Regex>>>,
) : AnnotationSource<String> {

    override val defDigest: String = annotationDigest(
        *categories.flatMap { (category, regexes) -> listOf(category) + regexes.map { it.pattern }.sorted() }
            .toTypedArray()
    )

    override fun compute(studyData: StudyData): DisjointRangeMap<String> =
        categories.fold(DisjointRangeMap()) { allMatches, (category, patterns) ->
            val matches: DisjointRangeSet = studyData.findAll(*patterns.toTypedArray())
            matches.forEach { range ->
                val maxExisting = allMatches.intersectedBy(range).maxByOrNull { it.key.length() }
                if (maxExisting == null) {
                    allMatches[range] = category
                } else {
                    val newExcerpt: Excerpt = studyData.excerpt(range)
                    val verse = studyData.verseEnclosing(range)?.format() ?: "UNK_VERSE"
                    if (maxExisting.key.length() > range.length()) {
                        val existingExcerpt: Excerpt = studyData.excerpt(maxExisting.key)
                        log.warn {
                            "In ${verse}, skipping ${matchString(newExcerpt, category)} because " +
                                "${matchString(existingExcerpt, maxExisting.value)} is longer!"
                        }
                    } else {
                        val replaced: DisjointRangeMap<String> = allMatches.putForcefully(range, category)
                        replaced.forEach { (replacedRange, replacedCategory) ->
                            val existingExcerpt: Excerpt = studyData.excerpt(replacedRange)
                            log.warn {
                                "In ${verse}, replacing ${matchString(existingExcerpt, replacedCategory)} because " +
                                    "${matchString(newExcerpt, category)} is longer!"
                            }
                        }
                    }
                }
            }
            allMatches
        }

    override fun encodeValue(value: String): String = value
    override fun decodeValue(cell: String): String = cell

    private fun matchString(excerpt: Excerpt, category: String): String =
        """"${excerpt.excerptText}" (${excerpt.excerptRange}:$category)"""
}
