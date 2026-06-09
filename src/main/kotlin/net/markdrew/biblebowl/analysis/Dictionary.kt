package net.markdrew.biblebowl.analysis

import java.io.Reader
import java.nio.file.Path
import kotlin.io.path.bufferedReader

/** A set of words — most often a vocabulary list loaded from a resource or file */
typealias Dictionary = Set<String>

/** Parses [Dictionary] instances from various line-oriented sources */
object DictionaryParser {
    private val classLoader: ClassLoader = DictionaryParser::class.java.classLoader

    /**
     * Builds a [Dictionary] from a sequence of lines, one word per line. Blank lines and `#` comment
     * lines are skipped, and surrounding whitespace is trimmed.
     */
    fun parse(words: Sequence<String>): Dictionary =
        words.map { it.trim() }.filter { it.isNotEmpty() && !it.startsWith("#") }.toHashSet()

    /** Reads one word per line from [reader] into a [Dictionary]; closes the reader when done. */
    fun parse(reader: Reader): Dictionary = reader.useLines { parse(it) }

    /** Reads one word per line from the file at [path] into a [Dictionary]. */
    fun parse(path: Path): Dictionary = parse(path.bufferedReader())

    /**
     * Reads one word per line from the classpath [resource] into a [Dictionary].
     *
     * @throws Exception if the resource can't be found
     */
    fun parse(resource: String): Dictionary = parse(
        classLoader.getResourceAsStream(resource)?.reader()
            ?: throw Exception("Could not find resource '$resource'")
    )

    @JvmStatic
    fun main(args: Array<String>) {
        parse("english3.txt").take(10).forEach { println(it) }
        println()
        parse("words_alpha.txt").take(10).forEach { println(it) }
    }
}
