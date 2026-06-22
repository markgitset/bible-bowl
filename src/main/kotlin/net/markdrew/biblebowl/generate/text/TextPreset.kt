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
