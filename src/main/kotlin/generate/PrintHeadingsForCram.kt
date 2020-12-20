package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.analysis.readHeadingsIndex
import net.markdrew.biblebowl.model.BookChapterVerse
import java.io.File
import java.io.PrintWriter


// Application keys must be included with each API request in an Authorization header.
// Authorization: Token 20e9b0330b3824603c4e2696c75d51c92529babc

fun main(args: Array<String>) {
    println("Bible Bowl!")
    val bookName: String = args[0] // simple book abbrev will work (e.g., "rev" for Revelation)
    val headingsFile = File("output/$bookName", args.getOrNull(1) ?: "$bookName-index-headings.tsv")
    val headingsMap: Map<BookChapterVerse, String> = readHeadingsIndex(headingsFile)

    val cramHeadingsFile = File("output/$bookName", "$bookName-cram-headings.tsv")
    printHeadings(cramHeadingsFile.printWriter(), headingsMap)
    //printHeadings(PrintWriter(System.out), headingIndex)
    println("Wrote data to: $cramHeadingsFile")

    val cramReverseHeadingsFile = File("output/$bookName", "$bookName-cram-reverse-headings.tsv")
    printReverseHeadings(cramReverseHeadingsFile.printWriter(), headingsMap)
    //printHeadings(PrintWriter(System.out), headingIndex)
    println("Wrote data to: $cramReverseHeadingsFile")
}

private fun printHeadings(printWriter: PrintWriter, headingIndex: Map<BookChapterVerse, String>) {
    printWriter.use { pw ->
        headingIndex.toSortedMap().toList()
            .groupBy({ (_, heading) -> heading }, { (bcv, _) -> bcv.chapter }) // headings to chapters
            .mapValues { (_, chapterList) -> chapterList.distinct() } // headings to distinct chapters list
            .forEach { (heading, chapters) -> pw.println("$heading\t${chapters.joinToString(" & ")}") }
    }
}

private fun printReverseHeadings(printWriter: PrintWriter, headingIndex: Map<BookChapterVerse, String>) {
    printWriter.use { pw ->
        headingIndex.toSortedMap().toList().map { (ref, heading) ->
            ref.chapter to heading
        }.distinct()
            .groupBy({ (chapter, _) -> chapter }, { (_, heading) -> heading }) // headings to chapters
            .forEach { (chapter, headings) -> pw.println("$chapter\t${headings.joinToString("<br/>")}") }
    }
}
