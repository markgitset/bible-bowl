package net.markdrew.biblebowl.generate.practice

enum class Round(
    val number: Int,
    val shortName: String,
    val longName: String,
    val questions: Int,
    val minutes: Int,
) {
    FIND_THE_VERSE(1, "find-the-verse", "Find the Verse", 40, 25),
    QUOTES(4, "quotes", "Quotes", 40, 15),
    EVENTS(5, "events", "Events", 40, 10),
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