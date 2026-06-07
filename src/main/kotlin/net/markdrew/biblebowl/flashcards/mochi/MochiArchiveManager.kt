package net.markdrew.biblebowl.flashcards.mochi

import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/** Reads and writes Mochi flashcard archive files (`.mochi`, a ZIP of Transit JSON + optional media) */
object MochiArchiveManager {

    private const val DATA_FILE_NAME = "data.json"

    // Security limits to prevent zip bombs
    private const val MAX_ENTRY_SIZE = 100 * 1024 * 1024 // 100 MB
    private const val MAX_TOTAL_SIZE = 500 * 1024 * 1024 // 500 MB
    private const val MAX_ENTRIES = 10000

    /**
     * Reads a `.mochi` archive from [inputStream], parsing the Transit JSON data and loading any media
     * files into memory.
     *
     * @throws IllegalArgumentException if the archive is missing the `data.json` entry
     * @throws IllegalStateException if the archive exceeds size or entry count limits (potential zip bomb)
     */
    fun readArchive(inputStream: InputStream): MochiArchive {
        var exportData: MochiData? = null
        val mediaFiles = mutableMapOf<String, ByteArray>()

        ZipInputStream(inputStream).use { zipIn ->
            var entry: ZipEntry? = zipIn.nextEntry
            var totalBytesRead = 0L
            var entryCount = 0

            while (entry != null) {
                entryCount++
                if (entryCount > MAX_ENTRIES) {
                    throw IllegalStateException("Archive contains too many entries (max $MAX_ENTRIES)")
                }

                if (!entry.isDirectory) {
                    val baos = java.io.ByteArrayOutputStream()
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    var entryBytesRead = 0L

                    while (zipIn.read(buffer).also { bytesRead = it } != -1) {
                        baos.write(buffer, 0, bytesRead)
                        entryBytesRead += bytesRead
                        totalBytesRead += bytesRead

                        if (entryBytesRead > MAX_ENTRY_SIZE) {
                            throw IllegalStateException("Archive entry exceeds maximum size limit (max $MAX_ENTRY_SIZE bytes)")
                        }
                        if (totalBytesRead > MAX_TOTAL_SIZE) {
                            throw IllegalStateException("Archive total size exceeds maximum limit (max $MAX_TOTAL_SIZE bytes)")
                        }
                    }

                    val bytes = baos.toByteArray()
                    
                    if (entry.name == DATA_FILE_NAME) {
                        // Pass the byte array as a stream to our Transit decoder
                        exportData = bytes.inputStream().use { 
                            MochiTransitFormat.decodeFromStream(it) 
                        }
                    } else {
                        // Store media files (e.g., images, audio)
                        mediaFiles[entry.name] = bytes
                    }
                }
                zipIn.closeEntry()
                entry = zipIn.nextEntry
            }
        }

        requireNotNull(exportData) { 
            "Invalid Mochi archive: Missing $DATA_FILE_NAME" 
        }

        return MochiArchive(
            data = exportData,
            mediaFiles = mediaFiles
        )
    }

    /** Packages [archive] back into the `.mochi` ZIP format and writes it to [outputStream]. */
    fun writeArchive(archive: MochiArchive, outputStream: OutputStream) {
        ZipOutputStream(outputStream).use { zipOut ->
            
            // 1. Write the Transit JSON data file
            zipOut.putNextEntry(ZipEntry(DATA_FILE_NAME))
            // We write to a temporary ByteArrayOutputStream to prevent the 
            // Transit writer from closing our ZIP stream.
            val dataBytes = java.io.ByteArrayOutputStream().use { baos ->
                MochiTransitFormat.encodeToStream(archive.data, baos)
                baos.toByteArray()
            }
            zipOut.write(dataBytes)
            zipOut.closeEntry()

            // 2. Write all media files
            for ((fileName, fileBytes) in archive.mediaFiles) {
                zipOut.putNextEntry(ZipEntry(fileName))
                zipOut.write(fileBytes)
                zipOut.closeEntry()
            }
        }
    }
}