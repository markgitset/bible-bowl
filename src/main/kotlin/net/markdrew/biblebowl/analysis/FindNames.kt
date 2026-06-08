package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.RAW_DATA_DIR_NAME
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
//    """\p{Lu}\w+ Sea""",
//    """(?:Valley|Brook|Feast|Book|Sea) of(?: the)?(?: \p{Lu}\w+){1,2}""",
//    """Mount (of )?\p{Lu}\w+""",
    """Book (of )?\p{Lu}\w+""",
//    """(?:tribes?|clans|families|congregation|people|assembly) of \p{Lu}\w+""",
//    """(?<=(?:men|elders) of )\p{Lu}\w+""",
//    (?<!all |(?:tribes?|clans|families|leaders|congregation|people|assembly|camp|officers|men|elders) of )(?:Reuben|Simeon|Levi|Judah|Dan|Naphtali|Gad|Asher|Issachar|Zebulun|Joseph|Ephraim|Manasseh|Benjamin)
//    (?<!passed through Gilead and |midst of Ephraim and |in |all |(?:tribes?|clans|leaders|families|congregation|people|assembly|camp|officers|men|elders|boundary|cities) of )(?:Reuben|Simeon|Levi|Judah|Dan|Naphtali|Gad|Asher|Issachar|Zebulun|Joseph|Ephraim|Manasseh|Benjamin)

//    "John the Baptist",
    """(?<=“)Legion(?=,”)""", // Luke 8:30
    """Lot(’s)?""", // Luke 17:28-31
    "The Skull", // Luke 23:33
    "King of the Jews", // Luke 23:3,37,38
)

private val ENGLISH_WORDS: Dictionary by lazy { DictionaryParser.parse("not-names.txt") }

/**
 * Builds a name index for [studyData], grouped by name with the verses each name appears in
 *
 * Names are detected via two complementary passes: a per-word capitalization heuristic that filters out
 * common English words and a stop list of capitalized words that aren't names, plus a regex pass for
 * multi-word names that the per-word pass misses.
 *
 * @param exceptNames names to exclude from the result (e.g. omit "God"/"Jesus" if they're indexed elsewhere)
 */
fun buildNamesIndex(
    studyData: StudyData,
    vararg exceptNames: String
): List<WordIndexEntry> =
    nameCandidates(studyData, exceptNames).map { excerpts ->
        val key = excerpts.first().excerptText
        WordIndexEntry(key, excerpts.map { studyData.verseEnclosing(it.excerptRange) ?: throw Exception() })
    }

/**
 * Builds a name index from precomputed name [ranges] (e.g. from a shared annotation cache).
 *
 * Groups occurrences by lowercased text exactly as [buildNamesIndex] does; pass the ranges from a
 * [findNames] run with the same `exceptNames` to get identical output.
 */
fun buildNamesIndex(studyData: StudyData, ranges: List<IntRange>): List<WordIndexEntry> =
    ranges.map { studyData.excerpt(it) }
        .groupBy { it.excerptText.lowercase() }
        .map { (_, excerpts) ->
            WordIndexEntry(excerpts.first().excerptText, excerpts.map {
                studyData.verseEnclosing(it.excerptRange) ?: throw Exception()
            })
        }

/**
 * Returns every name occurrence in [studyData] as an [Excerpt], in canonical Bible order.
 *
 * Same detection logic as [buildNamesIndex] but flattened and ordered by character offset.
 */
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

/** Prints each distinct name with its occurrence count, sorted by ascending frequency. */
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
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME), Paths.get(RAW_DATA_DIR_NAME))

//    val dict = DictionaryParser.parse("words_alpha.txt")
    val dict = DictionaryParser.parse("english.txt")
    val nameExcerpts: Sequence<Excerpt> = findNames(studyData, "god", "jesus", "christ")
        .filter { it.excerptText.lowercase() in dict }

//    printNameFrequencies(nameExcerpts)
    printExcerpts(nameExcerpts, studyData)
}