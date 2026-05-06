package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.model.AllEventsParser
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.rangeLabel
import net.markdrew.chupacabra.core.encloses
import net.markdrew.chupacabra.core.intersect
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
    writeEventCards(studyData)
}

private fun makePath(
    studyData: StudyData,
    fileType: String,
    chapterRange: ChapterRange,
    productsDir: Path = defaultProductsPath,
): Path {
    val setName = studyData.studySet.simpleName
    val actualChapterRange = chapterRange.intersect(studyData.chapterRange)
    val suffix =
        if (actualChapterRange == studyData.chapterRange) ""
        else rangeLabel("-chapter", with(actualChapterRange) { start.chapter..endInclusive.chapter })
    val dir: Path = productsDir.resolve(setName, "quizlet").also { Files.createDirectories(it) }
    return dir.resolve("$setName-$fileType$suffix.csv")
}

fun writeEventCards(
    studyData: StudyData,
    productsDir: Path = defaultProductsPath,
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val eventCardsPath = makePath(studyData, "events", chapterRange, productsDir)
    val flashcards: Sequence<EventFlashcard> = AllEventsParser.useCards(studyData.studySet) { eventCards ->
        eventCards.filter {
            chapterRange.encloses(it.verseRange.toChapterRange())
        }.map {
            EventFlashcard(
                it.event,
                it.verseRange,
                studyData.headingsIntersecting(it.verseRange)
            )
        }
    }

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter(","))
    val deck = generator.generateDeck(flashcards)
    Files.writeString(eventCardsPath, deck)

    println("Wrote data to: $eventCardsPath")
}
