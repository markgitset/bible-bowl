package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.defaultProductsPath
import java.nio.file.Path
import net.markdrew.biblebowl.analysis.oneSectionWords
import net.markdrew.biblebowl.flashcards.Card
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.format
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.encloses
import java.io.File
import java.nio.file.Paths

/**
 * Merges several maps of [Card] -> section [IntRange] into a single deduplicated card list
 *
 * When two maps contain cards with the same `front`, the one whose section range is enclosed by the other
 * wins (i.e. the more specific section is preferred). If neither encloses the other, both backs are
 * concatenated with "AND".
 *
 * @throws Exception if a `front` appears in more than two of [cardMaps]
 */
fun mergeCards(vararg cardMaps: Map<Card, IntRange>): Collection<Card> =
    cardMaps.asSequence()
        .flatMap { cardMap -> cardMap.asSequence() } // Entry<Card, IntRange>
        .groupBy { (card, _) -> card.front } // Entry<Front, List<Entry<Card, IntRange>>>
        .values // List<Entry<Card, IntRange>>
        .map { entries ->
            when (entries.size) {
                1 -> entries.single().key
                2 -> {
                    val (e1, e2) = entries
                    when {
                        e1.value.encloses(e2.value) -> e2.key
                        e2.value.encloses(e1.value) -> e1.key
                        else -> e1.key.copy(back = e2.key.back.substringBefore("<br") + " AND " + e1.key.back)
                    }
                }
                else -> throw Exception()
            }
        } // Card

/**
 * Build list of one-section word cards (where the sections could be chapters or headings)
 */
private fun <T : Any> oneSectionWordCards(
    studyData: StudyData,
    sectionMap: DisjointRangeMap<T>,
    formatSection: (T) -> String = { it.toString() },
): Map<Card, IntRange> =
    oneSectionWords(studyData, sectionMap).associate { (word, ranges, section, sectionRange) ->
        //println("""%20s occurs %2d times in %s""".format(""""$word"""", ranges.size, sectionPrefix + section))
        val verseListString = ranges.map {
            studyData.verses.valueEnclosing(it) ?: throw Exception()
        }.format(FULL_BOOK_FORMAT)
        val cardBack = listOf(formatSection(section), "(${ranges.size} times: $verseListString)").joinToString("<br/>")
        Card(word, cardBack) to sectionRange
    }

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME))
    writeCramFewTimeWords(studyData)
}

/**
 * Writes a Cram-style TSV of "few-time word" flashcards for [studyData]: words occurring multiple times but
 * confined to a single chapter or single heading
 *
 * One-chapter and one-heading cards are merged so any word appearing in both lists collapses to the
 * narrower (heading) scope.
 */
fun writeCramFewTimeWords(studyData: StudyData, productsDir: Path = defaultProductsPath) {
    // build one-chapter words
    val oneChapterWordCards: Map<Card, IntRange> =
        oneSectionWordCards(studyData, studyData.chapters) { it.format(FULL_BOOK_FORMAT) }

    // build one-heading words
    val oneHeadingWordCards: Map<Card, IntRange> = oneSectionWordCards(studyData, studyData.headingCharRanges)

    // combine the two sets of cards
    val fewTimeWordCards: Collection<Card> = mergeCards(oneChapterWordCards, oneHeadingWordCards)
        .sortedBy { it.front }

    // write 'em out
    val simpleName = studyData.studySet.simpleName
    val outFile = productsDir.resolve("$simpleName/cram/$simpleName-cram-local-words.tsv").toFile()
    CardWriter.writeCards(fewTimeWordCards, outFile)
    println("Wrote local words cards to $outFile")
}
