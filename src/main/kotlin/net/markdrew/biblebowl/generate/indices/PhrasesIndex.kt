package net.markdrew.biblebowl.generate

import net.markdrew.biblebowl.DATA_DIR
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.analysis.WithCount
import net.markdrew.biblebowl.analysis.WordIndexEntryC
import net.markdrew.biblebowl.analysis.buildNonLocalPhrasesIndex
import net.markdrew.biblebowl.analysis.buildPhrasesIndex
import net.markdrew.biblebowl.latex.toPdf
import net.markdrew.biblebowl.latex.writeDoc
import net.markdrew.biblebowl.latex.writeIndex
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import java.io.File
import java.nio.file.Paths

fun main() {
    val studyData = StudyData.readData(StandardStudySet.DEFAULT, Paths.get(DATA_DIR))
    writePhrasesIndex(studyData, maxPhraseLength = 23)
    writeNonLocalPhrasesIndex(studyData, maxPhraseLength = 23)
}

fun ((VerseRef) -> String).noBreak(): (VerseRef) -> String = { ref -> this(ref).replace(' ', '~') }
fun ((VerseRef) -> String).withCount(): (WithCount<VerseRef>) -> String = { ref ->
    this(ref.item) + (if (ref.count > 1) """(\(\times\)${ref.count})""" else "")
}

fun formatVerseRefWithCount(ref: WithCount<VerseRef>): String =
    ref.item.format(NO_BOOK_FORMAT) + (if (ref.count > 1) """(\(\times\)${ref.count})""" else "")

private fun writePhrasesIndex(studyData: StudyData, maxPhraseLength: Int = 50) {
    val indexEntries: List<WordIndexEntryC> = buildPhrasesIndex(studyData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
                phraseIndexEntry.key.joinToString(" "),
                phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
            )
        }
    val simpleName = studyData.studySet.simpleName
    val dir = File("$PRODUCTS_DIR/$simpleName/indices").also { it.mkdirs() }
    val file = dir.resolve("$simpleName-index-phrases.tex")
    val fullName = studyData.studySet.name
    file.writer().use { writer ->
        writeDoc(
            writer, "$fullName Reoccurring Phrases",
            docPreface = "The following phrases appear at least twice in $fullName."
        ) {

            writeIndex(
                writer, indexEntries.sortedBy { it.key }, "Alphabetical",
                columns = 2
            ) { formatVerseRefWithCount(it) }
            writer.appendLine("""\newpage""")
            writeIndex(
                writer, indexEntries.sortedByDescending { it.values.size }, "In Order of Decreasing Frequency",
                columns = 2
            ) { formatVerseRefWithCount(it) }
        }
    }
    file.toPdf()
}

public fun writeNonLocalPhrasesIndex(studyData: StudyData, maxPhraseLength: Int = 50) {
    val indexEntries: List<WordIndexEntryC> = buildNonLocalPhrasesIndex(studyData, maxPhraseLength)
        .map { phraseIndexEntry ->
            WordIndexEntryC(
                phraseIndexEntry.key.joinToString(" "),
                phraseIndexEntry.values.groupingBy { it }.eachCount().map { WithCount(it.key, it.value) }
            )
        }
    val simpleName = studyData.studySet.simpleName
    val dir = File("$PRODUCTS_DIR/$simpleName/indices").also { it.mkdirs() }
    val file = dir.resolve("$simpleName-index-nonlocal-phrases.tex")
    val fullName = studyData.studySet.name
    file.writer().use { writer ->
        writeDoc(
            writer, "$fullName Reoccurring Non-Local Phrases", docPreface = "The following phrases " +
                    "appear in at least two different chapters in $fullName."
        ) {

            writeIndex(
                writer, indexEntries.sortedBy { it.key }, "Alphabetical",
                columns = 2, formatValue = studyData.verseRefFormat.noBreak().withCount()
            )
            writer.appendLine("""\newpage""")
            writeIndex(
                writer, indexEntries.sortedByDescending { it.values.size }, "In Order of Decreasing Frequency",
                columns = 2, formatValue = studyData.verseRefFormat.noBreak().withCount()
            )
        }
    }
    file.toPdf(keepTexFiles = true)
}

