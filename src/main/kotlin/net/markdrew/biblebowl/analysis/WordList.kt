package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Files
import java.nio.file.Path

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
    NUMBERS {
        /** Numbers are compositional, so the patterns live in [FindNumbers] — the single source (no file). */
        override fun regexSequence(wordListsDir: Path?): Sequence<Regex> =
            sequenceOf(FRACTIONS, ORDINALS, MULTI_NUMBER_PATTERN, NUMERAL_PATTERN)
                .map { Regex(it, RegexOption.IGNORE_CASE) }
    },
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
     * Returns one [Regex] per entry, each wrapped in `\b…\b` with case/plural rules appropriate to the
     * category. Entries come from the classpath [dictionary] by default, or from
     * `<wordListsDir>/<token>.txt` when [wordListsDir] is given (the validator, so freshly-edited lists are
     * seen). [NUMBERS] overrides this to source compositional patterns from code.
     */
    open fun regexSequence(wordListsDir: Path? = null): Sequence<Regex> {
        val dict = if (wordListsDir == null) dictionary else loadDictionary(wordListsDir)
        return dict.asSequence().map { wordToRegex(it, caseSensitive = areNames, plural = !areNames) }
    }

    /** Loads this category's entries from `<wordListsDir>/<token>.txt`, or empty if the file is absent. */
    private fun loadDictionary(wordListsDir: Path): Dictionary =
        wordListsDir.resolve("$token.txt").let { if (Files.exists(it)) DictionaryParser.parse(it) else emptySet() }

    companion object {
        /**
         * A single [CategoryAnnotator] over every word-list category for [studySet], with that set's
         * per-occurrence overrides applied. One shared resolution that all word-list indices read, so a
         * term in two lists (or an override) lands in exactly one category everywhere.
         *
         * When [sourceDir] is given (the validator), word-lists and overrides are read from that editable
         * tree rather than the classpath, so the resolution reflects edits the validator just wrote.
         */
        fun categoryAnnotator(studySet: StudySet, sourceDir: Path? = null): CategoryAnnotator {
            // Use sourceDir only when it really is a resources tree; otherwise fall back to the classpath
            // (a bogus/absent --source-dir, or a packaged run). Keeps lists and overrides consistent.
            val eff = sourceDir?.takeIf { Files.isDirectory(it.resolve("word-lists")) }
            return CategoryAnnotator(
                name = "wordlist-categories",
                categories = entries.map { it.token to it.regexSequence(eff?.resolve("word-lists")).toSet() },
                overrides = CategoryOverrides.load(studySet, eff),
            )
        }

        /** Looks up the category by its [token], or null if none matches. */
        fun byToken(token: String): WordList? = entries.firstOrNull { it.token == token }
    }
}
