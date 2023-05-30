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
    private val annotationMaps: MutableMap<A, DisjointRangeMap<Annotation<A>>> = mutableMapOf(
        wholeDocAnnotationKey to DisjointRangeMap(
            docRange to Annotation(wholeDocAnnotationKey, true, docRange)
        )
    )
    // point annotations are considered to occur between index-1 and index
    private val pointAnnotationMaps: MutableMap<A, NavigableMap<Int, Annotation<A>>> = mutableMapOf()

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
//            annotationMapping(offset..offset, annotationKey, value, isPoint = true)
            if (offset !in docRange) null
            else offset to Annotation(annotationKey, value, offset..offset, isPoint = true)
        }.toMap(TreeMap())
    }

    private fun <V : Any> annotationMapping(range: IntRange, key: A, value: V): Pair<IntRange, Annotation<A>>? =
        range.intersect(docRange).let { inDocRange ->
            if (inDocRange.isEmpty()) null
            else inDocRange to Annotation(key, value, range)
        }

    fun textRuns(): Sequence<TextRun<A>> = sequence {
        var prevEndExclusive = 0
        // start with an empty run
        var run: TextRun<A>? = TextRun(prevEndExclusive until prevEndExclusive, emptySet())
        while (run != null) {
            yield(run)
            prevEndExclusive = run.range.last + 1
            run = runAt(prevEndExclusive)
        }
        // end with an empty run
        yield(TextRun(prevEndExclusive until prevEndExclusive, emptySet()))
    }

    private fun runAt(start: Int): TextRun<A>? {
        val annotations: List<Annotation<A>> = annotationsAt(start)
        return when {
            annotations.isEmpty() -> null
            else -> {
                val pointAnnotations: List<Annotation<A>> = nextPointAnnotationsAfter(start)
                val minEndOfRange = annotations.minOf { it.range.last }
                val minPoint = pointAnnotations.firstOrNull()?.let { it.range.first - 1 } ?: Int.MAX_VALUE
                val anns = if (minPoint <= minEndOfRange) (annotations + pointAnnotations) else annotations
                TextRun(
                    start..min(minEndOfRange, minPoint),
                    anns.filterNot { it.isAbsent() }.toSet()
                )
            }
        }
    }

    private fun annotationsAt(start: Int): List<Annotation<A>> =
        annotationMaps.mapNotNull { (annotationKey, annotationMap) ->
            annotationMap.valueContaining(start)
                ?: annotationMap.ceilingKey(start..start)?.let { ceilingRange ->
                    // need to generate null annotations so that we can correctly
                    // detect when the next non-null one starts
                    Annotation(annotationKey, null, start until ceilingRange.first)
                }
        }

    private fun nextPointAnnotationsAfter(start: Int): List<Annotation<A>> {
        val minOffset = pointAnnotationMaps.values.minOfOrNull { it.ceilingKey(start + 1) } ?: return emptyList()
        return pointAnnotationMaps.values.mapNotNull { it[minOffset] }
    }

    fun stateTransitions(): Sequence<Pair<Excerpt, StateTransition<A>>> = textRuns().windowed(2) { (prevRun, run) ->
        docText.excerpt(run.range) to prevRun.stateTransition(run.annotations)
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
            setAnnotations(AnalysisUnit.FOOTNOTE, footnotes)
        }
    }

