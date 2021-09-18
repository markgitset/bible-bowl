package net.markdrew.biblebowl.generate.annotations

data class TextRun<K>(
    val range: IntRange,
    val annotations: Set<Annotation<K>>,
) {
    fun stateTransition(newState: Set<Annotation<K>>): StateTransition<K> = StateTransition(
        ended = annotations - newState,
        continuing = newState.intersect(annotations),
        beginning = newState - annotations,
    )
}
