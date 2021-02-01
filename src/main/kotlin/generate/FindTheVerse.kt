package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import kotlin.math.roundToInt

fun main() {
//    val nSamples = 10
    (16..22).forEach {
        writeFindTheVerse(Book.REV, throughChapter = it)
    }
}

private fun writeFindTheVerse(
    book: Book = Book.REV,
    throughChapter: Int? = null,
    exampleNum: Int? = null,
    date: LocalDate = LocalDate.now(),
    minCharLength: Int = 15,
) {
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)
    val lastChapter: Int = throughChapter ?: bookData.chapters.lastEntry().value
    val throughChapterRange: IntRange? = bookData.chapterIndex[lastChapter]
    requireNotNull(throughChapterRange) { "$lastChapter is not a valid chapter in ${book.fullName}!" }
    val versesToFind: List<ReferencedVerse> = bookData.oneVerseSentParts.enclosedBy(0..throughChapterRange.last)
        .filterKeys { it.length() >= minCharLength }
        .entries.shuffled().take(40)
        .map { (range, verseNum) -> ReferencedVerse(verseNum.toVerseRef(), bookData.text.substring(range)) }

    var fileName = date.toString()
    if (exampleNum != null) fileName += "-$exampleNum"
    fileName += "-${book.fullName}-find-the-verse"
    if (throughChapter != null) fileName += "-through-ch-$throughChapter"

    File("output/$bookName/$fileName.tex").writer().use { writer ->
        versesToFind.toLatex(writer, book.fullName, lastChapter, date)
    }

    println("Wrote ${File("output/$bookName/$fileName.tex")}")
}

//fun List<ReferencedVerse>.versesThroughChapter(lastChapterToInclude: Int = Int.MAX_VALUE): List<ReferencedVerse> =
//    takeWhile { it.reference.chapter <= lastChapterToInclude }
//
//fun List<ReferencedVerse>.generateVersesToFind(numToFind: Int = 40): List<ReferencedVerse> =
//    shuffled().take(numToFind)

private val charPairs = listOf("()", "“”", "\"\"", "‘’", "''")

fun removeUnmatchedCharPairs(str: String): String =
    charPairs.fold(str) { s, pair -> removeUnmatchedCharPair(s, pair) }

fun removeUnmatchedCharPair(s: String, charPair: String): String {
    if (s.isBlank()) return s
    require(charPair.length == 2)
    val startCount = s.count { it == charPair[0] }
    val endCount = s.count { it == charPair[1] }
    if (startCount > endCount && s.first() == charPair[0])
        return s.drop(1)
    if (startCount < endCount && s.last() == charPair[1])
        return s.dropLast(1)
    return s
}

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
        appendable.appendLine("    \\item ${removeUnmatchedCharPairs(it.verse.normalizeWS())}")
    }
    appendable.appendLine("""
        \end{enumerate}

        \newpage
        \section*{Answers}
        \begin{multicols}{2}
        \begin{enumerate}
    """.trimIndent())
    this.forEach {
        appendable.appendLine("    \\item ${it.reference.toFullString()}")
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
