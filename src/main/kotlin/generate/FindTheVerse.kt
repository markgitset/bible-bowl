package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.BookData
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import kotlin.math.roundToInt

fun main() {
    val book = Book.REV
    val throughChapter = 12
    val date = LocalDate.now()

    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)
    val versesToFind: List<ReferencedVerse> = bookData.verseList()
        .versesThroughChapter(throughChapter)
        .generateVersesToFind()
    val toFile = File("output/$bookName/${date}-${book.fullName}-find-the-verse-1-$throughChapter.tex")
    toFile.writer().use { writer ->
        versesToFind.toLatex(writer, book.fullName, throughChapter, date)
    }

    println("Wrote $toFile")
}

fun List<ReferencedVerse>.versesThroughChapter(lastChapterToInclude: Int = Int.MAX_VALUE): List<ReferencedVerse> =
    takeWhile { it.reference.chapter <= lastChapterToInclude }

fun List<ReferencedVerse>.generateVersesToFind(numToFind: Int = 40): List<ReferencedVerse> =
    shuffled().take(numToFind)

fun List<ReferencedVerse>.toLatex(appendable: Appendable,
                                  book: String,
                                  throughChapter: Int,
                                  date: LocalDate = LocalDate.now()) {
    appendable.appendLine("""
        \documentclass[10pt, letter paper]{article} 
     
        \usepackage[utf8]{inputenc}
        \usepackage[margin=0.75in]{geometry}
        \usepackage{blindtext}
        \usepackage{multicol}
        \usepackage[T1]{fontenc}
        
        \title{$book 1-$throughChapter - Find the Verse - $date}
        
        \begin{document}
        \maketitle
        ${this.size} verses, ${(this.size * 25.0/40).roundToInt()} minutes, Open Bible
        \section*{Verses}
        \begin{enumerate}
     """.trimIndent())
    this.forEach {
        appendable.appendLine("    \\item ${it.verse}")
    }
    appendable.appendLine("""
        \end{enumerate}

        \newpage
        \section*{Answers}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent())
    this.forEach {
        appendable.appendLine("    \\item ${it.reference}")
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
