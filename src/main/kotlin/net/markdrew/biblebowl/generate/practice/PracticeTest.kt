package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Files
import java.nio.file.Path
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

    fun buildTexFileName(productsDir: Path = Path.of(PRODUCTS_DIR_NAME)): Path {
        val setName = studySet.simpleName
        val fileName: String = "$setName-${round.shortName}${coveredChaptersForFileName()}-seed%04d.tex"
            .format(randomSeed)
        return productsDir.resolve(setName, "practice", "round${round.number}", fileName)
            .also { Files.createDirectories(it.parent) }
    }
}