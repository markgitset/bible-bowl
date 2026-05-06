package net.markdrew.biblebowl.flashcards.space

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.flashcards.DelimitedExporter
import net.markdrew.biblebowl.flashcards.FlashcardGenerator
import net.markdrew.biblebowl.flashcards.HeadingFlashcard
import net.markdrew.biblebowl.flashcards.MarkdownStrategy
import net.markdrew.biblebowl.flashcards.cram.makePath
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)

    writeSpaceHeadings(studyData)
}

fun writeSpaceHeadings(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    chapterRange: ChapterRange = studyData.chapterRange
) {
    val spaceHeadingsPath = makePath(studyData, "space-headings", chapterRange, productsDir, "space")
    val flashcards: List<HeadingFlashcard> = HeadingFlashcard.fromStudyData(studyData)
//        .groupBy {
//        it.title
//    }.map { (headingTitle, headingList) ->
//        val answerString = headingList.joinToString("<br/>OR<br/>") { heading ->
//            heading.chapterRange.format(separator = " & ")
//        }
//        SimpleHeadingFlashcard(headingTitle, answerString)
//    }

    val generator = FlashcardGenerator(MarkdownStrategy(), DelimitedExporter(","))
    val deck: String = generator.generateDeck(flashcards)
    Files.writeString(spaceHeadingsPath, deck)

    println("Wrote data to: $spaceHeadingsPath")
}
