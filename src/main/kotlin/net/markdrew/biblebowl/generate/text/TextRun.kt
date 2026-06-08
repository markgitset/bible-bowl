package net.markdrew.biblebowl.generate.text

/**
 * A maximal run of document text over which the active set of annotations doesn't change
 *
 * Runs are produced by [AnnotatedDoc.textRuns]; a renderer walks them in order and uses [stateTransition]
 * to drive open/close logic for each annotation kind.
 *
 * @param range the character offset range covered by this run
 * @param annotations every range annotation active over [range]
 * @param prePointAnns point annotations anchored just before [range]
 * @param postPointAnns point annotations anchored just after [range]
 */
data class TextRun<K>(
    val range: IntRange,
    val annotations: Set<RangeAnnotation<K>>,
    val prePointAnns: Set<PointAnnotation<K>>,
    val postPointAnns: Set<PointAnnotation<K>>,
) {
    /**
     * Computes the [StateTransition] from this run to [newRun] by partitioning annotations into ended,
     * continuing, and beginning sets.
     */
    fun stateTransition(newRun: TextRun<K>): StateTransition<K> = StateTransition(
        ended = annotations - newRun.annotations,
        continuing = newRun.annotations.intersect(annotations),
        beginning = newRun.annotations - annotations,
        prePointAnns = newRun.prePointAnns,
        postPointAnns = this.postPointAnns,
    )
}
