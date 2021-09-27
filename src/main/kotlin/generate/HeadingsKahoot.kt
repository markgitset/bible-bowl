package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.io.File
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    writeHeadingsKahoot(Book.DEFAULT, throughChapter = 20, randomSeed = 0)
}

private fun writeHeadingsKahoot(
    book: Book = Book.DEFAULT,
    throughChapter: Int? = null,
    numQuestions: Int = 20,
    timeLimit: KahootTimeLimit = KahootTimeLimit.SEC_10,
    randomSeed: Int = Random.nextInt(1..9_999)
): File {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val lastIncludedChapter: Int? = lastIncludedChapter(bookData, throughChapter)

    val headingsToFind: List<MultiChoiceQuestion> =
        headingsCluePool(bookData, lastIncludedChapter, randomSeed, numQuestions)

    var fileName = "${book.name.lowercase()}-headings-kahoot"
    if (lastIncludedChapter != null) fileName += "-to-ch-$throughChapter"
    fileName += "-%04d".format(randomSeed)

    val xlsxFile = File("$PRODUCTS_DIR/$bookName/practice/$fileName.xlsx")
    kahoot(xlsxFile) {
        for (multiChoice in headingsToFind) {
            question(
                KahootQuestion(
                    multiChoice.question.question,
                    multiChoice.choices[0],
                    multiChoice.choices[1],
                    multiChoice.choices[2],
                    multiChoice.choices[3],
                    timeLimit,
                    listOf(multiChoice.correctChoice + 1)
                )
            )
        }
    }

    println("Wrote $xlsxFile")
    return xlsxFile
}
