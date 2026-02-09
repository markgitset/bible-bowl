package net.markdrew.biblebowl.flashcards.mochi

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.flashcards.HeadingCard
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRange
import net.markdrew.biblebowl.model.format
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT)
//    val studyData = StudyData.readData(StandardStudySet.LUKE.set)
    writeMochiHeadings(studyData)
}

fun writeMochiHeadings(studyData: StudyData, productsDir: Path = defaultProductsPath): Path {
    val studyName = studyData.studySet.simpleName
    val dir = productsDir.resolve(studyName, "mochi").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-mochi-headings.csv")
    Files.newBufferedWriter(file).use { writer ->
        writeFlashcards(writer, studyData)
    }
    println("Wrote data to: $file")
    return file
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

private fun formatCardVerseRanges(card: HeadingCard): String = card.verseRanges.joinToString("""  """) {
    "(${indexOfHeadingInChapter(it, card.allHeadings)}) ${it.format(NO_BOOK_FORMAT)} "
}

private fun formatCardFooter(card: HeadingCard): String =
    card.indices.joinToString(" & ") + " of " + card.allHeadings.size

private fun writeFlashcards(writer: Writer, studyData: StudyData) {
    // Write each card
    writer.appendLine("heading\tanswer\tverse_ranges\tfooter")
    HeadingCard.fromStudyData(studyData).forEach { card: HeadingCard ->
        writer.appendLine(
            "${card.heading}\t${formatCardBack(card)}\t${formatCardVerseRanges(card)}\t${formatCardFooter(card)}"
        )
    }
}
