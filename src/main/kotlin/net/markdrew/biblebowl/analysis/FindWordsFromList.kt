@file:Suppress("RegExpUnnecessaryNonCapturingGroup")

package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.generate.blankOut
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.Excerpt
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Paths

fun main(args: Array<String>) {

    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    val excerpts: Sequence<Excerpt> = findListWords(studyData.text, WordList.FOODS)
    printExcerpts(excerpts, studyData)

//    val setName = studySet.simpleName

}

fun findListWords(text: String, wordList: WordList): Sequence<Excerpt> =
    wordList.regexSequence().flatMap { regex ->
        regex.findAll(text).map { Excerpt(it.value, it.range) }
    }

fun buildWordListIndex(studyData: StudyData, wordList: WordList): List<WordIndexEntry> =
    findListWords(studyData.text, wordList)
        .groupBy { it.excerptText.lowercase() }
        .map { (key, excerpts) ->
            WordIndexEntry(key, excerpts.map { studyData.verseEnclosing(it.excerptRange) ?: throw Exception() })
        }

fun printExcerpts(excerpts: Sequence<Excerpt>, studyData: StudyData) {
    excerpts.sortedBy { it.excerptText }.forEachIndexed { i, numExcerpt ->
        val (numString, numRange) = numExcerpt
        val sentRange: Excerpt? = studyData.sentenceContext(numRange)
        val sentenceString: String = sentRange?.formatRange(numRange, blankOut())?.normalizeWS().orEmpty()
        val ref: VerseRef = studyData.verseEnclosing(numRange)!!
        println("%3d  %15s %20s    %s".format(i + 1, studyData.verseRefFormat(ref), numString, sentenceString))
    }
}
