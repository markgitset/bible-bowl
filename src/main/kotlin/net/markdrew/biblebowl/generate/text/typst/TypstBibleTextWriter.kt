package net.markdrew.biblebowl.generate.text.typst

import net.markdrew.biblebowl.generate.text.AnnotatedDoc
import net.markdrew.biblebowl.generate.text.BibleTextHandler
import net.markdrew.biblebowl.generate.text.BibleTextWalker
import net.markdrew.biblebowl.generate.text.BibleTextWriter
import net.markdrew.biblebowl.generate.text.FeatureOptions
import net.markdrew.biblebowl.generate.text.HighlightContext
import net.markdrew.biblebowl.generate.text.LayoutOptions
import net.markdrew.biblebowl.generate.text.OutputFormat
import net.markdrew.biblebowl.generate.text.Typst
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.typst.typstToPdf
import java.nio.file.Files
import java.nio.file.Path
import java.time.format.DateTimeFormatter

private val asteriskBracketedWordRegex = Regex("""\*([^*]+)\*""")

/**
 * Typst output writer. Owns file lifecycle and PDF compilation; the per-transition emit logic lives
 * in [TypstHandler], driven by [BibleTextWalker].
 *
 * Supports every layout option — Typst handles 1/2-column layout, page breaks, heading-style
 * chapters, and all feature highlights natively.
 */
class TypstBibleTextWriter(private val style: TypstStyle) : BibleTextWriter {

    override val format: OutputFormat = Typst

    override fun supports(layout: LayoutOptions): Boolean = true

    override fun write(
        outputFile: Path,
        doc: AnnotatedDoc<AnalysisUnit>,
        studyData: StudyData,
        layout: LayoutOptions,
        features: FeatureOptions,
        copyrightDisclaimer: String,
    ) {
        Files.createDirectories(outputFile.parent)
        outputFile.toFile().writer().use { out ->
            BibleTextWalker.walk(doc, studyData, layout, features, TypstHandler(out, style, copyrightDisclaimer))
        }
        println("Wrote $outputFile")
        if (System.getProperty("skip-pdf-generation") != "true") {
            outputFile.typstToPdf(keepTypFiles = true)
        }
    }
}

private class TypstHandler(
    private val out: Appendable,
    private val style: TypstStyle,
    private val copyrightDisclaimer: String,
) : BibleTextHandler {

    /** Indent level of the poetry line currently being emitted; passed from [paragraphBegin] to
     *  [verseBegin] so the hanging verse number knows how far back to reach. 0 outside poetry. */
    private var currentPoetryIndentLevel = 0

    override fun documentBegin(studyData: StudyData, layout: LayoutOptions, features: FeatureOptions) {
        val columns = if (layout.twoColumns) 2 else 1
        val justify = if (style.justified) "true" else "false"
        val date = layout.testDate.format(dateFormatter)
        val title = studyData.studySet.name
        out.appendLine("""
            #set page(
              paper: "us-letter",
              margin: (top: 1in, bottom: 0.75in, x: 0.75in),
              columns: $columns,
              header: context {
                let page-num = counter(page).get().first()
                let page-verses = query(<verse-marker>).filter(v => v.location().page() == page-num)
                let val = if page-verses.len() > 0 {
                  if calc.even(page-num) {
                    page-verses.first().value
                  } else {
                    page-verses.last().value
                  }
                } else {
                  let before-verses = query(<verse-marker>).filter(v => v.location().page() < page-num)
                  if before-verses.len() > 0 {
                    before-verses.last().value
                  } else {
                    ""
                  }
                }
                if val != "" {
                  if calc.even(page-num) {
                    align(left)[*#val*]
                  } else {
                    align(right)[*#val*]
                  }
                }
              },
              footer: context {
                let page-num = counter(page).get().first()
                if calc.even(page-num) {
                  grid(
                    columns: (1fr, 1fr),
                    align(left)[#counter(page).display()],
                    align(right)[Texas Bible Bowl, ${escape(date)}],
                  )
                } else {
                  grid(
                    columns: (1fr, 1fr),
                    align(left)[Texas Bible Bowl, ${escape(date)}],
                    align(right)[#counter(page).display()],
                  )
                }
              },
            )
            #set text(font: "${style.mainFont}", size: ${layout.fontSize}pt)
            #set par(justify: $justify)
            #show footnote.entry: set text(size: ${style.footnoteFontSize}pt)

            // Built-in highlight color — the default fill for divine names (matching DOCX)
            #let divineColor = rgb($DIVINE_R, $DIVINE_G, $DIVINE_B)
        """.trimIndent())

        // Palette-supplied colors (dedup against built-ins by name). Names (`other`) and numbers
        // (`numbers`) are ordinary palette entries now and emit their colors through this loop.
        val seen = mutableSetOf("divineColor")
        for ((color, _) in features.customHighlights.entries) {
            if (seen.add(color.name)) {
                val (r, g, b) = color.rgb
                out.appendLine("#let ${color.name} = rgb($r, $g, $b)")
            }
        }

        out.appendLine("""
            #let myhl(color, body) = highlight(fill: color, body)
            #let versenum(n) = box(
                fill: rgb("404040"),
                inset: (x: 3pt, y: 1pt),
                radius: 1pt,
                text(fill: white, weight: "bold", font: "${style.verseNumFont}")[#n],
            )
            #let chapter-heading(label) = heading(
                level: 1, outlined: false,
                text(font: "${style.headingFont}", size: ${style.chapterFontSize}pt, weight: "bold")[#label],
            )
            #let section-heading(label) = heading(
                level: 2, outlined: false,
                text(font: "${style.headingFont}", size: ${style.headingFontSize}pt, weight: "bold")[#label],
            )
            #let pstep = 2em
            #let pind(level) = h(pstep * level)
            // Poetry verse number: hangs into the whitespace to the left of the *first* indent
            // (`pstep`), regardless of this line's indent `level`, with zero net advance — so the
            // contents stay at `pstep * level` and the number sits near the margin at every level.
            #let pverse(n, level) = context {
                let label = versenum(n)
                let w = measure(label).width
                let gap = 0.3em
                let back = calc.max(level - 1, 0) * pstep + w + gap
                h(-back)
                label
                h(back - w)
            }
        """.trimIndent())
        out.appendLine()
    }

    override fun documentEnd() {
        out.appendLine()
        out.appendLine()
        val paragraphs = copyrightDisclaimer.split("\n\n")
            .map { escape(it.trim()) }
            .filter { it.isNotEmpty() }
            .joinToString("\n\n  ")
        out.appendLine("""
            #v(1em)
            #align(center)[
              #set text(size: 0.85em)
              $paragraphs
            ]
        """.trimIndent())
    }

    override fun chapterBegin(chapter: ChapterRef, multiBook: Boolean, asHeading: Boolean, inParagraph: Boolean) {
        if (asHeading) {
            out.appendLine().appendLine("#chapter-heading[${escape(chapterLabel(chapter, multiBook))}]").appendLine()
        }
    }

    override fun chapterEnd(pageBreak: Boolean) {
        if (pageBreak) out.appendLine().appendLine("#pagebreak()")
    }

    override fun headingBegin(heading: String, inParagraph: Boolean) {
        out.appendLine().appendLine("#section-heading[${escape(heading)}]").appendLine()
    }

    override fun paragraphBegin(poetryIndentLevel: Int, inPoetry: Boolean, isFirstParagraphOfPoetry: Boolean) {
        // Emit the poetry line's indent here, before the verse number, so every line's contents start
        // at `pstep * level` whether or not the line begins with a verse number (the number hangs into
        // the whitespace to the left via pverse). Same-level lines therefore align by their contents.
        // Prose paragraphs and base-level (0) poetry lines start flush; line separation comes from
        // paragraphEnd(). Wrapped lines hang at `pstep * 4` via the block's hanging-indent setting.
        currentPoetryIndentLevel = if (inPoetry) poetryIndentLevel else 0
        if (inPoetry && poetryIndentLevel > 0) out.append("#pind($poetryIndentLevel)")
    }

    override fun paragraphEnd(inPoetry: Boolean) {
        // Every line — prose paragraph or poetry line — is separated by a blank line. For poetry
        // this makes each line its own paragraph so `hanging-indent` applies per line.
        out.appendLine().appendLine()
    }

    override fun poetryBegin() {
        // Scope poetry in a breakable block (so it can still split across columns/pages) that renders
        // lines single-spaced, ragged-left, and hanging-indented — matching esv.org.
        out.appendLine()
        out.appendLine("#block(breakable: true)[")
        out.appendLine("#set par(justify: false, spacing: 0.6em, hanging-indent: pstep * 4)")
    }

    override fun poetryEnd() {
        out.appendLine("]")
        out.appendLine()
    }

    override fun verseBegin(
        verse: VerseRef,
        chapter: ChapterRef,
        multiBook: Boolean,
        isFirstVerseOfChapter: Boolean,
        useHeadingsForChapters: Boolean,
        inPoetry: Boolean,
    ) {
        val formattedRef = escape(verse.format(FULL_BOOK_FORMAT))
        out.append("#metadata(\"$formattedRef\")<verse-marker>")
        // In poetry, the line break is produced by paragraphEnd; only prose needs the leading newline.
        if (!inPoetry) out.appendLine()
        if (isFirstVerseOfChapter && !useHeadingsForChapters) {
            // Inline chapter label at the start of the chapter's first verse — mirrors DOCX's
            // useHeadingsForChapters=false path.
            out.append("*${escape(chapterLabel(chapter, multiBook))}*")
        } else {
            // In poetry the number hangs left of the first indent (near the margin) while the contents
            // stay at their indent; in prose it sits inline.
            out.append(
                if (inPoetry) "#pverse(${verse.verse}, $currentPoetryIndentLevel)"
                else "#versenum(${verse.verse})"
            )
        }
    }

    override fun verseSeparator(inPoetry: Boolean) {
        // Prose separates the verse number from its text with a non-breaking space; in poetry the gap
        // is built into the hanging pverse, and the contents must stay flush to their indent.
        if (!inPoetry) out.append("~")
    }

    override fun bookBegin(book: Book) {
        // No emit — book heading is handled by the chapter label.
    }

    override fun poetryIndent(numIndents: Int) {
        // No-op — the poetry indent is emitted in paragraphBegin(), before the verse number, so the
        // contents (not the number) carry the indent and same-level lines align.
    }

    override fun leadingFootnote(verseRef: VerseRef, content: String) {
        out.append(renderFootnote(verseRef, content))
    }

    override fun trailingFootnote(verseRef: VerseRef, content: String, continuing: HighlightContext) {
        // Typst's #footnote[] works fine inside #highlight(...)[ ... ], so no close/reopen dance.
        out.append(renderFootnote(verseRef, content))
    }

    override fun uniqueWordBegin() { out.append("#underline[") }
    override fun uniqueWordEnd()   { out.append(']') }
    override fun regexBegin(category: String) { out.append("#myhl($category)[") }
    override fun regexEnd()        { out.append(']') }
    override fun smallCapsBegin() {} // Typst handles small caps via inline `LORD` substitution in text().
    override fun smallCapsEnd()   {}

    override fun text(text: String, inPoetry: Boolean, inParagraph: Boolean) {
        // Inter-line regions in poetry are whitespace only (the newlines between lines and ESV's
        // trailing post-poetry indent line); drop them so indentation is driven solely by pind().
        if (inPoetry && !inParagraph) return
        out.append(emitText(text))
    }

    private fun chapterLabel(chapterRef: ChapterRef, multiBook: Boolean): String =
        if (multiBook) chapterRef.format(FULL_BOOK_FORMAT) else "Chapter ${chapterRef.chapter}"

    private fun emitText(rawText: String): String =
        escape(rawText).replace("LORD".toRegex(), "#smallcaps[Lord]")

    private fun renderFootnote(verseRef: VerseRef, content: String): String {
        val escaped = escape(content)
        val italicized = escaped.replace(asteriskBracketedWordRegex, "#emph[\$1]")
        return "#footnote[${verseRef.format(FULL_BOOK_FORMAT)} $italicized]"
    }

    /**
     * Escapes the four Typst structural delimiters: `\`, `[`, `]`, `#`. Backslash must be escaped
     * first so we don't double-escape backslashes introduced by the other replacements.
     */
    private fun escape(s: String): String =
        s.replace("\\", "\\\\")
            .replace("[", "\\[")
            .replace("]", "\\]")
            .replace("#", "\\#")

    companion object {
        // Built-in divine highlight color — matching DOCX historical value. Names and numbers come from
        // the palette now (the `other` and `numbers` categories).
        private const val DIVINE_R = 255
        private const val DIVINE_G = 255
        private const val DIVINE_B = 0

        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL d, uuuu")
    }
}
