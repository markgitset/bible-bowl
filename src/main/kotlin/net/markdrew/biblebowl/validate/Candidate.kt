package net.markdrew.biblebowl.validate

import net.markdrew.biblebowl.analysis.WordList

/**
 * One entity occurrence to validate: a character [range] over the study text, its [text], and the
 * category currently proposed for it ([proposed], null = no category yet).
 */
data class Candidate(val range: IntRange, val text: String, val proposed: WordList?)

/**
 * All occurrences sharing a surface [text], reviewed together (the bulk-validation unit).
 *
 * [proposed] is the group's pre-annotation: the single category if the occurrences agree, else the most
 * common one (the human splits out the rest).
 */
data class CandidateGroup(val text: String, val occurrences: List<Candidate>) {
    val proposed: WordList? = occurrences.groupingBy { it.proposed }.eachCount()
        .maxWithOrNull(compareBy({ it.value }, { it.key?.ordinal ?: -1 }))?.key

    val size: Int get() = occurrences.size
}
