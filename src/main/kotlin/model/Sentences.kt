package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.DisjointRangeSet
import java.text.BreakIterator
import java.util.*

fun identifySentences(text: String): DisjointRangeSet {
    val boundary: BreakIterator = BreakIterator.getSentenceInstance(Locale.US)
    boundary.setText(text)
    var start = boundary.first()
    var end = boundary.next()
    val sentences = DisjointRangeSet()
    while (end != BreakIterator.DONE) {
        val range = trimWhitespace(text, start until end)
        sentences.add(range)
        start = end
        end = boundary.next()
    }
    return sentences
}

fun forwardToNonWhitespace(text: String, startAt: Int): Int {
    var result = startAt
    while (text[result].isWhitespace()) result++
    return result
}

fun backToNonWhitespace(text: String, endAt: Int): Int {
    var result = endAt
    while (text[result - 1].isWhitespace()) result--
    return result
}

fun trimWhitespace(text: String, range: IntRange): IntRange {
    return forwardToNonWhitespace(text, range.first) until backToNonWhitespace(text, range.last + 1)
}

fun forwardToNot(text: String, startAt: Int, predicate: (Char) -> Boolean): Int {
    var result = startAt
    while (predicate(text[result])) result++
    return result
}

fun backToNot(text: String, endAt: Int, predicate: (Char) -> Boolean): Int {
    var result = endAt
    while (predicate(text[result - 1])) result--
    return result
}

fun trim(text: String, range: IntRange, predicate: (Char) -> Boolean): IntRange {
    return forwardToNot(text, range.first, predicate) until backToNot(text, range.last + 1, predicate)
}

