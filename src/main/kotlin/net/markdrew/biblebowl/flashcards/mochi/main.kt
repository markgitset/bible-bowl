package net.markdrew.biblebowl.flashcards.mochi

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main2() {
    val inputFile = File("Test Deck.mochi")
    val outputFile = File("my_deck_modified.mochi")

    // 1. Unpack and Parse
    println("Reading archive...")
    val archive = inputFile.inputStream().buffered().use { stream ->
        MochiArchiveManager.readArchive(stream)
    }

    println("Successfully loaded ${archive.data.decks.size} decks and ${archive.mediaFiles.size} media files.")

    // 2. Modify Data (e.g., append text to all card contents)
    val modifiedDecks = archive.data.decks.map { deck ->
        val modifiedCards = deck.cards.map { card ->
            card.copy(content = card.content + "\n\n*Updated by Kotlin!*")
        }
        deck.copy(cards = modifiedCards)
    }
    
    val modifiedExport = archive.data.copy(decks = modifiedDecks)
    val modifiedArchive = archive.copy(data = modifiedExport)

    // 3. Repack and Save
    println("Saving modified archive to ${outputFile.name}...")
    outputFile.outputStream().buffered().use { stream ->
        MochiArchiveManager.writeArchive(modifiedArchive, stream)
    }
    
    println("Done!")
}

fun main() {
    val data = MochiData(
        listOf(
            Deck("Test Deck",
                cards = listOf(
                    Card("Hello World!"),
                    Card("Bye World!"),
                )
            )
        )
    )

    val path: Path = Paths.get("test.mochi")
    println("Saving modified archive to $path...")
    val mochiArchive = MochiArchive(data)
    Files.newOutputStream(path).buffered().use { stream ->
        MochiArchiveManager.writeArchive(mochiArchive, stream)
    }
}