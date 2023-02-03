package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.latex.showPdf
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toString
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File

private const val VERSES_PER_PAGE = 20

fun main() {
    val readData = StudyData.readData()
    val content = PracticeContent(readData, readData.chapterRangeOfNChapters(22))
    showPdf(
        writeFindTheVerse(
            PracticeTest(Round.FIND_THE_VERSE, content, numQuestions = 20, randomSeed = 50)
        )
    )
//    val content: PracticeContent = BookData.readData().practice(1..14)
//    showPdf(writeFindTheVerse(
//        PracticeTest(Round.FIND_THE_VERSE, content, numQuestions = 20)
//    ))

//    val seeds = setOf(10, 20, 30, 40, 50)
//    for (throughChapter in bookData.chapterRange) {
//        for (seed in seeds) {
//            writeFindTheVerse(
//                PracticeTest(Round.FIND_THE_VERSE, throughChapter, numQuestions = 20, randomSeed = seed),
//                bookData = bookData,
//                directory = directory
//            )
//        }
//    }
}

private fun writeFindTheVerse(
    practiceTest: PracticeTest,
    minCharLength: Int = 15,
    directory: File? = null,
): File {
    val content = practiceTest.content
    val bookData = content.studyData
    var cluePool: DisjointRangeMap<VerseRef> = bookData.oneVerseSentParts
    if (!content.allChapters) {
        cluePool = cluePool.enclosedBy(content.coveredOffsets)
    }

    // remove any ambiguous clues
//    cluePool = DisjointRangeMap(cluePool.entries
//        .groupBy { (range, _) -> bookData.text.substring(range).lowercase() }
//        .also { it.forEach { println(it) } }
//        .values
//        .mapNotNull { it.singleOrNull() }
//        .associate { (range, verse) -> range to verse }
//    )

    // TODO need a better disambiguation approach!
    val versesToFind: List<ReferencedVerse> = cluePool
        .filterKeys { it.length() >= minCharLength }
        .entries.shuffled(practiceTest.random)
        .take(practiceTest.numQuestions)
        .map { (range, verseRef) -> ReferencedVerse(verseRef, bookData.text.substring(range)) }

    val outputFile = practiceTest.buildTexFileName(directory)
    outputFile.writer().use { writer ->
        versesToFind.toLatexInWhatChapter(writer, practiceTest)
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
                                               practiceTest: PracticeTest) {
    val seedString = "%04d".format(practiceTest.randomSeed)
    val minutes = Round.FIND_THE_VERSE.minutesAtPaceFor(this.size)
    val chapters: ChapterRange = practiceTest.content.coveredChapters
    val coverage = if (practiceTest.content.allChapters) "" else " (ONLY chapters ${chapters.toString("-")})"
    val bookDesc = practiceTest.studySet.name + coverage
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
        
        \section*{\#$seedString Find The Verse \textnormal{(Open Bible, $minutes minutes)}\hfill Round 1}
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
        \section*{ANSWER KEY\\\#$seedString Find The Verse \textnormal{(Open Bible, $minutes minutes)}\hfill Round 1}
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
