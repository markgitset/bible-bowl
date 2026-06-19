package net.markdrew.biblebowl

import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramFewTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramNameBlanks
import net.markdrew.biblebowl.flashcards.cram.writeCramOneTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramReverseHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramVerses
import net.markdrew.biblebowl.flashcards.writeEventCards
import net.markdrew.biblebowl.generate.indices.writeFullIndex
import net.markdrew.biblebowl.generate.indices.writeHeadingsCsv
import net.markdrew.biblebowl.generate.indices.writeHeadingsPdf
import net.markdrew.biblebowl.generate.indices.writeHeadingsText
import net.markdrew.biblebowl.generate.indices.writeNamesIndex
import net.markdrew.biblebowl.generate.indices.writeNonLocalPhrasesIndex
import net.markdrew.biblebowl.generate.indices.writeNumbersIndex
import net.markdrew.biblebowl.generate.indices.writeOneTimeWordsIndex
import net.markdrew.biblebowl.generate.practice.PracticeTest
import net.markdrew.biblebowl.generate.practice.Round
import net.markdrew.biblebowl.generate.practice.writeRound1VerseFind
import net.markdrew.biblebowl.generate.practice.writeRound4Quotes
import net.markdrew.biblebowl.generate.practice.writeRound5Events
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.ws.EsvClient
import net.markdrew.biblebowl.ws.EsvIndexer
import net.markdrew.biblebowl.ws.Passage
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

/** Default indent used by [net.markdrew.biblebowl.ws.EsvService.text] for poetry lines */
const val INDENT_POETRY_LINES = 4

//val userHomeDir = Path(System.getProperty("user.home"))
//val defaultTbbPath: Path = userHomeDir.resolve(".tbb")
//val defaultDataPath: Path = defaultTbbPath.resolve(DATA_DIR)
//val defaultProductsPath: Path = defaultTbbPath.resolve(PRODUCTS_DIR)
//val defaultRawDataPath: Path = defaultTbbPath.resolve(RAW_DATA_DIR)

/** Subdirectory name for raw ESV downloads (chapter-level JSON cache) */
const val RAW_DATA_DIR_NAME = "raw-data"

/** Subdirectory name for indexed [net.markdrew.biblebowl.model.StudyData] files */
const val DATA_DIR_NAME = "data"

@Deprecated("Use defaultProductsPath instead") const val PRODUCTS_DIR_NAME = "products"

/** The current user's home directory, as a [Path] */
val userHomeDir = Path(System.getProperty("user.home"))

/** Root of the BibleBowl I/O tree under the user's home (`~/.tbb/`) */
val defaultTbbPath: Path = userHomeDir.resolve(".tbb")

/** Default location for indexed StudyData files (`~/.tbb/data/`) */
val defaultDataPath: Path = defaultTbbPath.resolve(DATA_DIR_NAME)

/** Default location for generated artifacts (`~/.tbb/products/`) */
val defaultProductsPath: Path = defaultTbbPath.resolve("products")

/** Default location for the raw ESV download cache (`~/.tbb/raw-data/`) */
val defaultRawDataPath: Path = defaultTbbPath.resolve(RAW_DATA_DIR_NAME)

const val BANNER = """
  ______                        ____  _ __    __        ____                __
 /_  __/__  _  ______ ______   / __ )(_) /_  / /__     / __ )____ _      __/ /
  / / / _ \| |/_/ __ `/ ___/  / __  / / __ \/ / _ \   / __  / __ \ | /| / / / 
 / / /  __/>  </ /_/ (__  )  / /_/ / / /_/ / /  __/  / /_/ / /_/ / |/ |/ / /  
/_/  \___/_/|_|\__,_/____/  /_____/_/_.___/_/\___/  /_____/\____/|__/|__/_/   
"""

/**
 * Renders an `IntRange` as a label, choosing singular vs plural form and using [separator] between the
 * label and the range
 *
 * Single-element ranges produce `"$singular$separator${range.first}"`; multi-element ranges produce
 * `"$plural$separator${range.first}-${range.last}"`.
 */
fun rangeLabel(singular: String, range: IntRange, separator: String = "-", plural: String = "${singular}s"): String =
    if (range.count() == 1) "$singular$separator${range.first}"
    else "$plural$separator${range.first}-${range.last}"

private val runOfNonLetterOrDigit = """[^0-9a-z]+""".toRegex()

/** Normalizes [name] to lowercase letters and digits, with any other characters collapsed into a single `-`. */
fun normalizeFileName(name: String): String = runOfNonLetterOrDigit.replace(name.lowercase(), "-")

/** Curly opening double quote (U+201C) */
val FANCY_QUOTES_START = '“'

/** Curly closing double quote (U+201D) */
val FANCY_QUOTES_END = '”'

/** Curly opening single quote (U+2018) */
val FANCY_QUOTE_START = '‘'

/** Curly closing single quote (U+2019), also used for the curly apostrophe */
val FANCY_QUOTE_END = '’'

/** Alias for [FANCY_QUOTE_END] when used as an apostrophe */
val FANCY_APOSTROPHE = FANCY_QUOTE_END

/**
 * Streams TSV lines from this [InputStream] into [parseFun] as a sequence of pre-split column lists
 *
 * The first [headerLines] lines are skipped. Parse errors include the 1-based source line number.
 */
fun <T> InputStream.useTsvLines(headerLines: Int = 1, parseFun: (Sequence<List<String>>) -> T): T =
    this.bufferedReader().useLines { linesSeq ->
        val headerOffset = headerLines.coerceAtLeast(0)
        val lineOffset = headerOffset + 1
        parseFun(linesSeq.drop(headerOffset).mapIndexed { index, line ->
            try {
                line.split('\t')
            } catch (e: Exception) {
                throw Exception("Parse error on line ${index + lineOffset}: ${e.message}", e)
            }
        })
    }

/** Eager wrapper around [useTsvLines] that materializes the parsed rows into a [List]. */
fun <T> parseTsv(stream: InputStream, headerLines: Int = 1, parseFun: (List<String>) -> T): List<T> =
    stream.useTsvLines(headerLines) { tsvLinesSeq ->
        tsvLinesSeq.map(parseFun).toList()
    }

/**
 * Legacy non-CLI entry point that builds the full set of resources for one study set
 *
 * Defaults to the in-repo `data/` and `raw-data/` directories. New work should extend
 * [net.markdrew.biblebowl.cli.BibleBowlCli] instead; this function is preserved for historical use.
 */
fun main(args: Array<String>) {
    print(BANNER)

    // parse the study set from the arguments
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))

    // load the indices (or download and create them, if not found)
    val dataPath: Path = Paths.get(DATA_DIR_NAME)
    val studyData = try {
        StudyData.readData(studySet, dataPath)
    } catch (e: FileNotFoundException) {
        println("File not found: ${e.message}")
        println("Downloading and indexing the study set for ${studySet.name}...")
        val indexer = EsvIndexer(studySet)
        val chapterPassages: Sequence<Passage> = EsvClient(Path.of(RAW_DATA_DIR_NAME)).bookByChapters(studySet, forceDownload = false)
        indexer.indexBook(chapterPassages).also {
            it.writeData(dataPath)
        }
    }

    // Write PDF indices
    writeOneTimeWordsIndex(studyData)
    writeFullIndex(studyData)
    writeNumbersIndex(studyData)
    writeNamesIndex(studyData)
    writeNonLocalPhrasesIndex(studyData)
    writeHeadingsPdf(studyData)

    // Write text resources
    writeHeadingsText(studyData)

    // Write CSV resources
    writeHeadingsCsv(studyData)

    // Write Cram resources
    writeCramVerses(studyData)
    writeCramHeadings(studyData)
    writeEventCards(studyData)
    writeCramReverseHeadings(studyData)
    writeCramOneTimeWords(studyData, oneTimeWords(studyData))
    val nameExcerpts: Sequence<Excerpt> = findNames(studyData, "god", "jesus", "christ")
    writeCramNameBlanks(studyData, nameExcerpts)
    writeCramFewTimeWords(studyData)

    // Write practice tests
    val content = PracticeContent(studyData)
    writeRound1VerseFind(PracticeTest(Round.FIND_THE_VERSE, content, randomSeed = 50))
    writeRound5Events(PracticeTest(Round.EVENTS, content, randomSeed = 50))
    writeRound4Quotes(PracticeTest(Round.QUOTES, content, randomSeed = 50))
}

/**
 * Builds the standard output path for a generated artifact
 *
 * The result is `<productsDir>/<simpleName>/<productDirName>/<simpleName>-<slugified productName>` and the
 * parent directory is created as a side effect.
 */
fun fileForProduct(
    studyData: StudyData,
    productDirName: String,
    productName: String,
    productsDir: Path,
): Path {
    val simpleName = studyData.studySet.simpleName
    val dir = productsDir.resolve(simpleName, productDirName).also { Files.createDirectories(it) }
    return dir.resolve("$simpleName-${productName.lowercase().replace(' ', '-')}")
}

/** Opens this PDF path in `evince` and returns the path unchanged so it can be chained. */
fun Path.showPdf(): Path = this.also {
    ProcessBuilder("evince", it.absolutePathString()).inheritIO().start()
}

/**
 * Looks up a classpath resource by name
 *
 * @throws IOException if the resource can't be found
 */
fun getResource(resourceName: String): URL = (object {}.javaClass.getResource(resourceName)
    ?: throw IOException("Could not find resource '$resourceName'!"))
