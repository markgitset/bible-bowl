package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.parseTsv
import java.net.URL

/**
 * One question from a multiple-choice study guide
 *
 * @param chapterRef the chapter the question is about
 * @param questionNum the 1-based question number within that chapter
 * @param question the question text
 * @param verseRefString the reference (as printed in the study guide) for the answer source
 * @param correctAnswer 0-based index into [choices] of the correct option
 * @param choices the four answer choices, in display order
 */
data class StudyGuideQuestion(
    val chapterRef: ChapterRef,
    val questionNum: Int,
    val question: String,
    val verseRefString: String,
    val correctAnswer: Int,
    val choices: List<String>
)

/** Loads [StudyGuideQuestion]s from a per-study-set `study-guide.tsv` resource */
object StudyGuideParser {

    /**
     * Parses one TSV line into a [StudyGuideQuestion]; expects 10 columns: book, chapter, questionNum,
     * question, verseRef, answer letter (a-d), and four choices.
     *
     * @throws IllegalArgumentException if the column count is wrong, the book can't be parsed, or the
     *   answer letter is outside a-d
     */
    fun parseTsvLine(split: List<String>): StudyGuideQuestion {
        require(split.size >= 10) { "Missing fields in: $split" }
        require(split.size <= 10) { "Extra fields in: $split" }
        val book = Book.parse(split[0]) ?: throw Exception("Could not parse book '${split[0]}'")
        val chapterRef = book.chapterRef(split[1].toInt())
        val questionNum = split[2].toInt()
        val question: String = split[3]
        val verseRef: String = split[4]
        val correctAnswer: Int = split[5].trim().lowercase()[0] - 'a'
        require(correctAnswer in 0..3) { "Correct answer, ${split[5]}, not in expected range!" }
        val choices = split.subList(6, 10)
        return StudyGuideQuestion(chapterRef, questionNum, question, verseRef, correctAnswer, choices)
    }

    /** Loads the entire study guide for [studySet]. */
    fun loadStudyGuide(studySet: StandardStudySet): List<StudyGuideQuestion> = loadStudyGuide(studySet.set)

    /**
     * Loads the entire study guide for [studySet] from the bundled resource `/<simpleName>/study-guide.tsv`.
     *
     * @throws Exception if the resource is missing
     */
    fun loadStudyGuide(studySet: StudySet): List<StudyGuideQuestion> {
        val resourceName = "/${studySet.simpleName}/study-guide.tsv"
        val resource: URL = object {}.javaClass.getResource(resourceName)
            ?: throw Exception("Could not find resource '$resourceName'!")
        return parseTsv(resource.openStream()) { parseTsvLine(it) }
    }
}

fun main() {
    StudyGuideParser.loadStudyGuide(StandardStudySet.LUKE).take(10).forEach { println(it) }
}
