package net.markdrew.biblebowl.latex

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

fun Path.docxToPdf(showStdIo: Boolean = false, keepDocxFiles: Boolean = true): Path =
    toFile().docxToPdf(showStdIo, keepDocxFiles).toPath()

fun File.docxToPdf(
    showStdIo: Boolean = false,
    keepDocxFiles: Boolean = true,
): File {
    val processBuilder = ProcessBuilder(
        "libreoffice",
        "--convert-to", "pdf",
        absolutePath
    ).inheritIO()
    processBuilder.directory(parentFile)
    // without this, it may hang when the output buffer fills up
    if (!showStdIo) processBuilder.redirectOutput(DISCARD).redirectError(DISCARD)
    val process = processBuilder.start()
    process.waitFor()
    if (!keepDocxFiles) resolveSibling("$nameWithoutExtension.docx").delete()
    return resolveSibling("$nameWithoutExtension.pdf").also {
        println("Wrote $it")
    }
}

fun showPdf(pdfFile: File): File = pdfFile.also {
    ProcessBuilder("evince", pdfFile.absolutePath).inheritIO().start()
}

fun main() {
    showPdf(File("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.tex").latexToPdf())
}