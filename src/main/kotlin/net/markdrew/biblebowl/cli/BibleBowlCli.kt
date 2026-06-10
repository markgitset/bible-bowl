package net.markdrew.biblebowl.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.mordant.input.interactiveMultiSelectList
import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.validate.AnnotationValidator
import net.markdrew.biblebowl.validate.ValidationTui
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.generate.text.OutputFormat
import net.markdrew.biblebowl.generate.text.Typst
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.ws.EsvClient
import net.markdrew.biblebowl.ws.EsvIndexer
import net.markdrew.biblebowl.ws.Passage
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.Path
import kotlin.time.TimeSource

/**
 * Clikt command that drives the full BibleBowl generation pipeline
 *
 * Loads (or downloads + indexes) a [StudyData] for the chosen
 * [StudySet] and runs every generator family — text variants, indices,
 * flashcards, practice tests — under the supplied output directories.
 */
class BibleBowlCli : CliktCommand(name = "biblebowl") {

    private val defaultTextFormats: Set<OutputFormat> = setOf(Typst)

    // clikt 5 removed the `help`/`epilog` constructor params; help text now comes from this override.
    // (We override commandHelp, not help, so the member name doesn't collide with the imported
    // `.help()` option/argument extensions.)
    override fun help(context: Context): String = "Generate Texas Bible Bowl resources."

//    private val userHomeDir = Path(System.getProperty("user.home"))
//    private val defaultTbbPath: Path = userHomeDir.resolve(".tbb")
//    private val defaultDataPath: Path = defaultTbbPath.resolve(DATA_DIR_NAME)
//    private val defaultProductsPath: Path = defaultTbbPath.resolve(PRODUCTS_DIR_NAME)
//    private val defaultRawDataPath: Path = defaultTbbPath.resolve(RAW_DATA_DIR_NAME)

    private val studySetName: String? by argument("STUDY_SET")
        .optional()
        .help("Name of the study set to use (default: ${StandardStudySet.DEFAULT.simpleName})")

    private val forceDownload: Boolean by option("--force-download", "-F")
        .flag(default = false)
        .help("Force download and re-index the study set, even if data exists (default: false)")

    private val recomputeAnnotations: Boolean by option("--recompute-annotations")
        .flag(default = false)
        .help("Recompute and overwrite cached name/number/highlight annotations, ignoring any cache " +
            "(default: false)")

    private val validate: Boolean by option("--validate")
        .flag(default = false)
        .help("Launch the interactive annotation validator for the study set instead of generating")

    private val validateCategories: List<String> by option("--categories", "-c")
        .split(",")
        .default(emptyList())
        .help("With --validate, limit to these category tokens (e.g. men,places); default: all")

    private val sourceDir: Path by option("--source-dir")
        .convert { Path(it) }
        .default(Path("src/main/resources"))
        .help("With --validate, the resources root whose word-lists/overrides are edited " +
            "(default: src/main/resources)")

    private val resources: List<String> by option("--resource", "-r")
        .multiple()
        .help("Resource(s) to generate; repeatable. Each value is a category or a resource slug " +
            "(see --list). Default: generate everything.")

    private val interactive: Boolean by option("--interactive", "-i")
        .flag(default = false)
        .help("Interactively pick which resources to generate from a checklist (default: false)")

    private val listResources: Boolean by option("--list")
        .flag(default = false)
        .help("List the available resources and categories, then exit (default: false)")

    private val textFormats: List<OutputFormat> by option("--format", "-f")
        .choice(OutputFormat.all.associateBy { it.subdir }, ignoreCase = true)
        .multiple()
        .help("Text format(s) to generate; repeatable. One of: ${OutputFormat.all.joinToString { it.subdir }} " +
            "(default: ${defaultTextFormats.joinToString { it.subdir }})")

    private val dataDir: Path by option("--data-dir")
        .convert { Path(it) }
        .default(defaultDataPath)
        .help("Directory where Bible bowl data is stored (default: $defaultDataPath)")

    private val rawDataDir: Path by option("--raw-data-dir")
        .convert { Path(it) }
        .default(defaultRawDataPath)
        .help("Directory where Bible bowl raw data is stored (default: $defaultRawDataPath)")

    private val productsDir: Path by option("--products-dir", "-p")
        .convert { Path(it) }
        .default(defaultProductsPath)
        .help("Directory for output products (default: $defaultProductsPath)")

    override fun run() {
        val started = TimeSource.Monotonic.markNow()
        print(BANNER)

        if (validate) {
            runValidator()
            return
        }

        val formats: Set<OutputFormat> =
            if (textFormats.isEmpty()) defaultTextFormats
            else textFormats.toSet()
        val registry: List<StudyResource> = studyResources(formats, TEST_DATE)

        if (listResources) {
            printResourceList(registry)
            return
        }

        // Resolve the selection BEFORE loading data, so a bad -R token or a canceled picker fails
        // fast without paying the download/index cost.
        val selected: List<StudyResource> = when {
            interactive -> selectInteractively(registry)
            else -> try {
                resolveSelection(resources, registry)
            } catch (e: IllegalArgumentException) {
                throw UsageError(e.message ?: "invalid --resource selection")
            }
        }
        if (selected.isEmpty()) {
            echo("Nothing selected; nothing to generate.")
            return
        }

        val studySet: StudySet = resolveStudySet()
        val studyData = loadStudyData(studySet)

        val annotationStore = AnnotationStore(
            studyData,
            cacheDir = dataDir.resolve(studySet.simpleName),
            forceRecompute = recomputeAnnotations,
        )

        for (resource in selected) {
            echo("Generating ${resource.label} (${resource.slug})...")
            resource.generate(studyData, annotationStore, productsDir)
        }

        echo("Generation complete. Output in $productsDir (took ${started.elapsedNow()})")
    }

    private fun resolveStudySet(): StudySet = studySetName?.let { name ->
        StandardStudySet.parseOrNull(name) ?: throw UsageError("unrecognized study set: $name")
    } ?: StandardStudySet.DEFAULT

    private fun loadStudyData(studySet: StudySet): StudyData = try {
        StudyData.readData(studySet, dataDir, rawDataDir, forceDownload)
    } catch (e: NoSuchFileException) {
        echo("Data missing: ${e.message}")
        echo("Downloading and indexing the study set for ${studySet.name} (forceDownload=$forceDownload)...")
        val indexer = EsvIndexer(studySet)
        val chapterPassages: Sequence<Passage> = EsvClient(rawDataDir).bookByChapters(studySet, forceDownload)
        indexer.indexBook(chapterPassages).also { it.writeData(dataDir) }
    }

    /** Loads the study set and launches the interactive annotation validator. */
    private fun runValidator() {
        if (!terminal.terminalInfo.inputInteractive) {
            throw UsageError("--validate requires an interactive terminal")
        }
        val categories: Set<WordList> =
            if (validateCategories.isEmpty()) WordList.entries.toSet()
            else validateCategories.map { token ->
                WordList.byToken(token) ?: throw UsageError(
                    "unknown category '$token'; choose from ${WordList.entries.joinToString { it.token }}"
                )
            }.toSet()
        val studyData = loadStudyData(resolveStudySet())
        AnnotationValidator(studyData, categories, sourceDir).let { ValidationTui(it).run() }
    }

    /** Prints the available resources grouped by category, used by `--list`. */
    private fun printResourceList(registry: List<StudyResource>) {
        echo("Available resources (use -R <category|slug>, repeatable; default generates all):")
        for (category in ResourceCategory.entries) {
            val inCategory = registry.filter { it.category == category }
            if (inCategory.isEmpty()) continue
            echo("\n  ${category.name.lowercase()}")
            for (resource in inCategory) {
                echo("    ${resource.slug.padEnd(24)} ${resource.label}")
            }
        }
    }

    /** Presents a mordant checklist of all resources and returns those the user selected. */
    private fun selectInteractively(registry: List<StudyResource>): List<StudyResource> {
        if (!terminal.terminalInfo.inputInteractive) {
            throw UsageError("--interactive requires an interactive terminal; use -R to select resources instead.")
        }
        // Returns null if the user aborts (e.g. Ctrl-C); treat that as an empty selection.
        val chosenSlugs: Set<String> = terminal.interactiveMultiSelectList {
            title("Select resources to generate (↑/↓ move, space toggles, enter confirms)")
            for (resource in registry) {
                addEntry(resource.slug, "${resource.category.name.lowercase()} — ${resource.label}", false)
            }
        }.orEmpty().toSet()
        return registry.filter { it.slug in chosenSlugs }
    }

    private companion object {
        // Date stamped on generated covers/footers.
        private val TEST_DATE: LocalDate = LocalDate.of(2026, 3, 28)
    }
}

fun main(args: Array<String>) = BibleBowlCli().main(args)