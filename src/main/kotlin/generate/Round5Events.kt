package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import kotlin.random.Random

fun main() {
//    val nSamples = 10
    (22..22).forEach {
        writeRound5Events(Book.REV, throughChapter = it)
    }
}

private data class Question(val question: String, val answer: String)

private fun multiChoice(qAndA: Question, chaptersInBook: Int, nChoices: Int = 5): MultiChoiceQuestion {
    val chapter = qAndA.answer.toInt()
    val minChapter = maxOf(1, chapter - 4)
    val maxChapter = minOf(chaptersInBook, chapter + 4)
    val startOffset: Int = Random.nextInt(maxChapter - minChapter - 2)
    val choices = (0 until 4).map { (minChapter + startOffset + it).toString() } + "none of these"
    return MultiChoiceQuestion(qAndA, choices, 4)
}

private data class MultiChoiceQuestion(val question: Question, val choices: List<String>, val noneIndex: Int) {
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
    val lastChapter: Int = throughChapter ?: bookData.chapters.lastEntry().value
    val throughChapterRange: IntRange? = bookData.chapterIndex[lastChapter]
    requireNotNull(throughChapterRange) { "$lastChapter is not a valid chapter in ${book.fullName}!" }
    val headingsToFind: List<MultiChoiceQuestion> = bookData.headings.enclosedBy(0..throughChapterRange.last)
        .entries.shuffled().take(numQuestions)
        .map { (range, heading) ->
            Question(heading, bookData.chapters.intersectedBy(range).firstEntry().value.toString())
        }.map { multiChoice(it, lastChapter) }

    var fileName = date.toString()
    if (exampleNum != null) fileName += "-$exampleNum"
    fileName += "-${book.fullName}-events"
    if (throughChapter != null) fileName += "-through-ch-$throughChapter"

    File("output/$bookName/$fileName.tex").writer().use { writer ->
        headingsToFind.toLatex(writer, book.fullName, lastChapter)
    }

    println("Wrote ${File("output/$bookName/$fileName.tex")}")
}

private fun List<MultiChoiceQuestion>.toLatex(appendable: Appendable,
                                  book: String,
                                  throughChapter: Int) {
    appendable.appendLine("""
        \documentclass{exam}
        \usepackage{nopageno}
        \usepackage[utf8]{inputenc}
        \setlength{\parindent}{0in}
        \usepackage{multicol}
        \usepackage[letterpaper, left=1.25in, right=1.25in, top=0.5in, bottom=0.5in]{geometry}
        \usepackage{xpatch}
        \xpatchcmd{\oneparchoices}{\penalty -50\hskip 1em plus 1em\relax}{\hfill}{}{}
        
        \begin{document}
        
        \section*{Round 5: In What Chapter--Events (Closed Bible, 10 min)}
        
        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in $book
        (through chapter $throughChapter) in which each of the following events is found.
        
        \vspace{0.1in}
        
        \begin{questions}
    """.trimIndent())
    this.forEach { multiChoice ->
        appendable.appendLine("\\question ${multiChoice.question.question}").appendLine()
        appendable.appendLine("\\begin{oneparchoices}")
        for (choice in multiChoice.choices) appendable.appendLine("\\choice $choice")
        appendable.appendLine("\\end{oneparchoices}").appendLine()
    }
    appendable.appendLine("""
        \end{questions}
        
        \newpage
        
        \section*{ANSWERS Round 5: In What Chapter--Events}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent())
    this.forEach {
        appendable.appendLine("    \\item ${'A' + it.correctChoice}")
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
