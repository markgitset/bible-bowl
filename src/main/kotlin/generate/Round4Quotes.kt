package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.model.*
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.length
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate

fun main() {
//    val nSamples = 10
    (22..22).forEach {
        writeRound4Quotes(Book.REV, throughChapter = it)
    }
}

private fun writeRound4Quotes(
    book: Book = Book.REV,
    throughChapter: Int? = null,
    exampleNum: Int? = null,
    date: LocalDate = LocalDate.now(),
    numQuestions: Int = 40,
) {
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)

    val maxChapter: Int = bookData.chapters.lastEntry().value
    val lastIncludedChapter: Int? = throughChapter?.let {
        require(it in 1..maxChapter) { "$throughChapter is not a valid chapter in ${book.fullName}!" }
        if (it == maxChapter) null else it
    }

    var cluePool: DisjointRangeMap<Int> = DisjointRangeMap(bookData.chapters
        .maskedBy(identifySingleQuotes(bookData.text).gcdAlignment(identifyDoubleQuotes(bookData.text)))
        .maskedBy(bookData.sentences)
        .mapKeys { (range, _) ->
            trim(bookData.text, range) { c -> c in " :,‘’“”\n"}
        }.filterNot { (range, _) -> range.isEmpty() })
    if (lastIncludedChapter != null) {
        val lastIncludedOffset: Int = bookData.chapterIndex[lastIncludedChapter]?.last ?: throw Exception()
        cluePool = cluePool.enclosedBy(0..lastIncludedOffset)
    }
    val filteredCluePool = cluePool.entries.filter { (r, _) -> r.length() >= 15 }
    println("Final clue pools size is ${filteredCluePool.size}")

    val quotesToFind: List<MultiChoiceQuestion> = filteredCluePool
        .shuffled().take(numQuestions)
        .map { (range, chapter) ->
            Question("""“${bookData.text.substring(range)}”""", chapter.toString())
        }.map { multiChoice(it, lastIncludedChapter ?: maxChapter) }

    var fileName = date.toString()
    if (exampleNum != null) fileName += "-$exampleNum"
    fileName += "-${book.fullName}-quotes"
    if (lastIncludedChapter != null) fileName += "-to-ch-$throughChapter"

    File("output/$bookName/$fileName.tex").writer().use { writer ->
        toLatexKnowTheChapter(
            quotesToFind, writer, book, lastIncludedChapter, minutes = 15, round = 4, clueType = "quotes"
        )
    }

    println("Wrote ${File("output/$bookName/$fileName.tex")}")
}