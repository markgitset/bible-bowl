package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.model.BRIEF_BOOK_FORMAT
import net.markdrew.biblebowl.model.BookFormat
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Files
import java.nio.file.Path
import kotlin.random.Random
import kotlin.random.nextInt

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
//    val practice: PracticeContent = studyData.practice(throughChapter = NUM.chapterRef(22))
//    showPdf(writeRound5Events(PracticeTest(Round.EVENTS, practice)).latexToPdf())

    // PRODUCE THE FULL SET
    val seeds = setOf(10, 20, 30, 40, 50)
    for (throughChapter in studyData.chapterRefs.drop(5)) {
        val practice: PracticeContent = studyData.practice(throughChapter)
        for (seed in seeds) {
            writeRound5Events(PracticeTest(Round.EVENTS, practice, randomSeed = seed))
        }
    }
}

/**
 * A practice question whose correct answer is one or more chapters
 *
 * @param question the question text (usually a heading title or quoted clue)
 * @param answers acceptable correct chapter answers; the first entry is treated as canonical for the
 *   answer key
 * @param answerRefs optional source verse references shown in the answer key
 */
data class Question(
    val question: String,
    val answers: List<ChapterRef>,
    val answerRefs: List<VerseRef>? = null,
)

/**
 * Wraps [qAndA] as a multiple-choice question with [nChoices] options, drawing distractors from chapters
 * adjacent to the correct answer in [coveredChapters]
 *
 * One of the [nChoices] is always "none of these" (returned as a `null` element in [MultiChoiceQuestion.choices]);
 * with probability `1/nChoices` it is also the correct answer.
 */
fun multiChoice(
    qAndA: Question,
    coveredChapters: List<ChapterRef>,
    random: Random,
    nChoices: Int = 5
): MultiChoiceQuestion {
    val nSpecificChoices = nChoices - 1 // nChoices minus 1 for the "none of these" answer
    val answerIsNone = random.nextInt(1..nChoices) == 1 // i.e., 1/nChoices chance of the answer being none of these
    val nCorrectChoices = if (answerIsNone) 0 else 1
    val maxOffset = nSpecificChoices - nCorrectChoices
    val correctAnswers: List<ChapterRef> = qAndA.answers
    val wrongChoicesPool: List<ChapterRef> = correctAnswers
        .flatMap { answer ->
            val i = coveredChapters.indexOf(answer)
            coveredChapters.subList(
                (i - maxOffset).coerceAtLeast(0),
                (i + maxOffset + 1).coerceAtMost(coveredChapters.size)
            )
        }
        .filterNot { it in correctAnswers }
        .distinct()
        .shuffled(random)

    val specificChoices: List<ChapterRef> =
        if (answerIsNone) wrongChoicesPool.take(nSpecificChoices)
        else wrongChoicesPool.take(nSpecificChoices - 1) + correctAnswers.first()

    val allChoices: List<ChapterRef?> = specificChoices.sorted() + null
    return MultiChoiceQuestion(qAndA, allChoices, nSpecificChoices)
}

/**
 * A [Question] presented as multiple choice, with one slot reserved for "none of these"
 *
 * @param choices candidate answers in display order; a `null` element represents the "none of these" choice
 * @param noneIndex position of the "none of these" choice within [choices]
 */
data class MultiChoiceQuestion(val question: Question, val choices: List<ChapterRef?>, val noneIndex: Int) {
    /** Index of the correct choice in [choices]; falls back to [noneIndex] when none of the choices match. */
    val correctChoice: Int = choices.indexOf(question.answers.first()).let { if (it < 0) noneIndex else it }
}

/**
 * Generates a Round 5 ("In What Chapter — Events") test PDF, or null if [practiceTest]'s content covers
 * fewer than three chapters
 *
 * Uses chapter heading titles as the questions and asks contestants to identify which chapter each event
 * occurs in.
 */
fun writeRound5Events(
    practiceTest: PracticeTest,
    productsDir: Path = defaultProductsPath,
): Path? {
    // not enough chapters in practice content to build a reasonable practice test
    if (practiceTest.content.coveredChapters.size < 3) return null

    val texFile: Path = practiceTest.buildTexFileName(productsDir)

    val headingsToFind: List<MultiChoiceQuestion> = headingsCluePool(practiceTest, nChoices = 5)
    Files.newBufferedWriter(texFile).use { writer ->
        toLatexInWhatChapter(writer, practiceTest, headingsToFind)
    }

    return texFile.latexToPdf()
}

/**
 * Samples chapter headings from [practiceTest]'s content into a list of multiple-choice questions
 *
 * Repeated heading titles are merged so all chapters that share the title are accepted as correct.
 */
fun headingsCluePool(practiceTest: PracticeTest, nChoices: Int): List<MultiChoiceQuestion> {
    val content: PracticeContent = practiceTest.content

    val headings: List<Heading> = content.headings()
    val headingsByTitle: Map<String, List<Heading>> = headings.groupBy { it.title }

    return headings.shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { heading ->
            val answers: List<ChapterRef> = listOf(heading.chapterRange.start) +
                // and for the unusual case of repeated chapter headings, add other starting chapter refs
                headingsByTitle.getValue(heading.title).filterNot { it == heading }.map { it.chapterRange.start }
            Question(heading.title, answers)
        }.map { multiChoice(it, content.coveredChapters, practiceTest.random, nChoices) }
}

/** Writes a "In What Chapter" LaTeX test sheet (questions + answer key) for the given multiple-choice questions. */
fun toLatexInWhatChapter(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion>,
) {
    docHeader(appendable)
    val bookFormat = if (practiceTest.content.studyData.isMultiBook) BRIEF_BOOK_FORMAT else NO_BOOK_FORMAT
    val titleString = toLatexTest(appendable, practiceTest, questions, bookFormat)
    newPage(appendable)
    toLatexAnswerKey(appendable, titleString, questions, bookFormat)
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
        \usepackage[letterpaper, left=.7in, right=.7in, top=0.3in, bottom=0.3in]{geometry}
        \usepackage{xpatch}
        \xpatchcmd{\oneparchoices}{\penalty -50\hskip 1em plus 1em\relax}{\hfill}{}{}
        
        \begin{document}
        
    """.trimIndent())
}

private fun toLatexTest(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion>,
    bookFormat: BookFormat,
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
        else " (ONLY ${content.coveredChaptersString()})"
    appendable.appendLine(
        """
        \section*{$titleString}

        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in which 
        each of the following ${round.shortName} is found (i.e., begins) in ${practiceTest.studySet.name}$limitedTo.
        
        \vspace{0.08in}
        
        \begin{questions}
    """.trimIndent()
    )
    questions.forEach { multiChoice ->
        appendable.appendLine("\\question ${multiChoice.question.question}").appendLine()
        appendable.appendLine("\\begin{oneparchoices}")
        for (choice in multiChoice.choices) {
            appendable.appendLine("\\choice ${choice?.format(bookFormat) ?: "none of these"}")
        }
        appendable.appendLine("\\end{oneparchoices}").appendLine()
    }
    appendable.appendLine("\\end{questions}")
    return titleString
}

private fun toLatexAnswerKey(
    appendable: Appendable,
    titleString: String,
    questions: List<MultiChoiceQuestion>,
    bookFormat: BookFormat,
) {
    appendable.appendLine(
        """
        \section*{ANSWER KEY\\$titleString}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent()
    )
    questions.forEach { multiChoice ->
        val q = multiChoice.question
        val prefix = if (bookFormat == NO_BOOK_FORMAT) "chapter " else ""
        val ref: String =
            if (q.answerRefs != null) q.answerRefs.first().format(bookFormat)
            else prefix + q.answers.joinToString(" and ") { it.format(bookFormat) }
        appendable.appendLine("""    \item ${'A' + multiChoice.correctChoice} ($ref)""")
    }
    appendable.appendLine("""\end{enumerate}""")
    appendable.appendLine("""\end{multicols}""")
}
