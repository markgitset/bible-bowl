package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.cram.Card
import net.markdrew.biblebowl.cram.CardWriter
import net.markdrew.biblebowl.cram.FillInTheBlank
import net.markdrew.biblebowl.model.*
import net.markdrew.biblebowl.model.BookData
import net.markdrew.chupacabra.core.rangeFirstLastComparator
import java.nio.file.Paths

fun main(args: Array<String>) {

    println("Bible Bowl!")
    val book: Book = Book.parse(args.getOrNull(0), Book.DEFAULT)
    val bookName = book.name.lowercase()
    val bookData = BookData.readData(Paths.get(DATA_DIR), book)


    val nameExcerpts: Sequence<Excerpt> = findNames(bookData, "god", "jesus", "christ")
    printFrequencies(nameExcerpts)
    printMatches(nameExcerpts, bookData)

    val cramNameBlanksPath = Paths.get("$PRODUCTS_DIR/$bookName").resolve("$bookName-cram-name-blanks.tsv")
    CardWriter(cramNameBlanksPath).use {
        it.write(toCards(nameExcerpts, bookData))
    }

}

private val STOP_NAMES = setOf("o", "i", "amen", "surely", "lord", "alpha", "omega", "almighty", "hallelujah", "praise",
    "why", "yes", "release", "sir", "father", "pay", "sovereign", "mount", "remember")

fun buildNamesIndex(bookData: BookData,
                    frequencyRange: IntRange? = null,
                    vararg exceptNames: String): List<WordIndexEntry> =
    bookData.words.map { bookData.excerpt(it).disown() }
        .groupBy { it.excerptText.lowercase() }
        .filterKeys { it !in STOP_NAMES && it !in exceptNames}
        .filterValues { excerpts ->
            excerpts.none { excerpt ->
                excerpt.excerptText.first().let { it.isLowerCase() || it.isDigit() }
            }
        }.values.map { excerpts ->
            val key = excerpts.first().excerptText
            WordIndexEntry(key, excerpts.map { bookData.verseEnclosing(it.excerptRange) ?: throw Exception() })
        }

public fun findNames(bookData: BookData, vararg exceptNames: String): Sequence<Excerpt> {
    return bookData.words.map { bookData.excerpt(it).disown() }
        .groupBy { it.excerptText.lowercase() }
        .filterKeys { it !in STOP_NAMES && it !in exceptNames}
        .filterValues { excerpts ->
            excerpts.none { excerpt ->
                excerpt.excerptText.first().let { it.isLowerCase() || it.isDigit() }
            }
        }.values.flatten().sortedWith(compareBy(rangeFirstLastComparator) { it.excerptRange }).asSequence()
}

private fun toCards(nameExcerpts: Sequence<Excerpt>, bookData: BookData): List<Card> {
    return nameExcerpts.groupBy { excerpt ->
        bookData.singleVerseSentenceContext(excerpt.excerptRange) ?: throw Exception()
    }.map { (sentRange, nameExcerpts) ->
        FillInTheBlank(
            sentRange,
            nameExcerpts,
            bookData.verseEnclosing(sentRange.excerptRange) ?: throw Exception()
        ).toCramCard()
    }
}

private fun printFrequencies(nameExcerpts: Sequence<Excerpt>) {
    nameExcerpts.groupBy { it.excerptText }
        .map { (name, excerpts) -> excerpts.size to name }
        .sortedBy { it.first }
        .forEachIndexed { i, (count, name) ->
            println("%3d  %15s %15s".format(i+1, count, name))
        }
}

private fun printMatches(nameExcerpts: Sequence<Excerpt>, bookData: BookData) {
    nameExcerpts.forEachIndexed { i, numExcerpt ->
        val (nameString, nameRange) = numExcerpt
        val sentRange: Excerpt? = bookData.sentenceContext(nameRange)
        val sentenceString: String = sentRange?.formatRange(nameRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = bookData.verseEnclosing(nameRange)
        println("%3d  %15s %15s    %s".format(i, ref?.toChapterAndVerse(), nameString, sentenceString))
    }
}
