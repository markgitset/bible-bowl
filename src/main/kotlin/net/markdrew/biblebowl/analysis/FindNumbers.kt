package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.generate.cram.Card
import net.markdrew.biblebowl.generate.cram.CardWriter
import net.markdrew.biblebowl.generate.cram.FillInTheBlank
import net.markdrew.biblebowl.model.*
import net.markdrew.biblebowl.model.BookData
import java.nio.file.Paths

val ONE_AS_NUMBER = """(?<![Tt]he |a |[Nn]o |holy |true |each )\bone\b(?!\.| like| of| another| who| is| mind| seated)"""
val SPECIAL_NUMBERS_PATTERN = """\b(?:zero|$ONE_AS_NUMBER|two|three|five|ten|eleven|twelve|forty|hundreds?|thousands?|myriads?)\b"""
val NUMBER_PATTERN = """\b(?:$SPECIAL_NUMBERS_PATTERN|(?:twen|thir|four|fif|six|seven|eight?|nine)(?:teen|ty)?)\b"""
val MULTI_NUMBER_PATTERN = """$NUMBER_PATTERN(?:(?:\-| | of |)$NUMBER_PATTERN)?"""
val NUMERAL_PATTERN = """\d{1,3}(?:,\d\d\d)*"""

val BASE_FRACTIONS = """(?:half|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth)s?"""
val FRACTIONS = """(?:$MULTI_NUMBER_PATTERN\s+and\s+(a|$MULTI_NUMBER_PATTERN)\s+$BASE_FRACTIONS|""" +
        """$BASE_FRACTIONS(?=\s+(?:of|an?)\b))"""

val COMBINED_NUMBER_PATTERN = "$MULTI_NUMBER_PATTERN|$NUMERAL_PATTERN|$FRACTIONS"
val NUMBER_REGEX = COMBINED_NUMBER_PATTERN.toRegex()

fun main(args: Array<String>) {
    val NUMBER_WORDS = setOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
    "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty")

    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(book, Paths.get(DATA_DIR))


    val numberExcerpts: Sequence<Excerpt> = findNumbers(bookData.text)
    printNumberMatches(findNumbers(bookData.text), bookData)

    val cramNumberBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName").resolve("$bookName-cram-number-blanks.tsv")
    CardWriter(cramNumberBlanksPath).use {
        it.write(toCards(numberExcerpts, bookData))
    }

}

public fun findNumbers(text: String): Sequence<Excerpt> =
    NUMBER_REGEX.findAll(text).map { Excerpt(it.value, it.range) }

public fun buildNumbersIndex(bookData: BookData): List<WordIndexEntry> =
    NUMBER_REGEX.findAll(bookData.text).map { Excerpt(it.value, it.range) }
        .groupBy { it.excerptText.lowercase() }
        .map { (key, excerpts) ->
            WordIndexEntry(key, excerpts.map { bookData.verseEnclosing(it.excerptRange) ?: throw Exception() })
        }

private fun toCards(numberExcerpts: Sequence<Excerpt>, bookData: BookData): List<Card> {
    return numberExcerpts.groupBy { excerpt -> Pair(
        bookData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception(),
        excerpt.excerptText.lowercase()
    ) }.map { (sentTextPair, numExcerpts) ->
        FillInTheBlank(
            sentTextPair.first,
            numExcerpts,
            bookData.verseEnclosing(sentTextPair.first.excerptRange) ?: throw Exception()
        ).toCramCard()
    }
}

private fun printNumberMatches(numberExcerpts: Sequence<Excerpt>, bookData: BookData) {
    numberExcerpts.forEachIndexed { i, numExcerpt ->
        val (numString, numRange) = numExcerpt
        val sentRange: Excerpt? = bookData.sentenceContext(numRange)
        val sentenceString: String = sentRange?.formatRange(numRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = bookData.verseEnclosing(numRange)
        println("%3d  %7s %20s    %s".format(i, ref?.toChapterAndVerse(), numString, sentenceString))
    }
}
