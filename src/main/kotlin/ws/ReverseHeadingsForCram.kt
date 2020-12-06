package net.markdrew.biblebowl.ws

import java.io.File


fun main(args: Array<String>) {

    println("Bible Bowl!")

    val chMap: Map<String, String> = File("rev-headings.tsv").bufferedReader().useLines { lines ->
        lines.map { line -> line.split('\t', limit = 2) }
                .groupBy({ it[1] }, { it[0] })
                .mapValues { (_, headingList) -> headingList.joinToString("<br/>") }
    }

    println("hey!")
    File("rev-reverse-headings.tsv").printWriter().use { out ->
        for ((chapter, headings) in chMap) {
            out.println("$chapter\t$headings")
        }
    }
}
