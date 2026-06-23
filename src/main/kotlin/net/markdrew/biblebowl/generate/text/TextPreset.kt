package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.generate.text.docx.DocxStyle
import net.markdrew.biblebowl.generate.text.docx.MarksDocxStyle
import net.markdrew.biblebowl.generate.text.docx.TbbDocxStyle
import net.markdrew.biblebowl.generate.text.typst.MarksTypstStyle
import net.markdrew.biblebowl.generate.text.typst.TbbTypstStyle
import net.markdrew.biblebowl.generate.text.typst.TypstStyle

/**
 * Format-neutral identifier for a typographic style family.
 *
 * Styles are inherently format-specific (DOCX uses [DocxStyle], Typst uses [TypstStyle]), but a caller
 * shouldn't have to pick the right concrete object per format. A [StyleId] names the family once; each
 * writer resolves its own concrete style via [typst]/[docx]. This keeps the TBB/Mark's distinction in a
 * single place instead of hand-paired style objects scattered across the render matrix.
 */
enum class StyleId(val token: String) {
    TBB("tbb"),
    MARKS("marks");

    /** Concrete Typst style for this family. */
    fun typst(): TypstStyle = when (this) {
        TBB -> TbbTypstStyle
        MARKS -> MarksTypstStyle
    }

    /** Concrete DOCX style for this family. */
    fun docx(): DocxStyle = when (this) {
        TBB -> TbbDocxStyle
        MARKS -> MarksDocxStyle
    }

    companion object {
        /** Case-insensitive lookup by [token]; null if unrecognized. */
        fun byToken(token: String): StyleId? = entries.firstOrNull { it.token.equals(token, ignoreCase = true) }
    }
}

/**
 * A named, coherent bundle of style + layout + feature defaults for one Bible-text document.
 *
 * Presets are the "predefined formats" a caller can select by [name]; individual options can then be
 * overridden via [TextOverrides]. [LayoutOptions.testDate] is intentionally a placeholder here — it's a
 * per-run value injected during resolution, not a property of the preset.
 */
data class TextPreset(
    val name: String,
    val style: StyleId,
    val layout: LayoutOptions,
    val features: FeatureOptions,
)

/**
 * Field-level overrides applied on top of a [TextPreset]. Every field is nullable: `null` means "inherit
 * the preset's value", a non-null value replaces it. This is the CLI's "preset-then-override" model — a
 * caller picks a base preset and tweaks individual options, or starts from [Presets.plain] and sets
 * everything for a fully-custom document.
 *
 * @param highlight tri-state toggle for the full highlight palette: true applies [fullHighlightPalette],
 *   false clears highlights, null inherits the preset's [FeatureOptions.customHighlights].
 */
data class TextOverrides(
    val fontSize: Int? = null,
    val twoColumns: Boolean? = null,
    val chapterBreaksPage: Boolean? = null,
    val useHeadingsForChapters: Boolean? = null,
    val underlineUniqueWords: Boolean? = null,
    val highlight: Boolean? = null,
    val verseOnNewLine: Boolean? = null,
    val chapterEndLines: Boolean? = null,
    val mainFont: String? = null,
    val verseNumFont: String? = null,
    val headingFont: String? = null,
    val chapterFontSize: Int? = null,
    val headingFontSize: Int? = null,
    val footnoteFontSize: Int? = null,
    val justified: Boolean? = null,
) {
    /** True if any override is set; drives the hybrid pack-vs-single dispatch in the generator. */
    fun anySet(): Boolean = listOf(
        fontSize, twoColumns, chapterBreaksPage, useHeadingsForChapters,
        underlineUniqueWords, highlight, verseOnNewLine, chapterEndLines,
        mainFont, verseNumFont, headingFont, chapterFontSize, headingFontSize,
        footnoteFontSize, justified,
    ).any { it != null }
}

/** A fully-resolved single-document configuration: which style family plus concrete option groups. */
data class ResolvedTextConfig(
    val style: StyleId,
    val layout: LayoutOptions,
    val features: FeatureOptions,
)

/**
 * Resolves this preset against [overrides], stamping the per-run [testDate], into a concrete config.
 *
 * Each option falls back to the preset's value when the corresponding override is null. The [testDate] is
 * always taken from the argument (presets carry only a placeholder date).
 */
fun TextPreset.resolve(overrides: TextOverrides, testDate: java.time.LocalDate): ResolvedTextConfig {
    val resolvedLayout = layout.copy(
        testDate = testDate,
        fontSize = overrides.fontSize ?: layout.fontSize,
        twoColumns = overrides.twoColumns ?: layout.twoColumns,
        chapterBreaksPage = overrides.chapterBreaksPage ?: layout.chapterBreaksPage,
        useHeadingsForChapters = overrides.useHeadingsForChapters ?: layout.useHeadingsForChapters,
        chapterEndLines = overrides.chapterEndLines ?: layout.chapterEndLines,
        mainFont = overrides.mainFont ?: layout.mainFont,
        verseNumFont = overrides.verseNumFont ?: layout.verseNumFont,
        headingFont = overrides.headingFont ?: layout.headingFont,
        chapterFontSize = overrides.chapterFontSize ?: layout.chapterFontSize,
        headingFontSize = overrides.headingFontSize ?: layout.headingFontSize,
        footnoteFontSize = overrides.footnoteFontSize ?: layout.footnoteFontSize,
        justified = overrides.justified ?: layout.justified,
    )
    val resolvedFeatures = features.copy(
        underlineUniqueWords = overrides.underlineUniqueWords ?: features.underlineUniqueWords,
        customHighlights = when (overrides.highlight) {
            true -> fullHighlightPalette()
            false -> HighlightPalette.empty()
            null -> features.customHighlights
        },
        verseOnNewLine = overrides.verseOnNewLine ?: features.verseOnNewLine,
    )
    return ResolvedTextConfig(style, resolvedLayout, resolvedFeatures)
}

/** The registry of predefined text presets. */
object Presets {
    /** Texas Bible Bowl official format — single-column, 12pt, inline chapter labels, no emphasis. */
    val tbb = TextPreset(
        name = "tbb",
        style = StyleId.TBB,
        layout = LayoutOptions(fontSize = 12),
        features = FeatureOptions(),
    )

    /** Mark's format — two-column, 10pt, heading-style chapters, no emphasis. */
    val marks = TextPreset(
        name = "marks",
        style = StyleId.MARKS,
        layout = LayoutOptions(fontSize = 10, twoColumns = true, useHeadingsForChapters = true),
        features = FeatureOptions(),
    )

    /** Minimal single-column base, intended as a starting point for fully-custom configurations. */
    val plain = TextPreset(
        name = "plain",
        style = StyleId.TBB,
        layout = LayoutOptions(),
        features = FeatureOptions(),
    )

    /** All presets, in selection order. */
    val all: List<TextPreset> = listOf(tbb, marks, plain)

    /** Case-insensitive lookup by [TextPreset.name]; null if unrecognized. */
    fun byName(name: String): TextPreset? = all.firstOrNull { it.name.equals(name, ignoreCase = true) }
}
