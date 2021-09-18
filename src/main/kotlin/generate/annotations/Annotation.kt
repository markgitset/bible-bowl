package net.markdrew.biblebowl.generate.annotations

data class Annotation<K>(
    val key: K,
    val value: Any?, // null indicates absence of this annotation type, true indicates presence w/o value
    val range: IntRange,
) {
    fun isAbsent(): Boolean = value == null
}

