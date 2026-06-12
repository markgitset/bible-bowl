package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.OneTimeWordsSource
import net.markdrew.biblebowl.analysis.RegexSetSource
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.BOOK
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.LEADING_FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.REGEX
import net.markdrew.biblebowl.model.AnalysisUnit.SMALL_CAPS
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.StudyData

/**
 * Format-agnostic annotation pipeline that turns a [StudyData] plus a [FeatureOptions] into the
 * fully-annotated document that all writers consume.
 *
 * The resulting [AnnotatedDoc] carries the structural layers always present (book, chapter, verse,
 * heading, paragraph, poetry, footnotes, leading footnotes, small caps) plus the feature-driven layers
 * the caller asked for (custom regex highlights and unique-word underlining). Writers walk the doc's
 * state transitions and emit format-specific markup; they do not re-detect any of these layers.
 *
 * Each feature layer is produced through an [AnnotationStore], so the expensive regex/NLP passes run at
 * most once per study set per run (and can be served from disk across runs). The doc depends only on
 * `(studyData, features)` — never on layout or writer — so a single built doc is reused across every
 * format and layout that shares those features.
 *
 * REGEX annotation values are [HighlightColor]s (not bare strings), so each writer can format the color
 * for its own output: DOCX writers call [HighlightColor.toHex]; LaTeX writers reference
 * [HighlightColor.name] and emit `\definecolor` blocks for it in the preamble.
 */
object BibleAnnotationPipeline {

    /**
     * Builds an [AnnotatedDoc] for [studyData] with all structural layers plus the feature layers enabled
     * by [features], pulling each feature layer from [store].
     */
    fun build(
        studyData: StudyData,
        features: FeatureOptions,
        store: AnnotationStore = AnnotationStore(studyData, cacheDir = null),
    ): AnnotatedDoc<AnalysisUnit> {
        // One unified resolution over every category (word-lists incl. `other`/`numbers`, + overrides).
        // Each range is tagged with its category id; the palette categories (which now include `other`
        // and `numbers`) become the colored REGEX highlights, each resolved to its color by the writers.
        val paletteCategories: Set<String> = features.customHighlights.rules.map { it.first }.toSet()
        val resolved = store.get(WordList.categoryAnnotator(studyData.studySet))
        val regexRanges = DisjointRangeMap<String>().apply {
            resolved.forEach { (range, category) -> if (category in paletteCategories) put(range, category) }
        }
        val smallCaps = RegexSetSource("small-caps", features.smallCaps.keys.map { Regex.fromLiteral(it) }.toSet())
        return studyData.toAnnotatedDoc(
            BOOK, CHAPTER, HEADING, VERSE, POETRY, PARAGRAPH, LEADING_FOOTNOTE, FOOTNOTE, REGEX, SMALL_CAPS
        ).apply {
            setAnnotations(REGEX, regexRanges)
            setAnnotations(SMALL_CAPS, store.ranges(smallCaps))
            if (features.underlineUniqueWords) setAnnotations(UNIQUE_WORD, store.ranges(OneTimeWordsSource))
        }
    }
}
