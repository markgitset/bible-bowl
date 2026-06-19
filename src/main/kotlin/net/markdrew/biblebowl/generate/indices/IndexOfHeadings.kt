package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.model.BRIEF_BOOK_FORMAT
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import org.apache.commons.csv.CSVFormat
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
    writeHeadingsText(studyData)
    writeHeadingsCsv(studyData)
    writeHeadingsPdf(studyData)
}

/** Writes the headings list as a fixed-width text file (verse range left, title right). */
fun writeHeadingsText(studyData: StudyData, productsDir: Path = defaultProductsPath) {
    val studyName = studyData.studySet.simpleName
    val dir = productsDir.resolve(studyName, "lists").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-headings.txt")
    Files.newBufferedWriter(file).use { writer ->
        for (heading in studyData.headings) {
            writer.appendLine("%-18s %s".format(heading.verseRange.format(BRIEF_BOOK_FORMAT), heading.title))
        }
    }
    println("Wrote $file")
}

private fun Writer.writeCsv(headings: List<Heading>) {
    CSVFormat.DEFAULT.print(this).apply {
        printRecord("Book", "StartChapter", "EndChapter", "Heading", "VerseRange")
        headings.forEach { (title, verseRange) ->
            printRecord(
                verseRange.start.book.fullName,
                verseRange.start.chapter,
                verseRange.endInclusive.chapter,
                title,
                verseRange.format(NO_BOOK_FORMAT)
            )
        }
    }
}

/** Writes the headings list as a CSV file with book/chapter/heading/verseRange columns. */
fun writeHeadingsCsv(studyData: StudyData, productsDir: Path = defaultProductsPath) {
    val studyName = studyData.studySet.simpleName
    val dir = productsDir.resolve(studyName, "csv").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-headings.csv")
    Files.newBufferedWriter(file).use { it.writeCsv(studyData.headings) }
    println("Wrote $file")
}

/** Writes the headings index as a two-column LaTeX PDF, grouped by chapter. */
fun writeHeadingsPdf(studyData: StudyData, productsDir: Path = defaultProductsPath) {
    val studyName = studyData.studySet.simpleName
    val dir = productsDir.resolve(studyName, "indices").also { Files.createDirectories(it) }
    val file = dir.resolve("$studyName-headings.tex")
    val name = studyData.studySet.name
    Files.newBufferedWriter(file).use { writer ->
        writeDoc(writer, "$name Chapter Headings") {
            writer.appendLine("""\begin{multicols}{2}""")
            writer.appendLine("""\begin{center}""")
            val bookFormat = if (studyData.isMultiBook) BRIEF_BOOK_FORMAT else NO_BOOK_FORMAT
            val chapWidth = if (studyData.isMultiBook) "1.25cm" else ".25cm"
            val headingWidth = if (studyData.isMultiBook) "4.6cm" else "5.6cm"
            var lastChapterRef: ChapterRef? = null
            for (heading in studyData.headings) {
                val headingStart = heading.chapterRange.start
                if (headingStart != lastChapterRef) {
                    if (lastChapterRef != null) {
                        writer.appendLine("""\hline\end{tabular}""")
                    }
                    writer.appendLine("""\begin{tabular}{ | c L{$headingWidth} L{1.75cm} | }""")
                    val headingCount = studyData.headings.count { it.verseRange.start.chapterRef == headingStart }
                    writer.appendLine(
                        """\hline\multirow{$headingCount}{$chapWidth}{${headingStart.format(bookFormat)}}"""
                    )
                    lastChapterRef = headingStart
                }
                val verseRangeString = heading.verseRange.format(NO_BOOK_FORMAT)
                writer.appendLine("""& ${heading.title} & $verseRangeString \\""")
            }
            writer.appendLine("""\hline\end{tabular}""")
            writer.appendLine("""\end{center}""")
            writer.appendLine("""\end{multicols}""")
        }
    }
    file.latexToPdf(keepTexFiles = true)
}
