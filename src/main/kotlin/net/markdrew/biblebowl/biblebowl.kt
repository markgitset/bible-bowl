package net.markdrew.biblebowl

import net.markdrew.biblebowl.analysis.STOP_WORDS
import net.markdrew.biblebowl.generate.indices.writeOneTimeWordsIndex
import net.markdrew.biblebowl.generate.text.TextOptions
import net.markdrew.biblebowl.generate.text.writeBibleText
import net.markdrew.biblebowl.generate.indices.writeFullIndex
import net.markdrew.biblebowl.generate.writeNonLocalPhrasesIndex
import net.markdrew.biblebowl.generate.writeNumbersIndex
import net.markdrew.biblebowl.indices.writeNamesIndex
import net.markdrew.biblebowl.model.Book
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

/**
 * Build a whole set of resources
 */
fun main(args: Array<String>) {
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
        val chapterPassages: Sequence<Passage> = EsvClient().bookByChapters(studySet)
        indexer.indexBook(chapterPassages).also {
            it.writeData(dataPath)
        }
    }

    // write the highlighted text
    writeBibleText(studyData, TextOptions(names = true, numbers = true, uniqueWords = true))

    // write the one-time words index
    writeOneTimeWordsIndex(studyData)
    // write the full word index
    writeFullIndex(studyData)
    // write the numbers index
    writeNumbersIndex(studyData)
    // write the names index
    writeNamesIndex(studyData)
    // write the non-local reoccurring phrases index
    writeNonLocalPhrasesIndex(studyData)

}