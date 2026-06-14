package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.StudySet
import net.markdrew.chupacabra.core.DisjointRangeMap
import net.markdrew.chupacabra.core.DisjointRangeSet
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

/** An [AnnotationSource] that counts how often it recomputes, so tests can detect cache hits. */
private class CountingSource(
    override val name: String,
    override val defDigest: String,
    private val ranges: List<IntRange>,
) : AnnotationSource<String> {
    var computeCount: Int = 0
    override fun compute(studyData: StudyData): DisjointRangeMap<String> {
        computeCount++
        return DisjointRangeMap<String>().apply { ranges.forEach { put(it, "val${it.first}") } }
    }
    override fun encodeValue(value: String): String = value
    override fun decodeValue(cell: String): String = cell
}

private fun studyDataOf(text: String, simpleName: String = "test"): StudyData =
    StudyData(
        StudySet("Test", simpleName, emptyList()), text,
        DisjointRangeMap(), DisjointRangeMap(), DisjointRangeMap(), DisjointRangeMap(),
        sortedMapOf(), DisjointRangeSet(),
    )

class AnnotationStoreTest : StringSpec({

    fun tmpDir(): Path = Files.createTempDirectory("annotation-store-")

    "computes once, persists to disk, and re-reads without recomputing" {
        val dir = tmpDir()
        val sd = studyDataOf("the quick brown fox")
        val ranges = listOf(0..2, 5..9)

        val srcA = CountingSource("names", "abc", ranges)
        val resultA = AnnotationStore(sd, dir).get(srcA)
        srcA.computeCount shouldBe 1
        resultA.keys.toList() shouldBe ranges
        resultA[5..9] shouldBe "val5"
        dir.listDirectoryEntries().filter { it.extension == "tsv" } shouldHaveSize 1

        // A fresh source + store over the same dir must read the sidecar, not recompute.
        val srcB = CountingSource("names", "abc", ranges)
        val resultB = AnnotationStore(sd, dir).get(srcB)
        srcB.computeCount shouldBe 0
        resultB.entries.toList() shouldBe resultA.entries.toList()
    }

    "memoizes within a single store instance" {
        val store = AnnotationStore(studyDataOf("hello world"), tmpDir())
        val src = CountingSource("numbers", "", listOf(0..4))
        store.get(src)
        store.get(src)
        src.computeCount shouldBe 1
    }

    "a changed study text invalidates the sidecar (text-hash mismatch)" {
        val dir = tmpDir()
        AnnotationStore(studyDataOf("first text"), dir).get(CountingSource("names", "abc", listOf(0..3)))

        // Same study set (same filename) but different text -> header text-hash differs -> recompute.
        val src = CountingSource("names", "abc", listOf(0..3))
        AnnotationStore(studyDataOf("second text"), dir).get(src)
        src.computeCount shouldBe 1
    }

    "variants sharing a name but differing in defDigest use distinct files" {
        val dir = tmpDir()
        val sd = studyDataOf("one two three")
        val store = AnnotationStore(sd, dir)
        store.get(CountingSource("names", "d1", listOf(0..2)))
        store.get(CountingSource("names", "d2", listOf(4..6)))

        val files = dir.listDirectoryEntries().filter { it.extension == "tsv" }.map { it.name }
        files shouldHaveSize 2
        files.distinct() shouldHaveSize 2
    }

    "with no cacheDir, nothing is persisted but results are still memoized" {
        val store = AnnotationStore(studyDataOf("no disk here"), cacheDir = null)
        val src = CountingSource("names", "abc", listOf(0..1))
        store.rangeList(src) shouldBe listOf(0..1)
        store.get(src)
        src.computeCount shouldBe 1
    }

    "RegexSetSource matches over the real study text without needing analysis layers" {
        // Sanity check that a real range source works end-to-end through the store.
        val store = AnnotationStore(studyDataOf("cat dog cat"), tmpDir())
        val ranges = store.rangeList(RegexSetSource("cats", setOf(Regex("cat"))))
        ranges shouldBe listOf(0..2, 8..10)
        ranges.size shouldBeGreaterThan 1
    }
})
