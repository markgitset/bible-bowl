package net.markdrew.biblebowl.generate.text

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.findNames
import net.markdrew.biblebowl.analysis.findNumbers
import net.markdrew.biblebowl.analysis.oneTimeWords
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.BOOK
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.LEADING_FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.NAME
import net.markdrew.biblebowl.model.AnalysisUnit.NUMBER
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.REGEX
import net.markdrew.biblebowl.model.AnalysisUnit.SMALL_CAPS
import net.markdrew.biblebowl.model.AnalysisUnit.STUDY_SET
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.io.File

fun main(args: Array<String>) {
    val studySet = StandardStudySet.parse(args.firstOrNull())
    val studyData = StudyData.readData(studySet)
    val customHighlights = mapOf(
        "divineColor" to divineNames.map { it.toRegex() }.toSet(),
        "namesColor" to setOf("John the Baptist".toRegex()),
    )
//    writeBibleText(book, TextOptions(fontSize = 12, names = false, numbers = false, uniqueWords = true))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = false))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = true))
    writeBibleText(
        studyData,
        TextOptions(customHighlights = customHighlights, underlineUniqueWords = true, highlightNames = true, highlightNumbers = true)
    )
}

fun writeBibleText(studyData: StudyData, opts: TextOptions<String> = TextOptions()) {
    val name = studyData.studySet.simpleName
    val latexFile = File("$PRODUCTS_DIR/$name/text/latex/$name-bible-text-${opts.fileNameSuffix}.tex")
    BibleTextRenderer(opts).renderToFile(latexFile, studyData)
    println("Wrote $latexFile")
    latexFile.latexToPdf(twice = true, keepTexFiles = true, showStdIo = false)
}
//
//fun writeBibleText(book: Book, opts: TextOptions) {
//    val bookName = book.name.lowercase()
//    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))
//    val latexFile = File("$PRODUCTS_DIR/$bookName/text/$bookName-bible-text-${opts.fileNameSuffix}.tex")
//    BibleTextRenderer(opts).renderToFile(latexFile, studyData)
//    println("Wrote $latexFile")
//    latexFile.toPdf(twice = true)
//}

private val asteriskBracketedWordRegex = Regex("""\*([^*]+)\*""")

class BibleTextRenderer(private val opts: TextOptions<String> = TextOptions()) {

    fun renderToFile(file: File, studyData: StudyData) {
        file.parentFile.mkdirs()
        file.writer().use {
            renderText(it, studyData)
        }
    }

    private fun renderText(out: Appendable, studyData: StudyData) {
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = annotatedDoc(studyData, opts)
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            if (opts.underlineUniqueWords && transition.isEnded(UNIQUE_WORD)) out.append("}}")
            if (transition.isEnded(REGEX)) out.append('}')
            if (opts.highlightNames && transition.isEnded(NAME)) out.append('}')
            if (opts.highlightNumbers && transition.isEnded(NUMBER)) out.append('}')

            // since footnotes are zero-width and follow the text to which they refer,
            // we need to handle them before any endings
            transition.beginning(FOOTNOTE)?.apply {
                val outerAnns: List<Annotation<AnalysisUnit>> =
                    transition.continuing.filter { it.key in setOf(REGEX, NAME, NUMBER) }
                // assume/hope that only one of these ever matches
                val outerAnn: Annotation<AnalysisUnit>? = if (outerAnns.isEmpty()) null else outerAnns.single()

                // before inserting a footnote, need to end highlighting
                if (outerAnn?.key == REGEX) out.append('}')
                if (opts.highlightNames && outerAnn?.key == NAME) out.append('}')
                if (opts.highlightNumbers && outerAnn?.key == NUMBER) out.append('}')

                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verseRef = studyData.verses.valueContaining(excerpt.excerptRange.first)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first - 1)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first + 1)
                out.append(renderFootNote(verseRef!!, value as String))

                // after inserting a footnote, need to resume highlighting
                if (opts.highlightNames && outerAnn?.key == NAME) out.append("""\myname{""")
                if (opts.highlightNumbers && outerAnn?.key == NUMBER) out.append("""\mynumber{""")
                if (outerAnn?.key == REGEX) {
                    val color = outerAnn.value
                    out.append("""\myhl[$color]{""")
                }
            }

            // endings

            if (transition.isEnded(PARAGRAPH) && !transition.isPresent(POETRY)) out.appendLine()
            if (transition.isEnded(STUDY_SET)) postamble(out)
            if (transition.isEnded(POETRY)) out.appendLine("\\end{verse}\n")
            if (opts.chapterBreaksPage && transition.isEnded(CHAPTER)) out.appendLine("\\clearpage")

            // beginnings

            if (transition.isBeginning(STUDY_SET)) preamble(out, studyData.studySet.name)

            transition.beginning(BOOK)?.apply { out.appendBookTitle(value as Book) }

            transition.beginning(CHAPTER)?.apply {
                val chapterRef = value as ChapterRef
                val book = if (studyData.isMultiBook) chapterRef.book else null
                out.appendChapterTitle(chapterRef, book)
            }

            transition.beginning(HEADING)?.apply { out.appendHeadingTitle(value as String) }

            if (transition.isBeginning(POETRY)) out.appendLine("""\begin{verse}""")

            transition.beginning(VERSE)?.apply {
                out.append(
                    formatVerseNum(
                        (value as VerseRef).verse,
                        transition.isPresent(POETRY)
                    )
                )
            }

            if (opts.underlineUniqueWords && transition.isBeginning(UNIQUE_WORD)) out.append("""{\uline{""")
            if (opts.highlightNames && transition.isBeginning(NAME)) out.append("""\myname{""")
            if (opts.highlightNumbers && transition.isBeginning(NUMBER)) out.append("""\mynumber{""")
            if (transition.isBeginning(REGEX)) {
                val color = transition.beginning.first { it.key == REGEX }.value
                out.append("""\myhl[$color]{""")
            }

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

    private fun preamble(out: Appendable, bookName: String) {
        out.appendLine(
            """
                \documentclass[${opts.fontSize}pt, letter paper, twoside]{article}
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
                \definecolor{divineColor}{rgb}{1.0, 1.0, 0.4} % light yellow
                \definecolor{namesColor}{rgb}{0.8, 0.9, 1.0} % light blue
                \definecolor{numsColor}{RGB}{247, 191, 136} % light orange

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

    private fun formatVerseNum(verseNum: Int, inPoetry: Boolean): String =
        """\versenum{$verseNum}""".let { if (inPoetry) "\\flagverse{$it}" else it }

    private fun renderFootNote(verseRef: VerseRef, note: String): String {
        val formattedNote = note.replace(asteriskBracketedWordRegex, """\\textit{$1}""")
        return """\footnote{${verseRef.format(FULL_BOOK_FORMAT)} $formattedNote}"""
    }

    private fun Appendable.appendHeadingTitle(headingTitle: String) {
        appendLine()
        appendLine("""\subsection*{$headingTitle}""")
        appendLine()
    }

    private fun Appendable.appendChapterTitle(chapterRef: ChapterRef, book: Book? = null) {
        append("\n\n\\mychapter")
        if (book != null) append("[${book.fullName.uppercase()}]")
        appendLine("{${chapterRef.chapter}}")
    }

    private fun Appendable.appendBookTitle(@Suppress("UNUSED_PARAMETER") book: Book) {
        //appendLine("\n\n\\mychapter{${book.fullName}}")
    }

    companion object {
        fun <T : Any> annotatedDoc(studyData: StudyData, opts: TextOptions<T>): AnnotatedDoc<AnalysisUnit> {
            val annotatedDoc: AnnotatedDoc<AnalysisUnit> = studyData.toAnnotatedDoc(
                BOOK, CHAPTER, HEADING, VERSE, POETRY, PARAGRAPH, LEADING_FOOTNOTE, FOOTNOTE, REGEX, SMALL_CAPS
            ).apply {
                val regexAnnotationsRangeMap: DisjointRangeMap<T> =
                    opts.customHighlights.entries.fold(DisjointRangeMap()) { drm, (color, patterns) ->
                        drm.apply {
                            putAll(studyData.findAll(*patterns.toTypedArray()).associateWith { color })
                        }
                    }
                setAnnotations(REGEX, regexAnnotationsRangeMap)
                setAnnotations(SMALL_CAPS, DisjointRangeSet(
                    studyData.findAll(*opts.smallCaps.keys.map { Regex.fromLiteral(it) }.toTypedArray())
                ))
                if (opts.underlineUniqueWords) setAnnotations(UNIQUE_WORD, DisjointRangeSet(oneTimeWords(studyData)))
                if (opts.highlightNames) {
                    val namesRangeSet = DisjointRangeSet(
                        findNames(studyData, exceptNames = divineNames.toTypedArray())
                            .map { it.excerptRange }.toList()
                    )
                    // remove any ranges that intersect with custom regex ranges
                    val deconflicted = namesRangeSet.minusEnclosedBy(regexAnnotationsRangeMap)
                    setAnnotations(NAME, deconflicted)
                }
                if (opts.highlightNumbers) {
                    val numbersRangeSet = DisjointRangeSet(findNumbers(studyData.text).map { it.excerptRange }.toList())
                    setAnnotations(NUMBER, numbersRangeSet)
                }
            }
            return annotatedDoc
        }
    }

}
