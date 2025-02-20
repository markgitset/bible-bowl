package net.markdrew.biblebowl.generate.practice

import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.latex.latexToPdf
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.PracticeContent
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File

private const val VERSES_PER_PAGE = 20

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)

    // JUST DO ONE
//    val content: PracticeContent = studyData.practice(Book.EXO.chapterRef(20))
//    showPdf(writeFindTheVerse(
//        PracticeTest(Round.FIND_THE_VERSE, content, numQuestions = 20)
//    ))

    // PRODUCE THE FULL SET
    val seeds = setOf(10, 20, 30, 40, 50)
    for (throughChapter in studyData.chapterRefs) {
        //if (throughChapter < Book.EXO.chapterRef(20)) continue
        val content = studyData.practice(throughChapter)
        for (seed in seeds) {
            writeFindTheVerse(PracticeTest(Round.FIND_THE_VERSE, content, numQuestions = 20, randomSeed = seed))
        }
    }
}

fun writeFindTheVerse(
    practiceTest: PracticeTest,
    minCharLength: Int = 15,
    directory: File? = null,
): File {
    val content: PracticeContent = practiceTest.content
    val studyData: StudyData = content.studyData
    var cluePool: DisjointRangeMap<VerseRef> = studyData.oneVerseSentParts
    if (!content.allChapters) {
        cluePool = cluePool.enclosedBy(content.coveredOffsets)
    }

    // remove any ambiguous clues
//    cluePool = DisjointRangeMap(cluePool.entries
//        .groupBy { (range, _) -> studyData.text.substring(range).lowercase() }
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
        .map { (range, verseRef) -> ReferencedVerse(verseRef, studyData.text.substring(range)) }

    val outputFile = practiceTest.buildTexFileName(directory)
    outputFile.writer().use { writer ->
        versesToFind.toLatexInWhatChapter(writer, practiceTest)
    }
    return outputFile.latexToPdf(keepTexFiles = false)
}

/** Strings containing pairs of characters that normally occur as pairs */
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
    val chapters: String = practiceTest.content.coveredChaptersString()
    val multiBook = practiceTest.content.studyData.isMultiBook
    val coverage = if (practiceTest.content.allChapters) "" else " (ONLY $chapters)"
    val answerDesc = if (multiBook) {
        "book, chapter, and verse from ${practiceTest.studySet.chapterNamesWith2LetterCodes()}"
    } else {
        "chapter and verse from ${practiceTest.studySet.name}"
    }
    val bookColDef = if (multiBook) "m{0.3in}|" else ""
    val bookColHeading = if (multiBook) """\multicolumn{1}{c}{Book}&""" else ""
    val tabularEnv = if (size > VERSES_PER_PAGE) "longtable" else "tabular"
    appendable.appendLine("""
        \documentclass[10pt, letter paper]{article} 
        \usepackage[utf8]{inputenc}
        \usepackage[letterpaper, left=0.7in, right=0.7in, top=0.7in, bottom=0.7in]{geometry}
        \usepackage{multicol}
        \usepackage[T1]{fontenc}
    """.trimIndent())
    if (size > VERSES_PER_PAGE) appendable.appendLine("\\usepackage{longtable}")
    appendable.appendLine("""
        \usepackage{array}
        \renewcommand{\arraystretch}{1.4}
        \newcounter{rowcount}
        \setcounter{rowcount}{0}
        
        \begin{document}
        
        \noindent Number \rule{1in}{0.01in}\hfill Name \rule{3in}{0.01in}\hfill Score \rule{1in}{0.01in}
        
        \section*{\#$seedString Find The Verse \textnormal{(Open Bible, $minutes minutes)}\hfill Round 1}
        Using your Bible, write the $answerDesc$coverage of each quotation in its matching box.
        
        \begin{center}
        \begin{$tabularEnv}{|@{\stepcounter{rowcount} \therowcount.\hspace*{\tabcolsep}}%
            p{${if (multiBook) 5.2 else 5.5}in}||${bookColDef}m{0.3in}|m{0.3in}|}
            \multicolumn{1}{c}{}&\multicolumn{${if (multiBook) 3 else 2}}{c}{ANSWER}\\
            \multicolumn{1}{c}{}&$bookColHeading\multicolumn{1}{c}{Chapter}&\multicolumn{1}{c}{Verse}\\
            \hline
    """.trimIndent())
    this.forEachIndexed { i, refVerse ->
        if (i > 0 && i % VERSES_PER_PAGE == 0) appendable.appendLine("""    \newpage\hline""")
        appendable.appendLine(
            """    ${removeUnmatchedCharPairs(refVerse.verse.normalizeWS())} & & ${if (multiBook) "&" else ""}\\"""
        )
        appendable.appendLine("""    \hline""")
    }
    appendable.appendLine("""
        \end{$tabularEnv}
        \end{center}
        \clearpage
        \section*{ANSWER KEY\\\#$seedString Find The Verse \textnormal{(Open Bible, $minutes minutes)}\hfill Round 1}
        \begin{multicols}{2}
        \raggedright
        \begin{enumerate}
    """.trimIndent())
    this.forEach {
        val verseRef: VerseRef = it.reference
        val headings: List<String> = practiceTest.content.studyData.headingsIntersecting(verseRef)
        if (headings.isEmpty()) throw Exception("No chapter heading(s) found for $verseRef!")
        appendable.append("""    \item ${verseRef.format(FULL_BOOK_FORMAT)}\\""")
        headings.joinTo(appendable, """ AND \\""")
        appendable.appendLine()
    }
    appendable.appendLine("""
        \end{enumerate}
        \end{multicols}
        \end{document}
    """.trimIndent())
}
