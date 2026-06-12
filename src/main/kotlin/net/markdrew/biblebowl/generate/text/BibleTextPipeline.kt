package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.generate.text.BibleTextPipeline.computeOutputPath
import net.markdrew.biblebowl.generate.text.docx.DocxBibleTextWriter
import net.markdrew.biblebowl.generate.text.docx.MarksDocxStyle
import net.markdrew.biblebowl.generate.text.docx.TbbDocxStyle
import net.markdrew.biblebowl.generate.text.latex.LatexBibleTextWriter
import net.markdrew.biblebowl.generate.text.typst.MarksTypstStyle
import net.markdrew.biblebowl.generate.text.typst.TbbTypstStyle
import net.markdrew.biblebowl.generate.text.typst.TypstBibleTextWriter
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
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
    ): Path = render(studyData, BibleAnnotationPipeline.build(studyData, features, store), layout, features, writer, productsPath)

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
    ): Path {
        require(writer.supports(layout)) {
            "Writer ${writer::class.simpleName} does not support layout $layout"
        }
        val outputFile = computeOutputPath(studyData, layout, features, writer.format, productsPath)
        writer.write(outputFile, doc, studyData, layout, features)
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
    HighlightColor("other", Triple(0xcc, 0x99, 0xff)) to WordList.OTHER.regexSequence().toSet(),
))

/**
 * Generates the standard pack of Bible-text variants for [studyData] under [productsPath]
 *
 * For each requested format in [formats], produces six outputs: plain, unique-words-underlined, and
 * full-highlighting in both the TBB official style and Mark's two-column style. This is the unified
 * replacement for the legacy `writeBibleDoc(...)` and `writeBibleText(...)` entry points.
 *
 * @param testDate date stamped on the cover/footer
 * @param formats which output formats to generate (default: all of [Docx], [Latex], [Typst])
 */
fun generateBibleTexts(
    studyData: StudyData,
    testDate: LocalDate,
    productsPath: Path,
    formats: Set<OutputFormat> = setOf(Docx, Latex, Typst),
    store: AnnotationStore = AnnotationStore(studyData, cacheDir = null),
) {
    val tbbLayoutPlain = LayoutOptions(testDate = testDate, fontSize = 12)
    val marksLayoutPlain = LayoutOptions(
        testDate = testDate,
        fontSize = 10,
        twoColumns = true,
        useHeadingsForChapters = true,
    )

    val plain = FeatureOptions()
    val unique = FeatureOptions(underlineUniqueWords = true)
    val full = FeatureOptions(
        underlineUniqueWords = true,
        customHighlights = fullHighlightPalette(),
    )

    // The annotated doc depends only on (studyData, features), never on layout/writer, so build each
    // feature variant once and reuse it across every format and layout below.
    val featureVariants = listOf(plain, unique, full)
    val docByFeatures: Map<FeatureOptions, AnnotatedDoc<AnalysisUnit>> =
        featureVariants.associateWith { BibleAnnotationPipeline.build(studyData, it, store) }

    if (Docx in formats) {
        val tbb = DocxBibleTextWriter(TbbDocxStyle)
        val marks = DocxBibleTextWriter(MarksDocxStyle)
        for ((writer, layout) in listOf(tbb to tbbLayoutPlain, marks to marksLayoutPlain)) {
            for (features in featureVariants) {
                BibleTextPipeline.render(studyData, docByFeatures.getValue(features), layout, features, writer, productsPath)
            }
        }
    }

    if (Latex in formats) {
        val tbbLatex = LatexBibleTextWriter()
        val marksLatex = LatexBibleTextWriter()
        for ((writer, layout) in listOf(tbbLatex to tbbLayoutPlain, marksLatex to marksLayoutPlain)) {
            for (features in featureVariants) {
                try {
                    BibleTextPipeline.render(studyData, docByFeatures.getValue(features), layout, features, writer, productsPath)
                } catch (e: IllegalArgumentException) {
                    val outFile: Path = computeOutputPath(studyData, layout, features, writer.format, productsPath)
                    println("Skipping $outFile due to: " + e.message)
                }
            }
        }
    }

    if (Typst in formats) {
        val tbbTypst = TypstBibleTextWriter(TbbTypstStyle)
        val marksTypst = TypstBibleTextWriter(MarksTypstStyle)
        for ((writer, layout) in listOf(tbbTypst to tbbLayoutPlain, marksTypst to marksLayoutPlain)) {
            for (features in featureVariants) {
                BibleTextPipeline.render(studyData, docByFeatures.getValue(features), layout, features, writer, productsPath)
            }
        }
    }
}
