package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.printExcerpts
import net.markdrew.biblebowl.analysis.printNameFrequencies
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.DelimitedExporter
import net.markdrew.biblebowl.flashcards.FlashcardGenerator
import net.markdrew.biblebowl.flashcards.HtmlStrategy
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.encloses
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME))

    val nameExcerpts: Sequence<Excerpt> = findNames(studyData, "god", "jesus", "christ")
    printNameFrequencies(nameExcerpts)
    printExcerpts(nameExcerpts, studyData)

    val stepByNChapters = 10
    val chapterChunks: List<ChapterRange> = studyData.chapters.values.chunked(stepByNChapters) { it.first()..it.last() }
    for (chapterRange in chapterChunks) {
        writeCramNameBlanks(studyData, nameExcerpts, chapterRange)
    }
}

fun writeCramNameBlanks(
    studyData: StudyData,
    nameExcerpts: Sequence<Excerpt>,
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val bookName = studyData.studySet.simpleName
    val scopeString = studyData.chapterRangeOrEmpty("-chapters-", chapterRange)
    val cramNameBlanksPath = Paths.get("$defaultProductsPath/$bookName/cram")
        .resolve("$bookName-cram-name-blanks$scopeString.tsv")
    val validCharRange: IntRange = studyData.charRangeFromChapterRange(chapterRange)

    val flashcards = nameExcerpts.filter { validCharRange.encloses(it.excerptRange) }
        .groupBy { excerpt ->
            studyData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception()
        }.map { (sentRange, nameExcerpts) ->
            FillInTheBlank(
                sentRange,
                nameExcerpts,
                studyData.verseEnclosing(sentRange.excerptRange) ?: throw Exception()
            ).toFlashcard()
        }

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter("\t"))
    val deck = generator.generateDeck(flashcards)
    Files.createDirectories(cramNameBlanksPath.parent)
    Files.writeString(cramNameBlanksPath, deck)

    println("Wrote $cramNameBlanksPath")
}
