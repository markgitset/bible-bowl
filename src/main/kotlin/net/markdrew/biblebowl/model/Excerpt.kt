package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.shift

/**
 * A snippet of text together with the absolute character range it occupies in some larger document
 *
 * Operations on [Excerpt] convert between the document's coordinate system and the substring's local coordinate
 * system, so callers can keep working in the document's coordinates after extracting a slice.
 *
 * @param excerptText the substring itself
 * @param excerptRange the absolute character range of [excerptText] in the source document
 */
data class Excerpt(val excerptText: String, val excerptRange: IntRange) {

    /** Converts an absolute document range into a range relative to [excerptText]. */
    fun relative(range: IntRange): IntRange = range.shift(-excerptRange.first)

    /** Returns the substring of [excerptText] that corresponds to the given absolute document range. */
    fun substring(range: IntRange): String = excerptText.substring(relative(range))

    /**
     * Returns [excerptText] with the substring at the given absolute document range transformed by [formatFun].
     *
     * Useful for wrapping a span (e.g. a name or unique word) in highlight markup without losing the surrounding
     * excerpt.
     */
    fun formatRange(range: IntRange, formatFun: (String) -> String): String =
        relative(range).let { r -> excerptText.replaceRange(r, formatFun(excerptText.substring(r))) }

    /**
     * Returns [excerptText] with each of the given disjoint ranges transformed by [formatFun].
     *
     * Ranges are processed back-to-front so earlier offsets remain valid as later substrings are replaced.
     */
    fun formatRanges(ranges: DisjointRangeSet, formatFun: (String) -> String): String =
        ranges.reversed().fold(excerptText) { tmpStr, range ->
            net.markdrew.biblebowl.generate.formatRange(
                tmpStr,
                relative(range),
                formatFun
            )
        }

    /** Returns a copy with any trailing possessive ending ("'s" or "’s") stripped, or this excerpt unchanged. */
    fun disown(): Excerpt = if (excerptText.endsWith("'s") || excerptText.endsWith("’s")) {
        Excerpt(excerptText.dropLast(2), excerptRange.first..(excerptRange.last - 2))
    } else this
}

/** Wraps a regex match's value and absolute range as an [Excerpt]. */
fun MatchResult.toExcerpt(): Excerpt = Excerpt(value, range)
