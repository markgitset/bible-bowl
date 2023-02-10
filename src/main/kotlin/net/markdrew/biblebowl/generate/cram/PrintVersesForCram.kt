package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Paths


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val setName = studySet.simpleName
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))

    val cramFile = Paths.get("$PRODUCTS_DIR/$setName/cram").resolve("$setName-cram-verses.tsv")
    CardWriter(cramFile).use {
        studyData.verses.forEach { (range, verseRef) ->
            val verseText = studyData.text.substring(range).normalizeWS()
            it.write(verseText,
                "${studyData.headingCharRanges.valueEnclosing(range)}<br/>${verseRef.toFullString()}")
        }
    }
    println("Wrote data to: $cramFile")
}
