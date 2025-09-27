package net.markdrew.biblebowl.docx

import java.io.File
import java.lang.ProcessBuilder.Redirect.DISCARD
import java.nio.file.Path

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
