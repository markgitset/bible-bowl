package net.markdrew.biblebowl.generate.text

/**
 * Format-agnostic toggles for which content features the renderer should detect and emphasize.
 *
 * Every field here describes *what* should be highlighted in the rendered text, independent of how any
 * particular output format (DOCX, LaTeX, Typst) renders the emphasis. Layout and page-level concerns
 * live on [LayoutOptions] instead.
 *
 * @param underlineUniqueWords if true, underline words that occur exactly once in the study set
 * @param customHighlights palette of regex/category-driven highlights, with caller-chosen colors. Names
 *   (the `other` category) and numbers (the `numbers` category) are ordinary palette entries now — add
 *   them via [fullHighlightPalette] to highlight them, just like men/places/divine.
 * @param smallCaps map from match text to its small-caps replacement (e.g., `"LORD" -> "Lord"`)
 */
data class FeatureOptions(
    val underlineUniqueWords: Boolean = false,
    val customHighlights: HighlightPalette = HighlightPalette.empty(),
    val smallCaps: Map<String, String> = smallCapsNames,
)
