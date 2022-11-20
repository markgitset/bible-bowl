package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.latex.showPdf
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    val practice: PracticeContent = BookData.readData().practice(1..13)
    writeRound5Events(PracticeTest(Round.EVENTS, practice)).toPdf()
//    for (lastChapter in setOf(30)) {
//        writeRound5Events(Book.DEFAULT, throughChapter = lastChapter, randomSeed = 1).toPdf()
//    }
//    for (i in 1..10) {
//        writeRound5Events(Book.DEFAULT, randomSeed = i).toPdf()
//    }
}

data class Question(
    val question: String,
    // the FIRST answer is assumed to be the arbitrarily chosen correct answer
    val answers: List<String>,
    val answerRefs: List<VerseRef>? = null,
)

fun multiChoice(qAndA: Question, coveredChapters: IntRange, random: Random, nChoices: Int = 5): MultiChoiceQuestion {
    val nSpecificChoices = nChoices - 1 // nChoices minus 1 for the "none of these" answer
    val answerIsNone = random.nextInt(1..nChoices) == 1 // i.e., 1/nChoices chance of the answer being none of these
    val nCorrectChoices = if (answerIsNone) 0 else 1
    val maxOffset = ((nSpecificChoices + 0.4) / qAndA.answers.size).roundToInt() - nCorrectChoices
    val correctAnswers: List<Int> = qAndA.answers.map { it.toInt() }
    val wrongChoicesPool: List<Int> = correctAnswers
        .flatMap { answer -> ((answer - maxOffset)..(answer + maxOffset)).intersect(coveredChapters) }
        .filterNot { it in correctAnswers }
        .distinct()
        .shuffled(random)

    val specificChoices: List<Int> =
        if (answerIsNone) wrongChoicesPool.take(nSpecificChoices)
        else wrongChoicesPool.take(nSpecificChoices - 1) + correctAnswers.first()

    val allChoices = specificChoices.sorted().map { it.toString() } + "none of these"
    return MultiChoiceQuestion(qAndA, allChoices, nSpecificChoices)
}

data class MultiChoiceQuestion(val question: Question, val choices: List<String>, val noneIndex: Int) {
    val correctChoice: Int = choices.indexOf(question.answers.first()).let { if (it < 0) noneIndex else it }
}

private fun writeRound5Events(practiceTest: PracticeTest): File {
    val texFile: File = practiceTest.buildTexFileName()

    val headingsToFind: List<MultiChoiceQuestion> = headingsCluePool(practiceTest, nChoices = 5)
    texFile.writer().use { writer ->
        toLatexInWhatChapter(headingsToFind, writer, practiceTest)
    }

    println("Wrote $texFile")
    return texFile
}

fun headingsCluePool(practiceTest: PracticeTest, nChoices: Int): List<MultiChoiceQuestion> {
    val content = practiceTest.content
    val headings = content.headings()
    val headingsByTitle: Map<String, List<Heading>> = headings.groupBy { it.title }

    return headings.shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { heading ->
            val answers = listOf(heading.chapterRange.first) +
                headingsByTitle[heading.title]?.filterNot { it == heading }?.map { it.chapterRange.first }.orEmpty()
            Question(heading.title, answers.map { it.toString() })
        }.map { multiChoice(it, content.coveredChapters, practiceTest.random, nChoices) }
}

fun toLatexInWhatChapter(
    questions: List<MultiChoiceQuestion>,
    appendable: Appendable,
    practiceTest: PracticeTest,
    title: String = "In What Chapter - ${practiceTest.round.longName}",
    minutes: Int = practiceTest.round.minutesAtPaceFor(questions.size),
) {
    val seedString = "%04d".format(practiceTest.randomSeed)
    val content = practiceTest.content
    val limitedTo: String =
        if (content.allChapters) ""
        else " (ONLY chapters ${content.coveredChapters.first}-${content.coveredChapters.last})"
    appendable.appendLine("""
        \documentclass{exam}
        \usepackage{nopageno}
        \usepackage[utf8]{inputenc}
        \setlength{\parindent}{0in}
        \usepackage{multicol}
        \usepackage[letterpaper, left=1in, right=1in, top=0.5in, bottom=0.5in]{geometry}
        \usepackage{xpatch}
        \xpatchcmd{\oneparchoices}{\penalty -50\hskip 1em plus 1em\relax}{\hfill}{}{}
        
        \begin{document}
        
        \section*{\#$seedString $title \textnormal{(Closed Bible, $minutes min)}\hfill Round ${practiceTest.round.number}}

        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in which 
        each of the following ${practiceTest.round.shortName} is found (begins) in ${practiceTest.book.fullName}$limitedTo.
        
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
        
        \section*{ANSWER KEY\\\#$seedString $title \textnormal{(Closed Bible, $minutes min)}\hfill Round ${practiceTest.round.number}}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent())
    questions.forEach {
        val ref: String = if (it.question.answerRefs != null) {
            it.question.answerRefs.first().toChapterAndVerse()
        } else {
            "chapter " + it.question.answers.joinToString(" and ")
        }
        appendable.appendLine("    \\item ${'A' + it.correctChoice} ($ref)")
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
