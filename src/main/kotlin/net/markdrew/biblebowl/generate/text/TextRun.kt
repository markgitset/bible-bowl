package net.markdrew.biblebowl.generate.text

data class TextRun<K>(
    val range: IntRange,
    val annotations: Set<RangeAnnotation<K>>,
    val prePointAnns: Set<PointAnnotation<K>>,
    val postPointAnns: Set<PointAnnotation<K>>,
) {
    fun stateTransition(newRun: TextRun<K>): StateTransition<K> = StateTransition(
        ended = annotations - newRun.annotations,
        continuing = newRun.annotations.intersect(annotations),
        beginning = newRun.annotations - annotations,
        prePointAnns = newRun.prePointAnns,
        postPointAnns = newRun.postPointAnns,
    )
}
