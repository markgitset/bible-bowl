package net.markdrew.biblebowl.flashcards.cram

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.PRODUCTS_DIR_NAME
import net.markdrew.biblebowl.model.ChapterRange
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.format
import net.markdrew.biblebowl.rangeLabel
import net.markdrew.chupacabra.core.intersect
import java.nio.file.Files
import java.nio.file.Path


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

    writeCramHeadings(studyData)//, studyData.chapterRange(1, 9))
}

private fun makePath(
    studyData: StudyData,
    fileType: String,
    chapterRange: ChapterRange,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME)
): Path {
    val setName = studyData.studySet.simpleName
    val actualChapterRange = chapterRange.intersect(studyData.chapterRange)
    val suffix =
        if (actualChapterRange == studyData.chapterRange) ""
        else rangeLabel("-chapter", with(actualChapterRange) { start.chapter..endInclusive.chapter })
    val dir: Path = productsDir.resolve(setName, "cram").also { Files.createDirectories(it) }
    return dir.resolve("$setName-$fileType$suffix.tsv")
}

/**
 * Writes a Cram-style TSV of heading flashcards (heading title -> chapter range(s)) for [studyData],
 * optionally restricted to [chapterRange]
 *
 * Headings that share a title across chapters collapse to one card with the chapter list joined by "OR".
 */
fun writeCramHeadings(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    chapterRange: ChapterRange = studyData.chapterRange
) {
    // NOTE: Headings may not be unique within a book! (e.g., "Jesus Heals Many" in Mat 8 and 15)
    val cramHeadingsPath = makePath(studyData, "cram-headings", chapterRange, productsDir)
    CardWriter(cramHeadingsPath).use { writer ->
        studyData.headings(chapterRange).groupBy {
            it.title
        }.forEach { (headingTitle, headingList) ->
            // format and write out the results
            val answerString = headingList.joinToString("<br/>OR<br/>") { heading ->
                heading.chapterRange.format(separator = " & ")
            }
            writer.write(headingTitle, answerString)
        }
    }
    println("Wrote data to: $cramHeadingsPath")
}

/**
 * Writes a Cram-style TSV of reverse heading flashcards (chapter -> heading list) for [studyData],
 * optionally restricted to [chapterRange]
 */
fun writeCramReverseHeadings(
    studyData: StudyData,
    productsDir: Path = Path.of(PRODUCTS_DIR_NAME),
    chapterRange: ChapterRange = studyData.chapterRange,
) {
    val cramHeadingsPath = makePath(studyData, "cram-reverse-headings", chapterRange, productsDir)
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
