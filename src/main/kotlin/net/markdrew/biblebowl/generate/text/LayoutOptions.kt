package net.markdrew.biblebowl.generate.text

import java.time.LocalDate

/**
 * Format-agnostic layout/structural options for Bible-text rendering.
 *
 * Not every writer honors every option — capability is declared per-writer via
 * [BibleTextWriter.supports]. Unsupported combinations fail at config time rather than silently
 * dropping the option.
 *
 * @param testDate date stamped on the cover/footer (currently only honored by the DOCX writer)
 * @param fontSize body text size in points
 * @param twoColumns if true, render in two columns
 * @param chapterBreaksPage if true, force a page break between chapters
 * @param useHeadingsForChapters if true, render chapter titles as heading-style paragraphs instead of
 *   inline labels at the start of the first verse
 */
data class LayoutOptions(
    val testDate: LocalDate = LocalDate.now(),
    val fontSize: Int = 10,
    val twoColumns: Boolean = false,
    val chapterBreaksPage: Boolean = false,
    val useHeadingsForChapters: Boolean = false,
    val chapterEndLines: Boolean = false,
    val mainFont: String? = null,
    val verseNumFont: String? = null,
    val headingFont: String? = null,
    val chapterFontSize: Int? = null,
    val headingFontSize: Int? = null,
    val footnoteFontSize: Int? = null,
    val justified: Boolean? = null,
)
