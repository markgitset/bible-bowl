package net.markdrew.biblebowl.generate.text.typst

import net.markdrew.biblebowl.INDENT_POETRY_LINES
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
    ) {
        Files.createDirectories(outputFile.parent)
        outputFile.toFile().writer().use { out ->
            BibleTextWalker.walk(doc, studyData, layout, features, TypstHandler(out, style))
        }
        println("Wrote $outputFile")
        outputFile.typstToPdf(keepTypFiles = true)
    }
}

private class TypstHandler(
    private val out: Appendable,
    private val style: TypstStyle,
) : BibleTextHandler {

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
              footer: align(center)[${escape(title)} — ${escape(date)}],
            )
            #set text(font: "${style.mainFont}", size: ${layout.fontSize}pt)
            #set par(justify: $justify)
            #show footnote.entry: set text(size: ${style.footnoteFontSize}pt)

            // Built-in highlight colors (matching DOCX)
            #let namesColor  = rgb($NAMES_R, $NAMES_G, $NAMES_B)
            #let numsColor   = rgb($NUMBERS_R, $NUMBERS_G, $NUMBERS_B)
            #let divineColor = rgb($DIVINE_R, $DIVINE_G, $DIVINE_B)
        """.trimIndent())

        // Palette-supplied colors (dedup against built-ins by name)
        val seen = mutableSetOf("namesColor", "numsColor", "divineColor")
        for ((color, _) in features.customHighlights.entries) {
            if (seen.add(color.name)) {
                val (r, g, b) = color.rgb
                out.appendLine("#let ${color.name} = rgb($r, $g, $b)")
            }
        }

        out.appendLine("""
            #let myhl(color, body) = highlight(fill: color, body)
            #let myname(body)   = myhl(namesColor,  body)
            #let mynumber(body) = myhl(numsColor,   body)
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
            #let vin = h(2em)
        """.trimIndent())
        out.appendLine()
    }

    override fun documentEnd() {
        out.appendLine()
        out.appendLine()
        out.appendLine("""
            #v(1em)
            #align(center)[
              #set text(size: 0.85em)
              Taken from the _ESV® Bible_ (_The Holy Bible, English Standard Version®_),
              Copyright © 2001 by Crossway, a publishing ministry of Good News Publishers.
              Used by permission. All rights reserved.
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
        // No explicit emit — poetry runs use trailing `\` linebreaks; prose paragraphs are separated
        // by the blank line emitted in paragraphEnd().
    }

    override fun paragraphEnd(inPoetry: Boolean) {
        if (!inPoetry) out.appendLine().appendLine()
    }

    override fun poetryBegin() {
        // No explicit emit — poetry markup happens inline (per-line `\` + `#vin`).
    }

    override fun poetryEnd() {
        out.appendLine().appendLine()
    }

    override fun verseBegin(
        verse: VerseRef,
        chapter: ChapterRef,
        multiBook: Boolean,
        isFirstVerseOfChapter: Boolean,
        useHeadingsForChapters: Boolean,
        inPoetry: Boolean,
    ) {
        out.appendLine()
        if (isFirstVerseOfChapter && !useHeadingsForChapters) {
            // Inline chapter label at the start of the chapter's first verse — mirrors DOCX's
            // useHeadingsForChapters=false path.
            out.append("*${escape(chapterLabel(chapter, multiBook))}*")
        } else {
            out.append("#versenum(${verse.verse})")
        }
    }

    override fun verseSeparator(inPoetry: Boolean) {
        out.append(" ")
    }

    override fun bookBegin(book: Book) {
        // No emit — book heading is handled by the chapter label.
    }

    override fun poetryIndent(numIndents: Int) {
        // Typst converts leading-space runs into `#vin` inside text(); no separate event handling needed.
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
    override fun nameBegin()       { out.append("#myname[") }
    override fun nameEnd()         { out.append(']') }
    override fun numberBegin()     { out.append("#mynumber[") }
    override fun numberEnd()       { out.append(']') }
    override fun regexBegin(category: String) { out.append("#myhl($category)[") }
    override fun regexEnd()        { out.append(']') }
    override fun smallCapsBegin() {} // Typst handles small caps via inline `LORD` substitution in text().
    override fun smallCapsEnd()   {}

    override fun text(text: String, inPoetry: Boolean, inParagraph: Boolean) {
        out.append(emitText(text, inPoetry))
    }

    private fun chapterLabel(chapterRef: ChapterRef, multiBook: Boolean): String =
        if (multiBook) chapterRef.format(FULL_BOOK_FORMAT) else "Chapter ${chapterRef.chapter}"

    private fun emitText(rawText: String, inPoetry: Boolean): String {
        var text = escape(rawText)
        text = text.replace("LORD".toRegex(), "#smallcaps[Lord]")
        if (inPoetry) {
            text = text
                // Replace each newline with `\<NL>` so Typst renders it as a hard linebreak. Java's
                // replaceAll consumes `\\` as one literal `\`; Kotlin's `"\\\\"` is two backslashes,
                // so the replacement string is `\\<NL>` → output `\<NL>`.
                .replace("""\n""".toRegex(), "\\\\\n")
                // Each run of INDENT_POETRY_LINES spaces that follows another such run is a
                // second-level indent — emit a `#vin` (a 2em horizontal-space binding from the preamble).
                .replace("""(?<= {$INDENT_POETRY_LINES}) {$INDENT_POETRY_LINES}""".toRegex(), "#vin ")
        }
        return text
    }

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
        // Built-in highlight colors — matching DOCX historical values.
        private const val NAMES_R = 204
        private const val NAMES_G = 204
        private const val NAMES_B = 204
        private const val NUMBERS_R = 255
        private const val NUMBERS_G = 182
        private const val NUMBERS_B = 108
        private const val DIVINE_R = 255
        private const val DIVINE_G = 255
        private const val DIVINE_B = 0

        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL d, uuuu")
    }
}
