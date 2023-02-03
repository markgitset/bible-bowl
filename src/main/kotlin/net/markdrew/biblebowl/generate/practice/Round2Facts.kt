package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.latex.showPdf
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.toString
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.random.Random

fun main() {
    val studyData = StudyData.readData()
    val practice = PracticeContent(studyData, studyData.chapterRangeOfNChapters(13))
    showPdf(writeRound2Facts(PracticeTest(Round.FACT_FINDER, practice, randomSeed = 2792)).toPdf(keepTexFiles = true))

//    val seeds = setOf(10, 20, 30, 40, 50)
//    val directory = File("matthew-round5-set")
//    for (throughChapter in bookData.chapterRange.drop(5)) {
//        val practice: PracticeContent = bookData.practice(1..throughChapter)
//        for (seed in seeds) {
//            writeRound5Events(PracticeTest(Round.EVENTS, practice, randomSeed = seed), directory).toPdf()
//        }
//    }
}

private data class Question2(
    val question: String,
    val nCorrectAnswers: Int, // the FIRST nCorrectAnswers are correct
    val answers: List<String>,
    val answerRefs: String,
) {
    val correctAnswers = answers.take(nCorrectAnswers)
    val wrongAnswers = answers.drop(nCorrectAnswers)
}

private fun multiChoice2(qAndA: Question2, coveredChapters: ChapterRange, random: Random, nChoices: Int = 3): MultiChoiceQuestion2 {
    val allChoices: List<String> =
        qAndA.wrongAnswers.shuffled(random).take(nChoices - 1) + qAndA.correctAnswers.single()
    return MultiChoiceQuestion2(qAndA, allChoices.shuffled(random))
}

private data class MultiChoiceQuestion2(val question: Question2, val choices: List<String>) {
    val correctChoice: Int = choices.indexOf(question.answers.first())//.let { if (it < 0) noneIndex else it }
}

private fun writeRound2Facts(practiceTest: PracticeTest, directory: File? = null): File {
    val texFile: File = practiceTest.buildTexFileName(directory)

    val factsToFind: List<MultiChoiceQuestion2> = factsCluePool(practiceTest, nChoices = 3)
    texFile.writer().use { writer ->
        toLatexFactFinder(writer, practiceTest, factsToFind)
    }

    println("Wrote $texFile")
    return texFile
}

private fun escapeLatex(s: String): String =
    s.replace("_", "\\_")
private fun factsCluePool(practiceTest: PracticeTest, nChoices: Int): List<MultiChoiceQuestion2> {
    val resourceName = "/${practiceTest.studySet.name.lowercase()}/manual-questions.tsv"
    val resource: URL = object {}.javaClass.getResource(resourceName)
        ?: throw Exception("Could not find resource '$resourceName'!")
    val content = practiceTest.content
    val questions: List<Question2> = parseTsv(resource.openStream()) { fields ->
        val (ref, question, nCorrect) = fields
        val choices = fields.drop(3)
        Question2(escapeLatex(question), nCorrect.toInt(), choices.map { escapeLatex(it) }, ref)
    }.filter { it.nCorrectAnswers == 1 } // for round two, only 1-answer questions used
    return questions.shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { multiChoice2(it, content.coveredChapters, practiceTest.random, nChoices) }
}

private fun toLatexFactFinder(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion2>,
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
    questions: List<MultiChoiceQuestion2>
): String {
    val round: Round = practiceTest.round
    val minutes: Int = round.minutesAtPaceFor(questions.size)

    val seedString = "%04d".format(practiceTest.randomSeed)
    val titleString = "\\#$seedString ${round.longName} " +
            "\\textnormal{(${round.bibleUse} Bible, $minutes min)}" +
            "\\hfill Round ${round.number}"

    val content = practiceTest.content
    val limitedTo: String =
        if (content.allChapters) ""
        else " (ONLY chapters ${content.coveredChapters.toString("-")})"
    appendable.appendLine(
        """
        \section*{$titleString}

        Using your Bible, answer each of the following multiple-choice questions by marking the letter corresponding 
        to the correct response on your answer sheet.  Questions are from ${practiceTest.studySet.name}$limitedTo.
        
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
    questions: List<MultiChoiceQuestion2>,
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
//        val ref: String? = q.answerRefs?.first()?.toChapterAndVerse()
//            else "chapter " + q.answers.joinToString(" and ")
        appendable.appendLine("""    \item ${'A' + it.correctChoice} (${q.answerRefs})""")
    }
    appendable.appendLine("""\end{enumerate}""")
    appendable.appendLine("""\end{multicols}""")
}

private fun <T> parseTsv(stream: InputStream, headerLines: Int = 1, parseFun: (List<String>) -> T): List<T> =
    stream.bufferedReader().useLines { linesSeq ->
        linesSeq.drop(headerLines.coerceAtLeast(0)).map { line ->
            parseFun(line.split('\t'))
        }.toList()
    }