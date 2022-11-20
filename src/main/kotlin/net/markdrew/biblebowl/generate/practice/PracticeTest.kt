package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.PracticeContent
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
    val book: Book = content.bookData.book

    fun buildTexFileName(directory: File? = null): File {
        val bookName = content.bookData.book.name.lowercase()
        val dir = directory ?: File("$PRODUCTS_DIR/$bookName/practice/round${round.number}")
        val chapters = content.coveredChapters
        val fileName = "$bookName-${round.shortName}-ch-${chapters.first}-${chapters.last}-%04d".format(randomSeed)
        dir.mkdirs()
        return File(dir, "$fileName.tex")
    }
}