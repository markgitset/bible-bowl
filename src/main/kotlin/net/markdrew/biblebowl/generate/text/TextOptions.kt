package net.markdrew.biblebowl.generate.text

import java.time.LocalDate

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
    val fileNameSuffix: String by lazy {
        (if (highlightNames) "names-" else "") +
        (if (highlightNumbers) "nums-" else "") +
        (if (underlineUniqueWords) "unique-" else "") +
        (if (chapterBreaksPage) "breaks-" else "") +
        "${fontSize}pt"
    }
}