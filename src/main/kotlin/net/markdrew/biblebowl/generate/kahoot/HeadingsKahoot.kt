package net.markdrew.biblebowl.generate.kahoot

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.generate.practice.MultiChoiceQuestion
import net.markdrew.biblebowl.generate.practice.PracticeTest
import net.markdrew.biblebowl.generate.practice.Round
import net.markdrew.biblebowl.generate.practice.headingsCluePool
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.io.File
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    writeHeadingsKahoot(Book.DEFAULT, throughChapter = 10, randomSeed = 1)
}

private fun writeHeadingsKahoot(
    book: Book = Book.DEFAULT,
    throughChapter: Int? = null,
    numQuestions: Int = Int.MAX_VALUE,
    timeLimit: KahootTimeLimit = KahootTimeLimit.SEC_10,
    randomSeed: Int = Random.nextInt(1..9_999)
): File {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val practiceTest = PracticeTest(Round.EVENTS, throughChapter, book, numQuestions)
    val lastIncludedChapter: Int? = practiceTest.lastIncludedChapter(bookData)

    val headingsToFind: List<MultiChoiceQuestion> =
        headingsCluePool(bookData, lastIncludedChapter, practiceTest, nChoices = 4)

    var fileName = "${book.name.lowercase()}-headings-kahoot"
    if (lastIncludedChapter != null) fileName += "-to-ch-$throughChapter"
    fileName += "-%04d".format(randomSeed)

    val xlsxFile = File("$PRODUCTS_DIR/$bookName/kahoot/$fileName.xlsx")
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
