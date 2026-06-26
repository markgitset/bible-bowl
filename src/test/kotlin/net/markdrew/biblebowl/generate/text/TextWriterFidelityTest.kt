package net.markdrew.biblebowl.generate.text

import io.kotest.core.spec.style.StringSpec
import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.generate.text.docx.DocxBibleTextWriter
import net.markdrew.biblebowl.generate.text.typst.TypstBibleTextWriter
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.util.zip.ZipFile

/**
 * Snapshot fidelity tests. Generates documents under `/tmp/` and compares the output markup against
 * baseline snapshots checked into the repository (`src/test/resources/text-baselines/`).
 *
 * The .docx is compared via its normalized `word/document.xml` (the docx4j-serialized namespace
 * declarations on the root element vary between runs and are stripped). The .tex and .typ are
 * compared as-is.
 *
 * Skipped if `~/.tbb/data/josh-judg-ruth/` isn't indexed locally — this is a developer-local check.
 *
 * **Seeding snapshots**: run with `-Dregenerate-baseline-texts=true` to overwrite the committed
 * baselines in-place (writes directly to `src/test/resources/text-baselines/`). Use this when an
 * intentional output change is made and the snapshots need to be regenerated.
 */
class TextWriterFidelityTest : StringSpec({
    System.setProperty("skip-pdf-generation", "true")

    val regenerate = System.getProperty("regenerate-baseline-texts") != null
    val studySetDir = defaultDataPath.resolve("josh-judg-ruth")
    val dataAvailable = Files.isDirectory(studySetDir)

    val options = TextOptions(
        testDate = LocalDate.of(2026, 3, 28),
        fontSize = 10,
        twoColumns = true,
        useHeadingsForChapters = true,
        underlineUniqueWords = true,
        customHighlights = fullHighlightPalette(),
        mainFont = "Times New Roman",
        verseNumFont = "Liberation Sans",
        headingFont = "Liberation Sans",
        chapterFontSize = 14,
        headingFontSize = 12,
        footnoteFontSize = 9,
        justified = true,
    )

    fun loadStudyData(): StudyData = StudyData.readData(StandardStudySet.JOSHUA_JUDGES_RUTH.set)
    fun freshOutputDir(): Path = Files.createTempDirectory("bible-bowl-fidelity-")

    "DOCX writer source matches committed snapshot".config(enabled = dataAvailable) {
        val outDir = freshOutputDir()
        val docxPath = BibleTextPipeline.generate(
            loadStudyData(), options, Presets.marks, DocxBibleTextWriter(), outDir
        )
        val actual = normalizedDocumentXml(docxPath)
        compareWithBaseline("docx-marks-full.xml", actual, regenerate)
    }

    "Typst writer source matches committed snapshot".config(enabled = dataAvailable) {
        val outDir = freshOutputDir()
        val typPath = BibleTextPipeline.generate(
            loadStudyData(), options, Presets.marks, TypstBibleTextWriter(), outDir
        )
        val actual = Files.readString(typPath)
        compareWithBaseline("typst-marks-full.typ", actual, regenerate)
    }
})

private const val BASELINE_RESOURCE_DIR = "text-baselines"
private val BASELINE_DEV_DIR: Path = Path.of("src/test/resources/$BASELINE_RESOURCE_DIR")

/**
 * Asserts [actual] equals the committed baseline under [BASELINE_RESOURCE_DIR], or overwrites the
 * dev-tree baseline file when [regenerate] is true.
 */
private fun compareWithBaseline(name: String, actual: String, regenerate: Boolean) {
    if (regenerate) {
        Files.createDirectories(BASELINE_DEV_DIR)
        val target = BASELINE_DEV_DIR.resolve(name)
        Files.writeString(target, actual)
        println("Wrote $target (${actual.length} chars)")
        return
    }
    val resource = TextWriterFidelityTest::class.java.getResource("/$BASELINE_RESOURCE_DIR/$name")
        ?: error("Missing baseline: /$BASELINE_RESOURCE_DIR/$name (run with -Dregenerate-baseline-texts=true to seed)")
    val expected = resource.readText()
    if (actual != expected) {
        // Write the actual output next to the baseline dir so the developer can diff it externally.
        // Avoids materializing a multi-MB diff string in the test reporter (which OOMs the heap).
        val actualPath = BASELINE_DEV_DIR.resolveSibling("text-actuals").resolve(name)
        Files.createDirectories(actualPath.parent)
        Files.writeString(actualPath, actual)
        error(
            "Baseline mismatch for $name. " +
                "Expected ${expected.length} chars, got ${actual.length}. " +
                "Actual output written to $actualPath — diff against src/test/resources/$BASELINE_RESOURCE_DIR/$name. " +
                "Run with -Dregenerate-baseline-texts=true to accept the new output."
        )
    }
}

/**
 * Reads `word/document.xml` from [docxPath] and drops its first two lines (the XML declaration and
 * the root `<w:document …>` element whose namespace-declaration order is non-deterministic between
 * docx4j runs). What remains is the document body — semantically deterministic.
 */
private fun normalizedDocumentXml(docxPath: Path): String {
    ZipFile(docxPath.toFile()).use { zip ->
        val entry = zip.getEntry("word/document.xml")
            ?: error("word/document.xml not found in $docxPath")
        return zip.getInputStream(entry).bufferedReader(Charsets.UTF_8).use { reader ->
            reader.readLines().drop(2).joinToString("\n")
        }
    }
}
