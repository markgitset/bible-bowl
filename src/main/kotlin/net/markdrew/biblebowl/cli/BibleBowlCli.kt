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
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.flashcards.cram.writeCramHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramOneTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramReverseHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramVerses
import net.markdrew.biblebowl.generate.indices.writeFullIndex
import net.markdrew.biblebowl.generate.indices.writeHeadingsPdf
import net.markdrew.biblebowl.generate.indices.writeHeadingsText
import net.markdrew.biblebowl.generate.indices.writeNamesIndex
import net.markdrew.biblebowl.generate.indices.writeNonLocalPhrasesIndex
import net.markdrew.biblebowl.generate.indices.writeNumbersIndex
import net.markdrew.biblebowl.generate.indices.writeOneTimeWordsHomework
import net.markdrew.biblebowl.generate.indices.writeOneTimeWordsIndex
import net.markdrew.biblebowl.generate.indices.writeWordListIndex
import net.markdrew.biblebowl.generate.practice.Round
import net.markdrew.biblebowl.generate.practice.practiceTest
import net.markdrew.biblebowl.generate.practice.writeFullSet
import net.markdrew.biblebowl.generate.practice.writeRound1VerseFind
import net.markdrew.biblebowl.generate.practice.writeRound4Quotes
import net.markdrew.biblebowl.generate.practice.writeRound5Events
import net.markdrew.biblebowl.generate.text.generateBibleTexts
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.typst.writeTypstFlashCards
import net.markdrew.biblebowl.ws.EsvClient
import net.markdrew.biblebowl.ws.EsvIndexer
import net.markdrew.biblebowl.ws.Passage
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.Path

/**
 * Clikt command that drives the full BibleBowl generation pipeline
 *
 * Loads (or downloads + indexes) a [net.markdrew.biblebowl.model.StudyData] for the chosen
 * [net.markdrew.biblebowl.model.StudySet] and runs every generator family — text variants, indices,
 * flashcards, practice tests — under the supplied output directories.
 */
class BibleBowlCli : CliktCommand(
    name = "biblebowl",
    help = "Generate Bible Bowl resources and indices."
) {
//    private val userHomeDir = Path(System.getProperty("user.home"))
//    private val defaultTbbPath: Path = userHomeDir.resolve(".tbb")
//    private val defaultDataPath: Path = defaultTbbPath.resolve(DATA_DIR_NAME)
//    private val defaultProductsPath: Path = defaultTbbPath.resolve(PRODUCTS_DIR_NAME)
//    private val defaultRawDataPath: Path = defaultTbbPath.resolve(RAW_DATA_DIR_NAME)

    private val studySetName: String? by argument("STUDY_SET")
        .optional()
        .help("Name of the study set to use (default: ${StandardStudySet.DEFAULT.simpleName})")

    private val forceDownload: Boolean by option("--force-download", "-f")
        .flag(default = false)
        .help("Force download and re-index the study set, even if data exists (default: false)")

    private val skipText: Boolean by option("--no-text")
        .flag(default = false)
        .help("Skip generating the texts (default: false)")

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
        .help("Directory for output products (default: $defaultProductsPath)")

    override fun run() {
        print(BANNER)
        val studySet: StudySet =
            StandardStudySet.parse(studySetName)

//        val dataPath = Paths.get(dataDir)
        val studyData = try {
            StudyData.readData(studySet, dataDir, rawDataDir, forceDownload)
        } catch (e: NoSuchFileException) {
            echo("Data missing: ${e.message}")
            echo("Downloading and indexing the study set for ${studySet.name} (forceDownload=$forceDownload)...")
            val indexer = EsvIndexer(studySet)
            val chapterPassages: Sequence<Passage> = EsvClient(rawDataDir).bookByChapters(studySet, forceDownload)
            indexer.indexBook(chapterPassages).also {
                it.writeData(dataDir)
            }
        }

        // write a bunch of variations of the Bible text
        if (!skipText) {
            generateBibleTexts(studyData, LocalDate.of(2026, 3, 28), productsDir)
        }

        // Generate files (rest unchanged from BiblebowlKt.main)
        writeOneTimeWordsIndex(studyData, productsDir)
        writeOneTimeWordsHomework(studyData, productsDir)
        writeFullIndex(studyData, productsDir = productsDir)
        writeNumbersIndex(studyData, productsDir = productsDir)
        writeNamesIndex(studyData, productsDir)
        writeNonLocalPhrasesIndex(studyData, productsDir)

        writeWordListIndex(productsDir, studyData, WordList.ANGELS_DEMONS, "Angel or Demon", "Angels or Demons")
        writeWordListIndex(productsDir, studyData, WordList.ANIMALS, "Animal")
        writeWordListIndex(productsDir, studyData, WordList.BODY_PARTS, "Body Part")
        writeWordListIndex(productsDir, studyData, WordList.COLORS, "Color")
        writeWordListIndex(productsDir, studyData, WordList.FOODS, "Food")
        writeWordListIndex(productsDir, studyData, WordList.MEN, "Man", "Men")
        writeWordListIndex(productsDir, studyData, WordList.WOMEN, "Woman", "Women")
        writeWordListIndex(productsDir, studyData, WordList.PLACES, "Place")

        writeHeadingsPdf(studyData, productsDir)
        writeHeadingsText(studyData, productsDir)
        writeHeadingsText(studyData, productsDir)

        writeCramVerses(studyData, productsDir)
        writeCramHeadings(studyData, productsDir)
        writeCramReverseHeadings(studyData, productsDir)
        writeCramOneTimeWords(studyData, oneTimeWords(studyData), productsDir)

//        val nameExcerpts = findNames(studyData, "god", "jesus", "christ")
//        writeCramNameBlanks(studyData, nameExcerpts)
//        writeCramFewTimeWords(studyData)

        writeFullSet(studyData, productsDir) { content, seed, productsDir ->
            writeRound1VerseFind(practiceTest(Round.FIND_THE_VERSE, content, seed, numQuestions = 20), productsDir)
            writeRound4Quotes(practiceTest(Round.QUOTES, content, seed), productsDir)
            writeRound5Events(practiceTest(Round.EVENTS, content, seed), productsDir)
        }

        writeTypstFlashCards(studyData, productsDir)

        echo("Generation complete. Output in $productsDir")
    }
}

fun main(args: Array<String>) = BibleBowlCli().main(args)