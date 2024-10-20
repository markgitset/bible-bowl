package net.markdrew.biblebowl.generate.kahoot

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.BRIEF_BOOK_FORMAT
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudyGuideParser
import net.markdrew.biblebowl.model.StudyGuideQuestion
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.StreamStats
import java.io.File
import java.nio.file.Paths

fun main() {
    val studySet: StudySet = StandardStudySet.DEFAULT
    val studyData: StudyData = StudyData.readData(studySet, Paths.get(DATA_DIR))
    val studyGuideQuestions: List<StudyGuideQuestion> = StudyGuideParser.loadStudyGuide(studySet)
//    for (chapter in studyData.chapterRefs) {
//        writeStudyGuideKahoot(studyGuideQuestions, PracticeContent(studyData, chapter))
//    }

    // Kahoot has 120 and 75 characters limits on questions and answers, respectively.  :(
    val questionStats = StreamStats()
    val answerStats = StreamStats()
    val longQuestionStats = StreamStats()
    val longAnswerStats = StreamStats()
    var invalidQuestionOrAnswer = 0
    studyGuideQuestions.forEach { q ->
        val qLength = formatStudyGuideQuestion(q).length.toDouble()
        questionStats.update(qLength)
        if (qLength > 120) longQuestionStats.update(qLength)
        q.choices.forEach {
            val aLength = it.length.toDouble()
            answerStats.update(aLength)
            if (aLength > 75) longAnswerStats.update(aLength)
        }
        if (qLength > 120 || q.choices.any { it.length > 75 }) invalidQuestionOrAnswer++
    }
    println("ALL")
    println("Questions: $questionStats")
    println("Answers: $answerStats")
    println("\nTOO LONG")
    println("Questions: $longQuestionStats")
    println("Answers: $longAnswerStats")
    println("\n$invalidQuestionOrAnswer questions would be omitted")
}

private fun formatStudyGuideQuestion(q: StudyGuideQuestion): String =
    "${q.question} (${q.chapterRef.format(BRIEF_BOOK_FORMAT)}:${q.verseRefString})"

private fun writeStudyGuideKahoot(
    studyGuideQuestions: List<StudyGuideQuestion>,
    content: PracticeContent,
    timeLimit: KahootTimeLimit = KahootTimeLimit.SEC_20
): File {
    val setName = content.studyData.studySet.simpleName

    val chapterRef = content.coveredChapters.single()
    val chapterString = chapterRef.format(BRIEF_BOOK_FORMAT).lowercase().replace(' ', '-')
    val fileName = "study-guide-kahoot-$chapterString"

    val xlsxFile = File("$PRODUCTS_DIR/$setName/kahoot/study-guide/$fileName.xlsx")
    kahoot(xlsxFile) {
        for (sgQuestion in studyGuideQuestions.filter { it.chapterRef == chapterRef }) {
            question(
                KahootQuestion(
                    formatStudyGuideQuestion(sgQuestion),
                    sgQuestion.choices[0],
                    sgQuestion.choices[1],
                    sgQuestion.choices[2],
                    sgQuestion.choices[3],
                    timeLimit,
                    listOf(sgQuestion.correctAnswer + 1)
                )
            )
        }
    }

    println("Wrote $xlsxFile")
    return xlsxFile
}
