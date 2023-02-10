package net.markdrew.biblebowl.ws

private data class Footnote(val noteNum: Int, val verseRef: String, val noteText: String) {
    companion object {
        fun parse(line: String): Footnote {
            val (noteNum, verseRef, noteText) =
                """\s*\((\d+)\)\s+(\d+:\d+)\s+(.*?)\s*""".toRegex().matchEntire(line)?.destructured
                    ?: throw Exception("Unable to parse footnote: $line")
            return Footnote(noteNum.toInt(), verseRef, noteText)
        }
    }
}

