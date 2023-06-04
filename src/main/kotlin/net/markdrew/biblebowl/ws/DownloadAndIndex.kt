package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Paths

fun main(vararg args: String) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val indexer = EsvIndexer(studySet)
    val studyData: StudyData = indexer.indexBook(EsvClient().bookByChapters(studySet, forceDownload = false))
    studyData.writeData(Paths.get(DATA_DIR))
}
//fun main(vararg args: String) {
//    val bookName: String? = args.getOrNull(0)
//    val book: Book = bookName?.let { Book.valueOf(it.uppercase()) } ?: Book.DEFAULT
//    val client = EsvClient()
//    val indexer = BookIndexer(book)
//    val studyData: StudyData = indexer.indexBook(client.bookByChapters(book))
//    studyData.writeData(Paths.get(DATA_DIR))
//}

