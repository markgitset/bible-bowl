package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.random.nextInt

private const val ROUND_1_PACE = 40.0 / 25.0 // questions/minute
private const val VERSES_PER_PAGE = 20

fun main() {
    val book: Book = Book.DEFAULT
    writeFindTheVerse(book, randomSeed = 20, throughChapter = 20, numOfVersesToFind = 20)
//    for (i in setOf(4, 7, 10, 13, 16, 18, 20, 23, 24, 26, 28, 30, 32, 35, 37, 40, 41, 43, 45, 48, null)) {
//        writeFindTheVerse(book, randomSeed = 6, throughChapter = i, numOfVersesToFind = 20).toPdf()
//    }
}

private fun writeFindTheVerse(
    book: Book = Book.DEFAULT,
    throughChapter: Int? = null,
    randomSeed: Int = Random.nextInt(1..9_999),
    minCharLength: Int = 15,
    numOfVersesToFind: Int = 40,
): File {
    val random = Random(randomSeed)
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))

    val lastIncludedChapter: Int? = throughChapter?.let {
        val maxChapter = bookData.chapters.lastEntry().value
        require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${book.fullName}!" }
        if (it == maxChapter) null else it
    }

    var cluePool: DisjointRangeMap<Int> = bookData.oneVerseSentParts
    if (lastIncludedChapter != null) {
        val lastIncludedOffset: Int = bookData.chapterIndex[lastIncludedChapter]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }

    // remove any ambiguous clues
    cluePool = DisjointRangeMap(cluePool.entries
        .groupBy { (range, _) -> bookData.text.substring(range).lowercase() }
        .values
        .mapNotNull { it.singleOrNull() }
        .associate { (range, verse) -> range to verse }
    )

    val versesToFind: List<ReferencedVerse> = cluePool
        .filterKeys { it.length() >= minCharLength }
        .entries.shuffled(random).take(numOfVersesToFind)
        .map { (range, verseNum) -> ReferencedVerse(verseNum.toVerseRef(), bookData.text.substring(range)) }

    var fileName = "${book.name.lowercase()}-find-the-verse"
    if (throughChapter != null) fileName += "-to-ch-$throughChapter"
    fileName += "-%04d".format(randomSeed)

    val outputFile = File("$PRODUCTS_DIR/$bookName/practice/round1/$fileName.tex")
    outputFile.writer().use { writer ->
        versesToFind.toLatexInWhatChapter(writer, book.fullName, randomSeed, lastIncludedChapter)
    }
    return outputFile.toPdf()
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
                                               randomSeed: Int,
                                               throughChapter: Int?) {
    val seedString = "%04d".format(randomSeed)
    val bookDesc = book + throughChapter?.let { " (ONLY chapters 1-$it)" }.orEmpty()
    val tabularEnv = if (size > VERSES_PER_PAGE) "longtable" else "tabular"
    appendable.appendLine("""
        \documentclass[10pt, letter paper]{article} 
        \usepackage[utf8]{inputenc}
        \usepackage[letterpaper, left=0.75in, right=0.75in, top=0.75in, bottom=0.75in]{geometry}
        \usepackage{multicol}
        \usepackage[T1]{fontenc}
    """.trimIndent())
    if (size > VERSES_PER_PAGE) appendable.appendLine("\\usepackage{longtable}")
    appendable.appendLine("""
        \usepackage{array}
        \renewcommand{\arraystretch}{1.5}
        \newcounter{rowcount}
        \setcounter{rowcount}{0}
        
        \begin{document}
        
        \noindent Number \rule{1in}{0.01in}\hfill Name \rule{3in}{0.01in}\hfill Score \rule{1in}{0.01in}
        
        \section*{\#$seedString Find The Verse \textnormal{(Open Bible, ${(this.size / ROUND_1_PACE).toInt()} minutes)}\hfill Round 1}
        Using your Bible, write the chapter and verse from $bookDesc of each quotation in its matching box.
        
        \begin{center}
        \begin{$tabularEnv}{|@{\stepcounter{rowcount} \therowcount.\hspace*{\tabcolsep}}p{5.5in}||m{0.3in}|m{0.3in}|}
            \multicolumn{1}{c}{}&\multicolumn{2}{c}{ANSWER}\\
            \multicolumn{1}{c}{}&\multicolumn{1}{c}{Chapter}&\multicolumn{1}{c}{Verse}\\
            \hline
    """.trimIndent())
    this.forEachIndexed { i, refVerse ->
        if (i > 0 && i % VERSES_PER_PAGE == 0) appendable.appendLine("    \\newpage\\hline")
        appendable.appendLine("""    ${removeUnmatchedCharPairs(refVerse.verse.normalizeWS())} & & \\""")
        appendable.appendLine("""    \hline""")
    }
    appendable.appendLine("""
        \end{$tabularEnv}
        \end{center}
        \clearpage
        \section*{ANSWER KEY\\\#$seedString Find The Verse \textnormal{(Open Bible, ${(this.size / ROUND_1_PACE).toInt()} minutes)}\hfill Round 1}
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
