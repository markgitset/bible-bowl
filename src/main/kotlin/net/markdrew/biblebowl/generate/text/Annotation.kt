package net.markdrew.biblebowl.generate.text

sealed class Annotation<A>(
    val key: A, // what kind of annotation is this?
    val value: Any?, // null indicates absence of this annotation type, true indicates presence w/o value
) {
    fun isAbsent(): Boolean = value == null
    override fun toString(): String = toShortString()
    abstract fun toShortString(): String
}

class RangeAnnotation<A>(
    key: A, // what kind of annotation is this?
    value: Any?, // null indicates absence of this annotation type, true indicates presence w/o value
    val range: IntRange, // what character offset range does this annotation apply to?
) : Annotation<A>(key, value) {
    override fun toShortString(): String = if (value == true) "$key($range)" else "$key=$value($range)"
}

class PointAnnotation<A>(
    key: A, // what kind of annotation is this?
    value: Any?, // null indicates absence of this annotation type, true indicates presence w/o value
    val offset: Int, // character offset *before* which this annotation occurs
//    val leading: Boolean = false // true if this *precedes* its associated text, false if it *follows* text
) : Annotation<A>(key, value) {
    override fun toShortString(): String = if (value == true) "$key($offset)" else "$key=$value($offset)"
}

