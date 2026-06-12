package net.markdrew.biblebowl.model

/**
 * Categories of textual span that can be annotated over [StudyData.text]
 *
 * Used as keys when attaching analyses (verse boundaries, headings, name spans, unique words, etc.) to character
 * ranges in the joined text.
 */
enum class AnalysisUnit {
    WORD,
    WORD_NGRAM,
    SENTENCE,
    VERSE,
    PARAGRAPH,
    HEADING,
    CHAPTER,
    BOOK,
    FOOTNOTE,
    LEADING_FOOTNOTE,
    POETRY,
    UNIQUE_WORD,
    REGEX,
    SMALL_CAPS,
    STUDY_SET,
}
