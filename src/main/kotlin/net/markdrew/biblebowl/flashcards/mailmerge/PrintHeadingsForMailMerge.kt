package net.markdrew.biblebowl.flashcards.mailmerge

import net.markdrew.biblebowl.BANNER
import net.markdrew.biblebowl.defaultProductsPath
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.biblebowl.model.format
import java.nio.file.Path


fun main(args: Array<String>) {
    println(BANNER)
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)

    writeMailMergeHeadings(studyData)
}

private fun makePath(productsDir: Path, studyData: StudyData, fileType: String): Path {
    val setName = studyData.studySet.simpleName
    val dir: Path = productsDir.resolve(setName, "mail-merge").also { it.toFile().mkdirs() }
    return dir.resolve("$setName-$fileType.csv")
}

fun writeMailMergeHeadings(studyData: StudyData, productsDir: Path = defaultProductsPath) {
    // NOTE: Headings may not be unique within a book! (e.g., "Jesus Heals Many" in Mat 8 and 15)
    val mailMergeHeadingsPath = makePath(productsDir, studyData, "mail-merge-headings")
    MailMergeCardWriter(10, 2, mailMergeHeadingsPath).use { writer ->
        studyData.headings.groupBy {
            it.title
        }.forEach { (headingTitle, headingList) ->
            // format and write out the results
            val answerString = headingList.joinToString(" OR ") { heading ->
                heading.chapterRange.format(separator = "-")
            }
            writer.write(headingTitle, answerString)
        }
    }
    println("Wrote data to: $mailMergeHeadingsPath")
}

//fun writeMailMergeReverseHeadings(studyData: StudyData) {
//    val mailMergeHeadingsPath = makePath(studyData, "mail-merge-reverse-headings")
//    MailMergeCardWriter(10, mailMergeHeadingsPath).use { writer ->
//        studyData.chapters.map { (chapterRange, chapterRef) ->
//            val headings: List<String> = studyData.headingCharRanges.valuesIntersectedBy(chapterRange)
//            writer.write(chapterRef.format(FULL_BOOK_FORMAT), headings.joinToString(" "))
//        }
//    }
//    println("Wrote data to: $mailMergeHeadingsPath")
//}
