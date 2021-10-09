package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.chupacabra.core.DisjointRangeSet

fun String.normalizeWS(): String = replace("\\s+".toRegex(), " ").trim()
fun String.excerpt(range: IntRange) = Excerpt(this.substring(range), range)

fun formatRange(s: String, range: IntRange, formatFun: (String) -> String): String =
    s.replaceRange(range, formatFun(s.substring(range)))

fun formatRanges(s: String, ranges: DisjointRangeSet, formatFun: (String) -> String): String =
    ranges.reversed().fold(s) { tmpStr, range -> formatRange(tmpStr, range, formatFun) }

fun wrapWith(begin: String, end: String = begin): (String) -> String = { begin + it + end }
fun blankOut(blankChar: String = "_", blankLength: Int = 10): (String) -> String = { blankChar.repeat(blankLength) }
