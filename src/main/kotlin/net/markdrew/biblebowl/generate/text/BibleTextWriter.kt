package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.ws.DEFAULT_COPYRIGHT_DISCLAIMER
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.StudyData
import java.nio.file.Path


/**
 * Identifier for an output technology.
 *
 * Drives output-directory naming (`text/<subdir>/`) and source-file extension. Adding a new format is
 * a matter of adding an [OutputFormat] subtype and a matching [BibleTextWriter] implementation.
 */
sealed interface OutputFormat {
    /** Subdirectory under `<productsPath>/<simpleName>/text/` where this format's outputs are written. */
    val subdir: String

    /** Source file extension (without leading dot), e.g. `"docx"`, `"tex"`. */
    val extension: String

    companion object {
        /**
         * Every output format, in CLI/menu order; [subdir] doubles as the format's lowercase token.
         * Keep this in sync when adding a new [OutputFormat] subtype.
         */
        val all: List<OutputFormat> = listOf(Docx, Typst)
    }
}

/** DOCX/Word output. The writer's source file is a `.docx`; PDF is produced by LibreOffice headless. */
data object Docx : OutputFormat {
    override val subdir: String = "docx"
    override val extension: String = "docx"
}

/** Typst output. The writer's source file is a `.typ`; PDF is produced by the `typst compile` CLI. */
data object Typst : OutputFormat {
    override val subdir: String = "typst"
    override val extension: String = "typ"
}

/**
 * Format-specific Bible-text writer.
 *
 * A writer takes a [BibleAnnotationPipeline]-built [AnnotatedDoc] plus the layout/feature options the
 * matrix selected and produces a source file at [outputFile] (typically followed by a downstream PDF
 * conversion the writer handles internally).
 *
 * Capability is declared via [supports]: writers that cannot honor a particular [LayoutOptions]
 * combination (e.g., the LaTeX preamble currently hardcodes two-column layout) return false, and the
 * orchestrator fails at config time rather than silently dropping the option.
 */
interface BibleTextWriter {
    /** Identifies the output technology for path/extension purposes. */
    val format: OutputFormat

    /**
     * True if this writer can honor [options] without silently dropping any option. Default: true.
     * Override to reject layouts the writer cannot render correctly.
     */
    fun supports(options: TextOptions): Boolean = true

    /**
     * Renders [doc] to [outputFile] under the given [options].
     *
     * Implementations may also produce derived artifacts next to [outputFile] (e.g., a PDF compiled
     * from the source file). Parent directories are created as needed.
     */
    fun write(
        outputFile: Path,
        doc: AnnotatedDoc<AnalysisUnit>,
        studyData: StudyData,
        options: TextOptions,
        copyrightDisclaimer: String = DEFAULT_COPYRIGHT_DISCLAIMER,
    )
}
