package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.latex.showPdf
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.identifyDoubleQuotes
import net.markdrew.biblebowl.model.identifySingleQuotes
import net.markdrew.biblebowl.model.trim
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths

fun main() {
//    val nSamples = 10
//    for (lastChapter in setOf(16, 20, 23, 25, 28, 31, 35, 38, 41, 44, 47, 50)) {
//        writeRound4Quotes(Book.DEFAULT, numQuestions = 33, randomSeed = 1, throughChapter = lastChapter)
//    }
    val bookData: BookData = BookData.readData()
    val content: PracticeContent = bookData.practice(bookData.book.chapterRange(1, 10))
    showPdf(writeRound4Quotes(PracticeTest(Round.QUOTES, content, numQuestions = 20)))
}

private fun writeRound4Quotes(practiceTest: PracticeTest): File {
    val bookData = StudyData.readData(practiceTest.studySet, Paths.get(DATA_DIR))

    val filteredCluePool: Map<IntRange, ChapterRef> = round4CluePool(practiceTest)
    println("Final clue pools size is ${filteredCluePool.size}")

    val quotesToFind: List<MultiChoiceQuestion> = filteredCluePool.entries
        .shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { (range, chapter) ->
            Question(
                """“${bookData.text.substring(range)}”""",
                listOf(chapter.chapter.toString()),
                bookData.verseContaining(range.first)?.let { listOf(it) },
            )
        }.map { multiChoice(it, practiceTest.content.coveredChapters, practiceTest.random) }

    val latexFile = practiceTest.buildTexFileName()
    latexFile.writer().use { writer ->
        toLatexInWhatChapter(writer, practiceTest, quotesToFind)
        println("Wrote $latexFile")
    }
    return latexFile.toPdf()
}

private fun round4CluePool(practiceTest: PracticeTest): Map<IntRange, ChapterRef> {
    val bookData = practiceTest.content.studyData
    val chapters = practiceTest.content.coveredChapters
    var cluePool: DisjointRangeMap<ChapterRef> = DisjointRangeMap(bookData.chapters
            .maskedBy(identifySingleQuotes(bookData.text).gcdAlignment(identifyDoubleQuotes(bookData.text)))
            .maskedBy(bookData.sentences)
            .mapKeys { (range, _) ->
                trim(bookData.text, range) { c -> c in " :,‘’“”\n" }
            }.filterNot { (range, _) -> range.isEmpty() })
    if (!practiceTest.content.allChapters) {
        val lastIncludedOffset: Int = bookData.chapterIndex[chapters.endInclusive]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }
    val longEnoughClues = cluePool.filterKeys { it.length() >= 15 }
    // finally, ensure that we don't have any clues with more than one correct answer
    return longEnoughClues.entries
        .groupBy { (k, _) -> bookData.text.substring(k).lowercase() } // group clues by the text of the clue
        .values.filter { it.size == 1 }.flatten().associate { (k, v) -> k to v } // only keep groups of one clue
}
