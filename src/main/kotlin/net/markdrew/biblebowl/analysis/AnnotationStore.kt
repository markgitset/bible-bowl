package net.markdrew.biblebowl.analysis

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.io.path.bufferedReader
import kotlin.io.path.exists

private val log: KLogger = KotlinLogging.logger {}

/**
 * Computes each [AnnotationSource] layer at most once per run (in-memory memo) and, when a [cacheDir] is
 * given, persists it to a TSV sidecar so future runs can skip the regex/NLP passes entirely.
 *
 * A sidecar's first line is a header `# text-hash=<h> def=<defDigest> code=<codeVersion>`; the layer is
 * recomputed (and the file rewritten) whenever the file is missing, the study text changed (different
 * `text-hash`), the source definition changed (different `def`), or this app's code changed (different
 * `code`). The code fingerprint means edits to detector logic or config (e.g. [CategoryPrecedence]) that
 * the per-source `def` can't see still invalidate the cache — so there's no need to force a recompute.
 *
 * The same store instance should be shared across every generator in a run so they reuse each other's
 * layers. Returned maps must be treated as read-only — they are the live cached instances.
 *
 * @param cacheDir per-study-set directory (`<dataDir>/<simpleName>`); null disables disk persistence
 */
class AnnotationStore(
    private val studyData: StudyData,
    private val cacheDir: Path?,
) {
    private val memo: MutableMap<String, DisjointRangeMap<*>> = HashMap()

    /** Returns the layer for [source], from the in-memory memo, the disk sidecar, or a fresh computation. */
    fun <V : Any> get(source: AnnotationSource<V>): DisjointRangeMap<V> {
        val key = "${source.name}@${source.defDigest}"
        @Suppress("UNCHECKED_CAST")
        memo[key]?.let { return it as DisjointRangeMap<V> }
        return loadOrCompute(source).also { memo[key] = it }
    }

    /** Convenience: the layer for [source] as a [DisjointRangeSet] of its ranges. */
    fun ranges(source: AnnotationSource<*>): DisjointRangeSet = DisjointRangeSet(get(source).keys)

    /** Convenience: the layer for [source] as a list of ranges, in ascending offset order. */
    fun rangeList(source: AnnotationSource<*>): List<IntRange> = get(source).keys.toList()

    private fun <V : Any> loadOrCompute(source: AnnotationSource<V>): DisjointRangeMap<V> {
        val file: Path? = cacheDir?.resolve(fileName(source))
        if (file != null && file.exists()) {
            readSidecar(file, source)?.let {
                log.info { "Loaded ${source.name} annotations from cache: $file" }
                return it
            }
        }
        val computed: DisjointRangeMap<V> = source.compute(studyData)
        if (file != null) writeSidecar(file, source, computed)
        return computed
    }

    private fun fileName(source: AnnotationSource<*>): String {
        val suffix = if (source.defDigest.isEmpty()) "" else "-${source.defDigest}"
        return "${studyData.studySet.simpleName}-${source.name}$suffix.tsv"
    }

    private fun expectedHeader(source: AnnotationSource<*>): String =
        "# text-hash=${studyData.text.hashCode()} def=${source.defDigest} code=$codeVersion"

    /** Returns the cached layer, or null if the header doesn't match (caller should recompute). */
    private fun <V : Any> readSidecar(file: Path, source: AnnotationSource<V>): DisjointRangeMap<V>? =
        file.bufferedReader().use { reader ->
            val header: String? = reader.readLine()
            if (header != expectedHeader(source)) return null
            val map = DisjointRangeMap<V>()
            reader.forEachLine { line ->
                if (line.isNotEmpty()) {
                    val parts = line.split('\t')
                    val range = parts[0].toInt()..parts[1].toInt()
                    map[range] = source.decodeValue(parts.getOrElse(2) { "" })
                }
            }
            map
        }

    private fun <V : Any> writeSidecar(file: Path, source: AnnotationSource<V>, map: DisjointRangeMap<V>) {
        Files.createDirectories(file.parent)
        file.toFile().printWriter().use { pw ->
            pw.println(expectedHeader(source))
            map.forEach { (range, value) -> pw.println("${range.first}\t${range.last}\t${source.encodeValue(value)}") }
        }
        log.info { "Cached ${source.name} annotations to: $file" }
    }

    companion object {
        /**
         * Fingerprint of this app's own compiled classes, computed once per process. Any change to
         * detector logic or annotation config invalidates every cached layer, so no manual recompute is
         * needed. Third-party dependencies (separate code sources) are not fingerprinted.
         */
        private val codeVersion: String by lazy { fingerprintOwnCode() }

        private fun fingerprintOwnCode(): String {
            val location = AnnotationStore::class.java.protectionDomain?.codeSource?.location ?: return "nocode"
            val path = Paths.get(location.toURI())
            val md = MessageDigest.getInstance("SHA-256")
            if (Files.isDirectory(path)) { // `./gradlew run` runs from build/classes/kotlin/main
                Files.walk(path).use { stream ->
                    stream.filter { it.toString().endsWith(".class") }.sorted().forEach {
                        md.update(it.toString().toByteArray()); md.update(Files.readAllBytes(it))
                    }
                }
            } else { // packaged jar
                md.update(Files.readAllBytes(path))
            }
            return md.digest().take(6).joinToString("") { "%02x".format(it) }
        }
    }
}
