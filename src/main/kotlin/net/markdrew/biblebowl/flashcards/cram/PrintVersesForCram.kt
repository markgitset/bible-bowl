package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Paths


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))
    writeCramVerses(studyData)
}

fun writeCramVerses(studyData: StudyData) {
    val setName = studyData.studySet.simpleName

    val cramFile = Paths.get("$PRODUCTS_DIR/$setName/cram").resolve("$setName-cram-verses.tsv")
    CardWriter(cramFile).use {
        studyData.verses.forEach { (range, verseRef) ->
            val verseText = studyData.text.substring(range).normalizeWS()
            it.write(verseText,
                "${studyData.headingCharRanges.valueEnclosing(range)}<br/>${verseRef.format(FULL_BOOK_FORMAT)}")
        }
    }
    println("Wrote data to: $cramFile")
}