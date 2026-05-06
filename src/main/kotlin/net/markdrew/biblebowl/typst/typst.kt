package net.markdrew.biblebowl.typst

import net.markdrew.biblebowl.showPdf
import java.lang.ProcessBuilder.Redirect
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteIfExists
import kotlin.io.path.nameWithoutExtension

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