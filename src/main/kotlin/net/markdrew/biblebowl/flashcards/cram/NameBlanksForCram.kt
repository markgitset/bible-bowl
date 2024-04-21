package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.printNameFrequencies
import net.markdrew.biblebowl.analysis.printExcerpts
import net.markdrew.biblebowl.flashcards.Card
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.encloses
import java.nio.file.Paths

fun main(args: Array<String>) {

    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    val nameExcerpts: Sequence<Excerpt> = findNames(studyData, "god", "jesus", "christ")
    printNameFrequencies(nameExcerpts)
    printExcerpts(nameExcerpts, studyData)

    val stepByNChapters = 10
    val chapterChunks: List<ChapterRange> = studyData.chapters.values.chunked(stepByNChapters) { it.first()..it.last() }
    for (chapterRange in chapterChunks) {
        writeCramNameBlanks(studyData, nameExcerpts, chapterRange)
    }
//
//    val cramNameBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName/cram").resolve("$bookName-cram-name-blanks.tsv")
//    CardWriter(cramNameBlanksPath).use {
//        it.write(toCards(nameExcerpts, studyData))
//    }

}

fun writeCramNameBlanks(
    studyData: StudyData,
    nameExcerpts: Sequence<Excerpt>,
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val bookName = studyData.studySet.simpleName
    val scopeString = studyData.chapterRangeOrEmpty("-chapters-", chapterRange)
    val cramNameBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName/cram")
        .resolve("$bookName-cram-name-blanks$scopeString.tsv")
    val validCharRange: IntRange = studyData.charRangeFromChapterRange(chapterRange)
    CardWriter(cramNameBlanksPath).use { cardWriter ->
        cardWriter.write(toCards(nameExcerpts.filter { validCharRange.encloses(it.excerptRange) }, studyData))
    }
    println("Wrote $cramNameBlanksPath")
}

private fun toCards(nameExcerpts: Sequence<Excerpt>, studyData: StudyData): List<Card> =
    nameExcerpts.groupBy { excerpt ->
        studyData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception()
    }.map { (sentRange, nameExcerpts) ->
        FillInTheBlank(
            sentRange,
            nameExcerpts,
            studyData.verseEnclosing(sentRange.excerptRange) ?: throw Exception()
        ).toCramCard()
    }
