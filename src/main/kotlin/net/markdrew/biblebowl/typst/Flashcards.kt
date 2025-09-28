package net.markdrew.biblebowl.typst

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.HeadingCard
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.format
import net.markdrew.biblebowl.showPdf
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT)
//    val studyData = StudyData.readData(StandardStudySet.LUKE.set)
    writeTypstFlashCards(studyData).showPdf()
}

fun writeTypstFlashCards(studyData: StudyData, productsDir: Path = defaultProductsPath): Path {
    val studyName = studyData.studySet.simpleName
    val dir = productsDir.resolve(studyName, "flashcards").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-headings-cards.typ")
    Files.newBufferedWriter(file).use { writer ->
        writeFlashcards(writer, studyData)
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

private fun formatCardBack(card: HeadingCard): String =
    if (card.chapterRanges.size == 1 || card.chapterRanges.first().start.book != card.chapterRanges.last().start.book) {
        card.chapterRanges.joinToString(""" & """) {
            it.format(FULL_BOOK_FORMAT)
        }
    } else {
        card.chapterRanges.first().start.book.fullName + " " + card.chapterRanges.joinToString(" & ") {
            it.format(NO_BOOK_FORMAT)
        }
    }

private fun formatCardVerseRanges(card: HeadingCard): String = card.verseRanges.joinToString("""\n""") {
    "(${indexOfHeadingInChapter(it, card.allHeadings)}) ${it.format(NO_BOOK_FORMAT)} "
}

private fun formatCardFooter(card: HeadingCard): String =
    card.indices.joinToString(" & ") + " of " + card.allHeadings.size

fun writeFlashcards(writer: Writer, studyData: StudyData) {
    // Write front matter
    writer.appendLine("""
        #let card_width = 3.5in
        #let card_height = 2in
        #let columns = 2
        #let rows = 5
        #let cards_per_page = columns * rows
        
        #let flashcards = (
    """.trimIndent())

    // Write each card
    HeadingCard.fromStudyData(studyData).forEach { card: HeadingCard ->
        writer.appendLine(
            """(question: "${card.heading}", 
                |answer: "${formatCardBack(card)}", 
                |verse_ranges: "${formatCardVerseRanges(card)}", 
                |footer: "${formatCardFooter(card)}"),""".trimMargin()
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
          page(width: 8.5in, height: 11in, margin: (x: 0.75in, y: 0.5in))[
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
                      let content = text(size: 24pt, weight: "bold", flashcards.at(i).question)
                      flashcard(x, y, content)
                    }
                  }
                }
              ]
            ]
          ]
        
          // Generate card backs
          page(width: 8.5in, height: 11in, margin: (x: 0.75in, y: 0.5in))[
            #align(center + horizon)[
              #block(
                width: columns * card_width,
                height: rows * card_height,
              )[
                #for row in range(0, rows) {
                  for col in range(0, columns) {
                    let i = page_num * cards_per_page + row * columns + col
                    if i < flashcards.len() {
                      let x = (columns - col - 1) * card_width
                      let y = row * card_height
                      let content = stack(
                        dir: ttb, // Top-to-bottom
                        spacing: 0.25in, // Exactly 0.1in
                        text(size: 24pt, weight: "bold", flashcards.at(i).answer),
                        text(size: 18pt, flashcards.at(i).verse_ranges)
                      )
                      flashcard(x, y, content, footer: flashcards.at(i).footer)
                    }
                  }
                }
              ]
            ]
          ]
        }
    """.trimIndent())
}
