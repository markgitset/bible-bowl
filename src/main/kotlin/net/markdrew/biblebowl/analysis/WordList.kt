package net.markdrew.biblebowl.analysis

enum class WordList(private val areNames: Boolean = false) {
    ANIMALS,
    BODY_PARTS,
    COLORS,
    FOODS,
    MEN(areNames = true),
    WOMEN(areNames = true),
    ANGELS_DEMONS(areNames = true),
    ;

    val dictionary: Dictionary by lazy {
        DictionaryParser.parse("word-lists/${name.lowercase().replace('_', '-')}.txt")
    }

    private fun wordToRegex(word: String, caseSensitive: Boolean = false, plural: Boolean = true): Regex {
        val options = if (caseSensitive) emptySet() else setOf(RegexOption.IGNORE_CASE)
        val pluralExpr = if (plural) "s?" else ""
        return Regex("""\b$word$pluralExpr\b""", options)
    }

    fun regexSequence(): Sequence<Regex> =
        dictionary.asSequence().map { wordToRegex(it, caseSensitive = areNames, plural = !areNames) }
}