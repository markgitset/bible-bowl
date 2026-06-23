package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.typst.typstToPdf
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

    writeFullSet(studyData) { content, seed, productsDir ->
        writeRound5Events(PracticeTest(Round.EVENTS, content, randomSeed = seed), productsDir)
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

    val typFile: Path = practiceTest.buildTypFileName(productsDir)

    val headingsToFind: List<MultiChoiceQuestion> = headingsCluePool(practiceTest, nChoices = 5)
    Files.newBufferedWriter(typFile).use { writer ->
        toTypstInWhatChapter(writer, practiceTest, headingsToFind)
    }

    return typFile.typstToPdf()
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

private fun escapeTypst(s: String): String =
    s.replace("\\", "\\\\")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("#", "\\#")
        .replace("*", "\\*")
        .replace("_", "\\_")

/** Writes a "In What Chapter" Typst test sheet (questions + answer key) for the given multiple-choice questions. */
fun toTypstInWhatChapter(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion>,
) {
    val bookFormat = if (practiceTest.content.studyData.isMultiBook) BRIEF_BOOK_FORMAT else NO_BOOK_FORMAT
    val titleString = toTypstTest(appendable, practiceTest, questions, bookFormat)
    appendable.appendLine("\n#pagebreak()\n")
    toTypstAnswerKey(appendable, titleString, questions, bookFormat)
}

private fun toTypstTest(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion>,
    bookFormat: BookFormat,
): String {
    val round = practiceTest.round
    val minutes: Int = round.minutesAtPaceFor(questions.size)

    val seedString = "%04d".format(practiceTest.randomSeed)
    val titleString = "#$seedString ${round.longName} " +
            "(${round.bibleUse} Bible, $minutes min)" +
            " \\hfill Round ${round.number}"

    val content = practiceTest.content
    val limitedTo: String =
        if (content.allChapters) ""
        else " (ONLY ${content.coveredChaptersString()})"

    appendable.appendLine(
        """
        #set page(
          paper: "us-letter",
          margin: (left: 0.7in, right: 0.7in, top: 0.3in, bottom: 0.3in)
        )
        #set text(size: 10pt, font: "Libertinus Serif")
        #set par(justify: false)

        #align(center)[
          #text(size: 14pt, weight: "bold")[${escapeTypst(titleString)}]
        ]
        #v(0.1in)

        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in which 
        each of the following ${round.shortName} is found (i.e., begins) in ${escapeTypst(practiceTest.studySet.name)}${escapeTypst(limitedTo)}.
        
        #v(0.08in)
        
        #set enum(indent: 0pt, body-indent: 8pt)
    """.trimIndent()
    )
    questions.forEach { multiChoice ->
        val qText = escapeTypst(multiChoice.question.question)
        appendable.appendLine("+ $qText")
        appendable.appendLine("  #v(4pt)")
        appendable.appendLine("  #grid(")
        appendable.appendLine("    columns: (1fr,) * ${multiChoice.choices.size},")
        appendable.appendLine("    align: left,")
        val choicesStr = multiChoice.choices.mapIndexed { idx, choice ->
            val label = ('A' + idx).toString()
            val text = choice?.format(bookFormat) ?: "none of these"
            "[*$label.* ${escapeTypst(text)}]"
        }.joinToString(", ")
        appendable.appendLine("    $choicesStr")
        appendable.appendLine("  )")
        appendable.appendLine("  #v(8pt)")
    }
    return titleString
}

private fun toTypstAnswerKey(
    appendable: Appendable,
    titleString: String,
    questions: List<MultiChoiceQuestion>,
    bookFormat: BookFormat,
) {
    appendable.appendLine(
        """
        #align(center)[
          #text(size: 14pt, weight: "bold")[ANSWER KEY \ \ ${escapeTypst(titleString)}]
        ]
        #v(0.25in)
        #columns(2)[
          #set enum(indent: 0pt, body-indent: 6pt)
    """.trimIndent()
    )
    questions.forEach { multiChoice ->
        val q = multiChoice.question
        val prefix = if (bookFormat == NO_BOOK_FORMAT) "chapter " else ""
        val ref: String =
            if (q.answerRefs != null) q.answerRefs.first().format(bookFormat)
            else prefix + q.answers.joinToString(" and ") { it.format(bookFormat) }
        val label = ('A' + multiChoice.correctChoice).toString()
        appendable.appendLine("  + *$label* (${escapeTypst(ref)})")
    }
    appendable.appendLine("""]""")
}
