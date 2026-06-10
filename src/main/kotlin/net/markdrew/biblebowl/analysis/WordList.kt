package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.StudySet

/**
 * Curated word categories backed by per-category resource files (animals, foods, body parts, …)
 *
 * Each entry's [dictionary] is loaded lazily from a `word-lists/<lower-name>.txt` resource, with the enum
 * name converted to kebab-case (e.g. `BODY_PARTS` → `body-parts.txt`). Categories that are proper-name
 * lists ([areNames] = true) are matched case-sensitively and without plural forms; non-name lists are
 * matched case-insensitively and tolerate a trailing "s".
 *
 * @param areNames true if entries are proper names (case-sensitive, singular only)
 */
enum class WordList(val areNames: Boolean = false) {
    ANIMALS,
    BODY_PARTS,
    COLORS,
    FOODS,
    MEN(areNames = true),
    WOMEN(areNames = true),
    PLACES(areNames = true),
    PEOPLE_GROUPS(areNames = true),
    ANGELS_DEMONS(areNames = true),
    DIVINE(areNames = true),
    ;

    /** Stable kebab-case id (e.g. `body-parts`), used as the resource filename stem and category id. */
    val token: String = name.lowercase().replace('_', '-')

    /** Words in this category, lazily loaded from resources */
    val dictionary: Dictionary by lazy {
        DictionaryParser.parse("word-lists/$token.txt")
    }

    private fun wordToRegex(word: String, caseSensitive: Boolean = false, plural: Boolean = true): Regex {
        val options = if (caseSensitive) emptySet() else setOf(RegexOption.IGNORE_CASE)
        val pluralExpr = if (plural) "s?" else ""
        return Regex("""\b$word$pluralExpr\b""", options)
    }

    /**
     * Returns one whole-word [Regex] per entry in [dictionary], using case-sensitivity and pluralization
     * rules appropriate to this category.
     */
    fun regexSequence(): Sequence<Regex> =
        dictionary.asSequence().map { wordToRegex(it, caseSensitive = areNames, plural = !areNames) }

    companion object {
        /**
         * A single [CategoryAnnotator] over every word-list category for [studySet], with that set's
         * per-occurrence overrides applied. One shared resolution that all word-list indices read, so a
         * term in two lists (or an override) lands in exactly one category everywhere.
         */
        fun categoryAnnotator(studySet: StudySet): CategoryAnnotator = CategoryAnnotator(
            name = "wordlist-categories",
            categories = entries.map { it.token to it.regexSequence().toSet() },
            overrides = CategoryOverrides.load(studySet),
        )

        /** Looks up the category by its [token], or null if none matches. */
        fun byToken(token: String): WordList? = entries.firstOrNull { it.token == token }
    }
}
