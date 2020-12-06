package net.markdrew.biblebowl.ws

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


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

    val verse1Ref: String = args[0]
    val verse1Passage: PassageText = passageText(service, verse1Ref)
    val verse1: BookChapterVerse = BookChapterVerse.fromRefNum(verse1Passage.parsed.first().first())

    val verseMap: MutableList<Pair<String, String>> = mutableListOf()

    var nextChapterRange: IntRange? = verse1Passage.passageMeta.first().chapterStart.toRange()
    while (nextChapterRange != null && verse1.sameBook(BookChapterVerse.fromRefNum(nextChapterRange.first))) {
        val versesToQuery: String = nextChapterRange.joinToString(",")
        val passages: PassageText = passageText(service, versesToQuery)
        verseMap.addAll(formatChapter(passages))
        nextChapterRange = passages.passageMeta.first().nextChapter?.toRange()
    }

    val outFile = File(args.getOrNull(1) ?: "$verse1Ref-verses.tsv")
    outFile.printWriter().use {
        verseMap.forEach { (ref, verse) -> it.println("$verse\t$ref") }
    }
    println("Wrote data to: $outFile")
}

private fun formatChapter(passages: PassageText): List<Pair<String, String>> =
    passages.passageMeta.zip(passages.passages) { ref, verse ->
        ref.canonical to normalizeVerse(verse)
    }

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
