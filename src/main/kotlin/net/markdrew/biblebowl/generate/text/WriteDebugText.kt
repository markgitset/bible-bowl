package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.io.File
import java.io.PrintWriter

private val logger: KLogger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    val studySet = StandardStudySet.parse(args.firstOrNull())
    val studyData = StudyData.readData(studySet)
//    val customHighlights = mapOf(
////        "divineColor" to divineNames.map { it.toRegex() }.toSet(),
//        "namesColor" to setOf("John the Baptist".toRegex()),
//    )
//    writeBibleText(book, TextOptions(fontSize = 12, names = false, numbers = false, uniqueWords = true))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = false))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = true))
    writeBibleDocDebug(
        studyData,
//        TextOptions(names = true, numbers = true, uniqueWords = true, customHighlights = customHighlights)
    )
}

fun writeBibleDocDebug(studyData: StudyData, opts: TextOptions<String> = TextOptions()) {
    val name = studyData.studySet.simpleName
    val outputFile = File("$name-debug-text-${opts.fileNameSuffix}.txt")
//    val outputFile = File("$PRODUCTS_DIR/$name/text/$name-bible-text-${opts.fileNameSuffix}.docx")
    PrintWriter(outputFile).use { out ->
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleTextRenderer.annotatedDoc(studyData, opts)
        annotatedDoc.textRuns().forEach { run ->
            val annotationsString = run.annotations.joinToString(", ") { it.toShortString() }
            val text = annotatedDoc.docText.substring(run.range).replace("\n", "\\n")
            out.println("${run.range}")
            out.println("   $annotationsString")
            out.println("   '$text'")
        }
    }
    println("Wrote $outputFile")
}
