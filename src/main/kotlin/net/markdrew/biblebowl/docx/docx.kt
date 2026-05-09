package net.markdrew.biblebowl.docx

import java.io.File
import java.lang.ProcessBuilder.Redirect.DISCARD
import java.nio.file.Path

/** [docxToPdf] overload returning a [Path]; delegates to the [File] form. */
fun Path.docxToPdf(showStdIo: Boolean = false, keepDocxFiles: Boolean = true): Path =
    toFile().docxToPdf(showStdIo, keepDocxFiles).toPath()

/**
 * Converts a `.docx` to PDF by shelling out to LibreOffice's headless `--convert-to pdf` mode
 *
 * Requires `libreoffice` on PATH. By default the source `.docx` is preserved; pass [keepDocxFiles] = false
 * to delete it after the PDF is written.
 *
 * @param showStdIo if true, libreoffice's stdio is forwarded to the parent process instead of being
 *   discarded
 * @param keepDocxFiles preserve the source `.docx` file
 */
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
