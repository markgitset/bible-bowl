package net.markdrew.biblebowl.latex

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.File
import java.nio.file.Files

class LatexKtTest : StringSpec({
    "latexToPdf fails on invalid latex and preserves logs/tex files" {
        val tempDir = Files.createTempDirectory("latex-test").toFile()
        try {
            val texFile = File(tempDir, "invalid.tex")
            texFile.writeText("""
                \documentclass{article}
                \begin{document}
                \thisisinvalidcommand
                \end{document}
            """.trimIndent())

            val logFile = File(tempDir, "invalid.log")
            val pdfFile = File(tempDir, "invalid.pdf")

            val exception = shouldThrow<IllegalStateException> {
                texFile.latexToPdf(keepTexFiles = false, keepLogFiles = false)
            }

            val isNotInstalled = exception.message!!.contains("Failed to start 'pdflatex'")
            val isCompilationFailed = exception.message!!.contains("Failed to generate PDF")

            (isNotInstalled || isCompilationFailed) shouldBe true
            pdfFile.exists() shouldBe false
            
            // On failure, the tex file should still exist
            texFile.exists() shouldBe true
            if (isCompilationFailed) {
                // If it ran but failed, log file must be preserved
                logFile.exists() shouldBe true
            }
        } finally {
            tempDir.deleteRecursively()
        }
    }
})
