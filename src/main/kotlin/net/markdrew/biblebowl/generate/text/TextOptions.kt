package net.markdrew.biblebowl.generate.text

import java.time.LocalDate

/**
 * Format-agnostic layout/structural options and content features for Bible-text rendering.
 *
 * @param style typographic style family (StyleId.TBB or StyleId.MARKS)
 * @param testDate date stamped on the cover/footer (currently only honored by the DOCX writer)
 * @param fontSize body text size in points
 * @param twoColumns if true, render in two columns
 * @param chapterBreaksPage if true, force a page break between chapters
 * @param useHeadingsForChapters if true, render chapter titles as heading-style paragraphs instead of
 *   inline labels at the start of the first verse
 * @param chapterEndLines center the chapter label and draw bold, black horizontal lines extending across the column
 * @param mainFont main body text font family override
 * @param headingFont chapter and section headings font family override
 * @param verseNumFont verse numbers font family override
 * @param chapterFontSize chapter heading font size in points override
 * @param headingFontSize section heading font size in points override
 * @param footnoteFontSize footnote font size in points override
 * @param justified body text justification override
 * @param underlineUniqueWords if true, underline words that occur exactly once in the study set
 * @param customHighlights palette of regex/category-driven highlights, with caller-chosen colors.
 * @param smallCaps map from match text to its small-caps replacement (e.g., `"LORD" -> "Lord"`)
 * @param verseOnNewLine if true, start every verse on its own line. Affects prose only.
 */
data class TextOptions(
    val style: StyleId = StyleId.TBB,
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
    val underlineUniqueWords: Boolean = false,
    val customHighlights: HighlightPalette = HighlightPalette.empty(),
    val smallCaps: Map<String, String> = smallCapsNames,
    val verseOnNewLine: Boolean = false,
)
