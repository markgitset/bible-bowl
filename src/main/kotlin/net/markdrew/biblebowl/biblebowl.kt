package net.markdrew.biblebowl

import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramFewTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramNameBlanks
import net.markdrew.biblebowl.flashcards.cram.writeCramOneTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramReverseHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramVerses
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
import net.markdrew.biblebowl.generate.practice.writeFindTheVerse
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
import java.nio.file.Path
import java.nio.file.Paths

const val INDENT_POETRY_LINES = 4

const val RAW_DATA_DIR = "raw-data"
const val DATA_DIR = "data"
const val PRODUCTS_DIR = "products"
const val BANNER = """
  ______                        ____  _ __    __        ____                __
 /_  __/__  _  ______ ______   / __ )(_) /_  / /__     / __ )____ _      __/ /
  / / / _ \| |/_/ __ `/ ___/  / __  / / __ \/ / _ \   / __  / __ \ | /| / / / 
 / / /  __/>  </ /_/ (__  )  / /_/ / / /_/ / /  __/  / /_/ / /_/ / |/ |/ / /  
/_/  \___/_/|_|\__,_/____/  /_____/_/_.___/_/\___/  /_____/\____/|__/|__/_/   
"""

fun rangeLabel(singular: String, range: IntRange, separator: String = "-", plural: String = "${singular}s"): String =
    if (range.count() == 1) "$singular$separator${range.first}"
    else "$plural$separator${range.first}-${range.last}"

private val runOfNonLetterOrDigit = """[^0-9a-z]+""".toRegex()

fun normalizeFileName(name: String): String = runOfNonLetterOrDigit.replace(name.lowercase(), "-")

val FANCY_QUOTES_START = '“'
val FANCY_QUOTES_END = '”'
val FANCY_QUOTE_START = '‘'
val FANCY_QUOTE_END = '’'
val FANCY_APOSTROPHE = FANCY_QUOTE_END

/**
 * Build a whole set of resources
 */
fun main(args: Array<String>) {
    print(BANNER)

    // parse the study set from the arguments
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))

    // load the indices (or download and create them, if not found)
    val dataPath: Path = Paths.get(DATA_DIR)
    val studyData = try {
        StudyData.readData(studySet, dataPath)
    } catch (e: FileNotFoundException) {
        println("File not found: ${e.message}")
        println("Downloading and indexing the study set for ${studySet.name}...")
        val indexer = EsvIndexer(studySet)
        val chapterPassages: Sequence<Passage> = EsvClient().bookByChapters(studySet, forceDownload = false)
        indexer.indexBook(chapterPassages).also {
            it.writeData(dataPath)
        }
    }

    // Write LaTEX-set PDFs of the text
//    writeBibleText(studyData)
//    writeBibleText(studyData, TextOptions(names = true, numbers = true, uniqueWords = true))

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
    writeCramReverseHeadings(studyData)
    writeCramOneTimeWords(studyData, oneTimeWords(studyData))
    val nameExcerpts: Sequence<Excerpt> = findNames(studyData, "god", "jesus", "christ")
    writeCramNameBlanks(studyData, nameExcerpts)
    writeCramFewTimeWords(studyData)

    // Write practice tests
    val content = PracticeContent(studyData)
    writeFindTheVerse(PracticeTest(Round.FIND_THE_VERSE, content, randomSeed = 50))
    writeRound5Events(PracticeTest(Round.EVENTS, content, randomSeed = 50))
    writeRound4Quotes(PracticeTest(Round.QUOTES, content, randomSeed = 50))
}