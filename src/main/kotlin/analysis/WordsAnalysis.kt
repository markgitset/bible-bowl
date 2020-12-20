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

fun List<ReferencedVerse>.toVerseMap(): Map<BookChapterVerse, String> = this.map { (ref, verse) -> ref to verse }.toMap()

fun verseToWordList(refVerse: ReferencedVerse): List<ReferencedWord> =
        refVerse.verse.split(*" \t\r\n.‘!“”:;?()—".toCharArray())   // split around various punctuation
                .flatMap { it.split(""",(?!\d)""".toRegex()) } // split around , not followed by a digit
                .flatMap { it.split("""’(?!s)""".toRegex()) }  // split around ’ not followed by an s
                .map { ReferencedWord(refVerse.reference, it) }

fun shortenReference(reference: String): String = reference.substringAfter(' ')