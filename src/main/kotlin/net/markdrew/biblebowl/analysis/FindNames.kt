package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.BookData
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.rangeFirstLastComparator

private val STOP_NAMES = setOf("o", "i", "amen", "surely", "lord", "alpha", "omega", "almighty", "hallelujah", "praise",
    "why", "yes", "release", "sir", "father", "pay", "sovereign", "mount", "remember", "hurry", "possessor", "perhaps",
    "oh", "suppose", "knead", "meanwhile", "quick", "raiders", "whichever", "unstable")

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

fun findNames(bookData: BookData, vararg exceptNames: String): Sequence<Excerpt> =
    bookData.words.map { bookData.excerpt(it).disown() }
        .groupBy { it.excerptText.lowercase() }
        .filterKeys { it !in STOP_NAMES && it !in exceptNames}
        .filterValues { excerpts ->
            excerpts.none { excerpt ->
                excerpt.excerptText.first().let { it.isLowerCase() || it.isDigit() }
            }
        }.values.flatten().sortedWith(compareBy(rangeFirstLastComparator) { it.excerptRange }).asSequence()

fun printNameFrequencies(nameExcerpts: Sequence<Excerpt>) {
    nameExcerpts.groupBy { it.excerptText }
        .map { (name, excerpts) -> excerpts.size to name }
        .sortedBy { it.first }
        .forEachIndexed { i, (count, name) ->
            println("%3d  %15s %15s".format(i+1, count, name))
        }
}

fun printNameMatches(nameExcerpts: Sequence<Excerpt>, bookData: BookData) {
    nameExcerpts.forEachIndexed { i, numExcerpt ->
        val (nameString, nameRange) = numExcerpt
        val sentRange: Excerpt? = bookData.sentenceContext(nameRange)
        val sentenceString: String = sentRange?.formatRange(nameRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef? = bookData.verseEnclosing(nameRange)
        println("%3d  %15s %15s    %s".format(i, ref?.toChapterAndVerse(), nameString, sentenceString))
    }
}
