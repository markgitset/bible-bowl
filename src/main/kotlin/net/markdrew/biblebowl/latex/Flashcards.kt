package net.markdrew.biblebowl.latex

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.HeadingCard
import net.markdrew.biblebowl.latex.Flashcards.CARDS_PER_PAGE
import net.markdrew.biblebowl.latex.Flashcards.CARDS_PER_ROW
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT)
    writeLatexFlashCards(studyData)
}

@Deprecated("Use Typst flashcards instead")
fun writeLatexFlashCards(studyData: StudyData, productsDir: Path = defaultProductsPath): Path {
    val studyName = studyData.studySet.simpleName
    val dir = productsDir.resolve(studyName, "flashcards").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-headings-cards.tex")
    val name = studyData.studySet.name
    Files.newBufferedWriter(file).use { writer ->
        writeFlashcards(writer, studyData)
//        {
//            writer.appendLine("""\begin{multicols}{2}""")
//            writer.appendLine("""\begin{center}""")
//            val bookFormat = if (studyData.isMultiBook) BRIEF_BOOK_FORMAT else NO_BOOK_FORMAT
//        }
    }
    return file.latexToPdf(keepTexFiles = true)
}

@Deprecated("Use Typst flashcards instead")
fun writeFlashcards(writer: Writer, studyData: StudyData) {
    writer.appendLine("""
        \documentclass[12pt]{article}
        \usepackage[letterpaper,margin=0.5in]{geometry}
        \usepackage{multicol}
        \usepackage{tikz}
        \usepackage{enumitem}

        % Define card dimensions (Avery 5870 = 2 x 3.5 in)
        \newlength{\cardwidth}
        \newlength{\cardheight}
        \setlength{\cardwidth}{3.5in}
        \setlength{\cardheight}{2in}

        % Command to create one card
        %\newcommand{\card}[1]{%
        %  \begin{minipage}[c][\cardheight][c]{\cardwidth}
          %\raggedright
        %  \centering
        %  #1
        %  \end{minipage}
        %}

        \newcommand{\cardfront}[1]{%
          \begin{minipage}[c][\cardheight][c]{\cardwidth}
            \centering
            \vspace{0.1in} % top padding
            \begin{minipage}{0.9\cardwidth} % inset box = 90% width
              \centering
              #1
            \end{minipage}
            \vspace{0.1in} % bottom padding
          \end{minipage}
        }

        \newcommand{\cardback}[1]{%
          \cardfront{%
              \vbox to \cardheight{%
                \centering
                #1
              }%
          }%
        }

        % Command to create a row of cards
        \newcommand{\cardrow}[2]{%
          \makebox[\textwidth][c]{#1\hspace{0.25in}#2}%
        }

        \pagestyle{empty}

        \begin{document}

    """.trimIndent())

    HeadingCard.fromStudyData(studyData)
        .chunked(CARDS_PER_PAGE) {
            // to line up the last card, we need full rows on each page
            val padCount = (CARDS_PER_ROW - (it.size % CARDS_PER_ROW)) % CARDS_PER_ROW
            // WARNING: internal buffer is reused, so it.toList() is necessary to create a new list
            if (padCount > 0) it + List(padCount) { HeadingCard.EMPTY } else it.toList()
        }
        .take(1)
        .forEachIndexed { page, pageOfCards: List<HeadingCard> -> writePageOfCards(writer, page, pageOfCards) }

    // Write the end matter
    writer.appendLine("""\end{document}""")
}

private fun writePageOfCards(writer: Writer, page: Int, pageOfCards: List<HeadingCard>) {

    // Front of cards
    if (page > 0) writer.appendLine("\n\\newpage\n")
    writer.appendLine("""
                % --------- Page ${page + 1}: Questions (front side) ---------
                \noindent
                {\Large\textbf{
            """.trimIndent())
    writer.appendLine(
        pageOfCards.chunked(CARDS_PER_ROW).joinToString("\\\\%\n") { rowOfCards ->
            rowOfCards.joinToString(prefix = """\cardrow{\cardfront{""", separator = """}}{\cardfront{""", postfix = """}}""", transform = ::formatCardFront)
        } + "%"
    )
    writer.appendLine("""}}%""")

    // Back of cards
    writer.appendLine("""
                \newpage

                % --------- Page ${page + 1}: Answers (back side) ---------
                % These will align with the questions when duplex printed (flip on long edge)
                \noindent
            """.trimIndent())
    writer.appendLine(
        pageOfCards.chunked(CARDS_PER_ROW).joinToString("\\\\\n") {
                rowOfCards -> rowOfCards.reversed().joinToString(" ", transform = ::formatCardBack)
        }
    )
}

private fun formatCardFront(card: HeadingCard): String =
    """\LARGE ${card.heading.takeIf { card.heading.isNotEmpty() } ?: "\\nobreakspace" }"""

private fun indexOfHeadingInChapter(verseRange: VerseRange, allHeadings: List<Heading>): String {
    val index = allHeadings.indexOfFirst { it.verseRange == verseRange }
    require(index >= 0)

    val inChapterOffset = allHeadings
        .subList(0, index)
        .count { it.chapterRange.start == verseRange.start.chapterRef }

    return ('A' + inChapterOffset).toString()
//    var i: Int = allHeadings.indexOfFirst { it.verseRange == verseRange }
//    require(i >= 0)
//    var inChapter = 0
//    while (i > 0 && allHeadings[i-1].chapterRange.start == verseRange.start.chapterRef) {
//        inChapter++
//        i--
//    }
//    return ('a' + inChapter).toString()
}

private fun formatCardBack(card: HeadingCard): String {
    if (card == HeadingCard.EMPTY) return """\cardback{\nobreakspace}"""
    val answer = card.verseRanges.joinToString(" \\& \\\\\n") {
        "${it.format(FULL_BOOK_FORMAT)} (${indexOfHeadingInChapter(it, card.allHeadings)})"
    }

//    val contextHeadings: String = formatContextHeadings(card)

//        \vspace{2em}
    return """\cardback{
        \vfill
        % Print "Answer" in large, bold text
        {\LARGE\textbf{$answer}}%
        % Push footer to bottom and right
        \vfill
        \hfill {\small ${card.indices.joinToString(" \\& ")} of ${card.allHeadings.size}}
    }""".trimIndent()
//        \vspace{2em}
}

private fun formatContextHeadings(card: HeadingCard): String {
    return card.verseRanges.map { verseRange ->
        """
            \vspace{1em}
            {\small \begin{flushleft}
                a) Answer A\\
                b) \textbf{${verseRange.format(NO_BOOK_FORMAT)}: ${card.heading}}\\
                c) Answer C
            \end{flushleft} }
        """.trimIndent()
    }.joinToString("\n")
}

object Flashcards {
    const val CARDS_PER_PAGE = 10
    const val CARDS_PER_ROW = 2
}