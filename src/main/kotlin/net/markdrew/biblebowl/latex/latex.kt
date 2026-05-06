package net.markdrew.biblebowl.latex

import net.markdrew.biblebowl.showPdf
import java.io.File
import java.lang.ProcessBuilder.Redirect.DISCARD
import java.nio.file.Path

fun Path.latexToPdf(
    showStdIo: Boolean = false,
    keepLogFiles: Boolean = false,
    keepTexFiles: Boolean = false,
    twice: Boolean = false
): Path = toFile().latexToPdf(showStdIo, keepLogFiles, keepTexFiles, twice).toPath()

fun File.latexToPdf(
    showStdIo: Boolean = false,
    keepLogFiles: Boolean = false,
    keepTexFiles: Boolean = false,
    twice: Boolean = false
): File {
    if (twice) latexToPdf(showStdIo, keepLogFiles = true, keepTexFiles = true, twice = false)

    val processBuilder = ProcessBuilder(
        "pdflatex",
        "-output-directory", parent,
        "-interaction", "nonstopmode",
        absolutePath
    ).inheritIO()
    // without this, it may hang when the output buffer fills up
    if (!showStdIo) processBuilder.redirectOutput(DISCARD)
    val process = processBuilder.start()
    process.waitFor()
    if (!keepLogFiles) {
        for (ext in setOf(".aux", ".log")) {
            resolveSibling(nameWithoutExtension + ext).delete()
        }
    }
    if (!keepTexFiles) resolveSibling("$nameWithoutExtension.tex").delete()
    return resolveSibling("$nameWithoutExtension.pdf").also {
        println("Wrote $it")
    }
}

fun main() {
    Path.of("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.tex").latexToPdf().showPdf()
}