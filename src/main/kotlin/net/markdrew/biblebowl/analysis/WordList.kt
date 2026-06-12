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
 * @param rawRegex true if entries are full regexes used verbatim (no `\b`-wrap, no plural, case-insensitive),
 *   for categories like [NUMBERS] whose patterns manage their own boundaries
 */
enum class WordList(val areNames: Boolean = false, val rawRegex: Boolean = false) {
    ANIMALS,
    BODY_PARTS,
    COLORS,
    FOODS,
    NUMBERS(rawRegex = true),
    MEN(areNames = true),
    WOMEN(areNames = true),
    PLACES(areNames = true),
    PEOPLE_GROUPS(areNames = true),
    ANGELS_DEMONS(areNames = true),
    OTHER(areNames = true),
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
     * Returns one [Regex] per entry in [dictionary]. [rawRegex] categories use the entry verbatim
     * (case-insensitive); others wrap it in `\b…\b` with case/plural rules appropriate to the category.
     */
    fun regexSequence(): Sequence<Regex> = dictionary.asSequence().map {
        if (rawRegex) Regex(it, RegexOption.IGNORE_CASE) else wordToRegex(it, caseSensitive = areNames, plural = !areNames)
    }

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
