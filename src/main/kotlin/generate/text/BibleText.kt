package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.generate.findNames
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.BOOK
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.NAME
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val book = args.firstOrNull()?.uppercase()?.let { Book.valueOf(it) } ?: Book.DEFAULT
    writeBibleText(book)
}

private fun writeBibleText(book: Book) {
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))
    for (fontSize in setOf(10, 11, 12)) {
        val latexFile = File("$PRODUCTS_DIR/$bookName/text/$bookName-bible-text-unique-${fontSize}pt.tex")
        BibleTextRenderer(fontSize).renderToFile(latexFile, bookData)
        println("Wrote $latexFile")
        val pdfFile = latexFile.toPdf()
        println("Wrote $pdfFile")
    }
}

class BibleTextRenderer(val fontSize: Int = 10) {

    fun renderToFile(file: File, bookData: BookData) {
        file.parentFile.mkdirs()
        file.writer().use {
            renderText(it, bookData)
        }
    }

    fun renderText(out: Appendable, bookData: BookData) {
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = bookData.toAnnotatedDoc(
            CHAPTER, HEADING, VERSE, POETRY, PARAGRAPH, FOOTNOTE
        ).apply {
            setAnnotations(UNIQUE_WORD, DisjointRangeSet(oneTimeWords(bookData)))
            setAnnotations(NAME, DisjointRangeSet(findNames(bookData, "god").map { it.excerptRange }.toList()))
        }
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            // endings

            if (transition.isEnded(UNIQUE_WORD)) out.append('}')
//            if (transition.isEnded(NAME)) out.append('}')
            if (transition.isEnded(PARAGRAPH) && !transition.isPresent(POETRY)) out.appendLine()
            if (transition.isEnded(BOOK)) postamble(out)
            if (transition.isEnded(POETRY)) out.appendLine("\\end{verse}\n")

            // beginnings

            if (transition.isBeginning(BOOK)) preamble(out, bookData.book.fullName)

            transition.beginning(CHAPTER)?.apply { out.appendChapterTitle(value as Int) }

            transition.beginning(HEADING)?.apply { out.appendHeadingTitle(value as String) }

            if (transition.isBeginning(POETRY)) out.appendLine("""\begin{verse}""")

            transition.beginning(VERSE)?.apply {
                out.append(
                    formatVerseNum(
                        VerseRef.fromRefNum(value as Int).verse,
                        transition.isPresent(POETRY)
                    )
                )
            }

            transition.beginning(FOOTNOTE)?.apply {
                val verseRef = (
                        // subtract/add one from footnote offset to find verse in case
                        // the footnote occurs at the end/beginning of the verse
                        bookData.verses.valueContaining(excerpt.excerptRange.first)
                            ?: bookData.verses.valueContaining(excerpt.excerptRange.first - 1)
                            ?: bookData.verses.valueContaining(excerpt.excerptRange.first + 1)
                        )?.toVerseRef()
                out.append(renderFootNote(verseRef!!, value as String))
            }

            if (transition.isBeginning(UNIQUE_WORD)) out.append("""\uline{""")
//            if (transition.isBeginning(NAME)) out.append("""\myname{""")

            // text

            var textToOutput = excerpt.excerptText.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
            if (transition.isPresent(POETRY)) {
                textToOutput = textToOutput
                    .replace("""\n""".toRegex(), "\\\\\\\\\n")
                    .replace("""(?<= {$INDENT_POETRY_LINES}) {$INDENT_POETRY_LINES}""".toRegex(), """\\vin """)
            }
            out.append(textToOutput)
        }
    }

    private fun postamble(out: Appendable) {
        out.appendLine(
            """
                
                \end{multicols}
                \end{document}
            """.trimIndent()
        )
    }

    private fun preamble(out: Appendable, bookName: String) {
        out.appendLine(
            """
                \documentclass[${fontSize}pt, letter paper, twoside]{article}
                \usepackage[utf8]{inputenc}
                \usepackage[margin=0.6in, bindingoffset=0.5in]{geometry}
                \usepackage{multicol}
                \usepackage[normalem]{ulem}

                \setlength{\parindent}{0pt} % no paragraph indent
                \setlength{\parskip}{1ex plus 1ex} % vertical space before each paragraph
                
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
                \titlespacing*{\section}{0pt}{3ex plus 1ex}{1ex plus 1ex}
                \titlespacing*{\subsection}{0pt}{2ex plus 1ex}{1ex plus 1ex}
                
                % enable highlighting words
                \usepackage{tikz}
                \newcommand\mybox[2][]{
                    \tikz[overlay]\node[fill=blue!20,inner sep=2pt, anchor=text, rectangle, rounded corners=1mm,#1] {#2}; \phantom{#2}
                }
                
                % custom command for verse numbers
                % \mbox is needed to prevent line breaks immediately after a versenum
                \newcommand{\versenum}[1]{\mbox{\mybox[fill=black!70]{\color{white}\textbf{#1}}}}
                
                % custom command for names
                % \mbox is needed to prevent line breaks immediately after a versenum
                \newcommand{\myname}[1]{\mybox[fill=blue!50]{#1}}
                
                % custom command for chapter titles
                \newcommand{\mychapter}[1]{\section*{CHAPTER #1}}
                
                % restart footnote numbering on each page
                \usepackage{perpage}
                \MakePerPage{footnote}
                
                % use letters instead of numbers for footnotes
                \renewcommand{\thefootnote}{\alph{footnote}}
                
                \title{$bookName}
                \author{}
                \date{}
                
                \begin{document}
                
                \newpage
                \begin{multicols}{2}
                
            """.trimIndent()
        )
    }

    private fun formatVerseNum(verseNum: Int, inPoetry: Boolean): String =
        """\versenum{$verseNum}""".let { if (inPoetry) "\\flagverse{$it}" else it }

    private fun renderFootNote(verseRef: VerseRef, note: String): String {
        val formattedNote = note.replace(Regex("""\*([^*]+)\*"""), """\\textit{$1}""")
        return """\footnote{${verseRef.toFullString()} $formattedNote}"""
    }

    private fun Appendable.appendHeadingTitle(headingTitle: String) {
        appendLine()
        appendLine("""\subsection*{$headingTitle}""")
        appendLine()
    }

    private fun Appendable.appendChapterTitle(chapterNum: Int) {
        appendLine("\n\n\\mychapter{$chapterNum}")
    }

}
