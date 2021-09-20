package net.markdrew.biblebowl.generate

import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.shift

data class Excerpt(val excerptText: String, val excerptRange: IntRange) {

    fun relative(range: IntRange): IntRange = range.shift(-excerptRange.first)

    fun substring(range: IntRange): String = excerptText.substring(relative(range))

    fun formatRange(range: IntRange, formatFun: (String) -> String): String =
        relative(range).let { r -> excerptText.replaceRange(r, formatFun(excerptText.substring(r))) }

    fun formatRanges(ranges: DisjointRangeSet, formatFun: (String) -> String): String =
        ranges.reversed().fold(excerptText) { tmpStr, range -> formatRange(tmpStr, relative(range), formatFun) }

    /**
     * Remove a possessive endings (e.g., "'s"), if present
     */
    fun disown(): Excerpt = if (excerptText.endsWith("'s") || excerptText.endsWith("’s")) {
        Excerpt(excerptText.dropLast(2), excerptRange.first..(excerptRange.last-2))
    } else this
}

fun String.normalizeWS(): String = replace("\\s+".toRegex(), " ").trim()
fun String.excerpt(range: IntRange) = Excerpt(this.substring(range), range)

fun formatRange(s: String, range: IntRange, formatFun: (String) -> String): String =
    s.replaceRange(range, formatFun(s.substring(range)))

fun formatRanges(s: String, ranges: DisjointRangeSet, formatFun: (String) -> String): String =
    ranges.reversed().fold(s) { tmpStr, range -> formatRange(tmpStr, range, formatFun) }

fun wrapWith(begin: String, end: String = begin): (String) -> String = { begin + it + end }
fun blankOut(blankChar: String = "_", blankLength: Int = 10): (String) -> String = { blankChar.repeat(blankLength) }
