package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.typst.typstToPdf
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.parseTsv
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.random.Random

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
    val practice = PracticeContent(studyData)
    writeRound2Facts(PracticeTest(Round.FACT_FINDER, practice, randomSeed = 2792), defaultProductsPath)
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

private fun multiChoice2(qAndA: Question2, random: Random, nChoices: Int = 3): MultiChoiceQuestion2 {
    val allChoices: List<String> =
        qAndA.wrongAnswers.shuffled(random).take(nChoices - 1) + qAndA.correctAnswers.single()
    return MultiChoiceQuestion2(qAndA, allChoices.shuffled(random))
}

private data class MultiChoiceQuestion2(val question: Question2, val choices: List<String>) {
    val correctChoice: Int = choices.indexOf(question.answers.first())
}

private fun writeRound2Facts(practiceTest: PracticeTest, productsDir: Path): Path {
    val typFile: Path = practiceTest.buildTypFileName(productsDir)

    val factsToFind: List<MultiChoiceQuestion2> = factsCluePool(practiceTest, nChoices = 3)
    Files.newBufferedWriter(typFile).use { writer ->
        toTypstFactFinder(writer, practiceTest, factsToFind)
    }

    println("Wrote $typFile")
    return typFile.typstToPdf(keepTypFiles = true)
}

private fun escapeTypst(s: String): String =
    s.replace("\\", "\\\\")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("#", "\\#")
        .replace("*", "\\*")
        .replace("_", "\\_")

private fun factsCluePool(practiceTest: PracticeTest, nChoices: Int): List<MultiChoiceQuestion2> {
    val resourceName = "/${practiceTest.studySet.name.lowercase()}/manual-questions.tsv"
    val resource: URL = object {}.javaClass.getResource(resourceName)
        ?: throw Exception("Could not find resource '$resourceName'!")
    val questions: List<Question2> = parseTsv(resource.openStream()) { fields ->
        val (ref, question, nCorrect) = fields
        val choices = fields.drop(3)
        Question2(question, nCorrect.toInt(), choices, ref)
    }.filter { it.nCorrectAnswers == 1 } // for round two, only 1-answer questions used
    return questions.shuffled(practiceTest.random).take(practiceTest.numQuestions)
        .map { multiChoice2(it, practiceTest.random, nChoices) }
}

private fun toTypstFactFinder(
    appendable: Appendable,
    practiceTest: PracticeTest,
    questions: List<MultiChoiceQuestion2>,
) {
    val round = practiceTest.round
    val minutes = round.minutesAtPaceFor(questions.size)
    val seedString = "%04d".format(practiceTest.randomSeed)
    val titleLeft = "#$seedString ${round.longName} (${round.bibleUse} Bible, $minutes min)"
    val titleRight = "Round ${round.number}"
    
    val content = practiceTest.content
    val limitedTo = if (content.allChapters) "" else " (ONLY ${content.coveredChaptersString()})"
    
    appendable.appendLine("""
        #set page(
          paper: "us-letter",
          margin: (left: 1in, right: 1in, top: 0.5in, bottom: 0.5in)
        )
        #set text(size: 10pt, font: "Libertinus Serif")
        #set par(justify: false)
        
        #align(center)[
          #text(size: 14pt, weight: "bold")[${escapeTypst(titleLeft)} #h(1fr) ${escapeTypst(titleRight)}]
        ]
        #v(0.1in)
        Using your Bible, answer each of the following multiple-choice questions by marking the letter corresponding to the correct response on your answer sheet. Questions are from ${escapeTypst(practiceTest.studySet.name)}${escapeTypst(limitedTo)}.
        
        #v(0.1in)
        #set enum(indent: 0pt, body-indent: 8pt)
    """.trimIndent())

    questions.forEach { multiChoice ->
        val qText = escapeTypst(multiChoice.question.question)
        appendable.appendLine("+ $qText")
        appendable.appendLine("  #v(4pt)")
        appendable.appendLine("  #grid(")
        appendable.appendLine("    columns: (1fr,) * ${multiChoice.choices.size},")
        appendable.appendLine("    align: left,")
        val choicesStr = multiChoice.choices.mapIndexed { idx, choice ->
            val label = ('A' + idx).toString()
            "[*$label.* ${escapeTypst(choice)}]"
        }.joinToString(", ")
        appendable.appendLine("    $choicesStr")
        appendable.appendLine("  )")
        appendable.appendLine("  #v(8pt)")
    }

    appendable.appendLine("""
        #pagebreak()
        #align(center)[
          #text(size: 14pt, weight: "bold")[
            ANSWER KEY \ \
            ${escapeTypst(titleLeft)} #h(1fr) ${escapeTypst(titleRight)}
          ]
        ]
        #v(0.25in)
        #columns(2)[
          #set enum(indent: 0pt, body-indent: 6pt)
    """.trimIndent())

    questions.forEach { multiChoice ->
        val q = multiChoice.question
        val label = ('A' + multiChoice.correctChoice).toString()
        val ref = escapeTypst(q.answerRefs)
        appendable.appendLine("  + *$label* ($ref)")
    }

    appendable.appendLine("""
        ]
    """.trimIndent())
}
