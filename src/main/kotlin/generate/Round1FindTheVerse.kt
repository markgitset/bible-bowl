package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate

private const val ROUND_1_PACE = 40.0 / 25.0 // questions/minute

fun main() {
    writeFindTheVerse(Book.REV, numOfVersesToFind = 20)
}

private fun writeFindTheVerse(
    book: Book = Book.REV,
    throughChapter: Int? = null,
    exampleNum: Int? = null,
    date: LocalDate = LocalDate.now(),
    minCharLength: Int = 15,
    numOfVersesToFind: Int = 40,
) {
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    val lastIncludedChapter: Int? = throughChapter?.let {
        val maxChapter = bookData.chapters.lastEntry().value
        require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${book.fullName}!" }
        if (it == maxChapter) null else it
    }

    var cluePool = bookData.oneVerseSentParts
    if (lastIncludedChapter != null) {
        val lastIncludedOffset: Int = bookData.chapterIndex[lastIncludedChapter]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }

    val versesToFind: List<ReferencedVerse> = cluePool
        .filterKeys { it.length() >= minCharLength }
        .entries.shuffled().take(numOfVersesToFind)
        .map { (range, verseNum) -> ReferencedVerse(verseNum.toVerseRef(), bookData.text.substring(range)) }

    var fileName = date.toString()
    if (exampleNum != null) fileName += "-$exampleNum"
    fileName += "-${book.fullName}-find-the-verse"
    if (throughChapter != null) fileName += "-to-ch-$throughChapter"

    File("output/$bookName/$fileName.tex").writer().use { writer ->
        versesToFind.toLatexInWhatChapter(writer, book.fullName, lastIncludedChapter)
    }

    println("Wrote ${File("output/$bookName/$fileName.tex")}")
}

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

fun List<ReferencedVerse>.toLatexInWhatChapter(appendable: Appendable,
                                               book: String,
                                               throughChapter: Int?) {
    val bookDesc = book + throughChapter?.let { " (ONLY chapters 1-$it)" }.orEmpty()
    appendable.appendLine("""
        \documentclass[10pt, letter paper]{article} 
        \usepackage[utf8]{inputenc}
        \usepackage[letterpaper, left=0.75in, right=0.75in, top=1in, bottom=1in]{geometry}
        \usepackage{multicol}
        \usepackage[T1]{fontenc}
        \usepackage{longtable}
        \usepackage{array}
        \renewcommand{\arraystretch}{1.5}
        \newcounter{rowcount}
        \setcounter{rowcount}{0}
        
        \begin{document}
        
        \noindent Number \rule{1in}{0.01in}\hfill Name \rule{3in}{0.01in}\hfill Score \rule{1in}{0.01in}
        
        \section*{Find The Verse \textnormal{(Open Bible, ${(this.size / ROUND_1_PACE).toInt()} minutes)}\hfill Round 1}
        Using your Bible, write the chapter and verse from $bookDesc of each quotation in its matching box.
        
        \begin{center}
        \begin{longtable}{|@{\stepcounter{rowcount} \therowcount.\hspace*{\tabcolsep}}p{5.5in}||m{0.3in}|m{0.3in}|}
            \multicolumn{1}{c}{}&\multicolumn{2}{c}{ANSWER}\\
            \multicolumn{1}{c}{}&\multicolumn{1}{c}{Chapter}&\multicolumn{1}{c}{Verse}\\
            \hline
    """.trimIndent())
    this.forEach {
        appendable.appendLine("""    ${removeUnmatchedCharPairs(it.verse.normalizeWS())} & & \\""")
        appendable.appendLine("""    \hline""")
    }
    appendable.appendLine("""
        \end{longtable}
        \end{center}
        \clearpage
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
