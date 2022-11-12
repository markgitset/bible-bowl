package net.markdrew.biblebowl.latex

import java.io.File

fun File.toPdf(
    showStdIo: Boolean = false,
    keepLogFiles: Boolean = false,
    keepTexFiles: Boolean = false,
    twice: Boolean = false
): File {
    if (twice) toPdf(showStdIo, keepLogFiles = true, keepTexFiles = true, twice = false)

    val processBuilder = ProcessBuilder(
        "pdflatex",
        "-output-directory", parent,
        "-interaction", "nonstopmode",
        absolutePath
    ).inheritIO()
    // without this, it may hang when the output buffer fills up
    if (!showStdIo) processBuilder.redirectOutput(ProcessBuilder.Redirect.DISCARD)
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

fun showPdf(pdfFile: File): File = pdfFile.also {
    ProcessBuilder("evince", pdfFile.absolutePath).inheritIO().start()
}

fun main() {
    showPdf(File("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.tex").toPdf())
}