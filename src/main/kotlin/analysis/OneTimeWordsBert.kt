package net.markdrew.biblebowl.analysis

import analysis.Bert
import kotlin.io.path.ExperimentalPathApi

data class OneTimeWordData(val wordRange: IntRange, val sent: String, val wordSentIndex: Int)

@ExperimentalPathApi
fun main() {
//    val bookData = BookData.readData(Paths.get(DATA_DIR), Book.DEFAULT)
//    val oneTimeWords: List<IntRange> = oneTimeWords(bookData)

    Bert.load("com/robrua/nlp/easy-bert/bert-cased-L-12-H-768-A-12").use { bert ->
        val embedTokens: Array<FloatArray> = bert.embedTokens("It's hard to believe this actually works.")
        val vocabulary: Map<String, Int> = bert.tokenizer.vocabulary
//        val embedTokens: Array<FloatArray> = bert.embedTokens("“To the angel of the church in Ephesus write: ‘The words of him who holds the seven stars in his right hand, who walks among the seven golden lampstands.")
        for (embedToken in embedTokens) {
//            println(embedToken.size.toString() + ": " + embedToken.take(10).joinToString())
        }
        println("\n" + embedTokens.size)
    }
}