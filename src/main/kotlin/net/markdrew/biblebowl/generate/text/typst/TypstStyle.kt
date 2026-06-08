package net.markdrew.biblebowl.generate.text.typst

/**
 * Typographic settings for a Typst style preset
 *
 * Font sizes are stored in points (Typst's native unit). Body font size is *not* part of typography —
 * it's controlled by the caller's [net.markdrew.biblebowl.generate.text.LayoutOptions.fontSize].
 *
 * @param mainFont body-text font family (passed to `#set text(font: "...")`)
 * @param verseNumFont font family used inside the `versenum` helper
 * @param headingFont font family used for chapter and section headings
 * @param chapterFontSize chapter-label font size, in points
 * @param headingFontSize section-heading font size, in points
 * @param footnoteFontSize footnote font size, in points
 * @param justified true for justified body paragraphs (`#set par(justify: true)`); false for left-aligned
 */
sealed interface TypstStyle {
    val mainFont: String
    val verseNumFont: String
    val headingFont: String
    val chapterFontSize: Int
    val headingFontSize: Int
    val footnoteFontSize: Int
    val justified: Boolean
}

// Libertinus Serif is a Times-like serif font bundled with the Typst binary — guaranteed
// available on every Typst install regardless of system fonts. Used for both presets so the
// TBB / Mark's distinction is driven by size + layout (font size, columns, justification),
// not font family.
private const val BUNDLED_SERIF = "Libertinus Serif"

/** Texas Bible Bowl Typst preset — single-column-friendly, left-aligned. */
data object TbbTypstStyle : TypstStyle {
    override val mainFont: String = BUNDLED_SERIF
    override val verseNumFont: String = BUNDLED_SERIF
    override val headingFont: String = BUNDLED_SERIF
    override val chapterFontSize: Int = 14
    override val headingFontSize: Int = 16
    override val footnoteFontSize: Int = 10
    override val justified: Boolean = false
}

/** Mark's Typst preset — smaller body, two-column, justified. */
data object MarksTypstStyle : TypstStyle {
    override val mainFont: String = BUNDLED_SERIF
    override val verseNumFont: String = BUNDLED_SERIF
    override val headingFont: String = BUNDLED_SERIF
    override val chapterFontSize: Int = 14
    override val headingFontSize: Int = 12
    override val footnoteFontSize: Int = 9
    override val justified: Boolean = true
}
