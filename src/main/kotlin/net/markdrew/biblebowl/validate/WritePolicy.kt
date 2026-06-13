package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.analysis.CategoryOverride
import net.markdrew.biblebowl.analysis.CategoryPrecedence
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef

/** A per-occurrence decision: the category the human assigned, or null = "none" (exclude). */
data class OccurrenceDecision(val candidate: Candidate, val category: WordList?)

/**
 * The edits a group's decisions imply. [listRemoves] mutate shared word lists and must be confirmed by
 * the caller (the TUI's warning prompt); [listAdds] and [overrides] apply directly.
 */
data class WritePlan(
    val listAdds: List<Pair<WordList, String>>,
    val listRemoves: List<Pair<WordList, String>>,
    val overrides: List<CategoryOverride>,
    val verdict: String,
) {
    val touchesSharedLists: Boolean get() = listRemoves.isNotEmpty()
}

/**
 * Turns a group's per-occurrence decisions into list + override edits, realizing "lists first;
 * exceptions to overrides": the **majority** category becomes the (single) word-list membership for the
 * entry, and every occurrence that disagrees with the majority becomes a per-occurrence override.
 */
object WritePolicy {

    /**
     * @param currentListCategories the word-list categories whose list already contains [listEntry]
     * @param listEntry what to write to the list — the plain surface form, or a user-proposed regex
     */
    fun plan(
        studyData: StudyData,
        decisions: List<OccurrenceDecision>,
        currentListCategories: List<WordList>,
        listEntry: String,
    ): WritePlan {
        require(decisions.isNotEmpty()) { "no decisions" }

        val counts: Map<WordList?, Int> = decisions.groupingBy { it.category }.eachCount()
        // Majority wins; ties prefer a category over "none", then higher CategoryPrecedence.
        val majority: WordList? = counts.entries.sortedWith(
            compareByDescending<Map.Entry<WordList?, Int>> { it.value }
                .thenBy { it.key == null }
                .thenBy { it.key?.let { c -> CategoryPrecedence.rank(c.token) } ?: Int.MAX_VALUE }
        ).first().key

        // The form already resolves to the majority category (e.g. a number matched by the numbers
        // patterns, or a term already covered) — so a literal entry would be redundant. Uniform across
        // categories; no rawRegex-specific branch.
        val alreadyResolved = decisions.filter { it.category == majority }.all { it.candidate.proposed == majority }
        val listAdds = buildList {
            if (majority != null && majority !in currentListCategories && !alreadyResolved)
                add(majority to listEntry)
        }
        val listRemoves = currentListCategories.filter { it != majority }.map { it to listEntry }
        val overrides = decisions.filter { it.category != majority }.map { override(studyData, it) }

        val verdict = if (counts.size == 1) (majority?.token ?: "none") else "split:${majority?.token ?: "none"}"
        return WritePlan(listAdds, listRemoves, overrides, verdict)
    }

    private fun override(studyData: StudyData, d: OccurrenceDecision): CategoryOverride {
        val verse: VerseRef = studyData.verseEnclosing(d.candidate.range)
            ?: error("Occurrence not in a verse: ${d.candidate}")
        return CategoryOverride(
            verse = verse,
            text = d.candidate.text,
            category = d.category?.token,
            occurrence = occurrenceIndex(studyData, verse, d.candidate),
        )
    }

    /** 1-based index of this occurrence among literal occurrences of its text in the verse, or null if unique. */
    private fun occurrenceIndex(studyData: StudyData, verse: VerseRef, c: Candidate): Int? {
        val verseRange = studyData.verseIndex[verse] ?: return null
        val verseText = studyData.text.substring(verseRange)
        val starts = Regex(Regex.escape(c.text)).findAll(verseText).map { verseRange.first + it.range.first }.toList()
        if (starts.size <= 1) return null
        return starts.indexOf(c.range.first).takeIf { it >= 0 }?.plus(1)
    }
}
