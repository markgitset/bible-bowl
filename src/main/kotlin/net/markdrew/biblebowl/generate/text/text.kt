package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.analysis.WordList

/** Default small-caps replacements: forms of the divine name and "I AM" sayings rendered in small caps. */
val smallCapsNames = mapOf(
    "GOD" to "God",
    "LORD" to "Lord",
    "I AM" to "I am",
    "I AM WHO I AM" to "I am who I am",
    "I am what I am" to "I am what I am",
    "I will be what I will be" to "I will be what I will be"
)

/**
 * Regex patterns matching common references to God, Jesus, Holy Spirit, angels, etc.
 *
 * Sourced from the [WordList.DIVINE] list (`word-lists/divine.txt`), so divine highlighting, the
 * names-index exclusion, and the validator all share one definition. Used by name-extraction code to
 * exclude these from the regular names index (so "God" / "Jesus" / "Christ" don't show up there).
 */
val divineNames: Set<String> get() = WordList.DIVINE.dictionary