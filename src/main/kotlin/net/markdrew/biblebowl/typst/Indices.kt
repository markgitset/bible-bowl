package net.markdrew.biblebowl.typst

import net.markdrew.biblebowl.model.IndexEntry
import java.io.Writer

/**
 * Escapes the Typst structural delimiters: `\`, `[`, `]`, `#`, `*`, `_`.
 */
fun escapeTypst(s: String): String =
    s.replace("\\", "\\\\")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("#", "\\#")
        .replace("*", "\\*")
        .replace("_", "\\_")

/**
 * Writes one section of a Typst index — heading, intro paragraph, and the entries themselves.
 */
fun <K, V> writeIndex(
    writer: Writer,
    entries: Iterable<IndexEntry<K, V>>,
    indexTitle: String? = null,
    indexPreface: String? = null,
    columns: Int = 2,
    formatKey: (K) -> String = { it.toString() },
    formatValue: (V) -> String = { it.toString() },
    formatValues: (List<V>) -> String = { it.joinToString(transform = formatValue) },
) {
    if (indexTitle != null) {
        writer.appendLine("== ${escapeTypst(indexTitle)}")
        writer.appendLine()
    }
    if (indexPreface != null) {
        writer.appendLine(escapeTypst(indexPreface))
        writer.appendLine()
    }

    writer.appendLine("#columns($columns)[")
    writer.appendLine("  #set par(hanging-indent: 0.25in, justify: false)")
    entries.forEach {
        val escapedKey = escapeTypst(formatKey(it.key))
        writer.appendLine("  *${escapedKey}*, ${formatValues(it.values)} \\")
    }
    writer.appendLine("]")
    writer.appendLine()
}

/**
 * Writes a complete Typst document with a centered title and optional intro paragraph, then runs
 * [writeContent] inside the document body.
 */
fun writeDoc(
    writer: Writer,
    docTitle: String,
    docPreface: String? = null,
    allowParagraphBreaks: Boolean = true,
    writeContent: (Writer) -> Unit
) {
    writer.appendLine("""
        #set page(
          paper: "us-letter",
          margin: (x: 0.75in, y: 0.75in)
        )
        #set text(
          size: 10pt,
          font: "Libertinus Serif"
        )
        #align(center)[
          #text(size: 17pt, weight: "bold")[${escapeTypst(docTitle)}]
        ]
        #v(0.15in)
    """.trimIndent())
    if (docPreface != null) {
        writer.appendLine(escapeTypst(docPreface))
        writer.appendLine("#v(0.25in)")
    } else {
        writer.appendLine("#v(0.1in)")
    }
    writeContent(writer)
}
