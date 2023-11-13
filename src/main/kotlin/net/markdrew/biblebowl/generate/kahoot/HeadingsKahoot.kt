package net.markdrew.biblebowl.generate.kahoot

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.generate.practice.MultiChoiceQuestion
import net.markdrew.biblebowl.generate.practice.PracticeTest
import net.markdrew.biblebowl.generate.practice.Round
import net.markdrew.biblebowl.generate.practice.headingsCluePool
import net.markdrew.biblebowl.model.BRIEF_BOOK_FORMAT
import net.markdrew.biblebowl.model.Book.NUM
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.io.File
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    writeHeadingsKahoot(StandardStudySet.DEFAULT, throughChapter = NUM.chapterRef(1), randomSeed = 1)
}

private fun formatChapterRef(chapterRef: ChapterRef?): String =
    chapterRef?.format(BRIEF_BOOK_FORMAT) ?: "None of these"

private fun writeHeadingsKahoot(
    studySet: StudySet = StandardStudySet.DEFAULT,
    throughChapter: ChapterRef? = null,
    numQuestions: Int = Int.MAX_VALUE,
    timeLimit: KahootTimeLimit = KahootTimeLimit.SEC_10,
    randomSeed: Int = Random.nextInt(1..9_999)
): File {
    val setName = studySet.simpleName
    val content: PracticeContent = StudyData.readData(studySet, Paths.get(DATA_DIR)).practice(throughChapter)

    val practiceTest = PracticeTest(Round.EVENTS, content, numQuestions)

    val headingsToFind: List<MultiChoiceQuestion> = headingsCluePool(practiceTest, nChoices = 4)

    var fileName = "$setName-headings-kahoot"
    if (!content.allChapters) fileName += "-to-${content.coveredChapters.last().toString().lowercase()}"
    fileName += "-%04d".format(randomSeed)

    val xlsxFile = File("$PRODUCTS_DIR/$setName/kahoot/$fileName.xlsx")
    kahoot(xlsxFile) {
        for (multiChoice in headingsToFind) {
            question(
                KahootQuestion(
                    multiChoice.question.question,
                    formatChapterRef(multiChoice.choices[0]),
                    formatChapterRef(multiChoice.choices[1]),
                    formatChapterRef(multiChoice.choices[2]),
                    formatChapterRef(multiChoice.choices[3]),
                    timeLimit,
                    listOf(multiChoice.correctChoice + 1)
                )
            )
        }
    }

    println("Wrote $xlsxFile")
    return xlsxFile
}
