package net.markdrew.biblebowl.generate.text

import java.time.LocalDate

/**
 * Configuration knobs for the Bible-text renderers (LaTeX, DOCX)
 *
 * @param testDate date printed on the cover/footer; defaults to today.
 * @param fontSize body text size in points
 * @param customHighlights map from a colour key (interpreted by the renderer) to the set of regexes whose
 *   matches should be highlighted in that colour
 * @param underlineUniqueWords if true, underline words that occur exactly once in the study set
 * @param highlightNames if true, highlight detected names
 * @param highlightNumbers if true, highlight detected numbers
 * @param chapterBreaksPage if true, force a page break between chapters
 * @param smallCaps map from match text to its small-caps replacement
 * @param twoColumns if true, render in two columns
 * @param useHeadingsForChapters if true, use heading-style layout for chapter titles instead of inline labels
 */
data class TextOptions<T>(
    val testDate: LocalDate = LocalDate.now(),
    val fontSize: Int = 10,
    val customHighlights: Map<T, Set<Regex>> = emptyMap(),
    val underlineUniqueWords: Boolean = false,
    val highlightNames: Boolean = false,
    val highlightNumbers: Boolean = false,
    val chapterBreaksPage: Boolean = false,
    val smallCaps: Map<String, String> = smallCapsNames,
    val twoColumns: Boolean = false,
    val useHeadingsForChapters: Boolean = false,
) {
    /**
     * Filename suffix derived from the boolean flags and [fontSize], used to disambiguate output files when
     * multiple variants are generated for the same study set.
     */
    val fileNameSuffix: String by lazy {
        (if (highlightNames) "names-" else "") +
        (if (highlightNumbers) "nums-" else "") +
        (if (underlineUniqueWords) "unique-" else "") +
        (if (chapterBreaksPage) "breaks-" else "") +
        "${fontSize}pt"
    }
}
