package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.*
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.nio.file.Paths

fun main() {
//    val nSamples = 10
//    for (lastChapter in setOf(16, 20, 23, 25, 28, 31, 35, 38, 41, 44, 47, 50)) {
//        writeRound4Quotes(Book.DEFAULT, numQuestions = 33, randomSeed = 1, throughChapter = lastChapter)
//    }
    writeRound4Quotes(PracticeTest(Round.QUOTES, throughChapter = 10, numQuestions = 20))
}

private fun writeRound4Quotes(practiceTest: PracticeTest) {
    val bookData = BookData.readData(practiceTest.book, Paths.get(DATA_DIR))

    val maxChapter: Int = bookData.chapters.lastEntry().value
    val lastIncludedChapter: Int? = practiceTest.lastIncludedChapter(bookData)

    val filteredCluePool: Map<IntRange, Int> = round4CluePool(bookData, lastIncludedChapter)
    println("Final clue pools size is ${filteredCluePool.size}")

    val quotesToFind: List<MultiChoiceQuestion> = filteredCluePool.entries
        .shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { (range, chapter) ->
            Question(
                """“${bookData.text.substring(range)}”""",
                chapter.toString(),
                bookData.verseContaining(range.first)
            )
        }.map { multiChoice(it, lastIncludedChapter ?: maxChapter, practiceTest.random) }

    val latexFile = practiceTest.buildTexFileName(lastIncludedChapter)
    latexFile.writer().use { writer ->
        toLatexInWhatChapter(quotesToFind, writer, practiceTest, lastIncludedChapter)
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
    val longEnoughClues = cluePool.filterKeys { it.length() >= 15 }
    // finally, ensure that we don't have any clues with more than one correct answer
    return longEnoughClues.entries
        .groupBy { (k, _) -> bookData.text.substring(k).lowercase() } // group clues by the text of the clue
        .values.filter { it.size == 1 }.flatten().associate { (k, v) -> k to v } // only keep groups of one clue
}
