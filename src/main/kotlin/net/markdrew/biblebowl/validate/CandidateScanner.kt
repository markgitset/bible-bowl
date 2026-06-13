package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap

/**
 * Builds the high-recall set of entity occurrences to validate, grouped by surface form.
 *
 * [resolvedCategories] is the unified `range → category-token` resolution (from
 * [WordList.categoryAnnotator]); it supplies each occurrence's pre-annotation. Candidates are the
 * selected categories' current matches **plus** every capitalized word not already covered by a
 * category (so a proper-name entity that no list catches still surfaces) — but only when at least one
 * proper-name category is being validated.
 */
object CandidateScanner {

    /**
     * Groups pending candidate occurrences for [selectedCategories]. A form is skipped only when its
     * recorded verdict (from [verdictOf]) still matches the current resolution; a form whose verdict no
     * longer agrees with the lists/overrides re-surfaces as drift (see [isConsistent]).
     */
    fun scan(
        studyData: StudyData,
        selectedCategories: Set<WordList>,
        resolvedCategories: DisjointRangeMap<String>,
        verdictOf: (String) -> String? = { null },
    ): List<CandidateGroup> {
        val candidates = mutableListOf<Candidate>()

        // Existing matches of the selected categories (covers multi-word and lowercase categories).
        for ((range, token) in resolvedCategories) {
            val category = WordList.byToken(token)
            if (category in selectedCategories) candidates += candidate(studyData, range, category)
        }

        // High-recall net: every capitalized word not already covered by any category.
        if (selectedCategories.any { it.areNames }) {
            for (wordRange in studyData.words) {
                if (!studyData.text[wordRange.first].isUpperCase()) continue
                if (resolvedCategories.valueEnclosing(wordRange) != null) continue // already categorized
                candidates += candidate(studyData, wordRange, proposed = null)
            }
        }

        return group(candidates, verdictOf)
    }

    /**
     * Collects word occurrences whose text matches [query] for the `/` add-missed flow. [query] is a
     * substring (case-insensitive) unless [asRegex]; pre-annotations come from [resolvedCategories].
     */
    fun search(
        studyData: StudyData,
        query: String,
        asRegex: Boolean,
        resolvedCategories: DisjointRangeMap<String>,
    ): List<CandidateGroup> {
        if (query.isBlank()) return emptyList()
        val regex = if (asRegex) Regex(query) else Regex(Regex.escape(query), RegexOption.IGNORE_CASE)
        val candidates = studyData.words
            .filter { regex.containsMatchIn(studyData.text.substring(it)) }
            .map { candidate(studyData, it, WordList.byToken(resolvedCategories.valueEnclosing(it) ?: "")) }
        return group(candidates)
    }

    /**
     * Full-text occurrences of a proposed list **regex entry** (e.g. `Men of Israel`), matched the way a
     * proper-name list entry is applied — `\b`-wrapped over the whole text — so multi-word patterns work.
     * @throws java.util.regex.PatternSyntaxException if [pattern] is not a valid regex
     */
    fun regexMatches(
        studyData: StudyData,
        pattern: String,
        resolvedCategories: DisjointRangeMap<String>,
    ): List<Candidate> {
        if (pattern.isBlank()) return emptyList()
        val regex = Regex("""\b$pattern\b""")
        return studyData.findAll(regex).map { range ->
            candidate(studyData, range, WordList.byToken(resolvedCategories.valueEnclosing(range) ?: ""))
        }
    }

    private fun candidate(studyData: StudyData, range: IntRange, proposed: WordList?): Candidate =
        Candidate(range, studyData.text.substring(range), proposed)

    private fun group(candidates: List<Candidate>, verdictOf: (String) -> String? = { null }): List<CandidateGroup> =
        candidates.distinctBy { it.range }
            .groupBy { it.text }
            .map { (text, occ) -> CandidateGroup(text, occ.sortedBy { it.range.first }) }
            .filter { g -> verdictOf(g.text).let { v -> v == null || !isConsistent(v, g) } }
            .sortedBy { it.occurrences.first().range.first }

    /**
     * Whether [group]'s current resolution already matches its recorded [verdict] — if so it stays done;
     * otherwise it has drifted and is re-surfaced. `split:` verdicts can't be checked from the TSV (no
     * per-occurrence detail), so they're treated as consistent.
     */
    private fun isConsistent(verdict: String, group: CandidateGroup): Boolean {
        if (verdict.startsWith("split:")) return true
        val resolved: Set<String?> = group.occurrences.map { it.proposed?.token }.toSet()
        return if (verdict == "none") resolved == setOf<String?>(null) // nothing categorized
        else resolved == setOf<String?>(verdict)                       // every occurrence is exactly this category
    }
}
