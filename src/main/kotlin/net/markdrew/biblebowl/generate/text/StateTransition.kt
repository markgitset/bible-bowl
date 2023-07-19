package net.markdrew.biblebowl.generate.text

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
    val currentState: Set<Annotation<K>> by lazy { beginning + continuing }
    val previousState: Set<Annotation<K>> by lazy { ended + continuing }
    fun beginning(key: K): Annotation<K>? = beginning.zeroOrOne(key)
    fun isBeginning(key: K): Boolean = beginning(key) != null
    fun ended(key: K): Annotation<K>? = ended.zeroOrOne(key)
    fun isEnded(key: K): Boolean = ended(key) != null
    fun present(key: K): Annotation<K>? = currentState.zeroOrOne(key)
    fun prePoint(key: K): PointAnnotation<K>? = prePointAnns.zeroOrOne(key)
    fun postPoint(key: K): PointAnnotation<K>? = postPointAnns.zeroOrOne(key)
    fun isPresent(key: K): Boolean = present(key) != null
    companion object {
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
