package net.markdrew.biblebowl.generate.indices

import net.markdrew.biblebowl.analysis.AnnotationStore
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

/** Writes a plain-text list of [words] (one per line) to [file]. */
fun writeWordListList(words: Sequence<String>, file: Path) {
    Files.newBufferedWriter(file).use { writer ->
        for (w in words) writer.appendLine(w)
    }
    println("Wrote $file")
}

/**
 * Writes both the per-category index PDF and the simple word list for [wordList] in [studyData]
 *
 * The index PDF goes to `<simpleName>/indices/`; the word list (one word per line) goes to
 * `<simpleName>/lists/`.
 *
 * @param singularIndexType label for one entry (e.g. "Animal")
 * @param pluralIndexType label for many (defaults to [singularIndexType] + "s"; supply manually for
 *   irregular plurals like "Men", "Women")
 */
fun writeWordListIndex(
    productsDir: Path,
    studyData: StudyData,
    wordList: WordList,
    singularIndexType: String,
    pluralIndexType: String = "${singularIndexType}s",
    store: AnnotationStore = AnnotationStore(studyData, cacheDir = null),
) {
    // Pull this category's occurrences from the one unified resolution (honors cross-list disambiguation
    // and per-occurrence overrides), so the index agrees with the highlights.
    val categoryMap = store.categoryResolution(studyData.studySet)
    val ranges: List<IntRange> = categoryMap.entries.filter { it.value == wordList.token }.map { it.key }
    val wordListEntries: List<WordIndexEntry> = buildWordListIndex(studyData, ranges)
    val indexEntries: List<WordIndexEntryC> = wordListEntries.map { wordIndexEntry ->
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
    writeWordListList(wordListEntries.asSequence().map { it.key }, listFile)
}
