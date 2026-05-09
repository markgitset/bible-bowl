package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.chupacabra.core.DisjointRangeSet

/** Returns this string with runs of whitespace collapsed to a single space and outer whitespace stripped. */
fun String.normalizeWS(): String = replace("\\s+".toRegex(), " ").trim()

/** Slices this string and wraps the result with its absolute character range as an [Excerpt]. */
fun String.excerpt(range: IntRange) = Excerpt(this.substring(range), range)

/** Returns [s] with the substring at [range] transformed by [formatFun]. */
fun formatRange(s: String, range: IntRange, formatFun: (String) -> String): String =
    s.replaceRange(range, formatFun(s.substring(range)))

/**
 * Returns [s] with each disjoint range in [ranges] transformed by [formatFun].
 *
 * Ranges are processed in reverse order so earlier offsets remain valid as later substrings change length.
 */
fun formatRanges(s: String, ranges: DisjointRangeSet, formatFun: (String) -> String): String =
    ranges.reversed().fold(s) { tmpStr, range -> formatRange(tmpStr, range, formatFun) }

/** Builds a `(String) -> String` that wraps its input with [begin] and [end] (defaulting [end] to [begin]). */
fun wrapWith(begin: String, end: String = begin): (String) -> String = { begin + it + end }

/** Builds a `(String) -> String` that ignores its input and returns [blankLength] copies of [blankChar]. */
fun blankOut(blankChar: String = "_", blankLength: Int = 10): (String) -> String = { blankChar.repeat(blankLength) }
