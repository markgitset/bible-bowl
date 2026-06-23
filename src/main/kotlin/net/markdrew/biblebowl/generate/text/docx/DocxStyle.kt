package net.markdrew.biblebowl.generate.text.docx

/** Font names as accepted by docx4j's `<w:rFonts w:ascii="…">` attribute. */
enum class WmlFont(val value: String) {
    TIMES_NEW_ROMAN("Times New Roman:liga"),
    QUATTROCENTO_SANS("Quattrocento Sans"),
    MONOSPACE("Liberation Mono"),
    SANS_SERIF("Liberation Sans");

    companion object {
        fun byValueOrToken(name: String): WmlFont? {
            val normalized = name.lowercase().replace("-", "").replace("_", "").replace(" ", "")
            return entries.firstOrNull {
                it.name.lowercase().replace("_", "") == normalized ||
                it.value.lowercase().replace(" ", "").split(":")[0] == normalized
            }
        }
    }
}

/**
 * Typographic settings for a DOCX style preset
 *
 * Font sizes are stored in DOCX half-points (the unit `<w:sz w:val="…"/>` expects), so a 16pt heading
 * is `headingFontSizeHalfPt = 32`. Body font size is *not* part of typography — it's controlled by the
 * caller's [net.markdrew.biblebowl.generate.text.LayoutOptions.fontSize] and injected at template-binding
 * time via [toTemplateBindings].
 *
 * @param mainFont body-text font
 * @param verseNumFont font used for the verse-number run style
 * @param headingFont font used for headings and chapter labels
 * @param chapterFontSizeHalfPt chapter-label font size, in half-points
 * @param headingFontSizeHalfPt heading font size, in half-points
 * @param footnoteFontSizeHalfPt footnote font size, in half-points
 * @param justified justification mode for body paragraphs (`"left"` or `"both"`)
 */
data class DocxTypography(
    val mainFont: WmlFont,
    val verseNumFont: WmlFont,
    val headingFont: WmlFont,
    val chapterFontSizeHalfPt: Int,
    val headingFontSizeHalfPt: Int,
    val footnoteFontSizeHalfPt: Int,
    val justified: String,
) {
    /**
     * Builds the template-substitution map consumed by `styles.xml` and friends.
     *
     * The body font size (`mainFontSize`) is *not* a typography field — it's sourced from
     * [layoutFontSizePt] (in points) and converted to half-points here, so all callers go through one
     * place.
     */
    fun toTemplateBindings(layoutFontSizePt: Int): Map<String, String> = mapOf(
        "mainFont" to mainFont.value,
        "verseNumFont" to verseNumFont.value,
        "headingFont" to headingFont.value,
        "headingFontSize" to headingFontSizeHalfPt.toString(),
        "chapterFontSize" to chapterFontSizeHalfPt.toString(),
        "footnoteFontSize" to footnoteFontSizeHalfPt.toString(),
        "justified" to justified,
        "mainFontSize" to (layoutFontSizePt * 2).toString(),
    )
}

/**
 * A DOCX style preset, pairing template resources with typographic settings.
 *
 * The [resourcePath] points at a classpath subdirectory holding the docx template fragments
 * (`fontTable.xml`, `styles.xml`, `footer.xml`, optional `frontMatter.xml` / `endMatter.xml`); the
 * [typography] supplies the values substituted into those templates.
 */
sealed interface DocxStyle {
    /** Classpath subdirectory containing the template fragments (e.g., `"tbb-doc-format"`). */
    val resourcePath: String

    /** Fonts and font sizes for this preset. */
    val typography: DocxTypography
}

/** Texas Bible Bowl official document format — Quattrocento Sans, single-column, left-justified. */
data object TbbDocxStyle : DocxStyle {
    override val resourcePath: String = "tbb-doc-format"
    override val typography: DocxTypography = DocxTypography(
        mainFont = WmlFont.QUATTROCENTO_SANS,
        verseNumFont = WmlFont.QUATTROCENTO_SANS,
        headingFont = WmlFont.QUATTROCENTO_SANS,
        chapterFontSizeHalfPt = 27,
        headingFontSizeHalfPt = 32,
        footnoteFontSizeHalfPt = 20,
        justified = "left",
    )
}

/** Mark's two-column document format — Times New Roman body, sans-serif headings, justified body. */
data object MarksDocxStyle : DocxStyle {
    override val resourcePath: String = "marks-doc-format"
    override val typography: DocxTypography = DocxTypography(
        mainFont = WmlFont.TIMES_NEW_ROMAN,
        verseNumFont = WmlFont.SANS_SERIF,
        headingFont = WmlFont.SANS_SERIF,
        chapterFontSizeHalfPt = 28,
        headingFontSizeHalfPt = 24,
        footnoteFontSizeHalfPt = 18,
        justified = "both",
    )
}
