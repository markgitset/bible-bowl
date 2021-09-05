package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.endExclusive
import net.markdrew.chupacabra.core.intersect
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val book = args.firstOrNull()?.uppercase()?.let { Book.valueOf(it) } ?: Book.GEN
    writeBibleText(book)
}

private fun writeBibleText(book: Book) {

    val bookName = book.name.lowercase()
    val file = File("products/$bookName/$bookName-bible-text.tex")
    file.writer().use { writer ->
        val bookData = BookData.readData(Paths.get("data"), book)
        writer.appendBook(bookData)
    }

    println("Wrote $file")
}

fun Appendable.appendBook(bookData: BookData) {
    appendDocPreamble(bookData.book)

    val poetryAndProse = DisjointRangeMap<Boolean>().apply {
        bookData.paragraphs.forEach { put(it, false) }
        bookData.poetry.forEach { putForcefully(it, true) }
    }

    bookData.chapters.gcdAlignment(bookData.headings).forEach { (range, chapterHeading) ->
        val (chapter, heading) = chapterHeading
        if (chapter != null && bookData.chapters.keyEnclosing(range)!!.first == range.first) {
            appendChapterTitle(chapter)
        }
        if (heading != null && bookData.headings.keyEnclosing(range)!!.first == range.first) {
            appendHeadingTitle(heading)
        }
        poetryAndProse.enclosedBy(range).forEach { (paragraph, isPoetry) ->
            if (isPoetry) appendPoetry(bookData, paragraph)
            else appendParagraph(bookData, paragraph)
        }
    }

    closeDoc()
}

private fun formatVerseNum(verseNum: Int): String =
    """\versenum{$verseNum}"""

private fun Appendable.appendParagraph(bookData: BookData, paragraph: IntRange) {
    bookData.verses.intersectedBy(paragraph).forEach { (verseRange, refNum) ->
        val verseRef = VerseRef.fromRefNum(refNum)
        if (verseRange.first in paragraph) {
            append(formatVerseNum(verseRef.verse)).append('~')
        }
        val textRange: IntRange = verseRange.intersect(paragraph)
        appendText(bookData, verseRef, textRange)
        append(' ')
    }
    appendLine()
    appendLine()
}

private fun Appendable.appendPoetry(bookData: BookData, paragraph: IntRange) {
    appendLine("""\begin{verse}""")
    bookData.verses.intersectedBy(paragraph).entries.forEachIndexed { i, (verseRange, refNum) ->
        val verseRef = VerseRef.fromRefNum(refNum)
        if (verseRange.first in paragraph) {
            if (i > 0) append("\\\\\n")
            append("""\flagverse{""").append(formatVerseNum(verseRef.verse)).append("""} """)
        }
        val textRange: IntRange = verseRange.intersect(paragraph)
        appendText(bookData, verseRef, textRange, poetry = true)
        append(' ')
    }
    appendLine("""\\""")
    appendLine("""\end{verse}""")
    appendLine()
}

private fun Appendable.appendText(bookData: BookData,
                                  verseRef: VerseRef,
                                  textRange: IntRange,
                                  poetry: Boolean = false) {
    val text: StringBuilder = StringBuilder(bookData.text.substring(textRange))
    bookData.footnotes
        .subMap(textRange.first, textRange.endExclusive)
        .entries.sortedByDescending { (k, _) -> k }
        .forEach { (k, v) -> text.insert(k - textRange.first, renderFootNote(verseRef, v)) }
    var result = text.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
    if (poetry) {
        result = result
            .replace("""\n""".toRegex(), "\\\\\\\\\n")
            .replace("""(?<= {$INDENT_POETRY_LINES}) {$INDENT_POETRY_LINES}""".toRegex(), """\\vin """)
    }
    append(result)
}

private fun renderFootNote(verseRef: VerseRef, note: String): String {
    val formattedNote = note.replace(Regex("""\*([^*]+)\*"""), """\\textit{$1}""")
    return """\footnote{${verseRef.toFullString()} $formattedNote}"""
}

private fun Appendable.closeDoc() {
    appendLine(
        """
            
            \end{multicols}
            \end{document}
        """.trimIndent()
    )
}

private fun Appendable.appendDocPreamble(book: Book) {
    appendLine(
        """
            \documentclass[10pt, letter paper]{article} 
            
            \usepackage[utf8]{inputenc}
            \usepackage[margin=1in]{geometry}
            \usepackage{multicol}
            \usepackage{verse}
            
            % enable highlighting words
            \usepackage{tikz}
            \newcommand\mybox[2][]{\tikz[overlay]\node[fill=blue!20,inner sep=2pt, anchor=text, rectangle, rounded corners=1mm,#1] {#2};\phantom{#2}}

            \newcommand{\versenum}[1]{\mybox[fill=black!60]{\color{white}\textbf{#1}}}
            \newcommand{\mychapter}[1]{\section*{Chapter #1}}

            \setlength{\parindent}{0pt} % no paragraph indent
            \setlength{\parskip}{0.5em} % vertical space before each paragraph

            % restart footnote numbering on each page
            \usepackage{perpage}
            \MakePerPage{footnote}
            
            % use letters instead of numbers for footnotes
            \renewcommand{\thefootnote}{\alph{footnote}}
            
            \renewcommand{\flagverse}[1]{
                \hskip-25pt\rlap{#1}\hskip25pt
                \ignorespaces
            }
            
            \title{${book.fullName}}
            \author{}
            \date{}
            
            \begin{document}
            
            \newpage
            \begin{multicols}{2}
            
        """.trimIndent()
    )
}

private fun Appendable.addChapter(
    bookData: BookData,
    chapterNum: Int,
    chapterRange: IntRange
) {
    appendChapterTitle(chapterNum)

    val headingsInChapter: DisjointRangeMap<String> = bookData.headings.intersectedBy(chapterRange)

    headingsInChapter.forEach { (headingRange, headingTitle) ->
        if (headingRange.first in chapterRange) {
            appendHeadingTitle(headingTitle)

        }
    }
}

private fun Appendable.appendHeadingTitle(headingTitle: String) {
    appendLine()
    appendLine("""\subsection*{$headingTitle}""")
    appendLine()
}

private fun Appendable.appendChapterTitle(chapterNum: Int) {
    appendLine("\n\n\\mychapter{$chapterNum}")
}
