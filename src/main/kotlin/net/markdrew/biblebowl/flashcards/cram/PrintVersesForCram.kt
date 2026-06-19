package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.DATA_DIR_NAME
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Path
import java.nio.file.Paths


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR_NAME))
    writeCramVerses(studyData)
}

/** Writes a Cram-style TSV of verse flashcards (verse text -> heading + reference) for [studyData]. */
fun writeCramVerses(studyData: StudyData, productsDir: Path = defaultProductsPath) {
    val setName = studyData.studySet.simpleName

    val cramFile = productsDir.resolve(setName, "cram", "$setName-cram-verses.tsv")
    CardWriter(cramFile).use {
        studyData.verses.forEach { (range, verseRef) ->
            val verseText = studyData.text.substring(range).normalizeWS()
            it.write(verseText,
                "${studyData.headingCharRanges.valueEnclosing(range)}<br/>${verseRef.format(FULL_BOOK_FORMAT)}")
        }
    }
    println("Wrote data to: $cramFile")
}