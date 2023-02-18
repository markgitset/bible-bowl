package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.model.BRIEF_BOOK_FORMAT
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.Heading
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.format
import org.apache.commons.csv.CSVFormat
import java.io.File
import java.io.Writer

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
    writeHeadingsText(studyData)
    writeHeadingsCsv(studyData)
    writeHeadingsPdf(studyData)
}

fun writeHeadingsText(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val dir = File("$PRODUCTS_DIR/$studyName/lists").also { it.mkdirs() }
    val file = dir.resolve("$studyName-headings.txt")
    file.writer().use { writer ->
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

fun writeHeadingsCsv(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val dir = File("$PRODUCTS_DIR/$studyName/csv").also { it.mkdirs() }
    val file = dir.resolve("$studyName-headings.csv")
    file.writer().use { it.writeCsv(studyData.headings) }
    println("Wrote $file")
}

fun writeHeadingsPdf(studyData: StudyData) {
    val studyName = studyData.studySet.simpleName
    val dir = File("$PRODUCTS_DIR/$studyName/indices").also { it.mkdirs() }
    val file = dir.resolve("$studyName-headings.tex")
    val name = studyData.studySet.name
    file.writer().use { writer ->
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
    file.toPdf(keepTexFiles = true)
}
