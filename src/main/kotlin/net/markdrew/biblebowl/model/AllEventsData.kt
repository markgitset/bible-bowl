package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.getResource
import net.markdrew.biblebowl.useTsvLines
import java.io.IOException

/** A curated event tied to a verse range; backs round 5 (Events) practice questions */
data class EventCard(
    val event: String,
    val verseRange: VerseRange,
)

/** Loads [EventCard]s from a per-study-set `all-events.tsv` resource */
object AllEventsParser {

    /** Parses one TSV line into an [EventCard]; columns are `verseRange`, `event`. */
    fun parseTsvLine(split: List<String>): EventCard {
        require(split.size >= 2) { "Missing fields in: $split" }
        require(split.size <= 2) { "Extra fields in: $split" }
        val event = split[1]
        val range = VerseRange.parse(split[0])
        return EventCard(event, range)
    }

    /** Loads all event cards for [studySet]. */
    fun loadCards(studySet: StandardStudySet): List<EventCard> = loadCards(studySet.set)

    /**
     * Loads all event cards for [studySet] from the bundled resource `/<simpleName>/all-events.tsv`.
     *
     * @throws IOException if the resource is missing or any line fails to parse
     */
    fun loadCards(studySet: StudySet): List<EventCard> =
        getResource("/${studySet.simpleName}/all-events.tsv").openStream().useTsvLines { tsvLineSeq ->
            try {
                tsvLineSeq.map { parseTsvLine(it) }.toList()
            } catch (e: Exception) {
                throw IOException("Could not parse ${getResource("/${studySet.simpleName}/all-events.tsv")}", e)
            }
        }

    /** Streams event cards through [cardsFun] without materializing the whole list. */
    fun <T> useCards(studySet: StudySet, cardsFun: (Sequence<EventCard>) -> T): T =
        getResource("/${studySet.simpleName}/all-events.tsv").openStream().useTsvLines { tsvLineSeq ->
            cardsFun(tsvLineSeq.map { parseTsvLine(it) })
        }
}

fun main() {
    AllEventsParser.loadCards(StandardStudySet.JOSHUA_JUDGES_RUTH).take(100).forEach { println(it) }
}
