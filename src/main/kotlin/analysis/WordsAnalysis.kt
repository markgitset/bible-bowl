package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.BookChapterVerse
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.ReferencedWord
import net.markdrew.biblebowl.model.toBookChapterVerse
import java.io.File

/**
 * Read headings from TSV file of (reference)TAB(heading) to a Map<ref,heading>
 */
fun readHeadingsIndex(bookName: String): Map<BookChapterVerse, String> =
    readHeadingsIndex(File("output/$bookName/$bookName-index-headings.tsv"))
    
/**
 * Read headings from TSV file of (reference)TAB(heading) to a Map<ref,heading>
 */
fun readHeadingsIndex(headingsFile: File): Map<BookChapterVerse, String> = headingsFile.readLines(Charsets.UTF_8)
    .map { line ->
        val (ref, heading) = line.split('\t', limit = 2)
        ref.toInt().toBookChapterVerse() to heading
    }.toMap()

/**
 * Read verses from TSV file of (reference)TAB(verse) to a List<ReferencedVerse>
 */
fun readVersesIndex(bookName: String): List<ReferencedVerse> =
    readVersesIndex(File("output/$bookName/$bookName-index-verses.tsv"))

/**
 * Read verses from TSV file of (reference)TAB(verse) to a List<ReferencedVerse>
 */
fun readVersesIndex(versesFile: File): List<ReferencedVerse> = versesFile.readLines(Charsets.UTF_8).map { line ->
    val (ref, verse) = line.split('\t', limit = 2)
    ReferencedVerse(ref.toInt().toBookChapterVerse(), verse)
}

/**
 * Tokenizes a [ReferencedVerse] into a list of [ReferencedWord]s
 */
fun verseToWordList(refVerse: ReferencedVerse): List<ReferencedWord> =
        refVerse.verse.split(*" \t\r\n.‘!“”:;?()—".toCharArray())   // split around various punctuation
            .flatMap { it.split(""",(?!\d)""".toRegex()) } // split around , not followed by a digit
            .flatMap { it.split("""’(?!s)""".toRegex()) }  // split around ’ not followed by an s
            .filter { it.isNotBlank() }
            .map { ReferencedWord(refVerse.reference, it) }

fun shortenReference(reference: String): String = reference.substringAfter(' ')

val stopWords: List<String> = listOf("the", "and", "of", "to", "a", "i", "who", "in", "on", "with", "for", "will",
    "you", "was", "is", "his", "he", "from", "that", "they", "are", "their", "it", "be", "like", "were", "have",
    "him", "them", "her", "not", "had", "has", "its", "your", "then", "but", "those", "no", "as", "what", "this",
    "by", "my", "so", "into", "or", "when", "came", "an", "these", "which", "there", "been", "am", "at")

fun main1() {
    // word frequencies
    val versesIndex: List<ReferencedVerse> = readVersesIndex("rev")
    val words: List<String> = versesIndex.flatMap { verseToWordList(it) }
        .map { it.word }
        .filterNot { stopWords.contains(it.toLowerCase()) }
    val groupBy: Map<String, Int> = words.groupingBy { it.toLowerCase() }.eachCount()
        .filter { (word, count) -> count > 1 }
    groupBy.asSequence().sortedBy { it.value }.forEach { (word, count) ->
        println("%12s = %5d".format(word, count))
    }

}

fun main2() {
    // one heading words
    val versesIndex: List<ReferencedVerse> = readVersesIndex("rev")
    val headingsIndex: Map<BookChapterVerse, String> = readHeadingsIndex("rev")
    val wordHeadings: List<Pair<String, String>> = versesIndex.flatMap { verseToWordList(it) }
        .map { it.word to headingsIndex.getValue(it.reference) }
    val wordToHeadingsList: Map<String, List<String>> = wordHeadings.groupBy(
        { (word, heading) -> word.toLowerCase() },
        { (word, heading) -> heading }
    )
    val filterValues: Map<String, List<String>> = wordToHeadingsList
        .filterValues { headingList -> headingList.size > 1 } // remove one-time words
        .filterValues { headingList -> headingList.distinct().count() == 1 } // only keep entries all in same heading

    filterValues.entries.sortedBy { (word, headings) -> headings.size }.forEach { (word, headings) ->
        println("""%20s occurs %2d times in heading:  "%s"""".format(""""$word"""", headings.size, headings.first()))
    }

}

fun main() {
    // one chapter words
    val versesIndex: List<ReferencedVerse> = readVersesIndex("rev")
    val wordChapters: List<Pair<String, Int>> = versesIndex.flatMap { verseToWordList(it) }
        .map { it.word to it.reference.chapter }
    val wordToChaptersList: Map<String, List<Int>> = wordChapters.groupBy(
        { (word, chapter) -> word.toLowerCase() },
        { (word, chapter) -> chapter }
    )
    val filterValues: Map<String, List<Int>> = wordToChaptersList
        .filterValues { chapterList -> chapterList.size > 1 } // remove one-time words
        .filterValues { chapterList -> chapterList.distinct().count() == 1 } // only keep entries all in same heading

    filterValues.entries.sortedBy { (word, chapters) -> chapters.size }.forEach { (word, chapters) ->
        println("""%20s occurs %2d times in chapter  %2d""".format(""""$word"""", chapters.size, chapters.first()))
    }

}
