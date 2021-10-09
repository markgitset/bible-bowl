package net.markdrew.biblebowl.generate.text

import net.markdrew.chupacabra.core.DisjointRangeMap

enum class AnnotType {
    DOC, A, B
}
fun main() {
    val annotatedDoc = AnnotatedDoc(
        "This is a really, really, really long test of nonsense sentence.  I'm not sure what else to put in h",
        AnnotType.DOC
    )
    annotatedDoc.setAnnotations(AnnotType.A, DisjointRangeMap(10..19 to "a1", 30..39 to "a2"))
    annotatedDoc.setAnnotations(AnnotType.B, DisjointRangeMap(15..24 to "b1", 35..44 to "b2"))

    for (run in annotatedDoc.textRuns()) {
        println(run)
    }
    println()

    for ((excerpt, stateTransition) in annotatedDoc.stateTransitions()) {
        println("\n$excerpt\n$stateTransition")
    }
}