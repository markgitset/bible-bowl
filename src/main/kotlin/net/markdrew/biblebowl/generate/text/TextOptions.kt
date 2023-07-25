package net.markdrew.biblebowl.generate.text

import java.time.LocalDate

data class TextOptions<T>(
    val fontSize: Int = 10,
    val customHighlights: Map<T, Set<Regex>> = emptyMap(),
    val testDate: LocalDate = LocalDate.now(),
    val uniqueWords: Boolean = false,
    val names: Boolean = false,
    val numbers: Boolean = false,
    val chapterBreaksPage: Boolean = false,
    val smallCaps: Map<String, String> = smallCapsNames,
) {
    val fileNameSuffix: String by lazy {
        (if (names) "names-" else "") +
        (if (numbers) "nums-" else "") +
        (if (uniqueWords) "unique-" else "") +
        (if (chapterBreaksPage) "breaks-" else "") +
        "${fontSize}pt"
    }
}