package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StudyData
import java.util.Arrays

/** How much context to show around an occurrence. */
enum class ContextZoom { WORDS, SENTENCE, VERSE, VERSE_NEIGHBORS }

/**
 * Context around one occurrence, split so the instance can be highlighted: render [before] + a colored
 * [instance] + [after]. [location] is the `Book ch:verse` label.
 */
data class RenderedContext(val location: String, val before: String, val instance: String, val after: String)

/**
 * Produces zoomable context excerpts (word-window → sentence → verse → verse+neighbors) for an
 * occurrence, with the instance span isolated for highlighting. Word ranges are precomputed for fast
 * window lookup.
 */
class ContextRenderer(private val studyData: StudyData) {

    private val wordRanges: List<IntRange> = studyData.words.toList()
    private val wordStarts: IntArray = IntArray(wordRanges.size) { wordRanges[it].first }

    fun render(range: IntRange, zoom: ContextZoom, wordWindow: Int = 6): RenderedContext {
        val ctx: IntRange = when (zoom) {
            ContextZoom.WORDS -> wordWindowRange(range, wordWindow)
            ContextZoom.SENTENCE -> studyData.enclosingSentence(range) ?: verseRange(range) ?: range
            ContextZoom.VERSE -> verseRange(range) ?: range
            ContextZoom.VERSE_NEIGHBORS -> verseNeighborsRange(range)
        }
        val text = studyData.text
        return RenderedContext(
            location = studyData.verseEnclosing(range)?.format(FULL_BOOK_FORMAT) ?: "?",
            before = text.substring(ctx.first until range.first),
            instance = text.substring(range),
            after = text.substring((range.last + 1)..ctx.last),
        )
    }

    private fun wordWindowRange(range: IntRange, window: Int): IntRange {
        if (wordRanges.isEmpty()) return range
        val from = (wordIndexContaining(range.first) - window).coerceAtLeast(0)
        val to = (wordIndexContaining(range.last) + window).coerceAtMost(wordRanges.lastIndex)
        return wordRanges[from].first..wordRanges[to].last
    }

    private fun verseRange(range: IntRange): IntRange? =
        studyData.verseEnclosing(range)?.let { studyData.verseIndex[it] }

    private fun verseNeighborsRange(range: IntRange): IntRange {
        val verse = verseRange(range) ?: return range
        val firstWordBefore = wordRanges.getOrNull(wordIndexContaining(verse.first) - 8)?.first ?: verse.first
        val lastWordAfter = wordRanges.getOrNull(wordIndexContaining(verse.last) + 8)?.last ?: verse.last
        // expand to the enclosing verses of the neighbor edges
        val start = verseRange(firstWordBefore..firstWordBefore)?.first ?: firstWordBefore
        val end = verseRange(lastWordAfter..lastWordAfter)?.last ?: lastWordAfter
        return start..end
    }

    /** Index of the word range containing [pos], or the nearest preceding word. */
    private fun wordIndexContaining(pos: Int): Int {
        var i = Arrays.binarySearch(wordStarts, pos)
        if (i < 0) i = -i - 2
        return i.coerceIn(0, wordRanges.lastIndex)
    }
}
