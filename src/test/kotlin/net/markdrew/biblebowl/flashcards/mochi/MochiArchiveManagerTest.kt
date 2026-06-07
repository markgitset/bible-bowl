package net.markdrew.biblebowl.flashcards.mochi

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class MochiArchiveManagerTest : StringSpec({

    "should throw exception when zip entry is too large" {
        val out = ByteArrayOutputStream()
        ZipOutputStream(out).use { zos ->
            zos.putNextEntry(ZipEntry("large_file.txt"))
            val chunk = ByteArray(1024 * 1024) // 1MB
            for (i in 0 until 105) { // 105 MB total
                zos.write(chunk)
            }
            zos.closeEntry()
        }

        val input = ByteArrayInputStream(out.toByteArray())

        shouldThrow<IllegalStateException> {
            MochiArchiveManager.readArchive(input)
        }
    }

    "should throw exception when too many entries exist" {
        val out = ByteArrayOutputStream()
        ZipOutputStream(out).use { zos ->
            for (i in 0 until 10005) {
                zos.putNextEntry(ZipEntry("file_$i.txt"))
                zos.write("content".toByteArray())
                zos.closeEntry()
            }
        }

        val input = ByteArrayInputStream(out.toByteArray())

        shouldThrow<IllegalStateException> {
            MochiArchiveManager.readArchive(input)
        }
    }
})
