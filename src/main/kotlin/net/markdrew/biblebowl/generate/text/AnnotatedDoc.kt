package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.generate.excerpt
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.intersect
import java.util.NavigableMap
import java.util.TreeMap
import kotlin.math.min

class AnnotatedDoc<A>(val docText: String, wholeDocAnnotationKey: A) {

    private val docRange: IntRange = docText.indices
    private val annotationMaps: MutableMap<A, DisjointRangeMap<RangeAnnotation<A>>> = mutableMapOf(
        wholeDocAnnotationKey to DisjointRangeMap(
            docRange to RangeAnnotation(wholeDocAnnotationKey, true, docRange)
        )
    )
    // point annotations are considered to occur between index-1 and index
    private val pointAnnotationMaps: MutableMap<A, NavigableMap<Int, PointAnnotation<A>>> = mutableMapOf()

    fun <V : Any> setAnnotations(annotationKey: A, annotationsRangeMap: DisjointRangeMap<V>) {
        annotationMaps[annotationKey] = annotationsRangeMap.mapNotNull { (annotationRange, annotationValue) ->
            annotationMapping(annotationRange, annotationKey, annotationValue)
        }.toMap(DisjointRangeMap())
    }

    fun setAnnotations(annotationKey: A, annotationsRangeSet: DisjointRangeSet) {
        annotationMaps[annotationKey] = annotationsRangeSet.mapNotNull { annotationRange ->
            annotationMapping(annotationRange, annotationKey, true)
        }.toMap(DisjointRangeMap())
    }

    fun <V : Any> setAnnotations(annotationKey: A, pointAnnotationsMap: Map<Int, V>) {
        pointAnnotationMaps[annotationKey] = pointAnnotationsMap.mapNotNull { (offset: Int, value: V) ->
            if (offset !in docRange) null
            else offset to PointAnnotation(annotationKey, value, offset)
        }.toMap(TreeMap())
    }

    private fun <V : Any> annotationMapping(range: IntRange, key: A, value: V): Pair<IntRange, RangeAnnotation<A>>? =
        range.intersect(docRange).let { inDocRange ->
            if (inDocRange.isEmpty()) null
            else inDocRange to RangeAnnotation(key, value, range)
        }

    @Suppress("KotlinConstantConditions")
    fun textRuns(): Sequence<TextRun<A>> = sequence {
        var prevEndExclusive = 0
        // start with an empty run
        var run: TextRun<A>? = TextRun(prevEndExclusive until prevEndExclusive, emptySet(), emptySet(), emptySet())
        while (run != null) {
            yield(run)
            prevEndExclusive = run.range.last + 1
            run = runAt(prevEndExclusive)
        }
        // end with an empty run
        yield(TextRun(prevEndExclusive until prevEndExclusive, emptySet(), emptySet(), emptySet()))
    }

    private fun runAt(start: Int): TextRun<A>? {
        val annotations: List<RangeAnnotation<A>> = annotationsAt(start)
        return when {
            annotations.isEmpty() -> null
            else -> {
                val pointAnnotations: Set<PointAnnotation<A>> = nextPointAnnotationsAfter(start)
                val minEndOfRange = annotations.minOf { it.range.last }
                val minPoint = pointAnnotations.firstOrNull()?.let { it.offset - 1 } ?: Int.MAX_VALUE
                val postPointAnns = if (minPoint <= minEndOfRange) pointAnnotations else emptySet()
                TextRun(
                    start..min(minEndOfRange, minPoint),
                    annotations.filterNot { it.isAbsent() }.toSet(),
                    pointAnnotationMaps.values.mapNotNull { it[start] }.toSet(),
                    postPointAnns,
                )
            }
        }
    }

    private fun annotationsAt(start: Int): List<RangeAnnotation<A>> =
        annotationMaps.mapNotNull { (annotationKey, annotationMap) ->
            annotationMap.valueContaining(start)
                ?: annotationMap.ceilingKey(start..start)?.let { ceilingRange ->
                    // need to generate null annotations so that we can correctly
                    // detect when the next non-null one starts
                    RangeAnnotation(annotationKey, null, start until ceilingRange.first)
                }
        }

    private fun nextPointAnnotationsAfter(start: Int): Set<PointAnnotation<A>> {
        val minOffset = pointAnnotationMaps.values.mapNotNull { it.ceilingKey(start + 1) }.minOrNull()
            ?: return emptySet()
        return pointAnnotationMaps.values.mapNotNull { it[minOffset] }.toSet()
    }

    fun stateTransitions(): Sequence<Pair<Excerpt, StateTransition<A>>> = textRuns().windowed(2) { (prevRun, run) ->
        docText.excerpt(run.range) to prevRun.stateTransition(run)
    }

}

fun StudyData.toAnnotatedDoc(vararg annotationTypes: AnalysisUnit): AnnotatedDoc<AnalysisUnit> =
    AnnotatedDoc(text, AnalysisUnit.STUDY_SET).apply {
        fun addAnns(unit: AnalysisUnit, map: DisjointRangeMap<*>) {
            // only include requested annotation types, or all types if none were requested
            if (unit in annotationTypes || annotationTypes.isEmpty()) setAnnotations(unit, map)
        }
        fun addAnns(unit: AnalysisUnit, set: DisjointRangeSet) {
            // only include requested annotation types, or all types if none were requested
            if (unit in annotationTypes || annotationTypes.isEmpty()) setAnnotations(unit, set)
        }
        addAnns(AnalysisUnit.BOOK, books)
        addAnns(AnalysisUnit.VERSE, verses)
        addAnns(AnalysisUnit.HEADING, headingCharRanges)
        addAnns(AnalysisUnit.CHAPTER, chapters)
        addAnns(AnalysisUnit.PARAGRAPH, paragraphs)
        addAnns(AnalysisUnit.POETRY, poetry)
        addAnns(AnalysisUnit.SENTENCE, sentences)
        addAnns(AnalysisUnit.WORD, words)
        if (AnalysisUnit.FOOTNOTE in annotationTypes || annotationTypes.isEmpty()) {
            val (leadingNotes, normalNotes) = footnotes.toList().partition { (offset, _) ->
                offset == 0 || docText[offset - 1].isWhitespace()
            }
            setAnnotations(AnalysisUnit.FOOTNOTE, normalNotes.toMap())
            setAnnotations(AnalysisUnit.LEADING_FOOTNOTE, leadingNotes.toMap())
        }
    }

