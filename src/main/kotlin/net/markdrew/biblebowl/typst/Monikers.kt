package net.markdrew.biblebowl.typst

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.HeadingFlashcard
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.MonikersCard
import net.markdrew.biblebowl.model.MonikersParser
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.format
import net.markdrew.biblebowl.showPdf
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studySet: StudySet = StandardStudySet.DEFAULT
    val monikersCards: List<MonikersCard> = MonikersParser.loadCards(studySet)
    writeTypstMonikers(studySet, monikersCards).showPdf()
}

fun writeTypstMonikers(
    studySet: StudySet,
    monikersData: List<MonikersCard>,
    productsDir: Path = defaultProductsPath
): Path {
    val studyName = studySet.simpleName
    val dir = productsDir.resolve(studyName, "monikers").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-monikers-avery5870-cards.typ")
    Files.newBufferedWriter(file).use { writer ->
        writeMonikers(writer, monikersData)
    }
    return file.typstToPdf(keepTypFiles = true)
}

private fun indexOfHeadingInChapter(verseRange: VerseRange, allHeadings: List<Heading>): String {
    val index = allHeadings.indexOfFirst { it.verseRange == verseRange }
    require(index >= 0)

    val inChapterOffset = allHeadings
        .subList(0, index)
        .count { it.chapterRange.start == verseRange.start.chapterRef }

    return ('A' + inChapterOffset).toString()
}

private fun formatCardBack(card: HeadingFlashcard): String =
    if (card.chapterRanges.size == 1 || card.chapterRanges.first().start.book != card.chapterRanges.last().start.book) {
        card.chapterRanges.joinToString(""" & """) {
            it.format(FULL_BOOK_FORMAT)
        }
    } else {
        card.chapterRanges.first().start.book.fullName + " " + card.chapterRanges.joinToString(" & ") {
            it.format(NO_BOOK_FORMAT)
        }
    }

private fun formatCardVerseRanges(card: HeadingFlashcard): String = card.verseRanges.joinToString("""\n""") {
    "(${indexOfHeadingInChapter(it, card.allHeadings)}) ${it.format(NO_BOOK_FORMAT)} "
}

private fun formatCardFooter(card: HeadingFlashcard): String =
    card.indices.joinToString(" & ") + " of " + card.allHeadings.size

fun writeMonikers(writer: Writer, monikersCards: List<MonikersCard>) {
    // Write front matter
    writer.appendLine("""
        #let card_width = 2in
        #let card_height = 3.5in
        #let columns = 5
        #let rows = 2
        #let cards_per_page = columns * rows
        
        #let flashcards = (
    """.trimIndent())

    // Write each card
    monikersCards.forEach { card: MonikersCard ->
        writer.appendLine(
            """(clue: "${card.clue}",
                |description: "${card.description}",
                |ref: "${card.verseRef}",
                |points: "${card.points}"),""".trimMargin()
        )
    }

    // Write the end matter
    writer.appendLine("""
        )
        
        #let flashcard(x, y, content, footer: none) = {
          place(
            top + left,
            dx: x,
            dy: y,
            rect(
              width: card_width,
              height: card_height,
              //stroke: black,
              stroke: none,
              fill: none,
              box(
                width: card_width - 0.2in,
                height: card_height - 0.2in,
                inset: 0.1in,
                [
                  #align(center + horizon, content)
                  #if footer != none {
                    place(
                      bottom + right,
                      text(size: 14pt, footer)
                    )
                  }
                ]
              )
            )
          )
        }
        
        // Generate pages for card fronts
        #let total_cards = flashcards.len()
        #let total_pages = calc.ceil(total_cards / cards_per_page)
        
        #for page_num in range(0, total_pages) {
          // Generate card fronts
          page(height: 8.5in, width: 11in, margin: (y: 0.75in, x: 0.5in))[
            #align(center + horizon)[
              #block(
                width: columns * card_width,
                height: rows * card_height,
              )[
                #for row in range(0, rows) {
                  for col in range(0, columns) {
                    let i = page_num * cards_per_page + row * columns + col
                    if i < flashcards.len() {
                      let x = col * card_width
                      let y = row * card_height
                      let content = stack(
                        dir: ttb, // Top-to-bottom
                        spacing: 0.25in, // Exactly 0.1in
                        text(size: 14pt, weight: "bold", flashcards.at(i).clue),
                        text(size: 12pt, flashcards.at(i).description),
                        text(size: 10pt, "(" + flashcards.at(i).ref + ")"),
                        text(size: 12pt, weight: "bold", flashcards.at(i).points + " POINTS"),
                      )
                      flashcard(x, y, content)
                    }
                  }
                }
              ]
            ]
          ]
        
        }
    """.trimIndent())
}
