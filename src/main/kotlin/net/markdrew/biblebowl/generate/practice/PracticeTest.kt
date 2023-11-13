package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StudySet
import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

data class PracticeTest(
    val round: Round,
    val content: PracticeContent,
    val numQuestions: Int = round.questions,
    val randomSeed: Int = Random.nextInt(1..9_999),
) {

    val random = Random(randomSeed)
    val studySet: StudySet = content.studyData.studySet

    private fun coveredChaptersForFileName(): String {
        if (content.allChapters) return ""
        val chapters = content.coveredChapters
        val firstBook =
            if (content.studyData.isMultiBook) chapters.first().book.name.lowercase()
            else "chap"
        val lastBook =
            if (chapters.last().book == chapters.first().book) ""
            else chapters.last().book.name.lowercase()
        return "-%s%02dto%s%02d".format(firstBook, chapters.first().chapter, lastBook, chapters.last().chapter)
    }

    fun buildTexFileName(directory: File? = null): File {
        val setName = studySet.simpleName
        val dir = directory ?: File("$PRODUCTS_DIR/$setName/practice/round${round.number}")
        val fileName = "$setName-${round.shortName}${coveredChaptersForFileName()}-seed%04d".format(randomSeed)
        dir.mkdirs()
        return File(dir, "$fileName.tex")
    }
}