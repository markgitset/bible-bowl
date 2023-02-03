package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.generate.excerpt
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.intersect

class AnnotatedDoc<A>(val docText: String, wholeDocAnnotationKey: A) {

    private val docRange: IntRange = docText.indices
    private val annotationMaps = mutableMapOf(
        wholeDocAnnotationKey to DisjointRangeMap(
            docRange to Annotation(wholeDocAnnotationKey, true, docRange)
        )
    )

    fun setAnnotations(annotationKey: A, annotationsRangeMap: DisjointRangeMap<*>) {
        annotationMaps[annotationKey] = annotationsRangeMap.mapNotNull { (annotationRange, annotationValue) ->
            annotationMapping(annotationRange, annotationKey, annotationValue)
        }.toMap(DisjointRangeMap())
    }

    fun setAnnotations(annotationKey: A, annotationsRangeSet: DisjointRangeSet) {
        annotationMaps[annotationKey] = annotationsRangeSet.mapNotNull { annotationRange ->
            annotationMapping(annotationRange, annotationKey, true)
        }.toMap(DisjointRangeMap())
    }

    fun setAnnotations(annotationKey: A, pointAnnotationsMap: Map<Int, String>) {
        annotationMaps[annotationKey] = pointAnnotationsMap.mapNotNull { (annotationOffset, annotationValue) ->
            annotationMapping(annotationOffset..annotationOffset, annotationKey, annotationValue)
        }.toMap(DisjointRangeMap())
    }

    private fun annotationMapping(range: IntRange, key: A, value: Any): Pair<IntRange, Annotation<A>>? =
        range.intersect(docRange).let { inDocRange ->
            if (inDocRange.isEmpty()) null
            else inDocRange to Annotation(key, value, range)
        }

    fun textRuns(): Sequence<TextRun<A>> = sequence {
        var prevEndExclusive = 0
        var run: TextRun<A>? = TextRun(prevEndExclusive until prevEndExclusive, emptySet())
        while (run != null) {
            yield(run)
            prevEndExclusive = run.range.last + 1
            run = runAt(prevEndExclusive)
        }
        yield(TextRun(prevEndExclusive until prevEndExclusive, emptySet()))
    }

    private fun runAt(start: Int): TextRun<A>? {
        val annotations: List<Annotation<A>> = annotationsAt(start)
        return when {
            annotations.isEmpty() -> null
            else -> TextRun(
                start..annotations.minOf { it.range.last },
                annotations.filter { !it.isAbsent() }.toSet()
            )
        }
    }

    private fun annotationsAt(start: Int): List<Annotation<A>> =
        annotationMaps.mapNotNull { (annotationKey, annotationMap) ->
            annotationMap.valueContaining(start)
                ?: annotationMap.ceilingKey(start..start)?.let { ceilingRange ->
                    Annotation(annotationKey, null, start until ceilingRange.first)
                }
        }

    fun stateTransitions(): Sequence<Pair<Excerpt, StateTransition<A>>> = textRuns().windowed(2) { (prevRun, run) ->
        docText.excerpt(run.range) to prevRun.stateTransition(run.annotations)
    }

}

fun BookData.toAnnotatedDoc(vararg annotationTypes: AnalysisUnit): AnnotatedDoc<AnalysisUnit> =
    AnnotatedDoc(text, AnalysisUnit.BOOK).apply {
        fun addAnns(unit: AnalysisUnit, map: DisjointRangeMap<*>) {
            if (unit in annotationTypes || annotationTypes.isEmpty()) setAnnotations(unit, map)
        }
        fun addAnns(unit: AnalysisUnit, set: DisjointRangeSet) {
            if (unit in annotationTypes || annotationTypes.isEmpty()) setAnnotations(unit, set)
        }
        addAnns(AnalysisUnit.VERSE, verses)
        addAnns(AnalysisUnit.HEADING, headingCharRanges)
        addAnns(AnalysisUnit.CHAPTER, chapters)
        addAnns(AnalysisUnit.PARAGRAPH, paragraphs)
        addAnns(AnalysisUnit.POETRY, poetry)
        addAnns(AnalysisUnit.SENTENCE, sentences)
        addAnns(AnalysisUnit.WORD, words)
        if (AnalysisUnit.FOOTNOTE in annotationTypes || annotationTypes.isEmpty()) setAnnotations(AnalysisUnit.FOOTNOTE, footnotes)
    }

fun StudyData.toAnnotatedDoc(vararg annotationTypes: AnalysisUnit): AnnotatedDoc<AnalysisUnit> =
    AnnotatedDoc(text, AnalysisUnit.BOOK).apply {
        fun addAnns(unit: AnalysisUnit, map: DisjointRangeMap<*>) {
            if (unit in annotationTypes || annotationTypes.isEmpty()) setAnnotations(unit, map)
        }
        fun addAnns(unit: AnalysisUnit, set: DisjointRangeSet) {
            if (unit in annotationTypes || annotationTypes.isEmpty()) setAnnotations(unit, set)
        }
        addAnns(AnalysisUnit.VERSE, verses)
        addAnns(AnalysisUnit.HEADING, headingCharRanges)
        addAnns(AnalysisUnit.CHAPTER, chapters)
        addAnns(AnalysisUnit.PARAGRAPH, paragraphs)
        addAnns(AnalysisUnit.POETRY, poetry)
        addAnns(AnalysisUnit.SENTENCE, sentences)
        addAnns(AnalysisUnit.WORD, words)
        if (AnalysisUnit.FOOTNOTE in annotationTypes || annotationTypes.isEmpty()) setAnnotations(AnalysisUnit.FOOTNOTE, footnotes)
    }

