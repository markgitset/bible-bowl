package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.*
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
//    val nSamples = 10
    writeRound4Quotes(Book.DEFAULT, numQuestions = 35, throughChapter = 12, randomSeed = 9610)
}

private const val ROUND_4_PACE = 40.0 / 15.0 // questions/minute

private fun writeRound4Quotes(
    book: Book = Book.DEFAULT,
    throughChapter: Int? = null,
    numQuestions: Int = 40,
    randomSeed: Int = Random.nextInt(1..9_999)
) {
    val random = Random(randomSeed)
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val maxChapter: Int = bookData.chapters.lastEntry().value
    val lastIncludedChapter: Int? = throughChapter?.let {
        require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${book.fullName}!" }
        if (it == maxChapter) null else it
    }

    val filteredCluePool: Map<IntRange, Int> = round4CluePool(bookData, lastIncludedChapter)
    println("Final clue pools size is ${filteredCluePool.size}")

    val quotesToFind: List<MultiChoiceQuestion> = filteredCluePool.entries
        .shuffled(random).take(numQuestions)
        .map { (range, chapter) ->
            Question(
                """“${bookData.text.substring(range)}”""",
                chapter.toString(),
                bookData.verseContaining(range.first)
            )
        }.map { multiChoice(it, lastIncludedChapter ?: maxChapter, random) }

    var fileName = "${book.name.lowercase()}-quotes"
    if (lastIncludedChapter != null) fileName += "-to-ch-$throughChapter"
    fileName += "-$randomSeed"

    val latexFile = File("$PRODUCTS_DIR/$bookName/practice/round4/$fileName.tex").also { it.parentFile.mkdirs() }
    latexFile.writer().use { writer ->
        toLatexInWhatChapter(
            quotesToFind, writer, book, lastIncludedChapter, round = 4, clueType = "quotes", ROUND_4_PACE, randomSeed
        )
        println("Wrote $latexFile")
    }
    latexFile.toPdf()
}

private fun round4CluePool(bookData: BookData, lastIncludedChapter: Int?): Map<IntRange, Int> {
    var cluePool: DisjointRangeMap<Int> = DisjointRangeMap(bookData.chapters
            .maskedBy(identifySingleQuotes(bookData.text).gcdAlignment(identifyDoubleQuotes(bookData.text)))
            .maskedBy(bookData.sentences)
            .mapKeys { (range, _) ->
                trim(bookData.text, range) { c -> c in " :,‘’“”\n" }
            }.filterNot { (range, _) -> range.isEmpty() })
    if (lastIncludedChapter != null) {
        val lastIncludedOffset: Int = bookData.chapterIndex[lastIncludedChapter]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }
    return cluePool.filterKeys { it.length() >= 15 }
}