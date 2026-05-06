package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.flashcards.DelimitedExporter
import net.markdrew.biblebowl.flashcards.FlashcardGenerator
import net.markdrew.biblebowl.flashcards.HtmlStrategy
import net.markdrew.biblebowl.flashcards.VerseFlashcard
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("Bible Bowl!")
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME))
    writeCramVerses(studyData)
}

fun writeCramVerses(studyData: StudyData, productsDir: Path = Path.of(PRODUCTS_DIR_NAME)) {
    val setName = studyData.studySet.simpleName
    val cramFile = productsDir.resolve(setName, "cram", "$setName-cram-verses.tsv")
    
    val flashcards = studyData.verses.map { (range, verseRef) ->
        val verseText = studyData.text.substring(range).normalizeWS()
        val heading = studyData.headingCharRanges.valueEnclosing(range)
        VerseFlashcard(verseRef.format(FULL_BOOK_FORMAT), verseText, heading)
    }

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter("\t"))
    val deck = generator.generateDeck(flashcards)
    Files.createDirectories(cramFile.parent)
    Files.writeString(cramFile, deck)
    
    println("Wrote data to: $cramFile")
}
