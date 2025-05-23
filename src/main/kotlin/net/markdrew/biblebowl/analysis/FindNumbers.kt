@file:Suppress("RegExpUnnecessaryNonCapturingGroup")

package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.flashcards.Card
import net.markdrew.biblebowl.flashcards.cram.CardWriter
import net.markdrew.biblebowl.flashcards.cram.FillInTheBlank
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import org.intellij.lang.annotations.Language
import java.nio.file.Paths

@Language("RegExp") const val NUMERAL_PATTERN = """\d{1,3}(?:,\d\d\d)*"""
@Language("RegExp") const val BASE_FRACTIONS =
    """(?:hal(?:f|ve)|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth)s?"""
@Language("RegExp") const val ONE_AS_NUMBER =
    """(?<=-|just )one|(?<!(the|no) )\bone(?= and|[\- ]$BASE_FRACTIONS| coin| sinner| mile| hour| staff| (drawn )?out of| of every| have chased| eye| or two| flesh| talent| for)"""
@Language("RegExp") const val SPECIAL_NUMBERS_PATTERN =
    """\b(?:zero|$ONE_AS_NUMBER|twos?|threes?|fives?|tens?|elevens?|twelves?|fort(y|ies)|hundreds?|thousands?|myriads?)"""
@Language("RegExp") const val NUMBER_PATTERN =
    """\b(?:(?:$SPECIAL_NUMBERS_PATTERN|(?:twen|thir|fours?|fif|six(es)?|sevens?|eigh(ts?)?|nines?)(?:teens?|ty|ties)?)(?:fold)?)\b"""
@Language("RegExp") const val MULTI_NUMBER_PATTERN = """$NUMBER_PATTERN(?:(?:-| | of |)$NUMBER_PATTERN)*"""

@Language("RegExp") const val TENS = """(?:(?:twen|thir|for|fif|six|seven|eigh|nine)ty)"""
@Language("RegExp") const val TENS_ORDINALS = """(?:(?:twen|thir|for|fif|six|seven|eigh|nine)tieth)"""
@Language("RegExp") const val TEENS = """(?:(?:thir|four|fif|six|seven|eigh|nine)teen)"""
@Language("RegExp") const val FIRST_AS_ORDINAL = """(?:first(?=\s+(day|month))|(?<=((?<!\bat )[Tt]he|who) )first\b)"""
@Language("RegExp") const val ORDINALS =
    """(?:$FIRST_AS_ORDINAL|(?<!at the )(?:$MULTI_NUMBER_PATTERN and )?(?:$TENS_ORDINALS|(?:$TENS-first)\b|""" +
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
    printExcerpts(numberExcerpts, studyData)

    val setName = studySet.simpleName
    val cramNumberBlanksPath = Paths.get("$PRODUCTS_DIR/$setName/cram").resolve("$setName-cram-number-blanks.tsv")
    CardWriter(cramNumberBlanksPath).use {
        it.write(toCards(numberExcerpts, studyData))
    }

}

fun findNumbers(text: String): Sequence<Excerpt> =
    NUMBER_REGEX.findAll(text).map { Excerpt(it.value, it.range) }

fun buildNumbersIndex(studyData: StudyData): List<WordIndexEntry> =
    findNumbers(studyData.text)
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
