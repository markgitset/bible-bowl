@file:Suppress("RegExpUnnecessaryNonCapturingGroup")

package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.cram.Card
import net.markdrew.biblebowl.generate.cram.FillInTheBlank
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import org.intellij.lang.annotations.Language
import java.nio.file.Paths

@Language("RegExp") const val NUMERAL_PATTERN = """\d{1,3}(?:,\d\d\d)*"""
@Language("RegExp") const val BASE_FRACTIONS =
    """(?:hal(?:f|ve)|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth)s?"""
@Language("RegExp") const val ONE_AS_NUMBER =
    """(?<=-)one|(?<!the )\bone(?= and|[\- ]$BASE_FRACTIONS| mile| hour| eye| or two| flesh| talent|,| for)"""
@Language("RegExp") const val SPECIAL_NUMBERS_PATTERN =
    """\b(?:zero|$ONE_AS_NUMBER|two|three|five|ten|eleven|twelve|forty|hundreds?|thousands?|myriads?)"""
@Language("RegExp") const val NUMBER_PATTERN =
    """\b(?:(?:$SPECIAL_NUMBERS_PATTERN|(?:twen|thir|four|fif|six|seven|eight?|nine)(?:teen|ty)?)(?:fold)?)\b"""
@Language("RegExp") const val MULTI_NUMBER_PATTERN = """$NUMBER_PATTERN(?:(?:-| | of |)$NUMBER_PATTERN)*"""

@Language("RegExp") const val TENS = """(?:(?:twen|thir|for|fif|six|seven|eigh|nine)ty)"""
@Language("RegExp") const val TENS_ORDINALS = """(?:(?:twen|thir|for|fif|six|seven|eigh|nine)tieth)"""
@Language("RegExp") const val TEENS = """(?:(?:thir|four|fif|six|seven|eigh|nine)teen)"""
@Language("RegExp") const val ORDINALS =
    """(?:(?<!at the )(?:$MULTI_NUMBER_PATTERN and )?(?:$TENS_ORDINALS|(?:$TENS-first)\b|""" +
            """(?:$TENS-)?(?:(?<=and )first|second|third|(?:four|fif|six|seven|eigh|nin|ten|eleven|twelf|$TEENS)th))\b)"""
@Language("RegExp") const val FRACTIONS =
    """(?:\b(?:$MULTI_NUMBER_PATTERN(?:\s+and\s+(a|$MULTI_NUMBER_PATTERN))?[\-\s]\s*)?$BASE_FRACTIONS\b)"""

@Language("RegExp") const val COMBINED_NUMBER_PATTERN = "$FRACTIONS|$ORDINALS|$MULTI_NUMBER_PATTERN|$NUMERAL_PATTERN"
val NUMBER_REGEX = COMBINED_NUMBER_PATTERN.toRegex(RegexOption.IGNORE_CASE)

fun main(args: Array<String>) {

    println("Bible Bowl!")
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    val numberExcerpts: Sequence<Excerpt> = findNumbers(studyData.text)
    printNumberMatches(numberExcerpts, studyData)

//    val bookName = book.name.lowercase()
//    val cramNumberBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName").resolve("$bookName-cram-number-blanks.tsv")
//    CardWriter(cramNumberBlanksPath).use {
//        it.write(toCards(numberExcerpts, studyData))
//    }

}

public fun findNumbers(text: String): Sequence<Excerpt> =
    NUMBER_REGEX.findAll(text).map { Excerpt(it.value, it.range) }

public fun buildNumbersIndex(studyData: StudyData): List<WordIndexEntry> =
    NUMBER_REGEX.findAll(studyData.text).map { Excerpt(it.value, it.range) }
        .groupBy { it.excerptText.lowercase() }
        .map { (key, excerpts) ->
            WordIndexEntry(key, excerpts.map { studyData.verseEnclosing(it.excerptRange) ?: throw Exception() })
        }

private fun toCards(numberExcerpts: Sequence<Excerpt>, studyData: StudyData): List<Card> {
    return numberExcerpts.groupBy { excerpt -> Pair(
        studyData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception(),
        excerpt.excerptText.lowercase()
    ) }.map { (sentTextPair, numExcerpts) ->
        FillInTheBlank(
            sentTextPair.first,
            numExcerpts,
            studyData.verseEnclosing(sentTextPair.first.excerptRange) ?: throw Exception()
        ).toCramCard()
    }
}

private fun printNumberMatches(numberExcerpts: Sequence<Excerpt>, studyData: StudyData) {
    numberExcerpts.forEachIndexed { i, numExcerpt ->
        val (numString, numRange) = numExcerpt
        val sentRange: Excerpt? = studyData.sentenceContext(numRange)
        val sentenceString: String = sentRange?.formatRange(numRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = studyData.verseEnclosing(numRange)
        println("%3d  %7s %20s    %s".format(i, ref?.toChapterAndVerse(), numString, sentenceString))
    }
}
