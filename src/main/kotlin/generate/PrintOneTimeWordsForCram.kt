package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.readHeadingsIndex
import net.markdrew.biblebowl.analysis.readVersesIndex
import net.markdrew.biblebowl.analysis.verseToWordList
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.model.BookChapterVerse
import net.markdrew.biblebowl.model.ReferencedVerse
import net.markdrew.biblebowl.model.ReferencedWord
import net.markdrew.biblebowl.model.toVerseMap
import java.io.File

fun highlightVerse(target: String, verse: String): String =
    verse.replace(Regex.fromLiteral(target), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    val bookName = args.getOrNull(0) ?: "rev"

    val referencedVerses: List<ReferencedVerse> = readVersesIndex(bookName)
    val headingsIndex: Map<BookChapterVerse, String> = readHeadingsIndex(bookName)
    val versesIndex: Map<BookChapterVerse, String> = referencedVerses.toVerseMap()

    val referencedWords: List<ReferencedWord> = referencedVerses.flatMap { verseToWordList(it) }
    val wordsIndex: Map<String, List<ReferencedWord>> = referencedWords.groupBy({ it.word.toLowerCase() }, { it })

    val uniqueWordsFile = File("output/$bookName", "$bookName-cram-one-time-words.tsv")
    CardWriter(uniqueWordsFile).use { writer ->
        wordsIndex.values.filter { it.size == 1 }.flatten().forEach { (reference, word) ->
            val highlightedVerse = highlightVerse(word, versesIndex.getValue(reference))
            val heading = headingsIndex[reference]
            val answer = "$heading<br/><b>${reference.toFullString()}</b><br/>$highlightedVerse"
            writer.write(word, answer, highlightedVerse)
        }
    }

}
