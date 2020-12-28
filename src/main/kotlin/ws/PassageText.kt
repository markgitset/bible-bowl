package net.markdrew.biblebowl.ws

import com.google.gson.annotations.SerializedName

/*
 * Builds an IntRange from the first two values of a List<Int>
 */
fun List<Int>.toIntRange(): IntRange = this[0]..this[1]

data class PassageMeta(val canonical: String,
                       @SerializedName("chapter_start") val chapterStart: List<Int>,
                       @SerializedName("chapter_end") val chapterEnd: List<Int>,
                       @SerializedName("prev_verse") val prevVerse: Int?,
                       @SerializedName("next_verse") val nextVerse: Int?,
                       @SerializedName("prev_chapter") val prevChapter: List<Int>?,
                       @SerializedName("next_chapter") val nextChapter: List<Int>?) {

    fun startsInChapterRange(): IntRange = chapterStart.toIntRange()
    fun endsInChapterRange(): IntRange = chapterEnd.toIntRange()
    fun prevChapterRange(): IntRange? = prevChapter?.toIntRange()
    fun nextChapterRange(): IntRange? = nextChapter?.toIntRange()
}

data class Verse(val number: Int, val text: String)

data class Section(val heading: String, val paragraphs: List<String>) {
//    fun verses(prevVerse: Int): List<List<Verse>> = paragraphs.map { para ->
//        val find: MatchResult? = """\[\d+\] """.toRegex().find(para)
//        if (find == null) return listOf(Verse(prevVerse, para))
//        val verseList = mutableListOf<Verse>()
//        if (find.range.first > 0) verseList.add(Verse(prevVerse, para.slice(0 until find.range.first)))
//        while (find != null) {
//
//        }
//        verseList
//    }

}

data class Passage(val canonical: String, val range: IntRange, val meta: PassageMeta, val text: String) {
    fun sections(): List<Section> = text.split("""^_+$""".toRegex(RegexOption.MULTILINE)).map { textChunk ->
        val paragraphs = textChunk.split("\n\n", "\n\n\n")
        Section(heading = paragraphs.first(), paragraphs = paragraphs.drop(1))
    }
}

data class PassageText(val query: String,
                       val canonical: String,
                       val parsed: List<List<Int>>,
                       @SerializedName("passage_meta") val passageMeta: List<PassageMeta>,
                       val passages: List<String>) {

    fun parsedRanges(): List<IntRange> = parsed.map { it.toIntRange() }

    fun toList(): List<Passage> = canonical.split("; ").mapIndexed { i, canon ->
        Passage(canon, parsedRanges()[i], passageMeta[i], passages[i])
    }

    fun single() = toList().single()

}

// Example response:
/*
{
    "query": "01001001-01001003,01011030-01011032",
    "canonical": "Genesis 1:1–3; Genesis 11:30–32",
    "parsed": [
        [ 1001001, 1001003 ],
        [ 1011030, 1011032 ]
    ],
    "passage_meta": [
        {
            "canonical": "Genesis 1:1–3",
            "chapter_start": [ 1001001, 1001031 ],
            "chapter_end": [ 1001001, 1001031 ],
            "prev_verse": null,
            "next_verse": 1001004,
            "prev_chapter": null,
            "next_chapter": [ 1002001, 1002025 ]
        },
        {
            "canonical": "Genesis 11:30–32",
            "chapter_start": [ 1011001, 1011032 ],
            "chapter_end": [ 1011001, 1011032 ],
            "prev_verse": 1011029,
            "next_verse": 1012001,
            "prev_chapter": [ 1010001, 1010032 ],
            "next_chapter": [ 1012001, 1012020 ]
        }
    ],
    "passages": [
        "<h2 class=\"extra_text\">Genesis 1:1–3</h2>\n<h3 id=\"p01001001_01-1\">The Creation of the World</h3>\n<p id=\"p01001001_06-1\" class=\"starts-chapter\"><b class=\"chapter-num\" id=\"v01001001-1\">1:1&nbsp;</b>In the beginning, God created the heavens and the earth. <b class=\"verse-num\" id=\"v01001002-1\">2&nbsp;</b>The earth was without form and void, and darkness was over the face of the deep. And the Spirit of God was hovering over the face of the waters.</p>\n<p id=\"p01001002_06-1\"><b class=\"verse-num\" id=\"v01001003-1\">3&nbsp;</b>And God said, “Let there be light,” and there was light.</p>\n<p>(<a href=\"http://www.esv.org\" class=\"copyright\">ESV</a>)</p>",
        "<h2 class=\"extra_text\">Genesis 11:30–32</h2>\n<p id=\"p01011030_01-2\" class=\"virtual\"><b class=\"verse-num\" id=\"v01011030-1\">30&nbsp;</b>Now Sarai was barren; she had no child.</p>\n<p id=\"p01011030_01-2\"><b class=\"verse-num\" id=\"v01011031-1\">31&nbsp;</b>Terah took Abram his son and Lot the son of Haran, his grandson, and Sarai his daughter-in-law, his son Abram's wife, and they went forth together from Ur of the Chaldeans to go into the land of Canaan, but when they came to Haran, they settled there. <b class=\"verse-num\" id=\"v01011032-1\">32&nbsp;</b>The days of Terah were 205 years, and Terah died in Haran.</p>\n<p>(<a href=\"http://www.esv.org\" class=\"copyright\">ESV</a>)</p>"
    ]
}
*/
