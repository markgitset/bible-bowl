package net.markdrew.biblebowl.flashcards

import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.format

sealed interface Flashcard {
    fun renderFront(markup: RichTextStrategy): String
    fun renderBack(markup: RichTextStrategy): String
}

data class SimpleFlashcard(val frontText: String, val backText: String) : Flashcard {
    override fun renderFront(markup: RichTextStrategy): String = frontText
    override fun renderBack(markup: RichTextStrategy): String = backText
}

data class EventFlashcard(
    val event: String,
    val verseRange: VerseRange,
    val headings: List<String>
) : Flashcard {
    override fun renderFront(markup: RichTextStrategy): String = event
    override fun renderBack(markup: RichTextStrategy): String =
        "${verseRange.format(FULL_BOOK_FORMAT)} (${headings.joinToString(" AND ")})"
}

data class SimpleHeadingFlashcard(
    val heading: String,
    val reference: String
) : Flashcard {
    override fun renderFront(markup: RichTextStrategy): String = heading
    override fun renderBack(markup: RichTextStrategy): String = reference
}

/**
 * For normal, forward-direction headings flashcards
 *
 * @param verseRanges The verse ranges that the heading applies to
 * @param indices The 1-based indices of this heading in the study set
 * @param allHeadings all the headings in the study set
 */
data class HeadingFlashcard(
    val heading: String,
    val verseRanges: List<VerseRange>,
    val indices: List<Int>,
    val allHeadings: List<Heading>,
) : Flashcard {
    val chapterRanges = verseRanges.map { it.toChapterRange() }

    override fun renderFront(markup: RichTextStrategy): String = heading

    override fun renderBack(markup: RichTextStrategy): String =
        if (chapterRanges.size == 1 || chapterRanges.first().start.book != chapterRanges.last().start.book) {
            chapterRanges.joinToString(""" & """) {
                it.format(FULL_BOOK_FORMAT)
            }
        } else {
            chapterRanges.first().start.book.fullName + " " + chapterRanges.joinToString(" & ") {
                it.format(NO_BOOK_FORMAT)
            }
        }

    companion object {
        @Deprecated("Shouldn't need this with Typst")
        val EMPTY: HeadingFlashcard = HeadingFlashcard("", emptyList(), emptyList(), emptyList())

        fun fromStudyData(studyData: StudyData): List<HeadingFlashcard> =
            studyData.headings.groupBy { it.title }.map { (headingTitle, headingList) ->
                HeadingFlashcard(
                    headingTitle,
                    headingList.map { it.verseRange },
                    headingList.map { it.index },
                    studyData.headings
                )
            }
    }
}

data class VerseFlashcard(
    val reference: String,
    val text: String,
    val heading: String? = null
) : Flashcard {
    override fun renderFront(markup: RichTextStrategy): String = text
    override fun renderBack(markup: RichTextStrategy): String {
        return if (heading != null) {
            markup.combine(heading, markup.newline(), markup.bold(reference))
        } else {
            markup.bold(reference)
        }
    }
}

data class WordFlashcard(
    val word: String,
    val highlightedVerse: String,
    val reference: String,
    val heading: String? = null
) : Flashcard {
    override fun renderFront(markup: RichTextStrategy): String = word
    override fun renderBack(markup: RichTextStrategy): String {
        val parts = mutableListOf<String>()
        heading?.let { parts.add(it) }
        parts.add(markup.bold(reference))
        parts.add(highlightedVerse)
        return parts.joinToString(markup.newline())
    }
}

data class FillInTheBlankFlashcard(
    val blankedText: String,
    val answers: String,
    val reference: String
) : Flashcard {
    override fun renderFront(markup: RichTextStrategy): String = blankedText
    override fun renderBack(markup: RichTextStrategy): String =
        markup.combine(answers, markup.newline(), markup.bold("($reference)"))
}
