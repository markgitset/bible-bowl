package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.cram.Card
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.cram.FillInTheBlank
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
    val book: Book = Book.parse(args.getOrNull(0), Book.REV)
    val bookName = book.name.toLowerCase()
    val bookData = BookData.readData(Paths.get("output"), book)


    val numberExcerpts: Sequence<Excerpt> = findNumbers(bookData.text)
    printMatches(findNumbers(bookData.text), bookData)

    val cramNumberBlanksPath = Paths.get("output/$bookName").resolve("$bookName-cram-number-blanks.tsv")
    CardWriter(cramNumberBlanksPath).use {
        it.write(toCards(numberExcerpts, bookData))
    }

}

public fun findNumbers(text: String): Sequence<Excerpt> =
    NUMBER_REGEX.findAll(text).map { Excerpt(it.value, it.range) }

private fun toCards(numberExcerpts: Sequence<Excerpt>, bookData: BookData): List<Card> {
    return numberExcerpts.groupBy { excerpt -> Pair(
        bookData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception(),
        excerpt.excerptText.toLowerCase()
    ) }.map { (sentTextPair, numExcerpts) ->
        FillInTheBlank(
            sentTextPair.first,
            numExcerpts,
            bookData.verseEnclosing(sentTextPair.first.excerptRange) ?: throw Exception()
        ).toCramCard()
    }
}

private fun printMatches(numberExcerpts: Sequence<Excerpt>, bookData: BookData) {
    numberExcerpts.forEachIndexed { i, numExcerpt ->
        val (numString, numRange) = numExcerpt
        val sentRange: Excerpt? = bookData.sentenceContext(numRange)
        val sentenceString: String = sentRange?.formatRange(numRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = bookData.verseEnclosing(numRange)
        println("%3d  %7s %20s    %s".format(i, ref?.toChapterAndVerse(), numString, sentenceString))
    }
}
