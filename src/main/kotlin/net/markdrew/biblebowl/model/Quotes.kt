package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.generate.normalizeWS
import net.markdrew.chupacabra.core.DisjointRangeSet
import net.markdrew.chupacabra.core.toMathString

fun identifyDelimited(text: String, startChar: Char, endPattern: String): DisjointRangeSet {
    var start: Int
    var end = 0
    val endRegex = endPattern.toRegex()
    val quotes = DisjointRangeSet()
    while (true) {
        start = text.indexOf(startChar, end)
        if (start < 0) break
        end = endRegex.find(text, start)?.range?.first
            ?: throw Exception("Unmatched $startChar; no corresponding end pattern ($endPattern) found!")
        quotes.add(start + 1 until end)
    }
    return quotes
}

fun identifySingleQuotes(text: String): DisjointRangeSet = identifyDelimited(text, '‘', """’(?!s\b)""")
fun identifyDoubleQuotes(text: String): DisjointRangeSet = identifyDelimited(text, '“', "”")
fun identifyQuotes(text: String): DisjointRangeSet = DisjointRangeSet(
    identifySingleQuotes(text).gcdAlignment(identifyDoubleQuotes(text)).map {
        trim(text, it) { c -> c in " :,‘’“”\n"}
    }.filterNot { it.isEmpty() }
)

fun main() {
    val studyData = StudyData.readData()
//    for (r in identifySingleQuotes(studyData.text)) {
//        println(r.toMathString() + """ "${studyData.text.substring(r).normalizeWS()}"""")
//    }
//    println()
    for (r in identifyQuotes(studyData.text)) {
        println(r.toMathString() + """ "${studyData.text.substring(r).normalizeWS()}"""")
    }
}