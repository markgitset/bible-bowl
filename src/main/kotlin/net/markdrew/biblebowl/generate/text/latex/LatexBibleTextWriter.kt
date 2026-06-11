package net.markdrew.biblebowl.generate.text.latex

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.generate.text.AnnotatedDoc
import net.markdrew.biblebowl.generate.text.BibleTextHandler
import net.markdrew.biblebowl.generate.text.BibleTextWalker
import net.markdrew.biblebowl.generate.text.BibleTextWriter
import net.markdrew.biblebowl.generate.text.FeatureOptions
import net.markdrew.biblebowl.generate.text.HighlightColor
import net.markdrew.biblebowl.generate.text.HighlightContext
import net.markdrew.biblebowl.generate.text.HighlightPalette
import net.markdrew.biblebowl.generate.text.Latex
import net.markdrew.biblebowl.generate.text.LayoutOptions
import net.markdrew.biblebowl.generate.text.OutputFormat
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Files
import java.nio.file.Path

private val asteriskBracketedWordRegex = Regex("""\*([^*]+)\*""")

/**
 * LaTeX output writer. Owns file lifecycle and `pdflatex` compilation; the per-transition emit
 * logic lives in [LatexHandler], driven by [BibleTextWalker].
 *
 * Capability: the preamble hardcodes a two-column body via `\begin{multicols}{2}`, so [supports]
 * rejects layouts where `twoColumns = false`.
 */
class LatexBibleTextWriter : BibleTextWriter {

    override val format: OutputFormat = Latex

    override fun supports(layout: LayoutOptions): Boolean = layout.twoColumns

    override fun write(
        outputFile: Path,
        doc: AnnotatedDoc<AnalysisUnit>,
        studyData: StudyData,
        layout: LayoutOptions,
        features: FeatureOptions,
    ) {
        require(supports(layout)) {
            "LatexBibleTextWriter requires layout.twoColumns = true (preamble hardcodes \\begin{multicols}{2})"
        }
        Files.createDirectories(outputFile.parent)
        outputFile.toFile().writer().use { out ->
            BibleTextWalker.walk(doc, studyData, layout, features, LatexHandler(out))
        }
        println("Wrote $outputFile")
        outputFile.latexToPdf(twice = true, keepTexFiles = true, showStdIo = false)
    }
}

private class LatexHandler(private val out: Appendable) : BibleTextHandler {

    override fun documentBegin(studyData: StudyData, layout: LayoutOptions, features: FeatureOptions) {
        preamble(out, studyData.studySet.name, layout, features)
    }

    override fun documentEnd() {
        out.appendLine(
            """

                \end{multicols}
                \vspace*{\fill}
                \vspace{\baselineskip}
                \footnotesize
                \noindent %\raggedright
                Taken from the \textit{ESV}\textsuperscript{\textregistered}\textit{ Bible }
                (\textit{The Holy Bible, English Standard Version}\textsuperscript{\textregistered}),
                Copyright \textsuperscript{\textcopyright} 2001 by Crossway,
                a publishing ministry of Good News Publishers. Used by permission. All rights reserved.
                \vspace{\baselineskip}
                \end{document}
            """.trimIndent()
        )
    }

    override fun chapterBegin(chapter: ChapterRef, multiBook: Boolean, asHeading: Boolean, inParagraph: Boolean) {
        // LaTeX always renders chapter as a heading via `\mychapter{}`; the `asHeading` flag is
        // not honored (no inline-chapter-label mode in the LaTeX preamble).
        val book = if (multiBook) chapter.book else null
        out.append("\n\n\\mychapter")
        if (book != null) out.append("[${book.fullName.uppercase()}]")
        out.appendLine("{${chapter.chapter}}")
    }

    override fun chapterEnd(pageBreak: Boolean) {
        if (pageBreak) out.appendLine("\\clearpage")
    }

    override fun headingBegin(heading: String, inParagraph: Boolean) {
        out.appendLine()
        out.appendLine("""\subsection*{$heading}""")
        out.appendLine()
    }

    override fun paragraphBegin(poetryIndentLevel: Int, inPoetry: Boolean, isFirstParagraphOfPoetry: Boolean) {
        // No explicit per-paragraph emit; paragraph breaks come from blank lines in paragraphEnd.
    }

    override fun paragraphEnd(inPoetry: Boolean) {
        if (!inPoetry) out.appendLine()
    }

    override fun poetryBegin() {
        out.appendLine("""\begin{verse}""")
    }

    override fun poetryEnd() {
        out.appendLine("\\end{verse}\n")
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
        val core = """\versenum{${verse.verse}}"""
        out.append(if (inPoetry) "\\flagverse{$core}" else core)
    }

    override fun verseSeparator(inPoetry: Boolean) {
        out.append(" ")
    }

    override fun bookBegin(book: Book) {
        // No-op — the current LaTeX writer's appendBookTitle was commented out; preserved here.
    }

    override fun poetryIndent(numIndents: Int) {
        // LaTeX converts leading-space runs into `\vin` inside text(); no separate event handling needed.
    }

    override fun leadingFootnote(verseRef: VerseRef, content: String) {
        out.append(renderFootnote(verseRef, content))
    }

    override fun trailingFootnote(verseRef: VerseRef, content: String, continuing: HighlightContext) {
        // Close the currently-active TikZ highlight (if any) before \footnote so that TeX grouping
        // doesn't trap the footnote inside. Reopen the same highlight after the footnote.
        when (continuing) {
            is HighlightContext.Regex, HighlightContext.Name, HighlightContext.Number -> out.append('}')
            HighlightContext.None -> Unit
        }
//        out.append('}')
        out.append(renderFootnote(verseRef, content))
        when (continuing) {
            is HighlightContext.Regex -> out.append("""\myhl[${continuing.category}]{""")
            HighlightContext.Name -> out.append("""\myname{""")
            HighlightContext.Number -> out.append("""\mynumber{""")
            HighlightContext.None -> Unit
        }
    }

    override fun uniqueWordBegin() { out.append("""{\uline{""") }
    override fun uniqueWordEnd()   { out.append("}}") }
    override fun nameBegin()       { out.append("""\myname{""") }
    override fun nameEnd()         { out.append('}') }
    override fun numberBegin()     { out.append("""\mynumber{""") }
    override fun numberEnd()       { out.append('}') }
    override fun regexBegin(category: String) { out.append("""\myhl[$category]{""") }
    override fun regexEnd()        { out.append('}') }
    override fun smallCapsBegin() {} // LaTeX uses inline `LORD` substitution in text() instead of the annotation.
    override fun smallCapsEnd()   {}

    override fun text(text: String, inPoetry: Boolean, inParagraph: Boolean) {
        var out2 = text.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
        if (inPoetry) {
            out2 = out2
                .replace("""\n""".toRegex(), "\\\\\\\\\n")
                .replace("""(?<= {$INDENT_POETRY_LINES}) {$INDENT_POETRY_LINES}""".toRegex(), """\\vin """)
        }
        out.append(out2)
    }

    private fun renderFootnote(verseRef: VerseRef, content: String): String {
        val italicized = content.replace(asteriskBracketedWordRegex, """\\textit{$1}""")
        return """\footnote{${verseRef.format(FULL_BOOK_FORMAT)} $italicized}"""
    }

    private fun preamble(out: Appendable, bookName: String, layout: LayoutOptions, features: FeatureOptions) {
        out.appendLine(
            """
                \documentclass[${layout.fontSize}pt, letter paper, twoside]{article}
                \usepackage[utf8]{inputenc}
                \usepackage[margin=0.6in, bindingoffset=0.5in, foot=0.25in]{geometry}
                \usepackage{multicol}
                \usepackage[normalem]{ulem}

                \setlength{\parindent}{0pt} % no paragraph indent
                \setlength{\parskip}{1ex plus 0.5ex} % vertical space before each paragraph

                % for formatting poetry
                \usepackage{verse}
                \renewcommand{\flagverse}[1]{
                    \hskip-25pt\rlap{#1}\hskip25pt
                    \ignorespaces
                }

                % format chapter and section headings
                \usepackage{titlesec}
                \titleformat*{\section}{\Large\bfseries\raggedright}
                \titleformat*{\subsection}{\large\bfseries\raggedright}
                \titlespacing*{\section}{0pt}{3ex plus 0.5ex}{1ex plus 0.25ex}
                \titlespacing*{\subsection}{0pt}{2ex plus 0.5ex}{1ex plus 0.25ex}

                % enable highlighting words
                \usepackage{tikz}
                \newcommand\mybox[2][]{
                    \tikz[overlay]\node[fill=blue!20,inner sep=2pt, anchor=text, rectangle, rounded corners=1mm,#1] {#2}; \phantom{#2}
                }

                % custom command for verse numbers
                % \mbox is needed to prevent line breaks immediately after a versenum
                \newcommand{\versenum}[1]{\mbox{\mybox[fill=black!70]{\color{white}\textbf{#1}}}}

                % custom command for names
                \usepackage{color}
                \usepackage{soul}
            """.trimIndent()
        )
        emitColorDefinitions(out, features.customHighlights)
        out.appendLine(
            """

                \newcommand\myhl[2][divineColor]{%
                    \tikz[overlay]\node[
                        fill=#1,inner sep=1.5pt,anchor=text,rectangle,rounded corners=1pt
                    ]{#2};\phantom{#2}%
                }

                % custom command for highlighting numbers
                \newcommand\mynumber[2][]{\myhl[numsColor]{#2}}

                % custom command for highlighting names
                \newcommand\myname[2][]{\myhl[namesColor]{#2}}

                % custom command for chapter titles
                % Use \mychapter{1} for "CHAPTER 1" OR
                % use \mychapter[EXODUS]{1} for "EXODUS 1"
                \newcommand{\mychapter}[2][CHAPTER]{\section*{#1 #2}}

                % restart footnote numbering on each page
                %\usepackage{perpage}
                %\MakePerPage{footnote}

                % use letters instead of numbers for footnotes
                %\renewcommand{\thefootnote}{\alph{footnote}}

                \title{$bookName}
                \author{}
                \date{}

                \begin{document}

                \newpage
                \begin{multicols}{2}

            """.trimIndent()
        )
    }

    /**
     * Emits `\definecolor` for every palette entry, then fills in built-in fallbacks
     * (`divineColor`, `namesColor`, `numsColor`) for any name not supplied by the palette.
     */
    private fun emitColorDefinitions(out: Appendable, palette: HighlightPalette) {
        val emitted = mutableSetOf<String>()
        for ((color, _) in palette.entries) {
            if (emitted.add(color.name)) emitDefineColor(out, color)
        }
        if (emitted.add("divineColor")) emitDefineColor(out, BUILTIN_DIVINE)
        if (emitted.add("namesColor")) emitDefineColor(out, BUILTIN_NAMES)
        if (emitted.add("numsColor")) emitDefineColor(out, BUILTIN_NUMBERS)
    }

    private fun emitDefineColor(out: Appendable, color: HighlightColor) {
        val (r, g, b) = color.rgb
        out.appendLine("                \\definecolor{${color.name}}{RGB}{$r, $g, $b}")
    }

    companion object {
        // Built-in fallback colors — pinned to historical preamble values
        // (rgb 1.0,1.0,0.4 → 255,255,102; rgb 0.8,0.9,1.0 → 204,230,255; RGB 247,191,136 unchanged).
        private val BUILTIN_DIVINE = HighlightColor("divineColor", Triple(255, 255, 102))
        private val BUILTIN_NAMES = HighlightColor("namesColor", Triple(204, 153, 255)) // light purple
        private val BUILTIN_NUMBERS = HighlightColor("numsColor", Triple(247, 191, 136))
    }
}
