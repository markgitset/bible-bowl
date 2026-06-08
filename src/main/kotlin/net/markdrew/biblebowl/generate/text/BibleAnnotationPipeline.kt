package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.findNumbers
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.BOOK
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.LEADING_FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.NAME
import net.markdrew.biblebowl.model.AnalysisUnit.NUMBER
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.REGEX
import net.markdrew.biblebowl.model.AnalysisUnit.SMALL_CAPS
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.length

private val log: KLogger = KotlinLogging.logger {}

/**
 * Format-agnostic annotation pipeline that turns a [StudyData] plus a [FeatureOptions] into the
 * fully-annotated document that all writers consume.
 *
 * The resulting [AnnotatedDoc] carries the structural layers always present (book, chapter, verse,
 * heading, paragraph, poetry, footnotes, leading footnotes, small caps) plus the feature-driven layers
 * the caller asked for (custom regex highlights, unique-word underlining, name highlighting, number
 * highlighting). Writers walk the doc's state transitions and emit format-specific markup; they do not
 * re-detect any of these layers.
 *
 * REGEX annotation values are [HighlightColor]s (not bare strings), so each writer can format the color
 * for its own output: DOCX writers call [HighlightColor.toHex]; LaTeX writers reference
 * [HighlightColor.name] and emit `\definecolor` blocks for it in the preamble.
 */
object BibleAnnotationPipeline {

    /**
     * Builds an [AnnotatedDoc] for [studyData] with all structural layers plus the feature layers
     * enabled by [features].
     *
     * Custom highlights are deconflicted by length when multiple patterns overlap — the longer match
     * wins, and any displaced shorter matches are logged.
     */
    fun build(studyData: StudyData, features: FeatureOptions): AnnotatedDoc<AnalysisUnit> {
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = studyData.toAnnotatedDoc(
            BOOK, CHAPTER, HEADING, VERSE, POETRY, PARAGRAPH, LEADING_FOOTNOTE, FOOTNOTE, REGEX, SMALL_CAPS
        ).apply {
            val regexAnnotationsRangeMap: DisjointRangeMap<HighlightColor> =
                features.customHighlights.entries.fold(DisjointRangeMap()) { allHighlights, (color, patterns) ->
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
                                val replaced: DisjointRangeMap<HighlightColor> =
                                    allHighlights.putForcefully(range, color)
                                replaced.forEach { (replacedRange, replacedColor) ->
                                    val existingExcerpt: Excerpt = studyData.excerpt(replacedRange)
                                    log.warn {
                                        "In ${verse}, replacing " +
                                            "${highlightString(existingExcerpt, replacedColor)} because " +
                                            "${highlightString(newExcerpt, color)} is longer!"
                                    }
                                }
                            }
                        }
                    }
                    allHighlights
                }
            setAnnotations(REGEX, regexAnnotationsRangeMap)
            setAnnotations(SMALL_CAPS, DisjointRangeSet(
                studyData.findAll(*features.smallCaps.keys.map { Regex.fromLiteral(it) }.toTypedArray())
            ))
            if (features.underlineUniqueWords) setAnnotations(UNIQUE_WORD, DisjointRangeSet(oneTimeWords(studyData)))
            if (features.highlightNames) {
                val namesRangeSet = DisjointRangeSet(
                    findNames(studyData, exceptNames = divineNames.toTypedArray())
                        .map { it.excerptRange }.toList()
                )
                // remove any ranges that intersect with custom regex ranges
                val deconflicted = namesRangeSet.minusEnclosedBy(regexAnnotationsRangeMap)
                setAnnotations(NAME, deconflicted)
            }
            if (features.highlightNumbers) {
                val numbersRangeSet = DisjointRangeSet(findNumbers(studyData.text).map { it.excerptRange }.toList())
                setAnnotations(NUMBER, numbersRangeSet)
            }
        }
        return annotatedDoc
    }

    private fun highlightString(excerpt: Excerpt, color: HighlightColor): String =
        """"${excerpt.excerptText}" (${excerpt.excerptRange}:${color.name})"""
}
