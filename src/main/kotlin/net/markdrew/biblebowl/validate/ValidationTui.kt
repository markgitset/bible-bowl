package net.markdrew.biblebowl.validate

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import net.markdrew.biblebowl.analysis.WordList

/** Single-key category bindings (lowercase for proper-name lists, uppercase for common-word lists). */
private val CATEGORY_KEY: Map<WordList, Char> = mapOf(
    WordList.MEN to 'm', WordList.WOMEN to 'w', WordList.PLACES to 'p', WordList.PEOPLE_GROUPS to 'g',
    WordList.DIVINE to 'd', WordList.ANGELS_DEMONS to 'N', WordList.OTHER to 'o', WordList.ANIMALS to 'A',
    WordList.FOODS to 'F', WordList.BODY_PARTS to 'B', WordList.COLORS to 'C', WordList.NUMBERS to 'n',
)
private val KEY_CATEGORY: Map<Char, WordList> = CATEGORY_KEY.entries.associate { (k, v) -> v to k }

/**
 * Lanterna full-screen validator UI. Walks pending [CandidateGroup]s, renders each with highlighted,
 * zoomable context, and turns keypresses into [WritePlan]s applied through [AnnotationValidator].
 */
class ValidationTui(private val validator: AnnotationValidator) {

    private val screen: Screen = DefaultTerminalFactory().setForceTextTerminal(true).createScreen() as TerminalScreen
    private val ctx = validator.contextRenderer
    private var zoom = ContextZoom.WORDS

    /** A mutable queue so `/` search and `r` regex can splice new groups in to validate now. */
    private val queue: ArrayDeque<CandidateGroup> = ArrayDeque(validator.pendingGroups())
    private val total = queue.size

    private enum class Outcome { QUIT, DECIDED, SKIP, UNDO }

    /** Recently applied decisions (group + the file changes it made), newest last, for `u`ndo. */
    private val undoStack = ArrayDeque<Pair<CandidateGroup, AnnotationValidator.UndoEntry>>()

    fun run() {
        screen.startScreen()
        try {
            var skippedInARow = 0
            loop@ while (queue.isNotEmpty()) {
                when (handleGroup(queue.first())) {
                    Outcome.QUIT -> break@loop
                    Outcome.DECIDED -> { queue.removeFirst(); skippedInARow = 0 }
                    Outcome.SKIP -> {
                        queue.addLast(queue.removeFirst()) // defer to the back; never recorded as done
                        if (++skippedInARow >= queue.size) {
                            message("Skipped the remaining ${queue.size} for now — resume anytime.")
                            break@loop
                        }
                    }
                    Outcome.UNDO -> if (undoStack.isEmpty()) message("Nothing to undo.") else {
                        val (group, entry) = undoStack.removeLast()
                        validator.undo(entry)
                        queue.remove(group)   // in case it was a deferred (skipped) group
                        queue.addFirst(group) // bring it back to re-decide
                        skippedInARow = 0
                    }
                }
            }
            if (queue.isEmpty()) message("All done — nothing left to validate.")
        } finally {
            screen.stopScreen()
        }
    }

    // ---- group view ----------------------------------------------------------------------------

    private fun handleGroup(group: CandidateGroup): Outcome {
        if (group.occurrences.size == 1) return handleSingleOccurrence(group)
        var scroll = 0
        while (true) {
            renderGroup(group, scroll)
            val key = screen.readInput()
            val ch: Char? = key.character
            when {
                key.keyType == KeyType.Escape || ch == 'q' -> return Outcome.QUIT
                key.keyType == KeyType.Tab || ch == '.' -> return Outcome.SKIP
                key.keyType == KeyType.Enter || ch == 'a' -> { decideAll(group, group.proposed); return Outcome.DECIDED }
                ch == 'x' -> { decideAll(group, null); return Outcome.DECIDED }
                ch != null && ch in KEY_CATEGORY -> { decideAll(group, KEY_CATEGORY[ch]); return Outcome.DECIDED }
                // split and edit-bounds both drop into the per-occurrence flow (bounds are per occurrence)
                ch == 's' || ch == 'e' -> when (splitGroup(group)) {
                    SplitResult.DECIDED -> return Outcome.DECIDED
                    SplitResult.SKIPPED -> return Outcome.SKIP // a skipped instance keeps the group pending
                    SplitResult.CANCELLED -> {} // stay on the group view
                }
                ch == 'r' -> { if (regexGroup(group)) return Outcome.DECIDED }
                ch == '/' -> searchAndSplice()
                ch == 'u' -> return Outcome.UNDO
                ch == '+' -> zoom = zoom.zoomIn()
                ch == '-' -> zoom = zoom.zoomOut()
                key.keyType == KeyType.ArrowDown || ch == 'j' -> scroll++
                key.keyType == KeyType.ArrowUp || ch == 'k' -> scroll = (scroll - 1).coerceAtLeast(0)
            }
        }
    }

    private fun decideAll(group: CandidateGroup, category: WordList?) {
        val decisions = group.occurrences.map { OccurrenceDecision(it, category) }
        applyDecisions(group, listOf(group.text), decisions, listEntry = group.text)
    }

    // ---- single occurrence (no split needed) ---------------------------------------------------

    /** A group with exactly one occurrence: the per-occurrence menu directly (incl. edit-bounds). */
    private fun handleSingleOccurrence(group: CandidateGroup): Outcome {
        var occ = group.occurrences.single()
        while (true) {
            renderSingle(group, occ)
            val key = screen.readInput()
            val ch: Char? = key.character
            when {
                key.keyType == KeyType.Escape || ch == 'q' -> return Outcome.QUIT
                key.keyType == KeyType.Tab || ch == '.' -> return Outcome.SKIP
                key.keyType == KeyType.Enter || ch == 'a' -> { decideSingle(group, occ, group.proposed); return Outcome.DECIDED }
                ch == 'x' -> { decideSingle(group, occ, null); return Outcome.DECIDED }
                ch != null && ch in KEY_CATEGORY -> { decideSingle(group, occ, KEY_CATEGORY[ch]); return Outcome.DECIDED }
                ch == 'e' -> occ = editBounds(occ)
                ch == 'r' -> { if (regexGroup(group)) return Outcome.DECIDED }
                ch == '/' -> searchAndSplice()
                ch == 'u' -> return Outcome.UNDO
                ch == '+' -> zoom = zoom.zoomIn()
                ch == '-' -> zoom = zoom.zoomOut()
            }
        }
    }

    private fun decideSingle(group: CandidateGroup, occ: Candidate, category: WordList?) {
        // If bounds were edited, occ.text differs from the group form; write the edited text to the list.
        applyDecisions(group, setOf(group.text, occ.text), listOf(OccurrenceDecision(occ, category)), listEntry = occ.text)
    }

    // ---- split: per-occurrence -----------------------------------------------------------------

    private enum class SplitResult { DECIDED, SKIPPED, CANCELLED }

    private sealed interface OneOutcome {
        data object Cancel : OneOutcome
        data object Skip : OneOutcome
        data class Decided(val decision: OccurrenceDecision) : OneOutcome
    }

    /**
     * Steps through a group's occurrences. Returns [SplitResult.DECIDED] if all were decided (mark done),
     * [SplitResult.SKIPPED] if any single instance was skipped (decided ones are still applied, but the
     * group stays pending so the skipped instances can be finished later), or [SplitResult.CANCELLED].
     */
    private fun splitGroup(group: CandidateGroup): SplitResult {
        val decisions = mutableListOf<OccurrenceDecision>()
        var anySkipped = false
        for ((i, occ) in group.occurrences.withIndex()) {
            when (val outcome = decideOne(group, occ, i + 1, group.occurrences.size)) {
                OneOutcome.Cancel -> return SplitResult.CANCELLED
                OneOutcome.Skip -> anySkipped = true
                is OneOutcome.Decided -> decisions += outcome.decision
            }
        }
        if (decisions.isNotEmpty()) {
            applyDecisions(group, listOf(group.text), decisions, listEntry = group.text, markDone = !anySkipped)
        }
        return if (anySkipped) SplitResult.SKIPPED else SplitResult.DECIDED
    }

    private fun decideOne(group: CandidateGroup, occ: Candidate, n: Int, of: Int): OneOutcome {
        var current = occ
        while (true) {
            renderOne(group, current, n, of)
            val key = screen.readInput()
            val ch: Char? = key.character
            when {
                key.keyType == KeyType.Escape -> return OneOutcome.Cancel
                key.keyType == KeyType.Tab || ch == '.' -> return OneOutcome.Skip
                key.keyType == KeyType.Enter || ch == 'a' -> return OneOutcome.Decided(OccurrenceDecision(current, group.proposed))
                ch == 'x' -> return OneOutcome.Decided(OccurrenceDecision(current, null))
                ch != null && ch in KEY_CATEGORY -> return OneOutcome.Decided(OccurrenceDecision(current, KEY_CATEGORY[ch]))
                ch == 'e' -> current = editBounds(current)
                ch == '+' -> zoom = zoom.zoomIn()
                ch == '-' -> zoom = zoom.zoomOut()
            }
        }
    }

    // ---- regex group ---------------------------------------------------------------------------

    /** Returns true if a regex entry was written (advance the original group). */
    private fun regexGroup(group: CandidateGroup): Boolean {
        val pattern = readLine("Regex for this group (matched whole-text, \\b-wrapped; Esc cancels): ", group.text)
            ?: return false
        if (pattern.isBlank()) return false
        val matches = try {
            validator.regexMatches(pattern)
        } catch (e: Exception) {
            message("Invalid regex: ${e.message}"); return false
        }
        if (matches.isEmpty()) { message("Regex matched nothing (note: case-sensitive)."); return false }
        // preview as one group; pick a single category for all
        val preview = CandidateGroup(pattern, matches)
        renderGroup(preview, scroll = 0, titleOverride = "REGEX /$pattern/ matches ${matches.size} — pick category, Esc cancels")
        val ch: Char? = screen.readInput().character
        val category: WordList? = when {
            ch == 'x' -> null
            ch != null && ch in KEY_CATEGORY -> KEY_CATEGORY[ch]
            else -> return false
        }
        val decisions = matches.map { OccurrenceDecision(it, category) }
        val forms = matches.map { it.text }.toSet() + group.text
        applyDecisions(group, forms, decisions, listEntry = pattern)
        return true
    }

    // ---- search & splice -----------------------------------------------------------------------

    private fun searchAndSplice() {
        val raw = readLine("Search (substring, or /regex/): ") ?: return
        val asRegex = raw.length >= 2 && raw.startsWith('/') && raw.endsWith('/')
        val query = if (asRegex) raw.substring(1, raw.length - 1) else raw
        val found = validator.search(query, asRegex)
        if (found.isEmpty()) { message("No matches for \"$raw\""); return }
        found.asReversed().forEach { queue.addFirst(it) } // validate these next, in order
    }

    // ---- bounds editor -------------------------------------------------------------------------

    /** Lets the user grow/shrink the highlighted span; returns the (possibly) adjusted candidate. */
    private fun editBounds(occ: Candidate): Candidate {
        var range = occ.range
        val text = validator.studyData.text
        while (true) {
            renderBounds(occ, range)
            val key = screen.readInput()
            when (key.keyType) {
                KeyType.Enter, KeyType.Escape -> return occ.copy(range = range, text = text.substring(range))
                KeyType.ArrowRight -> if (range.last + 1 <= text.lastIndex) range = range.first..range.last + 1
                KeyType.ArrowLeft -> if (range.last > range.first) range = range.first until range.last
                else -> when (key.character) {
                    '[' -> if (range.first > 0) range = range.first - 1..range.last
                    ']' -> if (range.first < range.last) range = range.first + 1..range.last
                }
            }
        }
    }

    // ---- apply ---------------------------------------------------------------------------------

    private fun applyDecisions(
        group: CandidateGroup,
        forms: Collection<String>,
        decisions: List<OccurrenceDecision>,
        listEntry: String,
        markDone: Boolean = true,
    ) {
        val plan = validator.planFor(decisions, listEntry)
        if (plan.touchesSharedLists && !confirmRemovals(plan)) {
            // user declined the shared-list change: record nothing, leave for re-decision
            return
        }
        undoStack.addLast(group to validator.apply(forms, plan, markDone))
    }

    private fun confirmRemovals(plan: WritePlan): Boolean {
        val g = newScreen()
        var row = 1
        g.put(2, row++, "Shared word-list change — this affects EVERY study set:", TextColor.ANSI.YELLOW_BRIGHT)
        row++
        plan.listRemoves.forEach { (c, e) -> g.put(4, row++, "remove  \"$e\"  from  ${c.token}.txt", TextColor.ANSI.RED) }
        plan.listAdds.forEach { (c, e) -> g.put(4, row++, "add     \"$e\"  to    ${c.token}.txt", TextColor.ANSI.GREEN) }
        row++
        g.put(2, row, "Proceed? [y]es / [n]o", TextColor.ANSI.WHITE)
        screen.refresh()
        return screen.readInput().character == 'y'
    }

    // ---- rendering -----------------------------------------------------------------------------

    private fun renderGroup(group: CandidateGroup, scroll: Int, titleOverride: String? = null) {
        val g = newScreen()
        val proposed = group.proposed?.token ?: "none"
        val done = (total - queue.size).coerceAtLeast(0)
        g.put(2, 0, titleOverride ?: "[$done/$total · ${pct(done)}%]  \"${group.text}\"  →  $proposed  (${group.size}×)",
            TextColor.ANSI.CYAN_BRIGHT)
        var row = 2
        val height = screen.terminalSize.rows
        val shown = group.occurrences.drop(scroll)
        for (occ in shown) {
            if (row >= height - 3) { g.put(2, row, "… (j/k to scroll)", TextColor.ANSI.BLACK_BRIGHT); break }
            row = drawContext(g, row, ctx.render(occ.range, zoom))
        }
        legend(g)
        screen.refresh()
    }

    private fun renderSingle(group: CandidateGroup, occ: Candidate) {
        val g = newScreen()
        val done = (total - queue.size).coerceAtLeast(0)
        g.put(2, 0, "[$done/$total · ${pct(done)}%]  \"${occ.text}\"  →  ${group.proposed?.token ?: "none"}  (single)",
            TextColor.ANSI.CYAN_BRIGHT)
        drawContext(g, 2, ctx.render(occ.range, zoom))
        val keys = CATEGORY_KEY.entries.joinToString("  ") { "${it.value}=${it.key.token}" }
        legend(g, "[a](${group.proposed?.token ?: "none"})  $keys  [x]none  [e]dit  [r]egex  [Tab]skip  [u]ndo  [/]search  +/- ctx  [q]uit")
        screen.refresh()
    }

    private fun renderOne(group: CandidateGroup, occ: Candidate, n: Int, of: Int) {
        val g = newScreen()
        g.put(2, 0, "Split \"${group.text}\"  —  occurrence $n/$of  (proposed ${group.proposed?.token ?: "none"})",
            TextColor.ANSI.CYAN_BRIGHT)
        drawContext(g, 2, ctx.render(occ.range, zoom))
        val keys = CATEGORY_KEY.entries.joinToString("  ") { "${it.value}=${it.key.token}" }
        legend(g, "[a](${group.proposed?.token ?: "none"})  $keys  [x]none  [e]dit  [Tab]skip  +/- ctx  Esc cancel")
        screen.refresh()
    }

    private fun renderBounds(occ: Candidate, range: IntRange) {
        val g = newScreen()
        g.put(2, 0, "Edit bounds — ←/→ end, [ / ] start, Enter accept", TextColor.ANSI.CYAN_BRIGHT)
        drawContext(g, 2, ctx.render(range, ContextZoom.SENTENCE))
        g.put(2, 5, "selection: \"${validator.studyData.text.substring(range)}\"", TextColor.ANSI.YELLOW_BRIGHT)
        screen.refresh()
    }

    /** Draws one occurrence's context (location + highlighted instance) wrapped; returns the next row. */
    private fun drawContext(g: Paint, startRow: Int, rc: RenderedContext): Int {
        g.put(2, startRow, rc.location, TextColor.ANSI.GREEN_BRIGHT)
        val tokens = words(rc.before, false) + words(rc.instance, true) + words(rc.after, false)
        var row = startRow + 1
        var col = 4
        val width = screen.terminalSize.columns - 2
        for ((word, hot) in tokens) {
            val need = word.length + (if (col > 4) 1 else 0)
            if (col + need > width) { row++; col = 4 }
            else if (col > 4) col++
            g.put(col, row, word, if (hot) TextColor.ANSI.BLACK else TextColor.ANSI.WHITE,
                if (hot) TextColor.ANSI.YELLOW_BRIGHT else TextColor.ANSI.BLACK)
            col += word.length
        }
        return row + 2
    }

    private fun words(s: String, hot: Boolean): List<Pair<String, Boolean>> =
        s.split(Regex("\\s+")).filter { it.isNotEmpty() }.map { it to hot }

    private fun legend(g: Paint, line: String? = null) {
        val keys = CATEGORY_KEY.entries.joinToString("  ") { "${it.value}=${it.key.token}" }
        val row = screen.terminalSize.rows - 2
        g.put(2, row, line ?: "[a]ccept  $keys  [x]none  [s]plit  [r]egex  [Tab]skip  [u]ndo  [/]search  +/- ctx  [q]uit",
            TextColor.ANSI.BLACK_BRIGHT)
    }

    // ---- low-level helpers ---------------------------------------------------------------------

    /** Inline single-line text input; returns null on Esc. */
    private fun readLine(prompt: String, initial: String = ""): String? {
        val sb = StringBuilder(initial)
        while (true) {
            val g = newScreen()
            g.put(2, 1, prompt, TextColor.ANSI.CYAN_BRIGHT)
            g.put(2, 2, "> $sb", TextColor.ANSI.WHITE)
            screen.refresh()
            val key = screen.readInput()
            when (key.keyType) {
                KeyType.Escape -> return null
                KeyType.Enter -> return sb.toString()
                KeyType.Backspace -> if (sb.isNotEmpty()) sb.deleteCharAt(sb.length - 1)
                KeyType.Character -> key.character?.let { sb.append(it) }
                else -> {}
            }
        }
    }

    private fun message(msg: String) {
        val g = newScreen()
        g.put(2, 1, msg, TextColor.ANSI.WHITE)
        g.put(2, 3, "(press any key)", TextColor.ANSI.BLACK_BRIGHT)
        screen.refresh()
        screen.readInput()
    }

    private fun newScreen(): Paint {
        screen.clear()
        return Paint(screen)
    }

    private fun pct(done: Int): Int = if (total == 0) 100 else done * 100 / total
}

/** Thin wrapper over Lanterna [Screen] text drawing. */
private class Paint(private val screen: Screen) {
    fun put(col: Int, row: Int, text: String, fg: TextColor, bg: TextColor = TextColor.ANSI.BLACK) {
        if (row < 0 || row >= screen.terminalSize.rows) return
        val tg = screen.newTextGraphics()
        tg.foregroundColor = fg
        tg.backgroundColor = bg
        tg.putString(col, row, text)
    }
}

private fun ContextZoom.zoomIn(): ContextZoom = ContextZoom.entries.getOrElse(ordinal + 1) { this }
private fun ContextZoom.zoomOut(): ContextZoom = ContextZoom.entries.getOrElse(ordinal - 1) { this }
