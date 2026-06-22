package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.generate.text.BibleTextPipeline.computeOutputPath
import net.markdrew.biblebowl.generate.text.docx.DocxBibleTextWriter
import net.markdrew.biblebowl.generate.text.latex.LatexBibleTextWriter
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
     * Generates one Bible-text output for the given study data, options, and writer.
     *
     * Builds the [AnnotatedDoc] (reusing [store]'s cached layers) then renders it. Callers generating
     * several variants of the same study set should build the doc once per [FeatureOptions] and call
     * [render] directly instead — see [generateBibleTexts].
     *
     * @return the path of the source file that was written (the writer may also produce a sibling PDF)
     */
    fun generate(
        studyData: StudyData,
        layout: LayoutOptions,
        features: FeatureOptions,
        writer: BibleTextWriter,
        productsPath: Path,
        store: AnnotationStore = AnnotationStore(studyData, cacheDir = null),
        copyrightDisclaimer: String = DEFAULT_COPYRIGHT_DISCLAIMER,
    ): Path = render(studyData, BibleAnnotationPipeline.build(studyData, features, store), layout, features, writer, productsPath, copyrightDisclaimer)
 
    /**
     * Renders an already-built [doc] for the given [layout]/[features] via [writer].
     *
     * Separated from doc construction so one doc can be reused across multiple writers and layouts.
     *
     * @return the path of the source file that was written (the writer may also produce a sibling PDF)
     */
    fun render(
        studyData: StudyData,
        doc: AnnotatedDoc<AnalysisUnit>,
        layout: LayoutOptions,
        features: FeatureOptions,
        writer: BibleTextWriter,
        productsPath: Path,
        copyrightDisclaimer: String = DEFAULT_COPYRIGHT_DISCLAIMER,
    ): Path {
        require(writer.supports(layout)) {
            "Writer ${writer::class.simpleName} does not support layout $layout"
        }
        require(!features.verseOnNewLine || writer.format == Typst) {
            "${writer.format.subdir} does not support the verse-per-line option (only typst does)"
        }
        val outputFile = computeOutputPath(studyData, layout, features, writer.format, productsPath)
        writer.write(outputFile, doc, studyData, layout, features, copyrightDisclaimer)
        return outputFile
    }

    /**
     * Computes `<productsPath>/<simpleName>/text/<format.subdir>/<simpleName>-bible-text-<suffix>.<ext>`.
     * Suffix encodes which feature flags are active and the body font size.
     */
    fun computeOutputPath(
        studyData: StudyData,
        layout: LayoutOptions,
        features: FeatureOptions,
        format: OutputFormat,
        productsPath: Path,
    ): Path {
        val name = studyData.studySet.simpleName
        return productsPath.resolve(
            name, "text", format.subdir,
            "$name-bible-text-${fileNameSuffix(layout, features)}.${format.extension}"
        )
    }

    /** Filename suffix derived from feature toggles + font size, used to disambiguate variant outputs. */
    fun fileNameSuffix(layout: LayoutOptions, features: FeatureOptions): String =
        (if (features.customHighlights.entries.isNotEmpty()) "highlight-" else "") +
            (if (features.underlineUniqueWords) "unique-" else "") +
            (if (features.verseOnNewLine) "verse-line-" else "") +
            (if (layout.chapterBreaksPage) "breaks-" else "") +
            "${layout.fontSize}pt"
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
 * [ResolvedTextConfig] (base preset, default [Presets.tbb], with overrides applied) and emits one
 * document per requested format.
 *
 * Either way, formats that cannot honor the requested layout/features are skipped with a message rather
 * than aborting the run (see [renderGuarded]).
 *
 * @param testDate date stamped on the cover/footer
 * @param formats which output formats to generate (default: all of [Docx], [Latex], [Typst])
 * @param preset name of the base preset to resolve in single mode; null selects pack mode (unless
 *   [overrides] forces single mode)
 * @param overrides field-level overrides applied on top of the base preset in single mode
 */
fun generateBibleTexts(
    studyData: StudyData,
    testDate: LocalDate,
    productsPath: Path,
    formats: Set<OutputFormat> = setOf(Docx, Latex, Typst),
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
        generateSingleText(studyData, config, productsPath, orderedFormats, store, copyrightDisclaimer)
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
    val featureVariants = listOf(
        FeatureOptions(),
        FeatureOptions(underlineUniqueWords = true),
        FeatureOptions(underlineUniqueWords = true, customHighlights = fullHighlightPalette()),
    )
    // The annotated doc depends only on (studyData, features), never on layout/writer, so build each
    // feature variant once and reuse it across every format and preset below.
    val docByFeatures: Map<FeatureOptions, AnnotatedDoc<AnalysisUnit>> =
        featureVariants.associateWith { BibleAnnotationPipeline.build(studyData, it, store) }

    val packPresets = listOf(Presets.tbb, Presets.marks)
    for (format in formats) {
        for (base in packPresets) {
            val writer = writerFor(format, base.style)
            val layout = base.layout.copy(testDate = testDate)
            for (features in featureVariants) {
                renderGuarded(studyData, docByFeatures.getValue(features), layout, features, writer, productsPath, copyrightDisclaimer)
            }
        }
    }
}

/** Single mode: one resolved config, one document per requested format. */
private fun generateSingleText(
    studyData: StudyData,
    config: ResolvedTextConfig,
    productsPath: Path,
    formats: List<OutputFormat>,
    store: AnnotationStore,
    copyrightDisclaimer: String,
) {
    val doc = BibleAnnotationPipeline.build(studyData, config.features, store)
    for (format in formats) {
        val writer = writerFor(format, config.style)
        renderGuarded(studyData, doc, config.layout, config.features, writer, productsPath, copyrightDisclaimer)
    }
}

/** Builds the writer for [format], resolving the concrete style for [styleId] (LaTeX ignores style). */
private fun writerFor(format: OutputFormat, styleId: StyleId): BibleTextWriter = when (format) {
    Docx -> DocxBibleTextWriter(styleId.docx())
    Latex -> LatexBibleTextWriter()
    Typst -> TypstBibleTextWriter(styleId.typst())
}

/** Renders one document, skipping with a message if the writer can't honor the layout/features. */
private fun renderGuarded(
    studyData: StudyData,
    doc: AnnotatedDoc<AnalysisUnit>,
    layout: LayoutOptions,
    features: FeatureOptions,
    writer: BibleTextWriter,
    productsPath: Path,
    copyrightDisclaimer: String,
) {
    try {
        BibleTextPipeline.render(studyData, doc, layout, features, writer, productsPath, copyrightDisclaimer)
    } catch (e: IllegalArgumentException) {
        val outFile: Path = computeOutputPath(studyData, layout, features, writer.format, productsPath)
        println("Skipping $outFile due to: " + e.message)
    }
}
