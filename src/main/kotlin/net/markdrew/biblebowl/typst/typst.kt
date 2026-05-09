package net.markdrew.biblebowl.typst

import net.markdrew.biblebowl.showPdf
import java.lang.ProcessBuilder.Redirect
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteIfExists
import kotlin.io.path.nameWithoutExtension

/**
 * Compiles a Typst source file to PDF via the `typst` CLI and returns the resulting `.pdf` path
 *
 * Requires `typst` on PATH. Deletes the source `.typ` file after compilation unless [keepTypFiles] is true.
 *
 * @param showStdIo if true, typst's stdout is written to the parent process's stdout instead of being
 *   discarded
 * @param keepTypFiles preserve the `.typ` source file
 */
fun Path.typstToPdf(
    showStdIo: Boolean = false,
    keepTypFiles: Boolean = false,
): Path {
    val outPath = this.resolveSibling("${this.nameWithoutExtension}.pdf")
    val processBuilder = ProcessBuilder(
        "typst", "compile",
        this.absolutePathString(),
        outPath.absolutePathString(),
    ).inheritIO()
    // without this, it may hang when the output buffer fills up
    if (!showStdIo) processBuilder.redirectOutput(Redirect.DISCARD)
    val process = processBuilder.start()
    process.waitFor()
    if (!keepTypFiles) this.deleteIfExists()
    return outPath.also { println("Wrote $it") }
}

fun main() {
    Path.of("/home/mark/ws/bible-bowl/products/gen/indices/gen-index-numbers.typ").typstToPdf().showPdf()
}