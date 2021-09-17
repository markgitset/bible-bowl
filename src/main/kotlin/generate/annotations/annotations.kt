package net.markdrew.biblebowl.generate.annotations

import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.endExclusive
import net.markdrew.chupacabra.core.intersect

data class Annotation(
    val key: String?,
    val value: Any?,
    val range: IntRange,
)

fun buildAnnotationsMaps(
    docRange: IntRange,
    disjointRangeMaps: Map<String, DisjointRangeMap<*>>,
    docAnnotationKey: String = "DOC"
): Collection<DisjointRangeMap<Annotation>> =
    disjointRangeMaps.map { (annotationKey, drm) ->
        drm.mapNotNull { (annotationRange, annotationValue) ->
            val inDocRange = annotationRange.intersect(docRange)
            if (inDocRange.isEmpty()) null
            else inDocRange to Annotation(annotationKey, annotationValue, annotationRange)
        }.toMap(DisjointRangeMap())
    } + DisjointRangeMap(docRange to Annotation(docAnnotationKey, null, docRange)) // add the DOC annotation

data class StateTransition(
    val ended: Set<Annotation>,
    val continuing: Set<Annotation>,
    val beginning: Set<Annotation>,
) {
    init {
        // check that three sets are mutually exclusive
        assert(ended.intersect(continuing).isEmpty())
        assert(ended.intersect(beginning).isEmpty())
        assert(continuing.intersect(beginning).isEmpty())
    }
    fun nextStateTransition(newState: Set<Annotation>): StateTransition {
        return StateTransition(
            ended = (continuing + beginning) - newState,
            continuing = newState.intersect(continuing + beginning),
            beginning = newState - continuing - beginning
        )
    }

    companion object {
        fun initialState(beginning: Set<Annotation>): StateTransition =
            StateTransition(emptySet(), emptySet(), beginning.toSet())
    }
}

data class TextRun(
    val range: IntRange,
    val annotations: Set<Annotation>,
) {
    fun nextRun(annotationMaps: Collection<DisjointRangeMap<Annotation>>): TextRun? =
        firstRunOf(range.endExclusive, annotationMaps)

    companion object {
        fun fromAnnotations(start: Int, annotations: Collection<Annotation>): TextRun? = when {
            annotations.isEmpty() -> null
            else -> TextRun(
                start..annotations.minOf { it.range.last },
                annotations.filter { it.key != null }.toSet()
            )
        }

        fun sequence(start: Int, annotationMaps: Collection<DisjointRangeMap<Annotation>>): Sequence<TextRun> =
            generateSequence(firstRunOf(start, annotationMaps)) { run -> run.nextRun(annotationMaps) }

        fun firstRunOf(start: Int, annotationMaps: Collection<DisjointRangeMap<Annotation>>): TextRun? =
            fromAnnotations(start, annotationMaps.mapNotNull {
                it.valueContaining(start) ?: it.ceilingKey(start..start)?.let { ceilingRange ->
                    Annotation(null, null, start until ceilingRange.first)
                }
            })
    }
}


fun process(fullText: String, annotations: Map<String, DisjointRangeMap<*>>,
            runHandler: (textRun: String, stateTransition: StateTransition) -> String): String {

    val annotationsMaps: Collection<DisjointRangeMap<Annotation>> = buildAnnotationsMaps(fullText.indices, annotations)


    var stateTransition: StateTransition? = null
//    TextRun.sequence(0, annotationsMaps)
    for (run in TextRun.sequence(0, annotationsMaps)) {
        stateTransition = stateTransition?.nextStateTransition(run.annotations) ?: StateTransition.initialState(run.annotations)
        runHandler(fullText.substring(run.range), stateTransition)
//        println(run)
    }
    runHandler("", stateTransition?.nextStateTransition(emptySet()) ?: throw Exception())
    TODO()
}

fun visit(textRun: String, stateTransition: StateTransition): String = TODO()

fun main() {
    val ann1 = Annotation("1", 1, 1..1)
    val ann2 = Annotation("2", 2, 2..2)
    println(StateTransition(setOf(), setOf(), setOf()))
//    println(StateTransition(setOf(ann1), setOf(ann2), setOf(ann1)))
    val annotations: Map<String, DisjointRangeMap<*>> = mapOf(
        "A" to DisjointRangeMap(10..19 to "a1", 30..39 to "a2"),
        "B" to DisjointRangeMap(15..24 to "b1", 35..44 to "b2"),
    )
    val annotationsMaps: Collection<DisjointRangeMap<Annotation>> = buildAnnotationsMaps(0..100, annotations)
    for (run in TextRun.sequence(0, annotationsMaps)) {
        println(run)
    }
    println()
    process(
        "This is a really, really, really long test of nonsense sentence.  I'm not sure what else to put in h",
        annotations
    ) { textRun, stateTransition ->
        println("\n$textRun\n$stateTransition")
        ""
    }
}