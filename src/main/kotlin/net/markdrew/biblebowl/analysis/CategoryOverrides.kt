package net.markdrew.biblebowl.analysis

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Files
import java.nio.file.Path

private val log: KLogger = KotlinLogging.logger {}

/** Sentinel category meaning "this occurrence belongs to no category" (a false-positive exclusion). */
const val EXCLUDE_CATEGORY = "-"

/**
 * A single human-authored correction anchored to a specific occurrence of [text] within [verse].
 *
 * [category] is the category id this occurrence should resolve to (e.g. `"women"`, `"places"`), or
 * `null` to exclude the occurrence from every category. [occurrence] is a 1-based index used only when
 * [text] appears more than once in the verse; `null` applies the override to every occurrence in [verse].
 */
data class CategoryOverride(
    val verse: VerseRef,
    val text: String,
    val category: String?,
    val occurrence: Int? = null,
)

/** A [CategoryOverride] resolved to a concrete character [range] over [StudyData.text]. */
data class ResolvedOverride(val range: IntRange, val category: String?)

/**
 * Loads and resolves per-study-set category overrides from the bundled resource
 * `/<simpleName>/category-overrides.tsv`.
 *
 * The file is the human-editable source of truth for per-occurrence disambiguation (the future review
 * TUI edits the same file). Columns are tab-separated: `verse`, `text`, `category`, and an optional
 * `occurrence#`; lines beginning with `#` and blank lines are ignored. A `category` of `-` (or `none`)
 * excludes the occurrence. Overrides are anchored by verse + text rather than character offset so they
 * survive ESV re-downloads.
 */
object CategoryOverrides {

    /**
     * Returns the raw overrides for [studySet], or an empty list when no override file is present.
     *
     * When [sourceDir] is given (the validator), reads the editable file from
     * `<sourceDir>/<simpleName>/category-overrides.tsv` so freshly-written edits are seen; otherwise reads
     * the bundled classpath resource (the generate path).
     */
    fun load(studySet: StudySet, sourceDir: Path? = null): List<CategoryOverride> {
        val lines: List<String> = if (sourceDir != null) {
            val file = sourceDir.resolve(studySet.simpleName).resolve("category-overrides.tsv")
            if (!Files.exists(file)) return emptyList() else Files.readAllLines(file)
        } else {
            val resourceName = "/${studySet.simpleName}/category-overrides.tsv"
            val stream = CategoryOverrides::class.java.getResourceAsStream(resourceName) ?: return emptyList()
            stream.bufferedReader().use { it.readLines() }
        }
        return lines.map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .map { parseLine(it) }
    }

    /** Returns the raw file content for [studySet] (for cache fingerprinting), or "" when absent. */
    fun rawContent(studySet: StudySet): String =
        CategoryOverrides::class.java.getResourceAsStream("/${studySet.simpleName}/category-overrides.tsv")
            ?.bufferedReader()?.use { it.readText() } ?: ""

    private fun parseLine(line: String): CategoryOverride {
        val cols = line.split('\t').map { it.trim() }
        require(cols.size in 3..4) { "Expected 3-4 tab-separated columns in category override: '$line'" }
        val category = cols[2].lowercase().let { if (it == EXCLUDE_CATEGORY || it == "none") null else it }
        return CategoryOverride(
            verse = VerseRef.parse(cols[0]),
            text = cols[1],
            category = category,
            occurrence = cols.getOrNull(3)?.takeIf { it.isNotEmpty() }?.toInt(),
        )
    }

    /**
     * Resolves [overrides] against [studyData] to concrete character ranges by locating [text] within
     * each override's verse. Rows whose verse or text can't be found are logged and dropped (stale).
     */
    fun resolve(overrides: List<CategoryOverride>, studyData: StudyData): List<ResolvedOverride> =
        overrides.flatMap { override -> resolveOne(override, studyData) }

    private fun resolveOne(override: CategoryOverride, studyData: StudyData): List<ResolvedOverride> {
        val verseRange: IntRange? = studyData.verseIndex[override.verse]
        if (verseRange == null) {
            log.warn { "Stale category override: verse ${override.verse} not in this study set (${override.text})" }
            return emptyList()
        }
        val occurrences: List<IntRange> = literalOccurrences(studyData.text, override.text, verseRange)
        if (occurrences.isEmpty()) {
            log.warn { "Stale category override: \"${override.text}\" not found in ${override.verse}" }
            return emptyList()
        }
        val selected: List<IntRange> = when (val n = override.occurrence) {
            null -> occurrences
            else -> {
                val one = occurrences.getOrNull(n - 1)
                if (one == null) {
                    log.warn { "Stale category override: ${override.verse} has no occurrence #$n of \"${override.text}\"" }
                    return emptyList()
                }
                listOf(one)
            }
        }
        return selected.map { ResolvedOverride(it, override.category) }
    }

    /** Absolute character ranges of every literal occurrence of [text] within [within] over [docText]. */
    private fun literalOccurrences(docText: String, text: String, within: IntRange): List<IntRange> {
        val ranges = mutableListOf<IntRange>()
        var from = within.first
        while (from <= within.last) {
            val hit = docText.indexOf(text, startIndex = from)
            if (hit < 0 || hit + text.length - 1 > within.last) break
            ranges += hit..(hit + text.length - 1)
            from = hit + text.length
        }
        return ranges
    }
}
