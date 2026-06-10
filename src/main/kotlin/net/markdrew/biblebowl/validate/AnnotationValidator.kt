package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.CategoryOverride
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import java.nio.file.Path

/**
 * Holds the validation session: the pre-annotation resolution, the source editors, and the resume
 * state, and applies a group's decided [WritePlan]. The [ValidationTui] drives it; this class owns all
 * non-UI side effects so they're testable.
 *
 * @param sourceDir version-controlled resources root to edit (default `src/main/resources`)
 */
class AnnotationValidator(
    val studyData: StudyData,
    val selectedCategories: Set<WordList>,
    sourceDir: Path,
) {
    private val setDir = sourceDir.resolve(studyData.studySet.simpleName)
    private val listEditor = CategoryListEditor(sourceDir.resolve("word-lists"))
    private val overridesWriter = OverridesWriter(setDir.resolve("category-overrides.tsv"))
    private val state = ValidationState.load(setDir.resolve("annotation-validation.tsv"))

    /** Session-initial unified resolution, used to pre-annotate candidates. */
    private val resolved: DisjointRangeMap<String> =
        AnnotationStore(studyData, cacheDir = null).get(WordList.categoryAnnotator(studyData.studySet))

    val contextRenderer = ContextRenderer(studyData)

    /** Pending candidate groups (already-reviewed forms skipped), in reading order. */
    fun pendingGroups(): List<CandidateGroup> =
        CandidateScanner.scan(studyData, selectedCategories, resolved, state.doneForms)

    /** Word occurrences matching [query] for the add-missed `/` flow. */
    fun search(query: String, asRegex: Boolean): List<CandidateGroup> =
        CandidateScanner.search(studyData, query, asRegex, resolved)

    /** Full-text matches of a proposed list-regex entry (multi-word capable), for the `r` regex group. */
    fun regexMatches(pattern: String): List<Candidate> =
        CandidateScanner.regexMatches(studyData, pattern, resolved)

    /** The plan a group's [decisions] imply (list edits + overrides), writing [listEntry] to lists. */
    fun planFor(decisions: List<OccurrenceDecision>, listEntry: String): WritePlan =
        WritePolicy.plan(studyData, decisions, listEditor.categoriesContaining(listEntry), listEntry)

    /** The actual file changes one [apply] made, so it can be reversed by [undo]. */
    data class UndoEntry(
        val listAdds: List<Pair<WordList, String>>,
        val listRemoves: List<Pair<WordList, String>>,
        val overridesAdded: List<CategoryOverride>,
        val recordedForms: List<String>,
    )

    /**
     * Applies [plan]'s edits and, when [markDone], records [forms] as validated. The caller must have
     * already confirmed any `plan.listRemoves` (shared-list change). [markDone] is false for a partially
     * decided split (some occurrences skipped), so the group stays pending. Returns an [UndoEntry]
     * capturing exactly what changed.
     */
    fun apply(forms: Collection<String>, plan: WritePlan, markDone: Boolean = true): UndoEntry {
        val removed = plan.listRemoves.filter { (category, entry) -> listEditor.removeConfirmed(category, entry) }
        val added = plan.listAdds.filter { (category, entry) -> listEditor.add(category, entry) }
        val overridesAdded = overridesWriter.append(plan.overrides)
        val recorded = if (markDone) forms.toList() else emptyList()
        recorded.forEach { state.record(it, plan.verdict) }
        return UndoEntry(added, removed, overridesAdded, recorded)
    }

    /** Reverses an [apply]: undo list adds/removes, drop the appended overrides, un-record the forms. */
    fun undo(entry: UndoEntry) {
        entry.listAdds.forEach { (category, entry2) -> listEditor.removeConfirmed(category, entry2) }
        entry.listRemoves.forEach { (category, entry2) -> listEditor.add(category, entry2) }
        overridesWriter.remove(entry.overridesAdded)
        entry.recordedForms.forEach { state.remove(it) }
    }

    /** Records [forms] reviewed without edits (e.g. confirmed already-correct). */
    fun markReviewed(forms: Collection<String>, verdict: String) = forms.forEach { state.record(it, verdict) }
}
