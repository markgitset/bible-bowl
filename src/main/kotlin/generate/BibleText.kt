package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.*
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

    bookData.chapters.gcdAlignment(bookData.headings).forEach { (range, chapterHeading) ->
        val (chapter, heading) = chapterHeading
        if (chapter != null && bookData.chapters.keyEnclosing(range)!!.first == range.first) {
            appendChapterTitle(chapter)
        }
        if (heading != null && bookData.headings.keyEnclosing(range)!!.first == range.first) {
            appendHeadingTitle(heading)
        }
        bookData.paragraphs.enclosedBy(range).forEach { paragraph ->
            appendParagraph(bookData, paragraph)
        }
//        appendLine(bookData.excerpt(range).excerptText)
    }
//    bookData.chapters.forEach { (chapterRange, chapterNum) ->
//        addChapter(bookData, chapterNum, chapterRange)
//    }

    closeDoc()
}

private fun Appendable.appendParagraph(bookData: BookData, paragraph: IntRange) {
    bookData.verses.intersectedBy(paragraph).forEach { (verseRange, refNum) ->
        val verseRef = VerseRef.fromRefNum(refNum)
        if (verseRange.first in paragraph) {
            append("""\textbf{${verseRef.verse}}~""")
        }
        val textRange: IntRange = verseRange.intersect(paragraph)
        appendText(bookData, verseRef, textRange)
        append(' ')
    }
    appendLine()
    appendLine()
}

private fun Appendable.appendText(bookData: BookData, verseRef: VerseRef, textRange: IntRange) {
    val text: StringBuilder = StringBuilder(bookData.text.substring(textRange))
    bookData.footnotes
        .subMap(textRange.first, textRange.endExclusive)
        .entries.sortedByDescending { (k, _) -> k }
        .forEach { (k, v) -> text.insert(k - textRange.first, renderFootNote(verseRef, v)) }
    append(text)
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
            %\usepackage{verse}
            
            \renewcommand*{\thefootnote}{\alph{footnote}}
            
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
    appendLine()
    appendLine()
    appendLine("""\section*{Chapter $chapterNum}""")
}
