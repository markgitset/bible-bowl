package net.markdrew.biblebowl.generate.text

/**
 * A named RGB color used to highlight text matches.
 *
 * The [name] is used by writers that reference colors symbolically (e.g., LaTeX `\definecolor{<name>}`);
 * the [rgb] triple is used by writers that need explicit byte values (e.g., DOCX `<w:shd w:fill="…"/>`).
 *
 * @param name symbolic identifier, used as-is in LaTeX `\definecolor`
 * @param rgb red/green/blue components, each in `0..255`
 */
data class HighlightColor(val name: String, val rgb: Triple<Int, Int, Int>) {
    /** Six-character lowercase hex encoding of [rgb], e.g. `"ffb66c"`. */
    fun toHex(): String = "%02x%02x%02x".format(rgb.first, rgb.second, rgb.third)
}

/**
 * Ordered list of color/regex pairings used to drive custom text highlighting.
 *
 * Iteration order is load-bearing: when two patterns match overlapping ranges, the annotation builder
 * resolves the conflict using length first and insertion order as a fallback. Use a list, not a map,
 * so callers can guarantee the order.
 */
data class HighlightPalette(val entries: List<Pair<HighlightColor, Set<Regex>>>) {
    companion object {
        /** Empty palette — no custom highlights applied. */
        fun empty(): HighlightPalette = HighlightPalette(emptyList())
    }
}
