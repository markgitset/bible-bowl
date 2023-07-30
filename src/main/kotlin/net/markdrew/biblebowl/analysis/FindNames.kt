package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.rangeFirstLastComparator
import java.nio.file.Paths

private val STOP_NAMES = setOf("o", "i", "amen", "surely", "lord", "alpha", "omega", "almighty", "hallelujah", "praise",
    "why", "yes", "release", "sir", "father", "pay", "sovereign", "mount", "remember", "hurry", "possessor", "perhaps",
    "oh", "suppose", "knead", "meanwhile", "quick", "raiders", "whichever", "unstable", "assemble", "do")

private val ENGLISH_WORDS: Dictionary by lazy { DictionaryParser.parse("not-names.txt") }

fun buildNamesIndex(studyData: StudyData,
                    frequencyRange: IntRange? = null,
                    vararg exceptNames: String): List<WordIndexEntry> =
    nameCandidates(studyData, exceptNames).map { excerpts ->
        val key = excerpts.first().excerptText
        WordIndexEntry(key, excerpts.map { studyData.verseEnclosing(it.excerptRange) ?: throw Exception() })
    }

fun findNames(studyData: StudyData, vararg exceptNames: String): Sequence<Excerpt> =
    nameCandidates(studyData, exceptNames)
        .flatten()
        .sortedWith(compareBy(rangeFirstLastComparator) { it.excerptRange })
        .asSequence()

private fun isName(studyData: StudyData, wordExcerpt: Excerpt): Boolean {

    // names don't start with digits or lower-cased letters
    val word: String = wordExcerpt.excerptText
    if (word.first().let { it.isLowerCase() || it.isDigit() }) return false

    val lowerWord: String = word.lowercase()
    if (lowerWord in STOP_NAMES) return false

    val englishWord: Boolean = lowerWord in ENGLISH_WORDS
    val firstWordInSentence: Boolean = isFirstWordInSentence(studyData, wordExcerpt.excerptRange)

    return !englishWord
}

private fun isFirstWordInSentence(studyData: StudyData, wordRange: IntRange): Boolean {
    val precededByQuote = wordRange.first > 0 && studyData.text[wordRange.first - 1] in "“‘'\""
    if (precededByQuote) return true

    val sentenceRange: IntRange = studyData.enclosingSentence(wordRange)
        ?: throw Exception("Word range not in a sentence range!")
    // Identify the first word in the enclosing sentence (sentence could start with quotes)
//    val firstWordInSent: IntRange = studyData.words.enclosedBy(sentenceRange).first()
//    return wordRange == firstWordInSent
    return wordRange.first == sentenceRange.first
}

private fun nameCandidates(studyData: StudyData, exceptNames: Array<out String>): Collection<List<Excerpt>> =
    studyData.words
        .map { studyData.excerpt(it).disown() } // non-possessive word Excerpts
        .filter { isName(studyData, it) }
        .filter { it.excerptText !in exceptNames }
        .groupBy { it.excerptText.lowercase() } // Map<String, List<Excerpt>>
        .filterKeys { it !in STOP_NAMES } // remove stop names
//        .filterValues { excerpts ->
//            excerpts.none { excerpt ->
//                excerpt.excerptText.first().let { it.isLowerCase() || it.isDigit() }
//            }
//        }
        .values // only keep excerpt lists for which all excerpts start with a capital letter

fun printNameFrequencies(nameExcerpts: Sequence<Excerpt>) {
    nameExcerpts.groupBy { it.excerptText }
        .map { (name, excerpts) -> excerpts.size to name }
        .sortedBy { it.first }
        .forEachIndexed { i, (count, name) ->
            println("%3d  %15s %15s".format(i+1, count, name))
        }
}

fun printNameMatches(nameExcerpts: Sequence<Excerpt>, studyData: StudyData) {
    nameExcerpts.forEachIndexed { i, numExcerpt ->
        val (nameString, nameRange) = numExcerpt
        val sentRange: Excerpt? = studyData.sentenceContext(nameRange)
        val sentenceString: String = sentRange?.formatRange(nameRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = studyData.verseEnclosing(nameRange)
        println("%3d  %15s %15s    %s".format(i, ref?.format(NO_BOOK_FORMAT), nameString, sentenceString))
    }
}

fun main(args: Array<String>) {

    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

//    val dict = DictionaryParser.parse("words_alpha.txt")
    val dict = DictionaryParser.parse("english.txt")
    val nameExcerpts: Sequence<Excerpt> = findNames(studyData, "god", "jesus", "christ")
        .filter { it.excerptText.lowercase() in dict }

//    printNameFrequencies(nameExcerpts)
    printNameMatches(nameExcerpts, studyData)
}