package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.analysis.buildWordListIndex
import net.markdrew.biblebowl.fileForProduct
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.io.File
import java.nio.file.Paths


fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR))
    writeWordListIndex(studyData, WordList.ANGELS_DEMONS, "Angel or Demon", "Angels or Demons")
    writeWordListIndex(studyData, WordList.ANIMALS, "Animal")
    writeWordListIndex(studyData, WordList.BODY_PARTS, "Body Part")
    writeWordListIndex(studyData, WordList.COLORS, "Color")
    writeWordListIndex(studyData, WordList.FOODS, "Food")
    writeWordListIndex(studyData, WordList.MEN, "Man", "Men")
    writeWordListIndex(studyData, WordList.WOMEN, "Woman", "Women")
}

fun writeWordListList(words: Sequence<String>, file: File) {
    file.writer().use { writer ->
        for (w in words) writer.appendLine(w)
    }
    println("Wrote $file")
}

fun writeWordListIndex(
    studyData: StudyData,
    wordList: WordList,
    singularIndexType: String,
    pluralIndexType: String = "${singularIndexType}s",
) {
    val buildWordListIndex: List<WordIndexEntry> = buildWordListIndex(studyData, wordList)
    val indexEntries: List<WordIndexEntryC> = buildWordListIndex.map { wordIndexEntry ->
        WordIndexEntryC(
            wordIndexEntry.key,
            wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) ->
                WithCount(verseRef, count)
            }
        )
    }
    val indexFile = fileForProduct(studyData, "indices", "index-$pluralIndexType.tex")
    writeLatexIndex(studyData, pluralIndexType, indexEntries, singularIndexType, indexFile)
    val listFile = fileForProduct(studyData, "lists", "list-$pluralIndexType.txt")
    writeWordListList(buildWordListIndex.asSequence().map { it.key }, listFile)
}
