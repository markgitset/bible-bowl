package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.cli.CliConfig

/**
 * A named, coherent bundle of style + layout + feature defaults for one Bible-text document.
 *
 * Presets are the "predefined formats" a caller can select by [name]; individual options can then be
 * overridden via [TextOverrides].
 */
data class TextPreset(
    val name: String,
    val description: String,
    val options: TextOptions,
)

/**
 * Field-level overrides applied on top of a [TextPreset]. Every field is nullable: `null` means "inherit
 * the preset's value", a non-null value replaces it. This is the CLI's "preset-then-override" model — a
 * caller picks a base preset and tweaks individual options, or starts from [Presets.plain] and sets
 * everything for a fully-custom document.
 *
 * @param highlight tri-state toggle for the full highlight palette: true applies [fullHighlightPalette],
 *   false clears highlights, null inherits the preset's [TextOptions.customHighlights].
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

/**
 * Resolves this preset against [overrides], stamping the per-run [testDate], into a concrete [TextOptions].
 *
 * Each option falls back to the preset's value when the corresponding override is null. The [testDate] is
 * always taken from the argument.
 */
fun TextPreset.resolve(overrides: TextOverrides, testDate: java.time.LocalDate): TextOptions {
    return options.copy(
        testDate = testDate,
        fontSize = overrides.fontSize ?: options.fontSize,
        twoColumns = overrides.twoColumns ?: options.twoColumns,
        chapterBreaksPage = overrides.chapterBreaksPage ?: options.chapterBreaksPage,
        useHeadingsForChapters = overrides.useHeadingsForChapters ?: options.useHeadingsForChapters,
        chapterEndLines = overrides.chapterEndLines ?: options.chapterEndLines,
        mainFont = overrides.mainFont ?: options.mainFont,
        verseNumFont = overrides.verseNumFont ?: options.verseNumFont,
        headingFont = overrides.headingFont ?: options.headingFont,
        chapterFontSize = overrides.chapterFontSize ?: options.chapterFontSize,
        headingFontSize = overrides.headingFontSize ?: options.headingFontSize,
        footnoteFontSize = overrides.footnoteFontSize ?: options.footnoteFontSize,
        justified = overrides.justified ?: options.justified,
        underlineUniqueWords = overrides.underlineUniqueWords ?: options.underlineUniqueWords,
        customHighlights = when (overrides.highlight) {
            true -> fullHighlightPalette()
            false -> HighlightPalette.empty()
            null -> options.customHighlights
        },
        verseOnNewLine = overrides.verseOnNewLine ?: options.verseOnNewLine,
    )
}

/** The registry of predefined text presets. */
object Presets {
    /** Texas Bible Bowl official format — single-column, 12pt, inline chapter labels, no emphasis. */
    val tbb = TextPreset(
        name = "tbb",
        description = "Texas Bible Bowl official format — single-column, 12pt, inline chapter labels, no emphasis",
        options = TextOptions(),
    )

    /** Daesha's preference */
    val daesha = TextPreset(
        name = "daesha",
        description = "Texas Bible Bowl official format, except each verse starts on a new line",
        options = TextOptions(verseOnNewLine = true),
    )

    /** Raymond's preference */
    val raymond = TextPreset(
        name = "raymond",
        description = "Texas Bible Bowl official format, except each chapter is a header with a line",
        options = TextOptions(chapterEndLines = true, useHeadingsForChapters = true),
    )

    /** Mark's format — two-column, 10pt, heading-style chapters, no emphasis. */
    val marks = TextPreset(
        name = "marks",
        description = "Mark's format — two-column, 10pt, heading-style chapters, no emphasis",
        options = TextOptions(
            fontSize = 10,
            twoColumns = true,
            useHeadingsForChapters = true,
            mainFont = "Times New Roman",
            verseNumFont = "Liberation Sans",
            headingFont = "Liberation Sans",
            chapterFontSize = 14,
            headingFontSize = 12,
            footnoteFontSize = 9,
            justified = true,
            chapterEndLines = true,
        ),
    )

    /** Minimal single-column base, intended as a starting point for fully-custom configurations. */
    val plain = TextPreset(
        name = "plain",
        description = "Minimal single-column base, intended as a starting point for fully-custom configurations",
        options = TextOptions(fontSize = 10, useHeadingsForChapters = false),
    )

    private val customPresets: List<TextPreset> by lazy {
        val loaded = mutableMapOf<String, MutableMap<String, String>>()
        for (propName in CliConfig.props.stringPropertyNames()) {
            if (propName.startsWith("preset.")) {
                val parts = propName.split(".")
                if (parts.size == 3) {
                    val presetName = parts[1]
                    val property = parts[2]
                    val value = CliConfig.props.getProperty(propName)
                    loaded.getOrPut(presetName) { mutableMapOf() }[property] = value
                }
            }
        }
        loaded.map { (presetName, config) ->
            val desc = config["description"] ?: "User-defined custom preset '$presetName'"
            val styleName = config["style"]?.lowercase() ?: "tbb"
            val baseOptions = if (styleName == "marks") {
                TextOptions(
                    fontSize = 10,
                    twoColumns = true,
                    useHeadingsForChapters = true,
                    mainFont = "Times New Roman",
                    verseNumFont = "Liberation Sans",
                    headingFont = "Liberation Sans",
                    chapterFontSize = 14,
                    headingFontSize = 12,
                    footnoteFontSize = 9,
                    justified = true,
                )
            } else {
                TextOptions()
            }

            val customOptions = baseOptions.copy(
                fontSize = config["fontSize"]?.toInt() ?: baseOptions.fontSize,
                twoColumns = config["twoColumns"]?.toBoolean() ?: baseOptions.twoColumns,
                chapterBreaksPage = config["chapterBreaksPage"]?.toBoolean() ?: baseOptions.chapterBreaksPage,
                useHeadingsForChapters = config["useHeadingsForChapters"]?.toBoolean() ?: baseOptions.useHeadingsForChapters,
                chapterEndLines = config["chapterEndLines"]?.toBoolean() ?: baseOptions.chapterEndLines,
                mainFont = config["mainFont"] ?: baseOptions.mainFont,
                verseNumFont = config["verseNumFont"] ?: baseOptions.verseNumFont,
                headingFont = config["headingFont"] ?: baseOptions.headingFont,
                chapterFontSize = config["chapterFontSize"]?.toInt() ?: baseOptions.chapterFontSize,
                headingFontSize = config["headingFontSize"]?.toInt() ?: baseOptions.headingFontSize,
                footnoteFontSize = config["footnoteFontSize"]?.toInt() ?: baseOptions.footnoteFontSize,
                justified = config["justified"]?.toBoolean() ?: baseOptions.justified,
                underlineUniqueWords = config["underlineUniqueWords"]?.toBoolean() ?: baseOptions.underlineUniqueWords,
                verseOnNewLine = config["verseOnNewLine"]?.toBoolean() ?: baseOptions.verseOnNewLine
            )
            TextPreset(
                name = presetName,
                description = desc,
                options = customOptions,
            )
        }
    }

    /** All presets, in selection order. */
    val all: List<TextPreset> by lazy { listOf(tbb, marks, plain) + customPresets }

    /** Case-insensitive lookup by [TextPreset.name]; null if unrecognized. */
    fun byName(name: String): TextPreset? = all.firstOrNull { it.name.equals(name, ignoreCase = true) }
}
