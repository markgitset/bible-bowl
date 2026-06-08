package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.analysis.AnnotationSource
import net.markdrew.biblebowl.analysis.annotationDigest
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.length

private val log: KLogger = KotlinLogging.logger {}

/**
 * Custom regex-driven highlight layer: maps each matched range to the [HighlightColor] that should fill it.
 *
 * When two patterns match overlapping ranges, the longer match wins (insertion order — i.e. [palette]
 * iteration order — breaks ties), so [HighlightPalette.entries] order is load-bearing and part of the
 * cache key. Displaced shorter matches are logged.
 */
class RegexHighlightSource(private val palette: HighlightPalette) : AnnotationSource<HighlightColor> {

    override val name: String = "highlights"

    override val defDigest: String = annotationDigest(
        *palette.entries.flatMap { (color, regexes) ->
            listOf(color.name, color.toHex()) + regexes.map { it.pattern }.sorted()
        }.toTypedArray()
    )

    override fun compute(studyData: StudyData): DisjointRangeMap<HighlightColor> =
        palette.entries.fold(DisjointRangeMap()) { allHighlights, (color, patterns) ->
            val highlights: DisjointRangeSet = studyData.findAll(*patterns.toTypedArray())
            highlights.forEach { range ->
                val maxExistingHighlight = allHighlights.intersectedBy(range).maxByOrNull { it.key.length() }
                if (maxExistingHighlight == null) {
                    allHighlights[range] = color
                } else {
                    val newExcerpt: Excerpt = studyData.excerpt(range)
                    val verse = studyData.verseEnclosing(range)?.format() ?: "UNK_VERSE"
                    if (maxExistingHighlight.key.length() > range.length()) {
                        val existingExcerpt: Excerpt = studyData.excerpt(maxExistingHighlight.key)
                        log.warn {
                            "In ${verse}, skipping ${highlightString(newExcerpt, color)} because " +
                                "${highlightString(existingExcerpt, maxExistingHighlight.value)} is longer!"
                        }
                    } else {
                        val replaced: DisjointRangeMap<HighlightColor> = allHighlights.putForcefully(range, color)
                        replaced.forEach { (replacedRange, replacedColor) ->
                            val existingExcerpt: Excerpt = studyData.excerpt(replacedRange)
                            log.warn {
                                "In ${verse}, replacing ${highlightString(existingExcerpt, replacedColor)} because " +
                                    "${highlightString(newExcerpt, color)} is longer!"
                            }
                        }
                    }
                }
            }
            allHighlights
        }

    override fun encodeValue(value: HighlightColor): String = "${value.name}|${value.toHex()}"

    override fun decodeValue(cell: String): HighlightColor {
        val (colorName, hex) = cell.split('|', limit = 2)
        val rgb = Triple(hex.substring(0, 2).toInt(16), hex.substring(2, 4).toInt(16), hex.substring(4, 6).toInt(16))
        return HighlightColor(colorName, rgb)
    }

    private fun highlightString(excerpt: Excerpt, color: HighlightColor): String =
        """"${excerpt.excerptText}" (${excerpt.excerptRange}:${color.name})"""
}
