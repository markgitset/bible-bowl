package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import java.nio.file.Paths

fun main(vararg args: String) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet, Paths.get(DATA_DIR))
    fun verseOfPosition(position: Int): String =
        studyData.verses.valueContaining(position)?.toChapterAndVerse().orEmpty()
    studyData.sentences.filter { studyData.verseEnclosing(it) == null }
        .forEach { println("${verseOfPosition(it.first)}..${verseOfPosition(it.last)}") }
}