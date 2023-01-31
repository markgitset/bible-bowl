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
    val bookData = BookData.readData()
    val practice: PracticeContent = bookData.practice(1..14)
    showPdf(writeRound5Events(PracticeTest(Round.EVENTS, practice)).toPdf())

//    val seeds = setOf(10, 20, 30, 40, 50)
//    val directory = File("matthew-round5-set")
//    for (throughChapter in bookData.chapterRange.drop(5)) {
//        val practice: PracticeContent = bookData.practice(1..throughChapter)
//        for (seed in seeds) {
//            writeRound5Events(PracticeTest(Round.EVENTS, practice, randomSeed = seed), directory).toPdf()
//        }
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

private fun writeRound5Events(practiceTest: PracticeTest, directory: File? = null): File {
    val texFile: File = practiceTest.buildTexFileName(directory)

    val headingsToFind: List<MultiChoiceQuestion> = headingsCluePool(practiceTest, nChoices = 5)
    texFile.writer().use { writer ->
        toLatexInWhatChapter(writer, practiceTest, headingsToFind)
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
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion>,
) {
    docHeader(appendable)
    val titleString = toLatexTest(appendable, practiceTest, questions)
    newPage(appendable)
    toLatexAnswerKey(appendable, titleString, questions)
    docFooter(appendable)
}

private fun newPage(appendable: Appendable) {
    appendable.appendLine("\n\\newpage\n")
}

private fun docFooter(appendable: Appendable) {
    appendable.appendLine("""\end{document}""")
}

private fun docHeader(appendable: Appendable) {
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
        
    """.trimIndent())
}

private fun toLatexTest(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion>
): String {
    val round = practiceTest.round
    val minutes: Int = round.minutesAtPaceFor(questions.size)

    val seedString = "%04d".format(practiceTest.randomSeed)
    val titleString = "\\#$seedString ${round.longName} " +
            "\\textnormal{(${round.bibleUse} Bible, $minutes min)}" +
            "\\hfill Round ${round.number}"

    val content = practiceTest.content
    val limitedTo: String =
        if (content.allChapters) ""
        else " (ONLY chapters ${content.coveredChapters.first}-${content.coveredChapters.last})"
    appendable.appendLine(
        """
        \section*{$titleString}

        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in which 
        each of the following ${round.shortName} is found (begins) in ${practiceTest.book.fullName}$limitedTo.
        
        \vspace{0.1in}
        
        \begin{questions}
    """.trimIndent()
    )
    questions.forEach { multiChoice ->
        appendable.appendLine("\\question ${multiChoice.question.question}").appendLine()
        appendable.appendLine("\\begin{oneparchoices}")
        for (choice in multiChoice.choices) appendable.appendLine("\\choice $choice")
        appendable.appendLine("\\end{oneparchoices}").appendLine()
    }
    appendable.appendLine("\\end{questions}")
    return titleString
}

private fun toLatexAnswerKey(
    appendable: Appendable,
    titleString: String,
    questions: List<MultiChoiceQuestion>,
) {
    appendable.appendLine(
        """
        \section*{ANSWER KEY\\$titleString}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent()
    )
    questions.forEach {
        val q = it.question
        val ref: String =
            if (q.answerRefs != null) q.answerRefs.first().toChapterAndVerse()
            else "chapter " + q.answers.joinToString(" and ")
        appendable.appendLine("""    \item ${'A' + it.correctChoice} ($ref)""")
    }
    appendable.appendLine("""\end{enumerate}""")
    appendable.appendLine("""\end{multicols}""")
}
