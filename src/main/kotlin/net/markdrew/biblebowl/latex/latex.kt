package net.markdrew.biblebowl.latex

import net.markdrew.biblebowl.showPdf
import java.io.File
import java.lang.ProcessBuilder.Redirect.DISCARD
import java.nio.file.Path

/** [latexToPdf] overload returning a [Path]; delegates to the [File] form. */
fun Path.latexToPdf(
    showStdIo: Boolean = false,
    keepLogFiles: Boolean = false,
    keepTexFiles: Boolean = false,
    twice: Boolean = false
): Path = toFile().latexToPdf(showStdIo, keepLogFiles, keepTexFiles, twice).toPath()

/**
 * Compiles a LaTeX source file to PDF via `pdflatex` and returns the resulting `.pdf` file
 *
 * Requires `pdflatex` on PATH. Cleans up `.aux`, `.log`, and `.tex` siblings unless the corresponding
 * `keep…` flags are set.
 *
 * @param showStdIo if true, pdflatex's stdout is written to the parent process's stdout instead of being
 *   discarded (useful for diagnosing failures)
 * @param keepLogFiles preserve `.aux` and `.log` next to the output PDF
 * @param keepTexFiles preserve the `.tex` source file
 * @param twice run `pdflatex` twice so cross-references resolve correctly
 */
fun File.latexToPdf(
    showStdIo: Boolean = false,
    keepLogFiles: Boolean = false,
    keepTexFiles: Boolean = false,
    twice: Boolean = false
): File {
    val pdfFile = resolveSibling("$nameWithoutExtension.pdf")

    // Delete pre-existing PDF to ensure we detect a fresh failure/success correctly
    if (pdfFile.exists()) {
        pdfFile.delete()
    }

    var shouldKeepLogFiles = keepLogFiles
    val shouldKeepTexFiles = keepTexFiles

    if (twice) {
        latexToPdf(showStdIo, keepLogFiles = true, keepTexFiles = true, twice = false)
    }

    val outputDir = parent ?: "."
    var exitCode = -1
    val tempStderrFile = File.createTempFile("pdflatex-stderr-", ".log")

    try {
        val processBuilder = ProcessBuilder(
            "pdflatex",
            "-output-directory", outputDir,
            "-interaction", "nonstopmode",
            absolutePath
        ).inheritIO()
        
        if (!showStdIo) {
            processBuilder.redirectOutput(DISCARD)
            processBuilder.redirectError(tempStderrFile)
        }

        val process = processBuilder.start()
        exitCode = process.waitFor()
    } catch (e: java.io.IOException) {
        tempStderrFile.delete()
        val msg = "Failed to start 'pdflatex'. Is it installed and available on your PATH? (Error: ${e.message})"
        System.err.println("Error: $msg")
        throw IllegalStateException(msg, e)
    }

    val success = pdfFile.exists()
    val stderrText = if (tempStderrFile.exists()) tempStderrFile.readText() else ""
    tempStderrFile.delete()

    // Check if METAFONT font generation occurred (common in BasicTeX)
    val fontGenerationOccurred = stderrText.contains("mktexpk") || stderrText.contains("kpathsea")

    if (fontGenerationOccurred) {
        System.err.println("Note: pdflatex compiled missing fonts (like ec/tcrm) on-the-fly. This slows down PDF generation.")
        System.err.println("      To fix this, install the cm-super font package: tlmgr install cm-super")
        shouldKeepLogFiles = true
    }

    if (success) {
        if (!shouldKeepLogFiles) {
            for (ext in setOf(".aux", ".log")) {
                resolveSibling(nameWithoutExtension + ext).delete()
            }
        }
        if (!shouldKeepTexFiles) resolveSibling("$nameWithoutExtension.tex").delete()
        println("Wrote $pdfFile")
    } else {
        val logFile = resolveSibling("$nameWithoutExtension.log")
        System.err.println("Error: PDF generation failed for $absolutePath.")
        if (exitCode != 0) {
            System.err.println("pdflatex exited with code $exitCode")
        }
        if (stderrText.isNotBlank()) {
            System.err.println("stderr output:")
            System.err.println(stderrText.prependIndent("  "))
        }
        if (logFile.exists()) {
            System.err.println("LaTeX log file preserved at: ${logFile.absolutePath}")
        }
        val msg = "Failed to generate PDF. pdflatex exit code: $exitCode." +
                if (logFile.exists()) " Log file kept at ${logFile.absolutePath}" else ""
        throw IllegalStateException(msg)
    }

    return pdfFile
}

fun main() {
    Path.of("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.tex").latexToPdf().showPdf()
}