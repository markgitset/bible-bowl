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
    writeBibleDocDebug(studyData)
}

/**
 * Writes a plain-text debug dump of [studyData]'s annotated runs to a file in the working directory
 *
 * Each line shows a run's range, the active annotations, and the raw text (with newlines escaped).
 * Useful for diagnosing renderer issues without going through LaTeX/DOCX compilation.
 */
fun writeBibleDocDebug(
    studyData: StudyData,
    options: TextOptions = TextOptions(),
    preset: TextPreset = Presets.tbb,
) {
    val name = studyData.studySet.simpleName
    val suffix = BibleTextPipeline.fileNameSuffix(options, preset)
    val outputFile = File("$name-debug-text-$suffix.txt")
    PrintWriter(outputFile).use { out ->
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleAnnotationPipeline.build(studyData, options)
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
