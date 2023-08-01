package net.markdrew.biblebowl.latex

import java.io.Writer

data class IndexEntry<K, V>(val key: K, val values: List<V>)

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

    if (indexTitle != null) writer.appendLine("""\subsection*{$indexTitle}""")
    if (indexPreface != null) writer.appendLine(indexPreface).appendLine()
    writer.appendLine("""
        \begin{multicols}{$columns}
        \raggedright
        \begin{hangparas}{.25in}{1}
        
    """.trimIndent())
    entries.forEach {
        writer.appendLine("""\par\textbf{${formatKey(it.key)}},  ${formatValues(it.values)}""")
    }
    writer.appendLine("""
        \end{hangparas}
        \raggedright
        \end{multicols}
    """.trimIndent())
}

fun writeDoc(
    writer: Writer,
    docTitle: String,
    docPreface: String? = null,
    allowParagraphBreaks: Boolean = true,
    writeContent: (Writer) -> Unit
) {
    writer.appendLine("""
        \documentclass[10pt, letter paper]{article} 
        
        \usepackage[utf8]{inputenc}
        \usepackage[margin=0.75in]{geometry}
        \usepackage{multicol}
        \usepackage{multirow}
        \usepackage{hanging}
        \usepackage{array}
        \renewcommand{\arraystretch}{1}

        \newcolumntype{L}[1]{>{\raggedright\let\newline\\\arraybackslash\hspace{0pt}}m{#1}}
    """.trimIndent())
    if (!allowParagraphBreaks) writer.appendLine("""
        % the next two lines prevent breaks within paragraphs
        \widowpenalties 1 10000
        \raggedbottom

    """.trimIndent())
    writer.appendLine("""
        \begin{document}

        \begin{center}\section*{$docTitle}\end{center}
    """.trimIndent())
    if (docPreface != null) writer.appendLine("""\vspace{.15in}""").appendLine(docPreface)
    writer.appendLine("""\vspace{.25in}""")
    writeContent(writer)
    writer.appendLine("""\end{document}""")
}
