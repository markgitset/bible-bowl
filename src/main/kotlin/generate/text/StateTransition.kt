package net.markdrew.biblebowl.generate.text

data class StateTransition<K>(
    val ended: Set<Annotation<K>>,
    val continuing: Set<Annotation<K>>,
    val beginning: Set<Annotation<K>>,
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
    fun isPresent(key: K): Boolean = present(key) != null
    companion object {
        fun <K> Set<Annotation<K>>.zeroOrOne(key: K): Annotation<K>? =
            filter { it.key == key }.let {
                when {
                    it.isEmpty() -> null
                    it.size > 1 -> throw Exception("More than 1 $key!")
                    else -> it.single()
                }
            }
    }
}
