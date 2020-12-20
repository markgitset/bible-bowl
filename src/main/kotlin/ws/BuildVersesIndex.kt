package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.BookChapterVerse
import net.markdrew.biblebowl.model.toBookChapterVerse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.Paths


// Application keys must be included with each API request in an Authorization header.
// Authorization: Token 20e9b0330b3824603c4e2696c75d51c92529babc

fun main(args: Array<String>) {

    println("Bible Bowl!")

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: EsvService = retrofit.create(EsvService::class.java)

    val bookName: String = args[0]
    val verse1Passage: Passage = passageText(service, bookName).single()
    val verse1: BookChapterVerse = verse1Passage.range.first.toBookChapterVerse()

    val verseMap: MutableList<Pair<BookChapterVerse, String>> = mutableListOf()

    var nextChapterRange: IntRange? = verse1Passage.meta.startsInChapterRange()
    while (nextChapterRange != null && verse1.sameBook(nextChapterRange.first.toBookChapterVerse())) {
        val versesToQuery: String = nextChapterRange.joinToString(",")
        val passages: List<Passage> = passageText(service, versesToQuery).toList()
        verseMap.addAll(formatChapter(passages))
        nextChapterRange = passages.first().meta.nextChapter?.toRange()
    }

    val outDir = Paths.get("output").resolve(bookName)
    val outPath = outDir.resolve(args.getOrNull(1) ?: "$bookName-index-verses.tsv")
    outPath.toFile().printWriter().use {
        verseMap.forEach { (ref, verse) -> it.println("${ref.toRefNum()}\t$verse") }
    }
    println("Wrote data to: $outPath")
}

private fun formatChapter(passages: List<Passage>): List<Pair<BookChapterVerse, String>> =
    passages.map { it.range.first.toBookChapterVerse() to normalizeVerse(it.text) }

private fun normalizeVerse(verseText: String): String =
        verseText.replace("\\s+".toRegex(), " ").trim()

/*
 * Builds an IntRange from the first two values of a List<Int>
 */
private fun List<Int>.toRange(): IntRange = IntRange(this[0], this[1])

private fun passageText(service: EsvService, query: String): PassageText {
    val call: Call<PassageText> = service.text(query,
            includePassageReferences = false,
            includeVerseNumbers = false,
            includeFootnotes = false,
            includeFootnoteBody = false,
            includeShortCopyright = false,
            includePassageHorizontalLines = true,
            includeHeadingHorizontalLines = true,
            includeHeadings = false)
    val response: Response<PassageText> = call.execute()
    return response.body() ?: throw Exception(response.errorBody()?.string())
}
