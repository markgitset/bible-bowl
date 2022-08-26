package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.rangeFirstLastComparator
import java.nio.file.Paths

private val STOP_NAMES = setOf("i")
//private val STOP_NAMES = setOf("o", "i", "amen", "surely", "lord", "alpha", "omega", "almighty", "hallelujah", "praise",
//    "why", "yes", "release", "sir", "father", "pay", "sovereign", "mount", "remember", "hurry", "possessor", "perhaps",
//    "oh", "suppose", "knead", "meanwhile", "quick", "raiders", "whichever", "unstable", "assemble")

fun buildNamesIndex(bookData: BookData,
                    frequencyRange: IntRange? = null,
                    vararg exceptNames: String): List<WordIndexEntry> =
    nameCandidates(bookData, exceptNames).map { excerpts ->
        val key = excerpts.first().excerptText
        WordIndexEntry(key, excerpts.map { bookData.verseEnclosing(it.excerptRange) ?: throw Exception() })
    }

fun findNames(bookData: BookData, vararg exceptNames: String): Sequence<Excerpt> =
    nameCandidates(bookData, exceptNames)
        .flatten()
        .sortedWith(compareBy(rangeFirstLastComparator) { it.excerptRange })
        .asSequence()

private fun nameCandidates(bookData: BookData, exceptNames: Array<out String>): Collection<List<Excerpt>> =
    bookData.words.map { bookData.excerpt(it).disown() } // non-possessive word Excerpts
        .groupBy { it.excerptText.lowercase() } // Map<String, List<Excerpt>>
        .filterKeys { it !in STOP_NAMES && it !in exceptNames } // remove stop names
        .filterValues { excerpts ->
            excerpts.none { excerpt ->
                excerpt.excerptText.first().let { it.isLowerCase() || it.isDigit() }
            }
        }.values // only keep excerpt lists for which all excerpts start with a capital letter

fun printNameFrequencies(nameExcerpts: Sequence<Excerpt>) {
    nameExcerpts.groupBy { it.excerptText }
        .map { (name, excerpts) -> excerpts.size to name }
        .sortedBy { it.first }
        .forEachIndexed { i, (count, name) ->
            println("%3d  %15s %15s".format(i+1, count, name))
        }
}

fun printNameMatches(nameExcerpts: Sequence<Excerpt>, bookData: BookData) {
    nameExcerpts.forEachIndexed { i, numExcerpt ->
        val (nameString, nameRange) = numExcerpt
        val sentRange: Excerpt? = bookData.sentenceContext(nameRange)
        val sentenceString: String = sentRange?.formatRange(nameRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = bookData.verseEnclosing(nameRange)
        println("%3d  %15s %15s    %s".format(i, ref?.toChapterAndVerse(), nameString, sentenceString))
    }
}

fun main(args: Array<String>) {

    println(BANNER)
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

//    val dict = DictionaryParser.parse("words_alpha.txt")
    val dict = DictionaryParser.parse("english.txt")
    val nameExcerpts: Sequence<Excerpt> = findNames(bookData, "god", "jesus", "christ")
        .filter { it.excerptText.lowercase() in dict }

//    printNameFrequencies(nameExcerpts)
    printNameMatches(nameExcerpts, bookData)
}