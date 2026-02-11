package net.markdrew.biblebowl.flashcards.mochi

import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object MochiArchiveManager {

    private const val DATA_FILE_NAME = "data.json"

    /**
     * Reads a .mochi archive from an InputStream, parses the Transit data, 
     * and loads any media files into memory.
     */
    fun readArchive(inputStream: InputStream): MochiArchive {
        var exportData: MochiData? = null
        val mediaFiles = mutableMapOf<String, ByteArray>()

        ZipInputStream(inputStream).use { zipIn ->
            var entry: ZipEntry? = zipIn.nextEntry
            while (entry != null) {
                if (!entry.isDirectory) {
                    val bytes = zipIn.readBytes()
                    
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

    /**
     * Packages a MochiArchive object back into a .mochi ZIP format and 
     * writes it to the provided OutputStream.
     */
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