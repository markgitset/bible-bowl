package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntry
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.analysis.buildWordListIndex
import net.markdrew.biblebowl.defaultDataPath
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.fileForProduct
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import java.nio.file.Files
import java.nio.file.Path


fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, defaultDataPath)
    val productsDir = defaultProductsPath
    writeWordListIndex(productsDir, studyData, WordList.ANGELS_DEMONS, "Angel or Demon", "Angels or Demons")
    writeWordListIndex(productsDir, studyData, WordList.ANIMALS, "Animal")
    writeWordListIndex(productsDir, studyData, WordList.BODY_PARTS, "Body Part")
    writeWordListIndex(productsDir, studyData, WordList.COLORS, "Color")
    writeWordListIndex(productsDir, studyData, WordList.FOODS, "Food")
    writeWordListIndex(productsDir, studyData, WordList.MEN, "Man", "Men")
    writeWordListIndex(productsDir, studyData, WordList.WOMEN, "Woman", "Women")
}

fun writeWordListList(words: Sequence<String>, file: Path) {
    Files.newBufferedWriter(file).use { writer ->
        for (w in words) writer.appendLine(w)
    }
    println("Wrote $file")
}

fun writeWordListIndex(
    productsDir: Path,
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
    val indexFile = fileForProduct(studyData, "indices", "index-$pluralIndexType.tex", productsDir)
    writeLatexIndex(studyData, pluralIndexType, indexEntries, singularIndexType, indexFile)
    val listFile = fileForProduct(studyData, "lists", "list-$pluralIndexType.txt", productsDir)
    writeWordListList(buildWordListIndex.asSequence().map { it.key }, listFile)
}
