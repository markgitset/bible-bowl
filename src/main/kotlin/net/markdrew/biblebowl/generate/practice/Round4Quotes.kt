package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.latex.showPdf
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.identifyDoubleQuotes
import net.markdrew.biblebowl.model.identifySingleQuotes
import net.markdrew.biblebowl.model.trim
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
//    val nSamples = 10
//    for (lastChapter in setOf(16, 20, 23, 25, 28, 31, 35, 38, 41, 44, 47, 50)) {
//        writeRound4Quotes(Book.DEFAULT, numQuestions = 33, randomSeed = 1, throughChapter = lastChapter)
//    }
    val content: PracticeContent = studyData.practice(studyData.chapterRange)
    showPdf(writeRound4Quotes(PracticeTest(Round.QUOTES, content)))
}

fun writeRound4Quotes(practiceTest: PracticeTest): File {
    val studyData = StudyData.readData(practiceTest.studySet, Paths.get(DATA_DIR))

    val filteredCluePool: Map<IntRange, ChapterRef> = round4CluePool(practiceTest)
    println("Final clue pools size is ${filteredCluePool.size}")

    val quotesToFind: List<MultiChoiceQuestion> = filteredCluePool.entries
        .shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { (range, chapter) ->
            Question(
                """“${studyData.text.substring(range)}”""",
                listOf(chapter),
                studyData.verseContaining(range.first)?.let { listOf(it) },
            )
        }.map { multiChoice(it, practiceTest.content.coveredChapters, practiceTest.random) }

    val latexFile = practiceTest.buildTexFileName()
    latexFile.writer().use { writer ->
        toLatexInWhatChapter(writer, practiceTest, quotesToFind)
        println("Wrote $latexFile")
    }
    return latexFile.latexToPdf()
}

private fun round4CluePool(practiceTest: PracticeTest): Map<IntRange, ChapterRef> {
    val studyData = practiceTest.content.studyData
    val chapters = practiceTest.content.coveredChapters
    var cluePool: DisjointRangeMap<ChapterRef> = DisjointRangeMap(studyData.chapters
            .maskedBy(identifySingleQuotes(studyData.text).gcdAlignment(identifyDoubleQuotes(studyData.text)))
            .maskedBy(studyData.sentences)
            .mapKeys { (range, _) ->
                trim(studyData.text, range) { c -> c in " :,‘’“”\n" }
            }.filterNot { (range, _) -> range.isEmpty() })
    if (!practiceTest.content.allChapters) {
        val lastIncludedOffset: Int = studyData.chapterIndex[chapters.last()]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }
    val longEnoughClues = cluePool.filterKeys { it.length() >= 15 }
    // finally, ensure that we don't have any clues with more than one correct answer
    return longEnoughClues.entries
        .groupBy { (k, _) -> studyData.text.substring(k).lowercase() } // group clues by the text of the clue
        .values.filter { it.size == 1 }.flatten().associate { (k, v) -> k to v } // only keep groups of one clue
}
