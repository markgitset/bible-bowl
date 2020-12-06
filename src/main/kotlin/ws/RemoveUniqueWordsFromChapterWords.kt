package net.markdrew.biblebowl.ws

import java.io.File


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val bookId = "2sam"
    val bookName = "2 Samuel"
    val verseMap: Map<String, String> = File("$bookId-verses.tsv").bufferedReader().useLines { lines ->
        lines.map { line -> line.split('\t', limit = 2) }
             .map { it.component2().substringAfter("$bookName ") to it.component1() }
             .toMap()
    }

    fun findVerses(word: String): Map<String, String> =
        verseMap.filterValues { word in stripVerse(it) }

    val uniqueWords = File("$bookId-unique-words.tsv").readLines().map { line ->
        line.split('\t', limit = 2)[1]
    }.toSet()
    File("$bookId-chapter-words.tsv").printWriter().use { out ->
        File("$bookId-unique-chapter-words.tsv").readLines().forEach { line ->
            val (word, chapter) = line.split('\t', limit = 2)
            if (word !in uniqueWords) {
                out.print("$word\t$chapter")
                for ((verse, verseText) in findVerses(word)) {

                    val normalText: String = normalizeVerse(verseText)
                    val highlighted = Regex(Regex.escape(word), RegexOption.IGNORE_CASE).replace(normalText, "<b><u>$0</u></b>")
                    out.print("<br><b>$verse</b> - $highlighted")
                }
                out.println()
            }
        }
    }
}

private fun stripVerse(verseText: String): String =
        verseText.toLowerCase().filterNot { it in "-',.“”" }

private fun normalizeVerse(verseText: String): String =
        verseText.replace("\\s+".toRegex(), " ").trim()
