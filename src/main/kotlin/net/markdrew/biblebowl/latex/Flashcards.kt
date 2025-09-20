package net.markdrew.biblebowl.latex

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.HeadingCard
import net.markdrew.biblebowl.latex.Flashcards.CARDS_PER_ROW
import net.markdrew.biblebowl.latex.Flashcards.CARD_PER_PAGE
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.format
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT)
    writeLatexFlashCards(studyData)
}

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

fun writeFlashcards(writer: Writer, studyData: StudyData) {
    writer.appendLine("""
        \documentclass[12pt]{article}
        \usepackage[letterpaper,margin=0.5in]{geometry}
        \usepackage{multicol}
        \usepackage{tikz}

        % Define card dimensions (Avery 5870 = 2 x 3.5 in)
        \newlength{\cardwidth}
        \newlength{\cardheight}
        \setlength{\cardwidth}{3.5in}
        \setlength{\cardheight}{2in}

        % Command to create one card
        \newcommand{\card}[1]{%
          \begin{minipage}[c][\cardheight][c]{\cardwidth}
          \centering
          #1
          \end{minipage}
        }

        \pagestyle{empty}

        \begin{document}
        \Large
        \bfseries

    """.trimIndent())

    HeadingCard.fromStudyData(studyData)
        .chunked(CARD_PER_PAGE) {
            // to line up the last card, we need full rows on each page
            val padCount = CARDS_PER_ROW - (it.size % CARDS_PER_ROW)
            if (padCount < CARDS_PER_ROW) it + List(padCount) { HeadingCard.EMPTY } else it
        }
        .forEachIndexed { page, pageOfCards -> writePageOfCards(writer, page, pageOfCards) }

    // Write the end matter
    writer.appendLine("""\end{document}""")
}

private fun writePageOfCards(writer: Writer, page: Int, pageOfCards: List<HeadingCard>) {

    // Front of cards
    if (page > 0) writer.appendLine("\n\\newpage\n")
    writer.appendLine("""
                % --------- Page ${page + 1}: Questions (front side) ---------
                \noindent
            """.trimIndent())
    writer.appendLine(
        pageOfCards.chunked(CARDS_PER_ROW).joinToString("\\\\\n") {
                rowOfCards -> rowOfCards.joinToString(" ", transform = ::formatCardFront)
        }
    )

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
    """\card{\LARGE ${card.heading.takeIf { card.heading.isNotEmpty() } ?: "\\nobreakspace" }}"""
private fun formatCardBack(card: HeadingCard): String {
    val content =
        if (card == HeadingCard.EMPTY) "\\nobreakspace"
        else card.chapterRanges.joinToString(" AND ") { it.format(FULL_BOOK_FORMAT) }
    return """\card{\LARGE $content}"""
}

object Flashcards {
    const val CARD_PER_PAGE = 10
    const val CARDS_PER_ROW = 2
}