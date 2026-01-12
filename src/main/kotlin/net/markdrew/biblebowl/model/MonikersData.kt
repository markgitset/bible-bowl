package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.parseTsv
import java.io.IOException
import java.net.URL

data class MonikersCard(
    val clue: String,
    val description: String,
    val verseRef: VerseRange,
    val points: Int,
)

object MonikersParser {

    fun parseTsvLine(split: List<String>): MonikersCard {
        require(split.size >= 4) { "Missing fields in: $split" }
        require(split.size <= 4) { "Extra fields in: $split" }
        val clue = split[0]
        val description = split[1]
        val ref = parseVerseRange(split[2])
        val points = split[3].toInt()
        return MonikersCard(clue, description, ref, points)
    }

    fun loadCards(studySet: StandardStudySet): List<MonikersCard> = loadCards(studySet.set)

    fun loadCards(studySet: StudySet): List<MonikersCard> {
        val resourceName = "/${studySet.simpleName}/monikers.tsv"
        val resource: URL = object {}.javaClass.getResource(resourceName)
            ?: throw Exception("Could not find resource '$resourceName'!")
        try {
            return parseTsv(resource.openStream()) { parseTsvLine(it) }
        } catch (e: Exception) {
            throw IOException("Could not load resource $resource", e)
        }
    }
}

fun main() {
    MonikersParser.loadCards(StandardStudySet.JOSHUA_JUDGES_RUTH).take(10).forEach { println(it) }
}