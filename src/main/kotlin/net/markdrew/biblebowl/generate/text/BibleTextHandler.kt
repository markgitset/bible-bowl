package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef

/**
 * The state in which a writer finds itself when a footnote anchor lands at a transition boundary.
 *
 * Only the LaTeX writer uses this today: when a `\footnote` falls *inside* a still-open TikZ
 * highlight group, LaTeX must close the group, emit the footnote, then reopen it (TeX grouping
 * traps `\footnote` content otherwise). DOCX and Typst writers ignore the parameter — their
 * footnote constructs nest cleanly.
 */
sealed interface HighlightContext {
    /** No REGEX highlight continues across this transition. */
    data object None : HighlightContext

    /** A REGEX highlight continues, tagged with its highlight [category] id. */
    data class Regex(val category: String) : HighlightContext
}

/**
 * Event handler invoked by [BibleTextWalker] as it walks an annotated Bible document.
 *
 * The walker owns all structural dispatch: which event fires when, the order in which highlight
 * begins and ends are emitted, when verse-refs are looked up for footnote anchors, and which
 * options gate which events. Implementations of this interface contain *only* the format-specific
 * emit logic — DOCX builds a docx4j AST; LaTeX/Typst append textual markup.
 *
 * Every method is abstract by design: a missing override is a compile error, not a silent no-op.
 * This is a direct response to the pre-existing LaTeX footnote bug, in which the writer called
 * `transition.beginning(FOOTNOTE)` (which never fires for point annotations) and silently dropped
 * every footnote until adding Typst exposed the divergence.
 *
 * ## Method ordering contract
 *
 * Within one transition boundary, the walker invokes methods in this exact order. The unique-word
 * methods are gated on [FeatureOptions.underlineUniqueWords] — that gate lives in the walker, not in
 * handlers.
 *
 *  1. [uniqueWordEnd], [regexEnd], [smallCapsEnd]
 *  2. [trailingFootnote]
 *  3. [paragraphEnd], [poetryEnd], [chapterEnd]
 *  4. (the walker skips the rest of the iteration if we're not inside a paragraph)
 *  5. [bookBegin], [chapterBegin], [headingBegin]
 *  6. [paragraphBegin], [verseBegin]
 *  7. [leadingFootnote]
 *  8. [uniqueWordBegin], [regexBegin], [smallCapsBegin]
 *  9. [text]
 *
 * [documentBegin] fires once before the loop; [documentEnd] fires once after. They are not
 * driven by the STUDY_SET annotation transitions; the walker hoists them out.
 */
interface BibleTextHandler {

    /** Called once before any transitions are processed. Implementations typically emit a preamble. */
    fun documentBegin(studyData: StudyData, options: TextOptions)

    /** Called once after every transition has been processed. Implementations typically emit a postamble. */
    fun documentEnd()

    /**
     * A new chapter is starting.
     *
     * @param chapter the chapter being entered
     * @param multiBook whether the study set spans multiple books (affects label format)
     * @param asHeading [LayoutOptions.useHeadingsForChapters] — true means emit a standalone heading paragraph,
     *   false means defer the chapter label into the upcoming first-verse-of-chapter emission
     * @param inParagraph whether a PARAGRAPH annotation is active in the new run (i.e., not between
     *   paragraphs). The DOCX handler uses this to gate AST mutation; other handlers can ignore it.
     */
    fun chapterBegin(chapter: ChapterRef, multiBook: Boolean, asHeading: Boolean, inParagraph: Boolean)

    /**
     * A chapter has just ended.
     *
     * @param pageBreak [LayoutOptions.chapterBreaksPage] — true means emit a page break here
     */
    fun chapterEnd(pageBreak: Boolean)

    /**
     * An ESV section heading is starting.
     *
     * @param inParagraph see [chapterBegin] — same semantics.
     */
    fun headingBegin(heading: String, inParagraph: Boolean)

    /**
     * A new paragraph is starting.
     *
     * @param poetryIndentLevel the indent level for poetry paragraphs (1 or 2); 0 for prose
     * @param inPoetry true if this paragraph is inside a POETRY block
     * @param isFirstParagraphOfPoetry true if the POETRY block also begins at this transition
     */
    fun paragraphBegin(poetryIndentLevel: Int, inPoetry: Boolean, isFirstParagraphOfPoetry: Boolean)

    /** The current paragraph is ending. */
    fun paragraphEnd(inPoetry: Boolean)

    /** A POETRY block is starting (before its first paragraph begins). */
    fun poetryBegin()

    /** A POETRY block has ended. */
    fun poetryEnd()

    /**
     * A new verse is starting. Carries enough context for every writer's special cases:
     * the DOCX inline-chapter-label path, the multibook+poetry+verse-1 paragraph re-push, and
     * poetry-vs-prose verse-number presentation.
     *
     * @param verse the verse being entered
     * @param chapter the chapter this verse lives in
     * @param multiBook whether the study set spans multiple books
     * @param isFirstVerseOfChapter shorthand for `verse.verse == 1`
     * @param useHeadingsForChapters [LayoutOptions.useHeadingsForChapters]
     * @param inPoetry whether this verse is in a POETRY block
     */
    fun verseBegin(
        verse: VerseRef,
        chapter: ChapterRef,
        multiBook: Boolean,
        isFirstVerseOfChapter: Boolean,
        useHeadingsForChapters: Boolean,
        inPoetry: Boolean,
    )

    /**
     * Emitted after [verseBegin] (and [leadingFootnote]) to separate the verse label from the
     * verse text. Typically a space or non-breaking space.
     */
    fun verseSeparator(inPoetry: Boolean)

    /** Reserved for symmetry; no writer currently emits anything here. */
    fun bookBegin(book: Book)

    /**
     * Emitted after [verseBegin] when a new paragraph begins inside a POETRY block. The DOCX
     * handler emits this as N `<w:tab/>` runs to indent the line; LaTeX and Typst handlers convert
     * leading whitespace to `\vin` / `#vin` inside [text] instead and can no-op here.
     *
     * @param numIndents indent level (1 or 2)
     */
    fun poetryIndent(numIndents: Int)

    /** A leading footnote (zero-width, *precedes* the upcoming text run). */
    fun leadingFootnote(verseRef: VerseRef, content: String)

    /**
     * A trailing footnote (zero-width, *follows* the just-emitted text run).
     *
     * @param continuing the REGEX highlight (if any) that's still active across this transition. The
     *   LaTeX writer uses this to close-emit-reopen the group around `\footnote`; DOCX and Typst
     *   writers ignore the parameter.
     */
    fun trailingFootnote(verseRef: VerseRef, content: String, continuing: HighlightContext)

    /** A unique-word underline span is beginning. */
    fun uniqueWordBegin()

    /** The current unique-word underline span is ending. */
    fun uniqueWordEnd()

    /** A custom-palette highlight span is beginning, tagged with its highlight [category] id. */
    fun regexBegin(category: String)

    /** The current custom-palette highlight span is ending. */
    fun regexEnd()

    /** A small-caps span is beginning. (Only the DOCX writer consumes the annotation; LaTeX/Typst do inline `LORD` substitution at [text] time.) */
    fun smallCapsBegin()

    /** The current small-caps span is ending. */
    fun smallCapsEnd()

    /**
     * A text run to be emitted.
     *
     * @param text the raw excerpt text (un-escaped)
     * @param inPoetry true if this run is inside a POETRY block; affects line-break and indent handling
     * @param inParagraph whether a PARAGRAPH annotation is active in this run. DOCX gates AST mutation
     *   on this; LaTeX / Typst handlers historically emit inter-paragraph whitespace and can ignore it.
     */
    fun text(text: String, inPoetry: Boolean, inParagraph: Boolean)
}
