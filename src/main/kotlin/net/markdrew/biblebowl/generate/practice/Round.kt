package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.generate.practice.BibleUse.CLOSED
import net.markdrew.biblebowl.generate.practice.BibleUse.OPEN
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StudyData
import java.nio.file.Path

/** Whether contestants may use a Bible during a round */
enum class BibleUse {
    CLOSED, OPEN;
    override fun toString(): String = name.lowercase().replaceFirstChar { name.first() }
}

/**
 * The Texas Bible Bowl rounds, with their canonical question counts, time limits, and Bible-use rules
 *
 * @param number 0-5; round 0 is the team Power Round
 * @param shortName slug used in filenames (e.g. "verse-find")
 * @param longName display name as printed on test sheets
 * @param questions standard question count for the round
 * @param minutes standard time limit in minutes
 * @param bibleUse whether contestants may consult their Bible during this round
 */
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

    /** Standard pace of this round in questions per minute */
    val questionsPerMinute: Double = questions.toDouble() / minutes

    /** Time limit in minutes for a test of [questions] question(s) at this round's standard pace. */
    fun minutesAtPaceFor(questions: Int): Int = questions * minutes / this.questions

//    fun practiceTest(): PracticeTest = PracticeTest(this)
}

/** Convenience builder for a [PracticeTest] with [seed] and an optional [numQuestions] override. */
fun practiceTest(round: Round, content: PracticeContent, seed: Int, numQuestions: Int = round.questions): PracticeTest =
    PracticeTest(round, content, numQuestions = numQuestions, randomSeed = seed)

/**
 * Generates the full grid of practice tests for [studyData]: cumulative content through each chapter, times
 * [repsPerRange] random seeds per chapter range
 *
 * The [writer] callback is invoked once per (content, seed) pair and is responsible for actually emitting
 * the desired round(s) for that combination.
 */
fun writeFullSet(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    repsPerRange: Int = 5,
    writer: (content: PracticeContent, seed: Int, productsDir: Path) -> Unit,
) {
    for (throughChapter in studyData.chapterRefs) {
        //if (throughChapter < Book.EXO.chapterRef(20)) continue
        val content = studyData.practice(throughChapter)
        for (seed in 1..repsPerRange) {
            writer(content, 10 * seed, productsDir)
        }
    }
}
