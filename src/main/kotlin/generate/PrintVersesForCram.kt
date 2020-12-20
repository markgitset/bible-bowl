package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.readHeadingsIndex
import net.markdrew.biblebowl.analysis.readVersesIndex
import net.markdrew.biblebowl.model.BookChapterVerse
import net.markdrew.biblebowl.model.ReferencedVerse
import java.io.File


fun main(args: Array<String>) {

    println("Bible Bowl!")
    val bookName: String = args[0]
    val verses: List<ReferencedVerse> = readVersesIndex(bookName)
    val headings: Map<BookChapterVerse, String> = readHeadingsIndex(bookName)

    val cramFile = File("output/$bookName", args.getOrNull(1) ?: "$bookName-cram-verses.tsv")
    cramFile.printWriter().use {
        verses.forEach { (ref, verse) -> it.println("$verse\t${headings[ref]}<br/>${ref.toFullString()}") }
    }
    println("Wrote data to: $cramFile")
}
