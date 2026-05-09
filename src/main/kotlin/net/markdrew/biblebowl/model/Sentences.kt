package net.markdrew.biblebowl.model

import net.markdrew.chupacabra.core.DisjointRangeSet
import java.text.BreakIterator
import java.util.Locale

/**
 * Splits [text] into sentence ranges using a US-English [BreakIterator], trimming whitespace from each range.
 */
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

/** Returns the smallest offset at or after [startAt] whose character is not whitespace. */
fun forwardToNonWhitespace(text: String, startAt: Int): Int {
    var result = startAt
    while (text[result].isWhitespace()) result++
    return result
}

/** Returns the largest offset at or before [endAt] whose preceding character is not whitespace. */
fun backToNonWhitespace(text: String, endAt: Int): Int {
    var result = endAt
    while (text[result - 1].isWhitespace()) result--
    return result
}

/** Returns [range] with leading and trailing whitespace stripped. */
fun trimWhitespace(text: String, range: IntRange): IntRange {
    return forwardToNonWhitespace(text, range.first) until backToNonWhitespace(text, range.last + 1)
}

/** Returns the smallest offset at or after [startAt] whose character does not satisfy [predicate]. */
fun forwardToNot(text: String, startAt: Int, predicate: (Char) -> Boolean): Int {
    var result = startAt
    while (predicate(text[result])) result++
    return result
}

/** Returns the largest offset at or before [endAt] whose preceding character does not satisfy [predicate]. */
fun backToNot(text: String, endAt: Int, predicate: (Char) -> Boolean): Int {
    var result = endAt
    while (predicate(text[result - 1])) result--
    return result
}

/** Returns [range] with leading/trailing characters that match [predicate] stripped. */
fun trim(text: String, range: IntRange, predicate: (Char) -> Boolean): IntRange {
    return forwardToNot(text, range.first, predicate) until backToNot(text, range.last + 1, predicate)
}
