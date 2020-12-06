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

    File("$bookId-unique-words-with-verse.tsv").printWriter().use { out ->
        File("$bookId-unique-words.tsv").readLines()/*.take(5)*/.forEach { line ->
            val (originalVerse, word) = line.split('\t', limit = 2)
            val originalText: String = verseMap[originalVerse].orEmpty()
            val (verse, text) = if (!stripVerse(originalText).contains(word, ignoreCase = true)) {
                println("One-word '$word' not found in $originalVerse")
                val entry: Map.Entry<String, String>? = verseMap.filterValues {
                    it.contains(word, ignoreCase = true)
                }.entries.singleOrNull()

                entry?.also {
                    println("\tFound '$word' in ${it.key}: ${it.value}")
                }?.toPair() ?: originalVerse to "NOT FOUND!"
            } else originalVerse to originalText
            val normalText: String = normalizeVerse(text)
            val highlighted = Regex(Regex.escape(word), RegexOption.IGNORE_CASE).replace(normalText, "<b><u>$0</u></b>")
            out.println("$word\t<b>$verse</b> - $highlighted")
        }
    }
}

private fun stripVerse(verseText: String): String =
        verseText.toLowerCase().filterNot { it in "-',.“”" }

private fun normalizeVerse(verseText: String): String =
        verseText.replace("\\s+".toRegex(), " ").trim()
