package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import java.io.File
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    for (lastChapter in setOf(16, 20, 23, 25, 28, 31, 35, 38, 41, 44, 47, 50)) {
        writeRound5Events(Book.DEFAULT, throughChapter = lastChapter, randomSeed = 1).toPdf()
    }
//    for (i in 1..10) {
//        writeRound5Events(Book.DEFAULT, randomSeed = i).toPdf()
//    }
}

private const val ROUND_5_PACE = 40.0 / 10.0 // questions/minute

data class Question(val question: String, val answer: String, val answerRef: VerseRef? = null)

fun multiChoice(qAndA: Question, chaptersInBook: Int, random: Random, nChoices: Int = 5): MultiChoiceQuestion {
    val chapter = qAndA.answer.toInt()
    val nExplicitChoices = nChoices - 1
    val minChapter = maxOf(1, chapter - nExplicitChoices)
    val maxChapter = minOf(chaptersInBook, chapter + nExplicitChoices)
    val startOffset: Int = random.nextInt(maxChapter - minChapter - 2)
    val choices = (0 until nExplicitChoices).map { (minChapter + startOffset + it).toString() } + "none of these"
    return MultiChoiceQuestion(qAndA, choices, nExplicitChoices)
}

data class MultiChoiceQuestion(val question: Question, val choices: List<String>, val noneIndex: Int) {
    val correctChoice: Int = choices.indexOf(question.answer).let { if (it < 0) noneIndex else it }
}

private fun writeRound5Events(
    book: Book = Book.DEFAULT,
    throughChapter: Int? = null,
    numQuestions: Int = 40,
    randomSeed: Int = Random.nextInt(1..9_999)
): File {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val lastIncludedChapter: Int? = lastIncludedChapter(bookData, throughChapter)

    val headingsToFind: List<MultiChoiceQuestion> =
        headingsCluePool(bookData, lastIncludedChapter, randomSeed, numQuestions, nChoices = 5)

    var fileName = "${book.name.lowercase()}-events"
    if (lastIncludedChapter != null) fileName += "-to-ch-$throughChapter"
    fileName += "-%04d".format(randomSeed)

    val texFile = File("$PRODUCTS_DIR/$bookName/practice/round5/$fileName.tex").also { it.parentFile.mkdirs() }
    texFile.writer().use { writer ->
        toLatexInWhatChapter(
            headingsToFind, writer, book, lastIncludedChapter, round = 5, clueType = "events", ROUND_5_PACE, randomSeed
        )
    }

    println("Wrote $texFile")
    return texFile
}

fun headingsCluePool(
    bookData: BookData,
    lastIncludedChapter: Int?,
    randomSeed: Int,
    numQuestions: Int,
    nChoices: Int,
): List<MultiChoiceQuestion> {
    val random = Random(randomSeed)
    var cluePool: DisjointRangeMap<String> = bookData.headings
    if (lastIncludedChapter != null) {
        val lastIncludedOffset: Int = bookData.chapterIndex[lastIncludedChapter]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }

    return cluePool
        .entries.shuffled(random).take(numQuestions)
        .map { (range, heading) ->
            Question(
                heading,
                bookData.chapters.intersectedBy(range).firstEntry().value.toString()
            )
        }.map { multiChoice(it, lastIncludedChapter ?: bookData.chapterRange.last, random, nChoices) }
}

fun lastIncludedChapter(
    bookData: BookData,
    throughChapter: Int?,
): Int? = throughChapter?.let {
    val maxChapter = bookData.chapterRange.last
    require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${bookData.book.fullName}!" }
    if (it == maxChapter) null else it
}

fun toLatexInWhatChapter(
    questions: List<MultiChoiceQuestion>,
    appendable: Appendable,
    book: Book,
    throughChapter: Int?,
    round: Int,
    clueType: String,
    pace: Double,
    randomSeed: Int,
    title: String = "In What Chapter - ${clueType.capitalize()}",
    minutes: Int = (questions.size / pace + 0.01).toInt(),
) {
    val seedString = "%04d".format(randomSeed)
    val limitedTo: String = throughChapter?.let { " (ONLY chapters 1-$it)" }.orEmpty()
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
        
        \section*{\#$seedString $title \textnormal{(Closed Bible, $minutes min)}\hfill Round $round}

        Without using your Bible, mark on your score sheet the letter corresponding to the chapter number in which 
        each of the following ${clueType.lowercase()} is found in ${book.fullName}$limitedTo.
        
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
        
        \section*{ANSWER KEY\\\#$seedString $title \textnormal{(Closed Bible, $minutes min)}\hfill Round $round}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent())
    questions.forEach {
        val ref: String = it.question.answerRef?.toChapterAndVerse() ?: "chapter ${it.question.answer}"
        appendable.appendLine("    \\item ${'A' + it.correctChoice} ($ref)")
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
