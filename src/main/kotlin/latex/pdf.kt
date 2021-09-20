package net.markdrew.biblebowl.latex

import java.io.File

fun toPdf(latexFile: File): File {
    ProcessBuilder(
        "pdflatex",
        "-output-directory", latexFile.parent,
        "-interaction", "nonstopmode",
        latexFile.absolutePath
    ).inheritIO().start().waitFor()
    for (ext in setOf(".aux", ".log")) {
        latexFile.resolveSibling(latexFile.nameWithoutExtension + ext).delete()
    }
    return latexFile.resolveSibling(latexFile.nameWithoutExtension + ".pdf")
}

fun showPdf(pdfFile: File) {
    val process: Process = ProcessBuilder("evince", pdfFile.absolutePath).inheritIO().start()
}

fun main() {
    val pdf: File = toPdf(File("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.tex"))
    showPdf(pdf)
}