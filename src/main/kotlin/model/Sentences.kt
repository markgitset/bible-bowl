package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.DisjointRangeSet
import java.text.BreakIterator
import java.util.*

public fun identifySentences(text: String): DisjointRangeSet {
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

private fun forwardToNonWhitespace(text: String, startAt: Int): Int {
    var result = startAt
    while (text[result].isWhitespace()) result++
    return result
}

private fun backToNonWhitespace(text: String, endAt: Int): Int {
    var result = endAt
    while (text[result - 1].isWhitespace()) result--
    return result
}

private fun trimWhitespace(text: String, range: IntRange): IntRange {
    return forwardToNonWhitespace(text, range.first) until backToNonWhitespace(text, range.last + 1)
}

