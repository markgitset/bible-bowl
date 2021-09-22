package net.markdrew.biblebowl.latex

import java.io.File

fun File.toPdf(showStdIo: Boolean = false, keepLogFiles: Boolean = false): File {
    val processBuilder = ProcessBuilder(
        "pdflatex",
        "-output-directory", parent,
        "-interaction", "nonstopmode",
        absolutePath
    )
    if (showStdIo) processBuilder.inheritIO()
    processBuilder.start().waitFor()
    if (!keepLogFiles) {
        for (ext in setOf(".aux", ".log")) {
            resolveSibling(nameWithoutExtension + ext).delete()
        }
    }
    return resolveSibling("$nameWithoutExtension.pdf")
}

fun showPdf(pdfFile: File): File = pdfFile.also {
    ProcessBuilder("evince", pdfFile.absolutePath).inheritIO().start()
}

fun main() {
    showPdf(File("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.tex").toPdf())
}