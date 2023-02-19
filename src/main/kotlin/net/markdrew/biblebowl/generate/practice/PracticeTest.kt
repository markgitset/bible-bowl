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
            if (content.studyData.isMultiBook) chapters.start.book.name.lowercase()
            else "chap"
        val lastBook =
            if (chapters.endInclusive.book == chapters.start.book) ""
            else chapters.endInclusive.book.name.lowercase()
        return "-%s%02dto%s%02d".format(firstBook, chapters.start.chapter, lastBook, chapters.endInclusive.chapter)
    }

    fun buildTexFileName(directory: File? = null): File {
        val setName = studySet.simpleName
        val dir = directory ?: File("$PRODUCTS_DIR/$setName/practice/round${round.number}")
        val fileName = "$setName-${round.shortName}${coveredChaptersForFileName()}-seed%04d".format(randomSeed)
        dir.mkdirs()
        return File(dir, "$fileName.tex")
    }
}