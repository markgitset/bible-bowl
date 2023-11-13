package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.length

fun main(args: Array<String>) {
    val studySet: StudySet = StandardStudySet.parse(args.getOrNull(0))
    val studyData = StudyData.readData(studySet)
    println(studySet.longName)
    with(studyData) {
        println("%,8d   books".format(books.size))
        println("%,8d   chapters".format(chapters.size))
        println("%,8d   verses".format(verses.size))
        println("%,8d   sentences".format(sentences.size))
        println("%,8d   words".format(words.size))
        val nChars: Int = words.sumOf { it.length() }
        println("%,8d   characters".format(nChars))
        println("%,10.1f avg chapters/book".format(chapters.size/books.size.toFloat()))
        println("%,10.1f avg verses/chapter".format(verses.size/chapters.size.toFloat()))
        println("%,10.1f avg words/verse".format(words.size/verses.size.toFloat()))
        println("%,10.1f avg sentences/verse".format(sentences.size/verses.size.toFloat()))
        println("%,10.1f avg words/sentence".format(words.size/sentences.size.toFloat()))
        println("%,10.1f avg characters/word".format(nChars/words.size.toFloat()))
    }
}
