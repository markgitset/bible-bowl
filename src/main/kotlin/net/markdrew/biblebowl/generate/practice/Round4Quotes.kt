package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.typst.typstToPdf
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.identifyDoubleQuotes
import net.markdrew.biblebowl.model.identifySingleQuotes
import net.markdrew.biblebowl.model.trim
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)

    // PRODUCE THE FULL SET
    val seeds = setOf(10, 20, 30, 40, 50)
    for (throughChapter in studyData.chapterRefs) {
        val content = studyData.practice(throughChapter)
        for (seed in seeds) {
            writeRound4Quotes(PracticeTest(Round.QUOTES, content, randomSeed = seed))
        }
    }
}

/**
 * Generates a Round 4 ("In What Chapter — Quotes") test PDF, or null if [practiceTest]'s content covers
 * fewer than four chapters
 *
 * Mines the study text for unambiguous in-quotes sentences and asks contestants to identify the chapter
 * each quotation comes from.
 */
fun writeRound4Quotes(practiceTest: PracticeTest, productsDir: Path = defaultProductsPath): Path? {
    val studyData: StudyData = practiceTest.content.studyData

    // not enough chapters in practice content to build a reasonable practice test
    if (practiceTest.content.coveredChapters.size < 4) return null

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

    val typFile = practiceTest.buildTypFileName(productsDir)
    Files.newBufferedWriter(typFile).use { writer ->
        toTypstInWhatChapter(writer, practiceTest, quotesToFind)
    }
    return typFile.typstToPdf()
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
