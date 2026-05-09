package net.markdrew.biblebowl.generate.text

/**
 * The change in a document's annotation state between two adjacent [TextRun]s
 *
 * Range annotations are partitioned into three mutually-exclusive sets — [ended], [continuing], and
 * [beginning] — capturing what was active in the previous run, what's active in this run, and the diff.
 * Point annotations attached to this run are surfaced separately as [prePointAnns] (anchored *before* the
 * run's text) and [postPointAnns] (anchored *after* it).
 */
data class StateTransition<K>(
    val ended: Set<RangeAnnotation<K>>,
    val continuing: Set<RangeAnnotation<K>>,
    val beginning: Set<RangeAnnotation<K>>,
    val prePointAnns: Set<PointAnnotation<K>>,
    val postPointAnns: Set<PointAnnotation<K>>,
) {
    init {
        // check that three sets are mutually exclusive
        assert(ended.intersect(continuing).isEmpty())
        assert(ended.intersect(beginning).isEmpty())
        assert(continuing.intersect(beginning).isEmpty())
    }

    /** Annotations active in the new run (= [beginning] ∪ [continuing]) */
    val currentState: Set<Annotation<K>> by lazy { beginning + continuing }

    /** Annotations active in the previous run (= [ended] ∪ [continuing]) */
    val previousState: Set<Annotation<K>> by lazy { ended + continuing }

    /** The [Annotation] for [key] that's beginning at this transition, or null if none. */
    fun beginning(key: K): Annotation<K>? = beginning.zeroOrOne(key)

    /** True if an annotation with [key] is starting at this transition. */
    fun isBeginning(key: K): Boolean = beginning(key) != null

    /** The [Annotation] for [key] that's ending at this transition, or null if none. */
    fun ended(key: K): Annotation<K>? = ended.zeroOrOne(key)

    /** True if an annotation with [key] just ended at this transition. */
    fun isEnded(key: K): Boolean = ended(key) != null

    /** The currently-active [Annotation] for [key], or null if none. */
    fun present(key: K): Annotation<K>? = currentState.zeroOrOne(key)

    /** Pre-point annotation with [key] anchored to this run, or null. */
    fun prePoint(key: K): PointAnnotation<K>? = prePointAnns.zeroOrOne(key)

    /** Post-point annotation with [key] anchored to this run, or null. */
    fun postPoint(key: K): PointAnnotation<K>? = postPointAnns.zeroOrOne(key)

    /** True if an annotation with [key] is currently active. */
    fun isPresent(key: K): Boolean = present(key) != null

    companion object {
        /**
         * Returns the single annotation with the given [key], or null if there are zero.
         *
         * @throws Exception if more than one annotation with [key] is in the set
         */
        fun <A : Annotation<K>, K> Set<A>.zeroOrOne(key: K): A? =
            filter { it.key == key }.let {
                when {
                    it.isEmpty() -> null
                    it.size > 1 -> throw Exception("More than 1 $key!")
                    else -> it.single()
                }
            }
    }
}
