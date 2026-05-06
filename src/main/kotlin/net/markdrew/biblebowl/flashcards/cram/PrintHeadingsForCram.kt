package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.flashcards.DelimitedExporter
import net.markdrew.biblebowl.flashcards.FlashcardGenerator
import net.markdrew.biblebowl.flashcards.HtmlStrategy
import net.markdrew.biblebowl.flashcards.SimpleHeadingFlashcard
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.format
import net.markdrew.biblebowl.rangeLabel
import net.markdrew.chupacabra.core.intersect
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)

    writeCramHeadings(studyData)
}

fun makePath(
    studyData: StudyData,
    fileType: String,
    chapterRange: ChapterRange,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    subDir: String
): Path {
    val setName = studyData.studySet.simpleName
    val actualChapterRange = chapterRange.intersect(studyData.chapterRange)
    val suffix =
        if (actualChapterRange == studyData.chapterRange) ""
        else rangeLabel("-chapter", with(actualChapterRange) { start.chapter..endInclusive.chapter })
    val dir: Path = productsDir.resolve(setName, subDir).also { Files.createDirectories(it) }
    return dir.resolve("$setName-$fileType$suffix.tsv")
}

fun writeCramHeadings(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    chapterRange: ChapterRange = studyData.chapterRange
) {
    val cramHeadingsPath = makePath(studyData, "cram-headings", chapterRange, productsDir, "cram")
    val flashcards = studyData.headings(chapterRange).groupBy {
        it.title
    }.map { (headingTitle, headingList) ->
        val answerString = headingList.joinToString("<br/>OR<br/>") { heading ->
            heading.chapterRange.format(separator = " & ")
        }
        SimpleHeadingFlashcard(headingTitle, answerString)
    }

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter("\t"))
    val deck = generator.generateDeck(flashcards)
    Files.writeString(cramHeadingsPath, deck)

    println("Wrote data to: $cramHeadingsPath")
}

fun writeCramReverseHeadings(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val cramHeadingsPath = makePath(studyData, "cram-reverse-headings", chapterRange, productsDir, "cram")
    val flashcards = studyData.chapters
        .filterValues { it in chapterRange }
        .map { (range, chapterRef) ->
            val headings: List<String> = studyData.headingCharRanges.valuesIntersectedBy(range)
            SimpleHeadingFlashcard(chapterRef.format(FULL_BOOK_FORMAT), headings.joinToString("<br/>"))
        }

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter("\t"))
    val deck = generator.generateDeck(flashcards)
    Files.writeString(cramHeadingsPath, deck)

    println("Wrote data to: $cramHeadingsPath")
}
