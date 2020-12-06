package analysis

import java.io.File

fun highlightVerse(target: String, verse: String): String = verse.replace(Regex.fromLiteral(target), "<b><u>$0</u></b>")

fun main(args: Array<String>) {
    val versesFile = File(args.getOrNull(0) ?: "rev-verses.tsv")
    val uniqueWordsFile = File(args.getOrNull(1) ?: "rev-unique-words.tsv")
    val referencedVerses: List<ReferencedVerse> = readVerses(versesFile)
    val verseMap: Map<String, String> = referencedVerses.toVerseMap()
    val referencedWords: List<ReferencedWord> = referencedVerses.flatMap { verseToWordList(it) }
    val wordRefs: Map<String, List<ReferencedWord>> = referencedWords.groupBy({ it.word.toLowerCase() }, { it })
    uniqueWordsFile.writer(Charsets.UTF_8).use { writer ->
        wordRefs.values.filter { it.size == 1 }.flatten().forEach { (reference, word) ->
            val highlightedVerse = highlightVerse(word, verseMap.getValue(reference))
            writer.appendln("$word  <b>${shortenReference(reference)}</b> - $highlightedVerse")
        }
    }

}
