package net.markdrew.biblebowl.generate.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.toString
import net.markdrew.biblebowl.rangeLabel
import net.markdrew.chupacabra.core.intersect
import java.nio.file.Path
import java.nio.file.Paths


fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)

//    printReverseHeadings(studyData)
//
//    // write out cumulative sets (i.e., chapters 1 to N)
//    val newHeadingsPerSet = 10
//    val idealNumberOfSets = studyData.headingCharRanges.size / newHeadingsPerSet.toFloat()
//    //println("idealNumberOfSets = $idealNumberOfSets")
//    val newChaptersPerSet = (studyData.chapters.size / idealNumberOfSets).roundToInt()
//    //println("newChaptersPerSet = $newChaptersPerSet")
//    for (chunk in studyData.chapterRange.chunked(newChaptersPerSet)) {
////        println(1..chunk.last())
//        printHeadings(studyData, 1..chunk.last())
//    }
////    println()

    // write out exclusive sets (i.e., chapters N to M)
//    val nChunks = 4
//    val chunkSize = studyData.chapterRange.last / nChunks
//    for (chunk in studyData.chapterRange.chunked(chunkSize)) {
////        println(chunk.first()..chunk.last())
//        printHeadings(studyData, chunk.first()..chunk.last())
//    }

    writeCramHeadings(studyData, studyData.chapterRange(9, 12))
}

private fun makePath(studyData: StudyData, fileType: String, chapterRange: ChapterRange): Path {
    val setName = studyData.studySet.simpleName
    val actualChapterRange = chapterRange.intersect(studyData.chapterRange)
    val suffix =
        if (actualChapterRange == studyData.chapterRange) ""
        else rangeLabel("-chapter", with(actualChapterRange) { start.chapter..endInclusive.chapter })
    val dir: Path = Paths.get("$PRODUCTS_DIR/$setName/cram").also { it.toFile().mkdirs() }
    return dir.resolve("$setName-$fileType$suffix.tsv")
}

fun writeCramHeadings(studyData: StudyData, chapterRange: ChapterRange = studyData.chapterRange) {
    // NOTE: Headings may not be unique within a book! (e.g., "Jesus Heals Many" in Mat 8 and 15)
    val cramHeadingsPath = makePath(studyData, "cram-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        studyData.headings(chapterRange).groupBy {
            it.title
        }.forEach { (headingTitle, headingList) ->
            // format and write out the results
            val answerString = headingList.joinToString("<br/>OR<br/>") { heading ->
                heading.chapterRange.toString(" & ")
            }
            writer.write(headingTitle, answerString)
        }
    }
    println("Wrote data to: $cramHeadingsPath")
}

fun writeCramReverseHeadings(studyData: StudyData, chapterRange: ChapterRange = studyData.chapterRange) {
    val cramHeadingsPath = makePath(studyData, "cram-reverse-headings", chapterRange)
    CardWriter(cramHeadingsPath).use { writer ->
        studyData.chapters
            .filterValues { it in chapterRange }
            .map { (chapterRange, chapterRef) ->
                val headings: List<String> = studyData.headingCharRanges.valuesIntersectedBy(chapterRange)
                writer.write(chapterRef.format(FULL_BOOK_FORMAT), headings.joinToString("<br/>"))
            }
    }
    println("Wrote data to: $cramHeadingsPath")
}
