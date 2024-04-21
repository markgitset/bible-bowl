package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.analysis.buildWordListIndex
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
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

fun writeWordListIndex(
    studyData: StudyData,
    wordList: WordList,
    singularIndexType: String,
    pluralIndexType: String = "${singularIndexType}s",
) {
    val indexEntries: List<WordIndexEntryC> = buildWordListIndex(studyData, wordList).map { wordIndexEntry ->
        WordIndexEntryC(
            wordIndexEntry.key,
            wordIndexEntry.values.groupingBy { it }.eachCount().map { (verseRef, count) ->
                WithCount(verseRef, count)
            }
        )
    }
    writeLatexIndex(studyData, indexEntries, singularIndexType, pluralIndexType)
}
