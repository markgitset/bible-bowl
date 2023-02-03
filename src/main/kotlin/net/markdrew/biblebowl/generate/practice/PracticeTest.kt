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

    fun buildTexFileName(directory: File? = null): File {
        val setName = studySet.simpleName
        val dir = directory ?: File("$PRODUCTS_DIR/$setName/practice/round${round.number}")
        val chapters = content.coveredChapters
        val fileName = "$setName-${round.shortName}-chap%02dto%02d-seed%04d"
            .format(chapters.start.chapter, chapters.endInclusive.chapter, randomSeed)
        dir.mkdirs()
        return File(dir, "$fileName.tex")
    }
}