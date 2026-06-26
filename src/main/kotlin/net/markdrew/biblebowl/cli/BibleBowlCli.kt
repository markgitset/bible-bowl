package net.markdrew.biblebowl.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.multiple as multipleOption
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.options.switch
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.generate.text.OutputFormat
import net.markdrew.biblebowl.generate.text.Presets
import net.markdrew.biblebowl.generate.text.StyleId
import net.markdrew.biblebowl.generate.text.TextOverrides
import net.markdrew.biblebowl.generate.text.Typst
import net.markdrew.biblebowl.generate.text.Docx
import net.markdrew.biblebowl.generate.text.Latex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.validate.AnnotationValidator
import net.markdrew.biblebowl.validate.ValidationTui
import net.markdrew.biblebowl.ws.EsvClient
import net.markdrew.biblebowl.ws.EsvIndexer
import net.markdrew.biblebowl.ws.Passage
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.filter.ThresholdFilter
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.Path
import kotlin.time.TimeSource

/**
 * Root command. Holds no options of its own — it just groups the per-task subcommands so each exposes
 * only the options relevant to it (text generation, annotation, indices, flashcards, practice, or the
 * whole set with defaults).
 */
class BibleBowlCli : CliktCommand(name = "biblebowl") {
    override fun help(context: Context): String = "Generate Texas Bible Bowl resources."
    override fun run() = Unit
}

/**
 * Shared base for every subcommand: the study-set argument plus the I/O directories, source dir, and
 * global flags they all need, along with the data-loading helpers. Subclasses implement [execute];
 * [run] wraps it with the banner and console-logging setup.
 */
class GlobalOptions : OptionGroup(name = "Global Options") {
    val studySetName: String? by option("--study-set", "-s")
        .help("Study set to use. If not specified, it is read from 'default-study-set' in bible-bowl.properties, falling back to 'acts' (default: ${CliConfig.defaultStudySetName})")

    val forceDownload: Boolean by option("--force-download", "-F")
        .flag(default = false)
        .help("Force download and re-index the study set, even if data exists (default: false)")

    val verbose: Boolean by option("--verbose", "-v")
        .flag(default = false)
        .help("Enable verbose console logging (default: false)")

    val sourceDir: Path by option("--source-dir")
        .convert { Path(it) }
        .default(Path("src/main/resources"))
        .help("Resources root for word-lists/overrides: edited by `annotate` and read by generation, so edits show up without rebuilding. Falls back to bundled resources if absent (default: src/main/resources)")

    val dataDir: Path by option("--data-dir")
        .convert { Path(it) }
        .default(defaultDataPath)
        .help("Directory where Bible bowl data is stored (default: $defaultDataPath)")

    val rawDataDir: Path by option("--raw-data-dir")
        .convert { Path(it) }
        .default(defaultRawDataPath)
        .help("Directory where Bible bowl raw data is stored (default: $defaultRawDataPath)")

    val productsDir: Path by option("--products-dir", "-p")
        .convert { Path(it) }
        .default(defaultProductsPath)
        .help("Directory for output products (default: $defaultProductsPath)")

    val testDate: LocalDate by option("--test-date", envvar = "TEST_DATE")
        .convert { LocalDate.parse(it) }
        .default(CliConfig.defaultTestDate)
        .help("Date stamped on the cover/footer (format: YYYY-MM-DD). If not specified, it is read from the TEST_DATE environment variable, falling back to 'test-date' in bible-bowl.properties (default: ${CliConfig.defaultTestDate})")
}

abstract class BaseCommand(name: String, private val helpText: String) : CliktCommand(name = name) {

    override fun help(context: Context): String = helpText

    protected val globalOptions by GlobalOptions()

    protected val studySetName: String? get() = globalOptions.studySetName
    protected val forceDownload: Boolean get() = globalOptions.forceDownload
    protected val verbose: Boolean get() = globalOptions.verbose
    protected val sourceDir: Path get() = globalOptions.sourceDir
    protected val dataDir: Path get() = globalOptions.dataDir
    protected val rawDataDir: Path get() = globalOptions.rawDataDir
    protected val productsDir: Path get() = globalOptions.productsDir
    protected val testDate: LocalDate get() = globalOptions.testDate

    final override fun run() {
        print(BANNER)
        configureConsoleLogging(verbose)
        execute()
    }

    /** Subcommand-specific behavior, invoked after banner + logging setup. */
    protected abstract fun execute()

    protected fun resolveStudySet(): StudySet = studySetName?.let { name ->
        StandardStudySet.parseOrNull(name) ?: throw UsageError("unrecognized study set: $name")
    } ?: (StandardStudySet.parseOrNull(CliConfig.defaultStudySetName) ?: StandardStudySet.DEFAULT)

    protected fun loadStudyData(studySet: StudySet): StudyData = try {
        StudyData.readData(studySet, dataDir, rawDataDir, forceDownload)
    } catch (e: NoSuchFileException) {
        echo("Data missing: ${e.message}")
        echo("Downloading and indexing the study set for ${studySet.name} (forceDownload=$forceDownload)...")
        val indexer = EsvIndexer(studySet)
        val chapterPassages: Sequence<Passage> = EsvClient(rawDataDir).bookByChapters(studySet, forceDownload)
        indexer.indexBook(chapterPassages).also { it.writeData(dataDir) }
    }

    private fun configureConsoleLogging(verbose: Boolean) {
        val root = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as? Logger ?: return
        val consoleAppender = root.getAppender("STDERR") ?: return
        consoleAppender.clearAllFilters()
        val filter = ThresholdFilter()
        filter.setLevel(if (verbose) "INFO" else "WARN")
        filter.start()
        consoleAppender.addFilter(filter)
    }
}

/**
 * Base for subcommands that write products: adds `--products-dir` and the [runResources] helper that
 * loads the study data + annotation store once and runs the selected resources.
 */
abstract class GeneratingCommand(name: String, helpText: String) : BaseCommand(name, helpText) {

    protected fun runResources(resources: List<StudyResource>) {
        if (resources.isEmpty()) {
            echo("Nothing selected; nothing to generate.")
            return
        }
        val started = TimeSource.Monotonic.markNow()
        val studySet = resolveStudySet()
        val studyData = loadStudyData(studySet)
        val store = AnnotationStore(
            studyData,
            cacheDir = dataDir.resolve(studySet.simpleName),
            sourceDir = sourceDir,
        )
        for (resource in resources) {
            echo("Generating ${resource.label} (${resource.slug})...")
            resource.generate(studyData, store, productsDir)
        }
        echo("Generation complete. Output in $productsDir (took ${started.elapsedNow()})")
    }
}

/**
 * Base for the category subcommands (indices, flashcards, practice): a positional list of resource
 * slugs (default: all in the category) plus `--list` for discovery.
 */
abstract class SelectingCommand(
    name: String,
    helpText: String,
    private val category: ResourceCategory,
) : GeneratingCommand(name, helpText) {

    open val practiceVariants: Int get() = 5

    private val which: List<String> by argument("RESOURCE")
        .multiple()
        .help("Which ${category.name.lowercase()} resources to generate (default: all). See --list.")

    private val list: Boolean by option("--list")
        .flag(default = false)
        .help("List the available ${category.name.lowercase()} resources, then exit")

    final override fun execute() {
        val registry = studyResources(
            rawDataDir = rawDataDir,
            forceDownload = forceDownload,
            practiceVariants = practiceVariants,
        )
            .filter { it.category == category }
        if (list) {
            echo("Available ${category.name.lowercase()} resources:")
            echo(formatResourceList(registry))
            return
        }
        val selected = try {
            resolveSelection(which, registry)
        } catch (e: IllegalArgumentException) {
            throw UsageError(e.message ?: "invalid resource selection")
        }
        runResources(selected)
    }
}

/** `text` — parent command namespace for text formats. */
class TextCommand : CliktCommand(name = "text") {
    override fun help(context: Context): String = "Generate the Bible text"
    override fun run() = Unit
}

abstract class TextCommandBase(name: String, private val helpText: String, protected val format: OutputFormat) : GeneratingCommand(name, helpText) {

    protected val preset: String? by option("--preset")
        .choice(*Presets.all.map { it.name }.toTypedArray(), ignoreCase = true)
        .help("Base text preset to start from: ${Presets.all.joinToString { it.name }} " +
            "(omit for the full variant pack)")

    protected val listPresets: Boolean by option("--list-presets")
        .flag(default = false)
        .help("List all available text presets and their configurations, then exit")

    protected abstract fun buildOverrides(): TextOverrides

    final override fun execute() {
        if (listPresets) {
            echo("Available text presets:")
            for (p in Presets.all) {
                echo("- ${p.name}:")
                echo("    Style family:            ${p.style.token}")
                echo("    Font size:               ${p.layout.fontSize}pt")
                echo("    Columns:                 ${if (p.layout.twoColumns) "2" else "1"}")
                echo("    Chapter titles:          ${if (p.layout.useHeadingsForChapters) "headings" else "inline"}")
                echo("    Page break chapters:     ${p.layout.chapterBreaksPage}")
                echo("    Underline unique words:  ${p.features.underlineUniqueWords}")
                echo("    Verse per line:          ${p.features.verseOnNewLine}")
                echo("    Chapter end lines:       ${p.layout.chapterEndLines}")
                p.layout.mainFont?.let { echo("    Main font:               $it") }
                p.layout.verseNumFont?.let { echo("    Verse num font:          $it") }
                p.layout.headingFont?.let { echo("    Heading font:            $it") }
                p.layout.chapterFontSize?.let { echo("    Chapter font size:       ${it}pt") }
                p.layout.headingFontSize?.let { echo("    Heading font size:       ${it}pt") }
                p.layout.footnoteFontSize?.let { echo("    Footnote font size:      ${it}pt") }
                p.layout.justified?.let { echo("    Justified:               $it") }
            }
            return
        }
        if (format == Docx || format == Latex) {
            echo("WARNING: The ${format.subdir} output format is deprecated and will be removed in a future version.")
        }
        val overrides = buildOverrides()
        val registry = studyResources(setOf(format), testDate, rawDataDir, forceDownload, preset, overrides)
        runResources(registry.filter { it.category == ResourceCategory.TEXT })
    }
}

class TypstTextCommand : TextCommandBase("typst", "Generate the Bible text in Typst format", Typst) {

    private val mainFontOverride: String? by option("--main-font")
        .help("Override main body text font family")

    private val headingFontOverride: String? by option("--heading-font")
        .help("Override chapter and section headings font family")

    private val verseNumFontOverride: String? by option("--verse-num-font")
        .help("Override verse numbers font family")

    private val chapterFontSizeOverride: Int? by option("--chapter-font-size").int()
        .help("Override chapter heading font size in points")

    private val headingFontSizeOverride: Int? by option("--heading-font-size").int()
        .help("Override section heading font size in points")

    private val footnoteFontSizeOverride: Int? by option("--footnote-font-size").int()
        .help("Override footnote font size in points")

    private val justifiedOverride: Boolean? by option().switch(
        "--justified" to true, "--no-justified" to false,
    ).help("Override body text justification")

    private val fontSizeOverride: Int? by option("--font-size").int()
        .help("Override body font size in points")

    private val twoColumnsOverride: Boolean? by option().switch(
        "--two-columns" to true, "--no-two-columns" to false,
    ).help("Override two-column layout")

    private val inlineChapterLabelsOverride: Boolean? by option().switch(
        "--inline-chapter-labels" to true, "--no-inline-chapter-labels" to false,
    ).help("Override inline chapter labels vs. heading-style chapter titles")

    private val pageBreakChaptersOverride: Boolean? by option().switch(
        "--page-break-chapters" to true, "--no-page-break-chapters" to false,
    ).help("Override forcing a page break between chapters")

    private val underlineUniqueOverride: Boolean? by option().switch(
        "--underline-unique" to true, "--no-underline-unique" to false,
    ).help("Override underlining of one-time words")

    private val highlightOverride: Boolean? by option().switch(
        "--highlight" to true, "--no-highlight" to false,
    ).help("Override the full category highlight palette")

    private val versePerLineOverride: Boolean? by option().switch(
        "--verse-per-line" to true, "--no-verse-per-line" to false,
    ).help("Override starting each verse on a new line")

    private val chapterEndLinesOverride: Boolean? by option().switch(
        "--chapter-end-lines" to true, "--no-chapter-end-lines" to false,
    ).help("Center the chapter label and draw bold, black horizontal lines extending across the column with a break in the middle")

    override fun buildOverrides(): TextOverrides {
        val resolvedInlineChapters = when {
            inlineChapterLabelsOverride != null -> inlineChapterLabelsOverride
            chapterEndLinesOverride == true -> false
            else -> null
        }
        val resolvedChapterEndLines = when {
            chapterEndLinesOverride != null -> chapterEndLinesOverride
            inlineChapterLabelsOverride == true -> false
            else -> null
        }
        return TextOverrides(
            fontSize = fontSizeOverride,
            twoColumns = twoColumnsOverride,
            chapterBreaksPage = pageBreakChaptersOverride,
            useHeadingsForChapters = resolvedInlineChapters?.let { !it },
            underlineUniqueWords = underlineUniqueOverride,
            highlight = highlightOverride,
            verseOnNewLine = versePerLineOverride,
            chapterEndLines = resolvedChapterEndLines,
            mainFont = mainFontOverride,
            verseNumFont = verseNumFontOverride,
            headingFont = headingFontOverride,
            chapterFontSize = chapterFontSizeOverride,
            headingFontSize = headingFontSizeOverride,
            footnoteFontSize = footnoteFontSizeOverride,
            justified = justifiedOverride,
        )
    }
}

class DocxTextCommand : TextCommandBase("docx", "Generate the Bible text in DOCX format (DEPRECATED)", Docx) {

    private val mainFontOverride: String? by option("--main-font")
        .help("Override main body text font family (restricted to Times New Roman, Quattrocento Sans, Liberation Mono, Liberation Sans)")

    private val headingFontOverride: String? by option("--heading-font")
        .help("Override chapter and section headings font family")

    private val verseNumFontOverride: String? by option("--verse-num-font")
        .help("Override verse numbers font family")

    private val chapterFontSizeOverride: Int? by option("--chapter-font-size").int()
        .help("Override chapter heading font size in points")

    private val headingFontSizeOverride: Int? by option("--heading-font-size").int()
        .help("Override section heading font size in points")

    private val footnoteFontSizeOverride: Int? by option("--footnote-font-size").int()
        .help("Override footnote font size in points")

    private val justifiedOverride: Boolean? by option().switch(
        "--justified" to true, "--no-justified" to false,
    ).help("Override body text justification")

    private val fontSizeOverride: Int? by option("--font-size").int()
        .help("Override body font size in points")

    private val twoColumnsOverride: Boolean? by option().switch(
        "--two-columns" to true, "--no-two-columns" to false,
    ).help("Override two-column layout")

    private val inlineChapterLabelsOverride: Boolean? by option().switch(
        "--inline-chapter-labels" to true, "--no-inline-chapter-labels" to false,
    ).help("Override inline chapter labels vs. heading-style chapter titles")

    private val underlineUniqueOverride: Boolean? by option().switch(
        "--underline-unique" to true, "--no-underline-unique" to false,
    ).help("Override underlining of one-time words")

    private val highlightOverride: Boolean? by option().switch(
        "--highlight" to true, "--no-highlight" to false,
    ).help("Override the full category highlight palette")

    override fun buildOverrides(): TextOverrides {
        return TextOverrides(
            fontSize = fontSizeOverride,
            twoColumns = twoColumnsOverride,
            useHeadingsForChapters = inlineChapterLabelsOverride?.let { !it },
            underlineUniqueWords = underlineUniqueOverride,
            highlight = highlightOverride,
            mainFont = mainFontOverride,
            verseNumFont = verseNumFontOverride,
            headingFont = headingFontOverride,
            chapterFontSize = chapterFontSizeOverride,
            headingFontSize = headingFontSizeOverride,
            footnoteFontSize = footnoteFontSizeOverride,
            justified = justifiedOverride,
        )
    }
}

class LatexTextCommand : TextCommandBase("latex", "Generate the Bible text in LaTeX format (DEPRECATED)", Latex) {

    private val fontSizeOverride: Int? by option("--font-size").int()
        .help("Override body font size in points")

    private val pageBreakChaptersOverride: Boolean? by option().switch(
        "--page-break-chapters" to true, "--no-page-break-chapters" to false,
    ).help("Override forcing a page break between chapters")

    private val underlineUniqueOverride: Boolean? by option().switch(
        "--underline-unique" to true, "--no-underline-unique" to false,
    ).help("Override underlining of one-time words")

    private val highlightOverride: Boolean? by option().switch(
        "--highlight" to true, "--no-highlight" to false,
    ).help("Override the full category highlight palette")

    override fun buildOverrides(): TextOverrides {
        return TextOverrides(
            fontSize = fontSizeOverride,
            twoColumns = true, // LaTeX requires twoColumns = true
            chapterBreaksPage = pageBreakChaptersOverride,
            underlineUniqueWords = underlineUniqueOverride,
            highlight = highlightOverride,
        )
    }
}

/** `annotate` — launch the interactive annotation validator (formerly `--validate`). */
class AnnotateCommand : BaseCommand("annotate", "Interactively review/correct study-set annotations") {

    private val categories: List<String> by option("--categories", "-c")
        .split(",")
        .default(emptyList())
        .help("Limit to these category tokens (e.g. men,places); default: all")

    override fun execute() {
        if (!terminal.terminalInfo.inputInteractive) {
            throw UsageError("annotate requires an interactive terminal")
        }
        val selectedCategories: Set<WordList> =
            if (categories.isEmpty()) WordList.entries.toSet()
            else categories.map { token ->
                WordList.byToken(token) ?: throw UsageError(
                    "unknown category '$token'; choose from ${WordList.entries.joinToString { it.token }}"
                )
            }.toSet()
        val studyData = loadStudyData(resolveStudySet())
        AnnotationValidator(studyData, selectedCategories, sourceDir).let { ValidationTui(it).run() }
    }
}

/** `indices` — word/name/number/heading indices. */
class IndicesCommand : SelectingCommand("indices", "Generate index resources", ResourceCategory.INDICES)

/** `flashcards` — Cram/Typst flashcard decks. */
class FlashcardsCommand : SelectingCommand("flashcards", "Generate flashcard decks", ResourceCategory.FLASHCARDS)

/** `practice` — practice round tests. */
class PracticeCommand : SelectingCommand("practice", "Generate practice tests", ResourceCategory.PRACTICE) {
    private val variants: Int by option("--variants")
        .int()
        .default(5)
        .help("Number of variants of each test to generate (default: 5)")

    override val practiceVariants: Int get() = variants
}

/** `all` — generate every resource with default settings. */
class AllCommand : GeneratingCommand("all", "Generate every resource with default settings") {
    override fun execute() {
        runResources(studyResources(rawDataDir = rawDataDir, forceDownload = forceDownload))
    }
}

fun main(args: Array<String>) = BibleBowlCli()
    .subcommands(
        TextCommand().subcommands(
            TypstTextCommand(),
            DocxTextCommand(),
            LatexTextCommand(),
        ),
        AnnotateCommand(),
        IndicesCommand(),
        FlashcardsCommand(),
        PracticeCommand(),
        AllCommand(),
    )
    .main(args)
