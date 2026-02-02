package net.markdrew.biblebowl.model

typealias AbsoluteVerseNum = Int
fun AbsoluteVerseNum.toVerseRef(): VerseRef = VerseRef.fromAbsoluteVerseNum(this)

fun main() {
    val bcv = VerseRef.fromAbsoluteVerseNum(66013001)
    println(bcv)
    println(bcv.absoluteVerse)
}
