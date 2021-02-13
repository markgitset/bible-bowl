package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.chupacabra.core.DisjointRangeMap
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import kotlin.math.roundToInt
import kotlin.random.Random

fun main() {
    writeRound5Events(Book.REV, numQuestions = Int.MAX_VALUE)
}

private const val ROUND_5_PACE = 40.0 / 10.0 // questions/minute

data class Question(val question: String, val answer: String)

fun multiChoice(qAndA: Question, chaptersInBook: Int, nChoices: Int = 5): MultiChoiceQuestion {
    val chapter = qAndA.answer.toInt()
    val minChapter = maxOf(1, chapter - 4)
    val maxChapter = minOf(chaptersInBook, chapter + 4)
    val startOffset: Int = Random.nextInt(maxChapter - minChapter - 2)
    val choices = (0 until 4).map { (minChapter + startOffset + it).toString() } + "none of these"
    return MultiChoiceQuestion(qAndA, choices, 4)
}

data class MultiChoiceQuestion(val question: Question, val choices: List<String>, val noneIndex: Int) {
    val correctChoice: Int = choices.indexOf(question.answer).let { if (it < 0) noneIndex else it }
}

private fun writeRound5Events(
    book: Book = Book.REV,
    throughChapter: Int? = null,
    exampleNum: Int? = null,
    date: LocalDate = LocalDate.now(),
    numQuestions: Int = 40,
) {
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    val maxChapter: Int = bookData.chapters.lastEntry().value
    val lastIncludedChapter: Int? = throughChapter?.let {
        require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${book.fullName}!" }
        if (it == maxChapter) null else it
    }

    var cluePool: DisjointRangeMap<String> = bookData.headings
    if (lastIncludedChapter != null) {
        val lastIncludedOffset: Int = bookData.chapterIndex[lastIncludedChapter]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }

    val headingsToFind: List<MultiChoiceQuestion> = cluePool
        .entries.shuffled().take(numQuestions)
        .map { (range, heading) ->
            Question(heading, bookData.chapters.intersectedBy(range).firstEntry().value.toString())
        }.map { multiChoice(it, lastIncludedChapter ?: maxChapter) }

    var fileName = date.toString()
    if (exampleNum != null) fileName += "-$exampleNum"
    fileName += "-${book.fullName}-events"
    if (lastIncludedChapter != null) fileName += "-to-ch-$throughChapter"

    File("output/$bookName/$fileName.tex").writer().use { writer ->
        toLatexInWhatChapter(
            headingsToFind, writer, book, lastIncludedChapter, round = 5, clueType = "events", ROUND_5_PACE
        )
    }

    println("Wrote ${File("output/$bookName/$fileName.tex")}")
}

fun toLatexInWhatChapter(
    questions: List<MultiChoiceQuestion>, appendable: Appendable,
    book: Book,
    throughChapter: Int?,
    round: Int,
    clueType: String,
    pace: Double,
    title: String = "In What Chapter - ${clueType.capitalize()}",
    minutes: Int = (questions.size/pace).roundToInt(),
) {
    val limitedTo: String = throughChapter?.let { " (ONLY chapters 1-$it)" }.orEmpty()
    appendable.appendLine("""
        \documentclass{exam}
        \usepackage{nopageno}
        \usepackage[utf8]{inputenc}
        \setlength{\parindent}{0in}
        \usepackage{multicol}
        \usepackage[letterpaper, left=1.25in, right=1.25in, top=0.25in, bottom=0.25in]{geometry}
        \usepackage{xpatch}
        \xpatchcmd{\oneparchoices}{\penalty -50\hskip 1em plus 1em\relax}{\hfill}{}{}
        
        \begin{document}
        
        \section*{$title \textnormal{(Closed Bible, $minutes min)}\hfill Round $round}

        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in which 
        each of the following ${clueType.toLowerCase()} is found in ${book.fullName}$limitedTo.
        
        \vspace{0.1in}
        
        \begin{questions}
    """.trimIndent())
    questions.forEach { multiChoice ->
        appendable.appendLine("\\question ${multiChoice.question.question}").appendLine()
        appendable.appendLine("\\begin{oneparchoices}")
        for (choice in multiChoice.choices) appendable.appendLine("\\choice $choice")
        appendable.appendLine("\\end{oneparchoices}").appendLine()
    }
    appendable.appendLine("""
        \end{questions}
        
        \newpage
        
        \section*{ANSWERS ${book.fullName} Round $round: $title}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent())
    questions.forEach {
        appendable.appendLine("    \\item ${'A' + it.correctChoice}")
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
