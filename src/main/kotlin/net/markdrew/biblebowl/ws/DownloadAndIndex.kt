package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Paths

fun main(vararg args: String) {
    val setName: String? = args.getOrNull(0)
    val studySet: StudySet = setName?.let { StandardStudySet.valueOf(it.uppercase()).set } ?: StandardStudySet.DEFAULT
    val client = EsvClient()
    val indexer = EsvIndexer(studySet)
    val studyData: StudyData = indexer.indexBook(client.bookByChapters(studySet))
    studyData.writeData(Paths.get(DATA_DIR))
}
//fun main(vararg args: String) {
//    val bookName: String? = args.getOrNull(0)
//    val book: Book = bookName?.let { Book.valueOf(it.uppercase()) } ?: Book.DEFAULT
//    val client = EsvClient()
//    val indexer = BookIndexer(book)
//    val bookData: BookData = indexer.indexBook(client.bookByChapters(book))
//    bookData.writeData(Paths.get(DATA_DIR))
//}

