package net.markdrew.biblebowl.generate.kahoot

import net.markdrew.biblebowl.generate.kahoot.KahootTimeLimit.SEC_20
import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.File
import java.nio.file.Path

/** Allowed per-question time limits for a Kahoot quiz, in seconds */
enum class KahootTimeLimit {
    SEC_5, SEC_10, SEC_20, SEC_30, SEC_60, SEC_90, SEC_120, SEC_240
}

/**
 * One Kahoot quiz question with up to four answer choices
 *
 * @param prompt question text (Kahoot caps this at 120 characters)
 * @param answer1 first answer choice (cap: 75 characters)
 * @param answer2 second answer choice
 * @param answer3 third answer choice
 * @param answer4 fourth answer choice
 * @param timeLimit per-question time limit
 * @param correctAnswers 1-based indices of the correct answer(s) within `[answer1..answer4]`
 */
data class KahootQuestion(
    val prompt: String,
    val answer1: Any,
    val answer2: Any,
    val answer3: Any,
    val answer4: Any,
    val timeLimit: KahootTimeLimit,
    val correctAnswers: List<Int>
)

/**
 * Receiver type for the [kahoot] DSL block; writes question rows to the underlying Excel [sheet]
 *
 * The first row is filled in with Kahoot's required column headers when the context is created.
 */
class KahootContext(private val sheet: SXSSFSheet) {

    init {
        sheet.createRow(0).apply {
            createCell(1).setCellValue("Question (max 120 chars)")
            createCell(2).setCellValue("Answer 1 (max 75 chars)")
            createCell(3).setCellValue("Answer 2 (max 75 chars)")
            createCell(4).setCellValue("Answer 3 (max 75 chars)")
            createCell(5).setCellValue("Answer 4 (max 75 chars)")
            createCell(6).setCellValue("Time limit (5, 10, 20, 30, 60, 90, 120, or 240 secs)")
            createCell(7).setCellValue("Correct answers (CSV, but at least one)")
        }
    }

    private var rowNum = 1

    /** Appends a question row to the sheet from a [KahootQuestion]. */
    fun question(kahootQuestion: KahootQuestion) {
        sheet.apply {
            createRow(rowNum).apply {
                createCell(0).setCellValue(rowNum.toDouble())
                createCell(1).setCellValue(kahootQuestion.prompt)
                createCell(2).setCellValue(kahootQuestion.answer1.toString())
                createCell(3).setCellValue(kahootQuestion.answer2.toString())
                createCell(4).setCellValue(kahootQuestion.answer3.toString())
                createCell(5).setCellValue(kahootQuestion.answer4.toString())
                createCell(6).setCellValue(kahootQuestion.timeLimit.name.drop(4).toDouble())
                createCell(7).setCellValue(kahootQuestion.correctAnswers.joinToString(","))
            }
        }
        rowNum++
    }

    /** Convenience overload that builds a [KahootQuestion] inline from positional arguments. */
    fun question(
        prompt: String,
        answer1: Any,
        answer2: Any,
        answer3: Any,
        answer4: Any,
        timeLimit: KahootTimeLimit,
        correctAnswers: List<Int>
    ) {
        sheet.apply {
            createRow(rowNum).apply {
                createCell(0).setCellValue(rowNum.toDouble())
                createCell(1).setCellValue(prompt)
                createCell(2).setCellValue(answer1.toString())
                createCell(3).setCellValue(answer2.toString())
                createCell(4).setCellValue(answer3.toString())
                createCell(5).setCellValue(answer4.toString())
                createCell(6).setCellValue(timeLimit.name.drop(4).toDouble())
                createCell(7).setCellValue(correctAnswers.joinToString(","))
            }
        }
        rowNum++
    }
}

/** [kahoot] overload taking a [Path]; delegates to the [File] form. */
fun kahoot(path: Path, sheetFun: KahootContext.() -> Unit) {
    kahoot(path.toFile(), sheetFun)
}

/**
 * Builds a Kahoot-format `.xlsx` quiz at [file] using a builder block
 *
 * Calls [sheetFun] with a [KahootContext] receiver so questions can be added with the [KahootContext.question]
 * methods. The workbook is streamed to disk so very long quizzes don't keep all rows in memory.
 */
fun kahoot(file: File, sheetFun: KahootContext.() -> Unit) {
    SXSSFWorkbook().use { wb ->  // keep 100 rows in memory, exceeding rows will be flushed to disk
        KahootContext(wb.createSheet()).sheetFun()
        file.parentFile.mkdirs()
        file.outputStream().buffered().use { wb.write(it) }
        wb.dispose() // dispose of temporary files backing this workbook on disk
    }
}

fun main() {
    kahoot(File("/tmp/sxssf2.xlsx")) {
        question("How many books in the Bible?", 27, 39, 66, 82, SEC_20, listOf(3))
        question("How many books in the New Testament?", 27, 39, 66, 82, SEC_20, listOf(1))
        question("How many books in the Old Testament?", 27, 39, 66, 82, SEC_20, listOf(2))
    }
}
