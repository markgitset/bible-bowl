package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.rangeFirstLastComparator
import org.intellij.lang.annotations.Language
import java.nio.file.Paths

private val STOP_NAMES = setOf("o", "i", "amen", "surely", "lord", "alpha", "omega", "almighty", "hallelujah", "praise",
    "why", "yes", "release", "sir", "father", "pay", "sovereign", "mount", "remember", "hurry", "possessor", "perhaps",
    "oh", "suppose", "knead", "meanwhile", "quick", "raiders", "whichever", "unstable", "assemble", "do", "god")

@Language("RegExp")
private val REGEX_NAMES = setOf(
    """\p{Lu}\w+ Sea""",
    """(?:Valley|Brook|Feast|Sea) of(?: the)?(?: \p{Lu}\w+){1,2}""",
    """Mount (of )?\p{Lu}\w+""",
    "John the Baptist",
    """(?<=“)Legion(?=,”)""", // Luke 8:30
    """Lot(’s)?""", // Luke 17:28-31
    "The Skull", // Luke 23:33
    "King of the Jews", // Luke 23:3,37,38
)

private val ENGLISH_WORDS: Dictionary by lazy { DictionaryParser.parse("not-names.txt") }

fun buildNamesIndex(
    studyData: StudyData,
    vararg exceptNames: String
): List<WordIndexEntry> =
    nameCandidates(studyData, exceptNames).map { excerpts ->
        val key = excerpts.first().excerptText
        WordIndexEntry(key, excerpts.map { studyData.verseEnclosing(it.excerptRange) ?: throw Exception() })
    }

fun findNames(studyData: StudyData, vararg exceptNames: String): Sequence<Excerpt> =
    nameCandidates(studyData, exceptNames)
        .flatten()
        .sortedWith(compareBy(rangeFirstLastComparator) { it.excerptRange })
        .asSequence()

private fun isName(
    @Suppress("UNUSED_PARAMETER") studyData: StudyData,
    wordExcerpt: Excerpt
): Boolean {

    // names don't start with digits or lower-cased letters
    val word: String = wordExcerpt.excerptText
    if (word.first().let { it.isLowerCase() || it.isDigit() }) return false

    val lowerWord: String = word.lowercase()
    if (lowerWord in STOP_NAMES) return false

    val englishWord: Boolean = lowerWord in ENGLISH_WORDS
    //val firstWordInSentence: Boolean = isFirstWordInSentence(studyData, wordExcerpt.excerptRange)

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

private fun wordNames(studyData: StudyData, exceptNames: Array<out String>): DisjointRangeMap<String> =
    studyData.words.fold(DisjointRangeMap()) { drm, range ->
        val excerpt: Excerpt = studyData.excerpt(range).disown()
        drm.apply {
            if (isName(studyData, excerpt) && excerpt.excerptText !in exceptNames) {
                put(excerpt.excerptRange, excerpt.excerptText)
            }
        }
    }

private fun regexNames(
    text: CharSequence,
    regexes: List<Regex>,
    exceptNames: Array<out String>,
): DisjointRangeMap<String> = DisjointRangeMap<String>().apply {
    regexes.forEach { regex ->
        regex.findAll(text).forEach {
            if (it.value !in exceptNames) put(it.range, it.value)
        }
    }
}

private fun nameCandidates(studyData: StudyData, exceptNames: Array<out String>): Collection<List<Excerpt>> {
    val a: DisjointRangeMap<String> = wordNames(studyData, exceptNames)
    val b: DisjointRangeMap<String> = regexNames(studyData.text, REGEX_NAMES.map { it.toRegex() }, exceptNames)
    b.putAllNonIntersecting(a)
    return b.map { (range, value) -> Excerpt(value, range) }
        .groupBy { it.excerptText.lowercase() } // Map<String, List<Excerpt>>
        .filterKeys { it !in STOP_NAMES } // remove stop names
        .values // only keep excerpt lists for which all excerpts start with a capital letter
}

fun printNameFrequencies(nameExcerpts: Sequence<Excerpt>) {
    nameExcerpts.groupBy { it.excerptText }
        .map { (name, excerpts) -> excerpts.size to name }
        .sortedBy { it.first }
        .forEachIndexed { i, (count, name) ->
            println("%3d  %15s %15s".format(i+1, count, name))
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
    printExcerpts(nameExcerpts, studyData)
}