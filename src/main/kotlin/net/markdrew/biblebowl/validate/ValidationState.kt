package net.markdrew.biblebowl.validate

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/**
 * Tracks which surface forms have been reviewed, so validation can stop and resume across sessions.
 *
 * Persisted as `<simpleName>/annotation-validation.tsv` (`text <TAB> verdict`); a form is **pending iff
 * it has no row**. The verdict is a human-readable summary of the decision (a category token, `none`,
 * or `split:<majority>`); only the keys matter for resume. Committed — it's human labor.
 */
class ValidationState private constructor(
    private val file: Path,
    private val verdicts: MutableMap<String, String>,
) {
    val doneForms: Set<String> get() = verdicts.keys

    fun isDone(text: String): Boolean = text in verdicts

    /** The recorded verdict for [text] (a category token, `none`, or `split:<majority>`), or null if pending. */
    fun verdictOf(text: String): String? = verdicts[text]

    /** Records [text] as reviewed with [verdict] and flushes to disk. */
    fun record(text: String, verdict: String) {
        verdicts[text] = verdict
        save()
    }

    /** Un-records [text] (used to undo), flushing to disk. */
    fun remove(text: String) {
        if (verdicts.remove(text) != null) save()
    }

    private fun save() {
        file.parent.createDirectories()
        val body = verdicts.entries.sortedBy { it.key }.joinToString("\n") { "${it.key}\t${it.value}" }
        file.writeText("# Reviewed surface forms (text <TAB> verdict). A form is pending iff absent.\n$body\n")
    }

    companion object {
        fun load(file: Path): ValidationState {
            val verdicts = LinkedHashMap<String, String>()
            if (file.exists()) {
                file.readText().lineSequence()
                    .map { it.trim() }
                    .filter { it.isNotEmpty() && !it.startsWith("#") }
                    .forEach { line ->
                        val cols = line.split('\t')
                        if (cols.size >= 2) verdicts[cols[0]] = cols[1]
                    }
            }
            return ValidationState(file, verdicts)
        }
    }
}
