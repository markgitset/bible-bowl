package net.markdrew.biblebowl.analysis

import java.io.Reader
import java.nio.file.Path
import kotlin.io.path.bufferedReader

typealias Dictionary = Set<String>

object DictionaryParser {
    private val classLoader: ClassLoader = DictionaryParser::class.java.classLoader
    fun parse(words: Sequence<String>): Dictionary = words.toHashSet()
    fun parse(reader: Reader): Dictionary = reader.useLines { parse(it) }
    fun parse(path: Path): Dictionary = parse(path.bufferedReader())
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