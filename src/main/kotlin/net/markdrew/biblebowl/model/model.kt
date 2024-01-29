package net.markdrew.biblebowl.model

/**
 * Used to combine books, chapters, and (optionally) verses into a single integer.  See [AbsoluteVerseNum].  Can also
 * be thought of as one more than the maximum number of books, chapters (for a given book), or verses (for a given
 * chapter)
 */
const val BCV_FACTOR = 1_000
