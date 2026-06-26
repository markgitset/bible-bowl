package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.generate.text.BibleTextPipeline.computeOutputPath
import net.markdrew.biblebowl.generate.text.docx.DocxBibleTextWriter
import net.markdrew.biblebowl.generate.text.typst.TypstBibleTextWriter
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.ws.DEFAULT_COPYRIGHT_DISCLAIMER
import net.markdrew.biblebowl.ws.EsvClient
import java.nio.file.Path
import java.time.LocalDate

fun main() {
    val data: StudyData = StudyData.readData(StandardStudySet.DEFAULT, defaultDataPath, defaultRawDataPath)
    generateBibleTexts(data, LocalDate.of(2027, 4, 3), defaultProductsPath)
}

/**
 * Orchestrates one render: builds the [AnnotatedDoc], computes the output path, writes via [writer].
 *
 * The writer is responsible for any format-specific downstream steps (e.g., compiling `.tex` to PDF).
 * The orchestrator only builds the doc once per call and hands it off.
 */
object BibleTextPipeline {

    /**
     * Generates one Bible-text output for the given study data, options, preset, and writer.
     *
     * @return the path of the source file that was written (the writer may also produce a sibling PDF)
     */
    fun generate(
        studyData: StudyData,
        options: TextOptions,
        preset: TextPreset,
        writer: BibleTextWriter,
        productsPath: Path,
        store: AnnotationStore = AnnotationStore(studyData, cacheDir = null),
        copyrightDisclaimer: String = DEFAULT_COPYRIGHT_DISCLAIMER,
    ): Path = render(studyData, BibleAnnotationPipeline.build(studyData, options, store), options, preset, writer, productsPath, copyrightDisclaimer)

    /**
     * Renders an already-built [doc] for the given [options] via [writer].
     *
     * @return the path of the source file that was written (the writer may also produce a sibling PDF)
     */
    fun render(
        studyData: StudyData,
        doc: AnnotatedDoc<AnalysisUnit>,
        options: TextOptions,
        preset: TextPreset,
        writer: BibleTextWriter,
        productsPath: Path,
        copyrightDisclaimer: String = DEFAULT_COPYRIGHT_DISCLAIMER,
    ): Path {
        require(writer.supports(options)) {
            "Writer ${writer::class.simpleName} does not support options $options"
        }
        require(!options.verseOnNewLine || writer.format == Typst) {
            "${writer.format.subdir} does not support the verse-per-line option (only typst does)"
        }
        require(!options.chapterEndLines || writer.format == Typst) {
            "${writer.format.subdir} does not support the chapter-end-lines option (only typst does)"
        }
        require(!options.chapterEndLines || options.useHeadingsForChapters) {
            "chapter-end-lines option is incompatible with inline chapter labels"
        }
        val outputFile = computeOutputPath(studyData, options, preset, writer.format, productsPath)
        writer.write(outputFile, doc, studyData, options, copyrightDisclaimer)
        return outputFile
    }

    /**
     * Computes `<productsPath>/<simpleName>/text/<format.subdir>/<simpleName>-bible-text-<suffix>.<ext>`.
     * Suffix encodes the preset and any non-preset options.
     */
    fun computeOutputPath(
        studyData: StudyData,
        options: TextOptions,
        preset: TextPreset,
        format: OutputFormat,
        productsPath: Path,
    ): Path {
        val name = studyData.studySet.simpleName
        return productsPath.resolve(
            name, "text", format.subdir,
            "$name-bible-text-${fileNameSuffix(options, preset)}.${format.extension}"
        )
    }

    /** Filename suffix derived from options, comparing with the preset baseline to determine overrides. */
    fun fileNameSuffix(options: TextOptions, preset: TextPreset): String {
        val baseOptions = preset.options
        val slugs = mutableListOf<String>()
        slugs.add(preset.name) // Always start with the preset slug

        // Compare each relevant option and add a non-hyphenated slug if it differs
        if (options.fontSize != baseOptions.fontSize) {
            slugs.add("${options.fontSize}pt")
        }
        if (options.twoColumns != baseOptions.twoColumns) {
            slugs.add(if (options.twoColumns) "twocol" else "onecol")
        }
        if (options.chapterBreaksPage != baseOptions.chapterBreaksPage) {
            slugs.add(if (options.chapterBreaksPage) "breaks" else "nobreaks")
        }
        if (options.useHeadingsForChapters != baseOptions.useHeadingsForChapters) {
            slugs.add(if (options.useHeadingsForChapters) "noinlinechap" else "inlinechap")
        }
        if (options.chapterEndLines != baseOptions.chapterEndLines) {
            slugs.add(if (options.chapterEndLines) "chaplines" else "nochaplines")
        }
        if (options.underlineUniqueWords != baseOptions.underlineUniqueWords) {
            slugs.add(if (options.underlineUniqueWords) "unique" else "nounique")
        }
        if (options.verseOnNewLine != baseOptions.verseOnNewLine) {
            slugs.add(if (options.verseOnNewLine) "verseline" else "noverseline")
        }
        if (options.justified != baseOptions.justified) {
            slugs.add(if (options.justified) "justified" else "unjustified")
        }

        // Font overrides
        if (options.mainFont != baseOptions.mainFont) {
            slugs.add(options.mainFont.lowercase().replace(" ", "").replace("-", ""))
        }
        if (options.verseNumFont != baseOptions.verseNumFont) {
            slugs.add(options.verseNumFont.lowercase().replace(" ", "").replace("-", ""))
        }
        if (options.headingFont != baseOptions.headingFont) {
            slugs.add(options.headingFont.lowercase().replace(" ", "").replace("-", ""))
        }

        // Font sizes
        if (options.chapterFontSize != baseOptions.chapterFontSize) {
            slugs.add("chapsize${options.chapterFontSize}pt")
        }
        if (options.headingFontSize != baseOptions.headingFontSize) {
            slugs.add("headsize${options.headingFontSize}pt")
        }
        if (options.footnoteFontSize != baseOptions.footnoteFontSize) {
            slugs.add("footsize${options.footnoteFontSize}pt")
        }

        // Custom highlights
        val highlightRulesDiff = options.customHighlights.rules.map { it.first }.toSet() !=
                baseOptions.customHighlights.rules.map { it.first }.toSet()
        if (highlightRulesDiff) {
            slugs.add(if (options.customHighlights.entries.isNotEmpty()) "highlight" else "nohighlight")
        }

        return slugs.joinToString("-")
    }
}

/**
 * Standard palette used for the "full highlighting" variant — divine names yellow, women pink, men blue,
 * places green, people-groups gray, proper names (`other`) purple, numbers orange. Hex values match the
 * historically-shipping DOCX colors. Names and numbers are ordinary palette entries: nothing is detected
 * by heuristic — a span highlights only because its category resolves through [WordList.categoryAnnotator].
 */
fun fullHighlightPalette(): HighlightPalette = HighlightPalette(listOf(
    HighlightColor("men", Triple(0x99, 0xcc, 0xff)) to WordList.MEN.regexSequence().toSet(),
    HighlightColor("places", Triple(0x99, 0xff, 0x99)) to WordList.PLACES.regexSequence().toSet(),
    HighlightColor("women", Triple(0xff, 0x99, 0xff)) to WordList.WOMEN.regexSequence().toSet(),
    HighlightColor("people-groups", Triple(0xcc, 0xcc, 0xcc)) to WordList.PEOPLE_GROUPS.regexSequence().toSet(),
    HighlightColor("divine", Triple(0xff, 0xff, 0x00)) to WordList.DIVINE.regexSequence().toSet(),
    HighlightColor("numbers", Triple(0xff, 0xb6, 0x6c)) to WordList.NUMBERS.regexSequence().toSet(),
    HighlightColor("other", Triple(0x2e, 0xe6, 0xd9)) to WordList.OTHER.regexSequence().toSet(),
))

/**
 * Generates Bible-text outputs for [studyData] under [productsPath], in one of two modes.
 *
 * **Pack mode** (default — no [preset] and no [overrides] set): produces the curated set of six variants
 * per format — plain, unique-words-underlined, and full-highlighting, each in the TBB official style and
 * Mark's two-column style. This is the "give me everything" convenience.
 *
 * **Single mode** (a [preset] is named or any field of [overrides] is set): resolves exactly one
 * [TextOptions] (base preset, default [Presets.tbb], with overrides applied) and emits one
 * document per requested format.
 */
fun generateBibleTexts(
    studyData: StudyData,
    testDate: LocalDate,
    productsPath: Path,
    formats: Set<OutputFormat> = setOf(Docx, Typst),
    store: AnnotationStore = AnnotationStore(studyData, cacheDir = null),
    rawDataDir: Path = defaultRawDataPath,
    forceDownload: Boolean = false,
    preset: String? = null,
    overrides: TextOverrides = TextOverrides(),
) {
    val copyrightDisclaimer = try {
        EsvClient(rawDataDir = rawDataDir).fetchCopyrightDisclaimer(forceDownload = forceDownload)
    } catch (e: Exception) {
        DEFAULT_COPYRIGHT_DISCLAIMER
    }

    val orderedFormats = OutputFormat.all.filter { it in formats }

    if (preset == null && !overrides.anySet()) {
        generateTextPack(studyData, testDate, productsPath, orderedFormats, store, copyrightDisclaimer)
    } else {
        val base = preset?.let { Presets.byName(it) ?: error("unknown preset '$it'") } ?: Presets.tbb
        val config = base.resolve(overrides, testDate)
        generateSingleText(studyData, base, config, productsPath, orderedFormats, store, copyrightDisclaimer)
    }
}

/** The curated pack: (tbb, marks) presets × (plain, unique, full) feature variants × [formats]. */
private fun generateTextPack(
    studyData: StudyData,
    testDate: LocalDate,
    productsPath: Path,
    formats: List<OutputFormat>,
    store: AnnotationStore,
    copyrightDisclaimer: String,
) {
    val packPresets = listOf(Presets.tbb, Presets.marks, Presets.raymond, Presets.daesha)
    for (format in formats) {
        for (base in packPresets) {
            val writer = writerFor(format)
            val baseOptions = base.options.copy(testDate = testDate)

            val variants = listOf(
                baseOptions,
                baseOptions.copy(underlineUniqueWords = true),
                baseOptions.copy(underlineUniqueWords = true, customHighlights = fullHighlightPalette())
            )

            for (options in variants) {
                val doc = BibleAnnotationPipeline.build(studyData, options, store)
                renderGuarded(studyData, doc, options, base, writer, productsPath, copyrightDisclaimer)
            }
        }
    }
}

/** Single mode: one resolved config, one document per requested format. */
private fun generateSingleText(
    studyData: StudyData,
    preset: TextPreset,
    options: TextOptions,
    productsPath: Path,
    formats: List<OutputFormat>,
    store: AnnotationStore,
    copyrightDisclaimer: String,
) {
    val doc = BibleAnnotationPipeline.build(studyData, options, store)
    for (format in formats) {
        val writer = writerFor(format)
        renderGuarded(studyData, doc, options, preset, writer, productsPath, copyrightDisclaimer)
    }
}

/** Builds the writer for [format], resolving the concrete style for [styleId] (LaTeX ignores style). */
private fun writerFor(format: OutputFormat): BibleTextWriter = when (format) {
    Docx -> DocxBibleTextWriter()
    Typst -> TypstBibleTextWriter()
}

/** Renders one document, skipping with a message if the writer can't honor the options. */
private fun renderGuarded(
    studyData: StudyData,
    doc: AnnotatedDoc<AnalysisUnit>,
    options: TextOptions,
    preset: TextPreset,
    writer: BibleTextWriter,
    productsPath: Path,
    copyrightDisclaimer: String,
) {
    try {
        BibleTextPipeline.render(studyData, doc, options, preset, writer, productsPath, copyrightDisclaimer)
    } catch (e: IllegalArgumentException) {
        val outFile: Path = computeOutputPath(studyData, options, preset, writer.format, productsPath)
        println("Skipping $outFile due to: " + e.message)
    }
}
