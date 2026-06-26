package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.BOOK
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.LEADING_FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.REGEX
import net.markdrew.biblebowl.model.AnalysisUnit.SMALL_CAPS
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef

/**
 * Walks an [AnnotatedDoc]'s state transitions and translates them into [BibleTextHandler] events.
 *
 * Encapsulates the AST state traversal rules:
 * - Closing/opening inline highlights in correct nest order (longest first when opening, shortest
 *   first when closing).
 * - Skipping iterations that fall outside any paragraph (matches the previous per-writer guard).
 * - Honoring the [TextOptions.underlineUniqueWords] guard on unique-word events.
 */
object BibleTextWalker {

    fun walk(
        doc: AnnotatedDoc<AnalysisUnit>,
        studyData: StudyData,
        options: TextOptions,
        handler: BibleTextHandler,
    ) {
        handler.documentBegin(studyData, options)

        for ((excerpt, transition) in doc.stateTransitions()) {

            // 1. Close ended inline highlights, innermost (shortest span) first, so the writers'
            //    positional close tags nest correctly even when a multi-word highlight contains a
            //    shorter underline/small-caps span.
            transition.ended.filter { it.key in INLINE_HIGHLIGHTS }
                .sortedWith(compareBy<RangeAnnotation<AnalysisUnit>> { it.range.last }.thenByDescending { it.range.first })
                .forEach { closeInline(it.key, handler, options) }

            // 8. Trailing footnote. The continuing-highlight context lets LaTeX handlers do their
            //    close-emit-reopen dance; other writers ignore it.
            transition.postPoint(FOOTNOTE)?.apply {
                val anchorPos = excerpt.excerptRange.first - 1
                val verseRef = lookupVerse(studyData, anchorPos)
                val continuing = continuingHighlightContext(transition)
                handler.trailingFootnote(verseRef, value as String, continuing)
            }

            // 2. Structural endings
            if (transition.isEnded(PARAGRAPH)) handler.paragraphEnd(transition.isPresent(POETRY))
            if (transition.isEnded(POETRY)) handler.poetryEnd()
            if (transition.isEnded(CHAPTER)) handler.chapterEnd(options.chapterBreaksPage)

            // 3. Structural beginnings — book, chapter, heading. These (and steps 5-8) fire even
            //    when no PARAGRAPH is active in the new run, mirroring the historical LaTeX/Typst
            //    behavior (no paragraph guard). The `inParagraph` parameter exposes the annotation
            //    state so the DOCX handler — which historically gated on `transition.present(PARAGRAPH)`
            //    — can match its old behavior exactly. Other handlers can ignore the flag.
            val inParagraph: Boolean = transition.isPresent(PARAGRAPH)
            transition.beginning(BOOK)?.apply { handler.bookBegin(value as Book) }
            transition.beginning(CHAPTER)?.apply {
                handler.chapterBegin(value as ChapterRef, studyData.isMultiBook, options.useHeadingsForChapters, inParagraph)
            }
            transition.beginning(HEADING)?.apply { handler.headingBegin(value as String, inParagraph) }

            // 4. Paragraph + poetry + verse begins
            val paragraphAnn = transition.present(PARAGRAPH)
            val inPoetry: Boolean = transition.isPresent(POETRY)
            val isFirstPoetryParagraph: Boolean = transition.isBeginning(POETRY)
            val poetryIndentLevel: Int = if (inPoetry && paragraphAnn != null) paragraphAnn.value as Int else 0
            // `inParagraph` already computed above for the structural-beginnings call.
            if (isFirstPoetryParagraph) handler.poetryBegin()
            if (transition.isBeginning(PARAGRAPH)) {
                handler.paragraphBegin(poetryIndentLevel, inPoetry, isFirstPoetryParagraph)
            }
            transition.beginning(VERSE)?.apply {
                val verseRef = value as VerseRef
                val chapterRef = transition.present(CHAPTER)?.value as? ChapterRef
                    ?: error("VERSE begin outside any CHAPTER at $verseRef")
                handler.verseBegin(
                    verse = verseRef,
                    chapter = chapterRef,
                    multiBook = studyData.isMultiBook,
                    isFirstVerseOfChapter = verseRef.verse == 1,
                    useHeadingsForChapters = options.useHeadingsForChapters,
                    inPoetry = inPoetry,
                )
            }

            // 4b. Poetry indent — when a new paragraph begins inside POETRY, emit indentation
            //     between the verse number and any leading footnote/text. DOCX uses this for tab
            //     runs; LaTeX/Typst handle poetry indentation in `text()` and no-op here.
            if (transition.isBeginning(PARAGRAPH) && inPoetry && poetryIndentLevel > 0) {
                handler.poetryIndent(poetryIndentLevel)
            }

            // 5. Leading footnote — precedes the upcoming text, outside any new highlight group
            transition.prePoint(LEADING_FOOTNOTE)?.apply {
                val anchorPos = excerpt.excerptRange.first
                val verseRef = lookupVerse(studyData, anchorPos)
                handler.leadingFootnote(verseRef, value as String)
            }

            // 5b. Verse separator — emitted after verseBegin and leadingFootnote, but before text/highlights
            if (transition.isBeginning(VERSE)) {
                handler.verseSeparator(inPoetry)
            }

            // 6. Open new inline highlights, outermost (longest span) first, so a multi-word highlight
            //    wraps any shorter underline/small-caps span nested within it.
            transition.beginning.filter { it.key in INLINE_HIGHLIGHTS }
                .sortedWith(
                    compareByDescending<RangeAnnotation<AnalysisUnit>> { it.range.last }
                        .thenBy { it.range.first }.thenBy { nestRank(it.key) }
                )
                .forEach { openInline(it, handler, options) }

            // 7. Text
            handler.text(excerpt.excerptText, inPoetry, inParagraph)
        }

        handler.documentEnd()
    }

    /** The inline, character-level highlight layers that nest within each other (by span length). */
    private val INLINE_HIGHLIGHTS = setOf(UNIQUE_WORD, REGEX, SMALL_CAPS)

    /** Tie-break for equal-span inline layers: underline outermost, then highlight, then small-caps. */
    private fun nestRank(key: AnalysisUnit): Int = when (key) {
        UNIQUE_WORD -> 0; REGEX -> 1; SMALL_CAPS -> 2; else -> 3
    }

    private fun openInline(ann: RangeAnnotation<AnalysisUnit>, handler: BibleTextHandler, options: TextOptions) {
        when (ann.key) {
            UNIQUE_WORD -> if (options.underlineUniqueWords) handler.uniqueWordBegin()
            REGEX -> handler.regexBegin(ann.value as String)
            SMALL_CAPS -> handler.smallCapsBegin()
            else -> {}
        }
    }

    private fun closeInline(key: AnalysisUnit, handler: BibleTextHandler, options: TextOptions) {
        when (key) {
            UNIQUE_WORD -> if (options.underlineUniqueWords) handler.uniqueWordEnd()
            REGEX -> handler.regexEnd()
            SMALL_CAPS -> handler.smallCapsEnd()
            else -> {}
        }
    }

    /**
     * Resolves the verse a footnote anchor sits in. The annotation pipeline can place an anchor
     * exactly at a verse boundary; in that case the `valueContaining` lookup at the literal anchor
     * position returns null. Probe ±1 to find the adjacent verse.
     */
    private fun lookupVerse(studyData: StudyData, anchorPos: Int): VerseRef {
        return studyData.verses.valueContaining(anchorPos)
            ?: studyData.verses.valueContaining(anchorPos - 1)
            ?: studyData.verses.valueContaining(anchorPos + 1)
            ?: error("Could not resolve verse for footnote anchor at position $anchorPos")
    }

    /**
     * Returns the REGEX annotation that continues across this transition, packaged as a
     * [HighlightContext]. Assumes at most one such annotation is active (the deconfliction logic in
     * `BibleAnnotationPipeline` enforces this).
     */
    private fun continuingHighlightContext(transition: StateTransition<AnalysisUnit>): HighlightContext {
        val ann = transition.continuing.firstOrNull { it.key == REGEX } ?: return HighlightContext.None
        return HighlightContext.Regex(ann.value as String)
    }
}
