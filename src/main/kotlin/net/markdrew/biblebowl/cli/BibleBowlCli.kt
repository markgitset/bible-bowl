package net.markdrew.biblebowl.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.RAW_DATA_DIR
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.ws.EsvClient
import net.markdrew.biblebowl.ws.EsvIndexer
import net.markdrew.biblebowl.ws.Passage
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.Path

class BibleBowlCli : CliktCommand(
    name = "biblebowl",
    help = "Generate Bible Bowl resources and indices."
) {
    private val userHomeDir = Path(System.getProperty("user.home"))
    private val defaultTbbPath: Path = userHomeDir.resolve(".tbb")
    private val defaultDataPath: Path = defaultTbbPath.resolve(DATA_DIR)
    private val defaultProductsPath: Path = defaultTbbPath.resolve(PRODUCTS_DIR)
    private val defaultRawDataPath: Path = defaultTbbPath.resolve(RAW_DATA_DIR)

    private val studySetName: String? by argument("STUDY_SET")
        .optional()
        .help("Name of the study set to use (default: ${StandardStudySet.DEFAULT.simpleName})")

    private val forceDownload: Boolean by option("--force-download", "-f")
        .flag(default = false)
        .help("Force download and re-index the study set, even if data exists (default: false)")

    private val dataDir: Path by option("--data-dir", "-d")
        .convert { Path(it) }
        .default(defaultDataPath)
        .help("Directory where Bible bowl data is stored (default: $defaultDataPath)")

    private val rawDataDir: Path by option("--raw-data-dir", "-r")
        .convert { Path(it) }
        .default(defaultRawDataPath)
        .help("Directory where Bible bowl raw data is stored (default: $defaultRawDataPath)")

    private val productsDir: Path by option("--products-dir", "-p")
        .convert { Path(it) }
        .default(defaultProductsPath)
        .help("Directory for output products (default: ${defaultTbbPath.resolve(PRODUCTS_DIR)})")

    override fun run() {
        print(BANNER)
        val studySet: StudySet =
            StandardStudySet.parse(studySetName)

//        val dataPath = Paths.get(dataDir)
        val studyData = try {
            StudyData.readData(studySet, dataDir, forceDownload)
        } catch (e: FileNotFoundException) {
            echo("Data missing: ${e.message}")
            echo("Downloading and indexing the study set for ${studySet.name} (forceDownload=$forceDownload)...")
            val indexer = EsvIndexer(studySet)
            val chapterPassages: Sequence<Passage> =
                EsvClient().bookByChapters(studySet, forceDownload)
            indexer.indexBook(chapterPassages).also {
                it.writeData(dataDir)
            }
        }

//        // Generate files (rest unchanged from BiblebowlKt.main)
//        writeOneTimeWordsIndex(studyData)
//        writeFullIndex(studyData)
//        writeNumbersIndex(studyData)
//        writeNamesIndex(studyData)
//        writeNonLocalPhrasesIndex(studyData)
//        writeHeadingsPdf(studyData)
//
//        writeHeadingsText(studyData)
//        writeHeadingsCsv(studyData)
//
//        writeCramVerses(studyData)
//        writeCramHeadings(studyData)
//        writeCramReverseHeadings(studyData)
//        writeCramOneTimeWords(studyData, oneTimeWords(studyData))
//        val nameExcerpts = findNames(studyData, "god", "jesus", "christ")
//        writeCramNameBlanks(studyData, nameExcerpts)
//        writeCramFewTimeWords(studyData)
//
//        val content = PracticeContent(studyData)
//        writeFindTheVerse(PracticeTest(Round.FIND_THE_VERSE, content, randomSeed = 50))
//        writeRound5Events(PracticeTest(Round.EVENTS, content, randomSeed = 50))
//        writeRound4Quotes(PracticeTest(Round.QUOTES, content, randomSeed = 50))
        echo("Generation complete. Output in $productsDir")
    }
}

fun main(args: Array<String>) = BibleBowlCli().main(args)