package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.oneSectionWords
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.encloses
import java.io.File
import java.nio.file.Paths

/**
 * Combines two [Card] lists such that no front values are duplicated--if duplicates are found, the one with the smaller
 * section range is preferred.
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
private fun oneSectionWordCards(studyData: StudyData,
                                sectionMap: DisjointRangeMap<out Any>,
                                sectionPrefix: String = ""): Map<Card, IntRange> =
    oneSectionWords(studyData, sectionMap).associate { (word, ranges, section, sectionRange) ->
        println("""%20s occurs %2d times in %s""".format(""""$word"""", ranges.size, sectionPrefix + section))
        val verseListString = ranges.joinToString {
            studyData.verses.valueEnclosing(it)?.toChapterAndVerse() ?: throw Exception()
        }
        val cardBack = listOf(
            sectionPrefix + section,
            "(${ranges.size} times: $verseListString)"
        ).joinToString("<br/>")
        Card(word, cardBack) to sectionRange
    }

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val simpleName = studySet.simpleName
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    // build one-chapter words
    val oneChapterWordCards: Map<Card, IntRange> = oneSectionWordCards(studyData, studyData.chapters, "Chapter ")

    // build one-heading words
    val oneHeadingWordCards: Map<Card, IntRange> = oneSectionWordCards(studyData, studyData.headingCharRanges)

    // combine the two sets of cards
    val fewTimeWordCards: Collection<Card> = mergeCards(oneChapterWordCards, oneHeadingWordCards)
        .sortedBy { it.front }

    // write 'em out
    val outFile = File("$PRODUCTS_DIR/$simpleName/cram", "$simpleName-cram-local-words.tsv")
    CardWriter.writeCards(fewTimeWordCards, outFile)
    println("Wrote local words cards to $outFile")
}
