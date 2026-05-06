package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.getResource
import net.markdrew.biblebowl.useTsvLines
import java.io.IOException

data class EventCard(
    val event: String,
    val verseRange: VerseRange,
)

object AllEventsParser {

    fun parseTsvLine(split: List<String>): EventCard {
        require(split.size >= 2) { "Missing fields in: $split" }
        require(split.size <= 2) { "Extra fields in: $split" }
        val event = split[1]
        val range = VerseRange.parse(split[0])
        return EventCard(event, range)
    }

    fun loadCards(studySet: StandardStudySet): List<EventCard> = loadCards(studySet.set)

    fun loadCards(studySet: StudySet): List<EventCard> =
        getResource("/${studySet.simpleName}/all-events.tsv").openStream().useTsvLines { tsvLineSeq ->
            try {
                tsvLineSeq.map { parseTsvLine(it) }.toList()
            } catch (e: Exception) {
                throw IOException("Could not parse ${getResource("/${studySet.simpleName}/all-events.tsv")}", e)
            }
        }

    fun <T> useCards(studySet: StudySet, cardsFun: (Sequence<EventCard>) -> T): T =
        getResource("/${studySet.simpleName}/all-events.tsv").openStream().useTsvLines { tsvLineSeq ->
            cardsFun(tsvLineSeq.map { parseTsvLine(it) })
        }
}

fun main() {
    AllEventsParser.loadCards(StandardStudySet.JOSHUA_JUDGES_RUTH).take(100).forEach { println(it) }
}