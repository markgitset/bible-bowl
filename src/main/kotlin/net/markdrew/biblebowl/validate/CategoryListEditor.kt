package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.analysis.WordList
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/**
 * Adds/removes plain-or-regex entries in the shared `word-lists/<token>.txt` files.
 *
 * These lists are used by **every** study set, so [add] (a pure addition) applies immediately while
 * [removeConfirmed] must only be called after the caller has warned the user — see [WritePolicy]/the
 * TUI, which gate removals/moves behind a confirmation. File order and comments are preserved.
 */
class CategoryListEditor(private val wordListsDir: Path) {

    private fun file(category: WordList): Path = wordListsDir.resolve("${category.token}.txt")

    /** Current entries of [category] (blank/comment lines dropped). */
    fun entries(category: WordList): List<String> {
        val f = file(category)
        if (!f.exists()) return emptyList()
        return f.readText().lineSequence().map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }.toList()
    }

    fun contains(category: WordList, entry: String): Boolean = entry in entries(category)

    /** Word-list categories whose list currently contains [entry], in enum order. */
    fun categoriesContaining(entry: String): List<WordList> = WordList.entries.filter { contains(it, entry) }

    /** Appends [entry] to [category]'s list if absent. Returns true if the file changed. */
    fun add(category: WordList, entry: String): Boolean {
        if (contains(category, entry)) return false
        val f = file(category)
        wordListsDir.createDirectories()
        val existing = if (f.exists()) f.readText() else ""
        val separator = if (existing.isEmpty() || existing.endsWith("\n")) "" else "\n"
        f.writeText(existing + separator + entry + "\n")
        return true
    }

    /** Removes every line equal to [entry] from [category]'s list. The caller must have confirmed. */
    fun removeConfirmed(category: WordList, entry: String): Boolean {
        val f = file(category)
        if (!f.exists()) return false
        val lines = f.readText().split("\n")
        val kept = lines.filterNot { it.trim() == entry }
        if (kept.size == lines.size) return false
        f.writeText(kept.joinToString("\n"))
        return true
    }
}
