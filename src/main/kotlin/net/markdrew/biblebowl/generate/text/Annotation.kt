package net.markdrew.biblebowl.generate.text

/**
 * A typed annotation attached to a position in a document
 *
 * [key] discriminates the kind of annotation (e.g. [net.markdrew.biblebowl.model.AnalysisUnit.VERSE]). [value]
 * carries the per-occurrence payload — `null` indicates the annotation is currently absent at this position;
 * `true` indicates presence without a meaningful value.
 *
 * @param key what kind of annotation this is
 * @param value payload, or null if this annotation is absent at the position
 */
sealed class Annotation<A>(
    val key: A,
    val value: Any?,
) {
    /** True if this annotation is absent (i.e. [value] is null). */
    fun isAbsent(): Boolean = value == null

    override fun toString(): String = toShortString()

    /** Compact "key(...)" or "key=value(...)" form for diagnostic output. */
    abstract fun toShortString(): String
}

/**
 * An [Annotation] that spans a contiguous character range
 *
 * @param range the character offset range this annotation applies to
 */
class RangeAnnotation<A>(
    key: A,
    value: Any?,
    val range: IntRange,
) : Annotation<A>(key, value) {
    override fun toShortString(): String = if (value == true) "$key($range)" else "$key=$value($range)"
}

/**
 * An [Annotation] anchored at a single character offset (e.g. a footnote callout)
 *
 * The annotation is considered to occur immediately *before* [offset]; renderers can use [TextRun.prePointAnns]
 * or [TextRun.postPointAnns] to place it relative to the surrounding text.
 *
 * @param offset character offset before which this annotation occurs
 */
class PointAnnotation<A>(
    key: A,
    value: Any?,
    val offset: Int,
) : Annotation<A>(key, value) {
    override fun toShortString(): String = if (value == true) "$key($offset)" else "$key=$value($offset)"
}
