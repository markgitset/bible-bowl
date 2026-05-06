package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.analysis.oneSectionWords
import net.markdrew.biblebowl.flashcards.DelimitedExporter
import net.markdrew.biblebowl.flashcards.FlashcardGenerator
import net.markdrew.biblebowl.flashcards.HtmlStrategy
import net.markdrew.biblebowl.flashcards.SimpleFlashcard
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.format
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.encloses
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun mergeCards(vararg cardMaps: Map<SimpleFlashcard, IntRange>): Collection<SimpleFlashcard> =
    cardMaps.asSequence()
        .flatMap { cardMap -> cardMap.asSequence() }
        .groupBy { (card, _) -> card.frontText }
        .values
        .map { entries ->
            when (entries.size) {
                1 -> entries.single().key
                2 -> {
                    val (e1, e2) = entries
                    when {
                        e1.value.encloses(e2.value) -> e2.key
                        e2.value.encloses(e1.value) -> e1.key
                        else -> e1.key.copy(backText = e2.key.backText.substringBefore("<br") + " AND " + e1.key.backText)
                    }
                }
                else -> throw Exception()
            }
        }

private fun <T : Any> oneSectionWordCards(
    studyData: StudyData,
    sectionMap: DisjointRangeMap<T>,
    formatSection: (T) -> String = { it.toString() },
): Map<SimpleFlashcard, IntRange> =
    oneSectionWords(studyData, sectionMap).associate { (word, ranges, section, sectionRange) ->
        val verseListString = ranges.map {
            studyData.verses.valueEnclosing(it) ?: throw Exception()
        }.format(FULL_BOOK_FORMAT)
        val cardBack = listOf(formatSection(section), "(${ranges.size} times: $verseListString)").joinToString("<br/>")
        SimpleFlashcard(word, cardBack) to sectionRange
    }

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME))
    writeCramFewTimeWords(studyData)
}

fun writeCramFewTimeWords(studyData: StudyData) {
    val oneChapterWordCards = oneSectionWordCards(studyData, studyData.chapters) { it.format(FULL_BOOK_FORMAT) }
    val oneHeadingWordCards = oneSectionWordCards(studyData, studyData.headingCharRanges)
    val fewTimeWordCards = mergeCards(oneChapterWordCards, oneHeadingWordCards).sortedBy { it.frontText }

    val simpleName = studyData.studySet.simpleName
    val outFile = File("$PRODUCTS_DIR_NAME/$simpleName/cram", "$simpleName-cram-local-words.tsv")

    val generator = FlashcardGenerator(HtmlStrategy(), DelimitedExporter("\t"))
    val deck = generator.generateDeck(fewTimeWordCards)
    Files.createDirectories(outFile.toPath().parent)
    Files.writeString(outFile.toPath(), deck)

    println("Wrote local words cards to $outFile")
}
