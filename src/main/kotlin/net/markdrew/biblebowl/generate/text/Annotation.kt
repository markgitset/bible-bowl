package net.markdrew.biblebowl.generate.text

data class Annotation<A>(
    val key: A, // what kind of annotation is this?
    val value: Any?, // null indicates absence of this annotation type, true indicates presence w/o value
    val range: IntRange, // what character offset range does this annotation apply to?
    val isPoint: Boolean = false, // is this a point annotation (range length is zero, only use start index of range)
) {
    fun isAbsent(): Boolean = value == null
    fun toShortString(): String = if (value == true) "$key($range)" else "$key=$value($range)"
}

