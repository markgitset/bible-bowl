package net.markdrew.biblebowl.model

/**
 * Multiplier used to pack book, chapter, and (optionally) verse numbers into a single integer
 *
 * One more than the maximum number of books, chapters per book, or verses per chapter; see [AbsoluteVerseNum]
 * and [AbsoluteChapterNum].
 */
const val BCV_FACTOR = 1_000
