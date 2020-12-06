package net.markdrew.biblebowl.ws

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.PrintWriter
import java.util.*


// Application keys must be included with each API request in an Authorization header.
// Authorization: Token 20e9b0330b3824603c4e2696c75d51c92529babc

fun main(args: Array<String>) {

    println("Bible Bowl!")

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory()
            .build()

    val service: EsvService = retrofit.create(EsvService::class.java)

    val bookName: String = args[0] // simple book abbrev will work (e.g., "rev" for Revelation)
    val headingIndex: SortedMap<BookChapterVerse, String> = service.buildHeadingsIndex(bookName)

    val indexFile = File("output", args.getOrNull(1) ?: "$bookName-headings-index.tsv")
    printIndex(indexFile.printWriter(), headingIndex)
    //printIndex(PrintWriter(System.out), headingIndex)
    println("Wrote data to: $indexFile")

    val headingsFile = File("output", args.getOrNull(1) ?: "$bookName-headings.tsv")
    printHeadings(headingsFile.printWriter(), headingIndex)
    //printHeadings(PrintWriter(System.out), headingIndex)
    println("Wrote data to: $headingsFile")

}

private fun printIndex(printWriter: PrintWriter, headingIndex: SortedMap<BookChapterVerse, String>) {
    printWriter.use { pw ->
        headingIndex.forEach { (bcv, heading) -> pw.println("${bcv.chapter}:${bcv.verse}\t$heading") }
    }
}

private fun printHeadings(printWriter: PrintWriter, headingIndex: Map<BookChapterVerse, String>) {
    printWriter.use { pw ->
        headingIndex.toSortedMap().toList()
            .groupBy({ (_, heading) -> heading }, { (bcv, _) -> bcv.chapter }) // headings to chapters
            .mapValues { (_, chapterList) -> chapterList.distinct() } // headings to distinct chapters list
            .forEach { (heading, chapters) -> pw.println("$heading\t${chapters.joinToString(" & ")}") }
    }
}

/**
 * @param bookName simple book abbrev will work (e.g., "rev" for Revelation)
 */
private fun EsvService.buildHeadingsIndex(bookName: String): SortedMap<BookChapterVerse, String> {
    val initPassage: Passage = passageText(bookName)
    val chapterRange: IntRange = initPassage.meta.startsInChapterRange()
    var chapterPassage: Passage? =
        if (initPassage.range == chapterRange) initPassage
        else passageFromRange(chapterRange)

    val headingIndex: SortedMap<BookChapterVerse, String> = sortedMapOf()

    do {
        if (chapterPassage == null) break
        val verse1: BookChapterVerse = chapterPassage.firstVerse()
        processChapterText(verse1, chapterPassage.text, headingIndex)
        chapterPassage = nextChapter(chapterPassage)
    } while (chapterPassage != null && verse1.sameBook(BookChapterVerse.fromRefNum(chapterPassage.range.first)))

    return headingIndex
}

private fun EsvService.nextChapter(chapterPassage: Passage): Passage? =
    chapterPassage.nextChapterRange()?.let { nextChapterRange ->
        passageFromRange(nextChapterRange)
    }

private fun Passage.firstVerse(): BookChapterVerse =
    BookChapterVerse.fromRefNum(range.first())

private fun Passage.nextChapterRange(): IntRange? =
    meta.nextChapterRange()

private fun EsvService.passageFromRange(range: IntRange): Passage =
    this.passageText("${range.first}-${range.last}")

private fun processChapterText(
    firstVerseOfChapter: BookChapterVerse,
    chapterText: String,
    headingIndex: SortedMap<BookChapterVerse, String>
) {
    var currentHeading = if (headingIndex.isNotEmpty()) headingIndex[headingIndex.lastKey()] else null
    val lines: List<String> = chapterText.lineSequence().toList()
    for (i in lines.indices) {
        val line = lines[i].trim()
        if (line.isEmpty()) continue
        if (line.trim().matches("""_+""".toRegex())) {
            currentHeading = lines[i + 1].trim()
        }
        """\[(\d+)] """.toRegex().findAll(line).forEach { match ->
            val verse: Int = match.groupValues[1].toInt()
            assert(currentHeading != null)
            headingIndex[firstVerseOfChapter.copy(verse = verse)] = currentHeading!!
        }
    }
}

private fun EsvService.passageText(query: String): Passage {
    println("query = $query")
    val call: Call<PassageText> = text(query,
            includePassageReferences = false,
            includeVerseNumbers = true,
            includeFootnotes = false,
            includeFootnoteBody = false,
            includeShortCopyright = false,
            includePassageHorizontalLines = true,
            includeHeadingHorizontalLines = true,
            includeHeadings = true)
    val response: Response<PassageText> = call.execute()
    return response.body()?.single() ?: throw Exception(response.errorBody()?.string())
}
