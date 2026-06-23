package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Files
import java.nio.file.Path
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * One practice test instance: a [round], its source [content], and a fixed [randomSeed] for reproducibility
 *
 * Defaulting [numQuestions] from the round and using a deterministic [randomSeed] means generating the same
 * test twice (same seed, same content) yields identical questions.
 *
 * @param numQuestions overrides [Round.questions] when fewer questions are wanted
 * @param randomSeed seed used to drive question selection and shuffling
 */
data class PracticeTest(
    val round: Round,
    val content: PracticeContent,
    val numQuestions: Int = round.questions,
    val randomSeed: Int = Random.nextInt(1..9_999),
) {

    /** [Random] instance keyed by [randomSeed]; reusable for any sampling needed by the generator. */
    val random = Random(randomSeed)

    /** Convenience accessor for `content.studyData.studySet`. */
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

    /**
     * Builds the standard `.typ` output path for this test under [productsDir], encoding the round, covered
     * chapter range, and seed. Creates parent directories as a side effect.
     */
    fun buildTypFileName(productsDir: Path = defaultProductsPath): Path {
        val setName = studySet.simpleName
        val fileName: String = "$setName-${round.shortName}${coveredChaptersForFileName()}-seed%04d.typ"
            .format(randomSeed)
        return productsDir.resolve(setName, "practice", "round${round.number}", fileName)
            .also { Files.createDirectories(it.parent) }
    }
}