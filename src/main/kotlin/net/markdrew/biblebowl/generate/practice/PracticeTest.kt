package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

data class PracticeTest(
    val round: Round,
    val throughChapter: Int? = null,
    val book: Book = Book.DEFAULT,
    val numQuestions: Int = round.questions,
    val randomSeed: Int = Random.nextInt(1..9_999),
) {

    val random = Random(randomSeed)

//    val bookData = lazy { BookData.readData(book, Paths.get(DATA_DIR)) }

    fun lastIncludedChapter(bookData: BookData): Int? = throughChapter?.let {
        val maxChapter = bookData.chapterRange.last
        require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${bookData.book.fullName}!" }
        if (it == maxChapter) null else it
    }

    fun buildTexFileName(
        lastIncludedChapter: Int?,
        directory: File? = null,
//    setName: String? = null,
    ): File {
        val bookName = book.name.lowercase()
        val dir = directory ?: File("$PRODUCTS_DIR/$bookName/practice/round${round.number}")
        var fileName = "$bookName-${round.shortName}"
        if (lastIncludedChapter != null) fileName += "-to-ch-$lastIncludedChapter"
        fileName += "-%04d".format(randomSeed)
        dir.mkdirs()
        return File(dir, "$fileName.tex")
    }
}