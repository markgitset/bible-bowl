package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.analysis.CategoryOverride
import net.markdrew.biblebowl.analysis.EXCLUDE_CATEGORY
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.writeText

/**
 * Appends per-occurrence override rows to a study set's `category-overrides.tsv` in the same
 * tab-separated `verse <TAB> text <TAB> category [<TAB> occurrence#]` format [CategoryOverrides] reads
 * (`-` excludes). Rows already present are skipped, so appending is idempotent.
 */
class OverridesWriter(private val file: Path) {

    private val header =
        "# Per-occurrence category overrides. Columns: verse <TAB> text <TAB> category <TAB> [occurrence#]\n" +
            "# category \"-\" excludes the occurrence; occurrence# (1-based) only when text repeats in the verse.\n"

    /** Serialized form used both to write and to dedupe against existing lines. */
    fun serialize(o: CategoryOverride): String {
        val category = o.category ?: EXCLUDE_CATEGORY
        val occurrence = o.occurrence?.let { "\t$it" }.orEmpty()
        return "${o.verse.format(FULL_BOOK_FORMAT)}\t${o.text}\t$category$occurrence"
    }

    /** Appends any [rows] not already present; returns exactly the rows that were newly written. */
    fun append(rows: List<CategoryOverride>): List<CategoryOverride> {
        if (rows.isEmpty()) return emptyList()
        if (!file.exists()) {
            file.parent.createDirectories()
            file.writeText(header)
        }
        val existing = file.readLines().map { it.trim() }.toHashSet()
        val added = rows.distinctBy { serialize(it) }.filter { serialize(it) !in existing }
        if (added.isNotEmpty()) file.appendText(added.joinToString("\n", postfix = "\n") { serialize(it) })
        return added
    }

    /** Removes the lines matching [rows] (used to undo a prior [append]). */
    fun remove(rows: List<CategoryOverride>) {
        if (rows.isEmpty() || !file.exists()) return
        val targets = rows.map { serialize(it) }.toHashSet()
        val kept = file.readLines().filterNot { it.trim() in targets }
        file.writeText(kept.joinToString("\n", postfix = if (kept.isEmpty()) "" else "\n"))
    }
}
