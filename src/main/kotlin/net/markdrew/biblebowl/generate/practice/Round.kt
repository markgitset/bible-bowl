package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.generate.practice.BibleUse.CLOSED
import net.markdrew.biblebowl.generate.practice.BibleUse.OPEN

enum class BibleUse { 
    CLOSED, OPEN;
    override fun toString(): String = name.lowercase().replaceFirstChar { name.first() }
}

enum class Round(
    val number: Int,
    val shortName: String,
    val longName: String,
    val questions: Int,
    val minutes: Int,
    val bibleUse: BibleUse,
) {
    POWER(0, "power", "Power Round", 50, 25, CLOSED),
    FIND_THE_VERSE(1, "verse-find", "Find the Verse", 40, 25, OPEN),
    FACT_FINDER(2, "facts", "Fact Finder", 40, 20, OPEN),
    IDENTIFICATION(3, "id", "Identification", 40, 20, OPEN),
    QUOTES(4, "quotes", "In What Chapter - Quotes", 40, 15, CLOSED),
    EVENTS(5, "events", "In What Chapter - Events", 40, 10, CLOSED),
    ;

    /**
     * The pace of this type of test in questions per minute
     */
    val questionsPerMinute: Double = questions.toDouble() / minutes

    /**
     * Compute how many minutes to allow for a test at the standard pace, but with a nonstandard number of [questions].
     */
    fun minutesAtPaceFor(questions: Int): Int = questions * minutes / this.questions
}