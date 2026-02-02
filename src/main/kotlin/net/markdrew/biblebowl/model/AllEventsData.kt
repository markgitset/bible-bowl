package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.getResource
import net.markdrew.biblebowl.useTsvLines
import java.io.IOException

data class AllEventsCard(
    val event: String,
    val verseRange: VerseRange,
)

object AllEventsParser {

    fun parseTsvLine(split: List<String>): AllEventsCard {
        require(split.size >= 4) { "Missing fields in: $split" }
        require(split.size <= 4) { "Extra fields in: $split" }
        val event = split[2]
        val range = VerseRange.parse(split[3])
        return AllEventsCard(event, range)
    }

    fun loadCards(studySet: StandardStudySet): List<AllEventsCard> = loadCards(studySet.set)

    fun loadCards(studySet: StudySet): List<AllEventsCard> =
        getResource("/${studySet.simpleName}/all-events.tsv").openStream().useTsvLines { tsvLineSeq ->
            try {
                tsvLineSeq.map { parseTsvLine(it) }.toList()
            } catch (e: Exception) {
                throw IOException("Could not parse ${getResource("/${studySet.simpleName}/all-events.tsv")}", e)
            }
        }
}

fun main() {
    AllEventsParser.loadCards(StandardStudySet.JOSHUA_JUDGES_RUTH).take(100).forEach { println(it) }
}