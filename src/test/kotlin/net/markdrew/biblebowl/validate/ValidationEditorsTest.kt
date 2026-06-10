package net.markdrew.biblebowl.validate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import net.markdrew.biblebowl.analysis.CategoryOverride
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.VerseRef
import java.nio.file.Files
import kotlin.io.path.createDirectories
import kotlin.io.path.readText
import kotlin.io.path.writeText

class ValidationEditorsTest : StringSpec({

    "CategoryListEditor adds (idempotent), removes, and round-trips regex entries" {
        val dir = Files.createTempDirectory("wl").also { it.createDirectories() }
        dir.resolve("men.txt").writeText("Aaron\nMoses\n")
        val editor = CategoryListEditor(dir)

        editor.contains(WordList.MEN, "Moses").shouldBeTrue()
        editor.add(WordList.MEN, "Moses").shouldBeFalse()           // already present
        editor.add(WordList.MEN, "Joshua").shouldBeTrue()           // appended
        editor.entries(WordList.MEN) shouldContainExactly listOf("Aaron", "Moses", "Joshua")

        editor.removeConfirmed(WordList.MEN, "Aaron").shouldBeTrue()
        editor.contains(WordList.MEN, "Aaron").shouldBeFalse()

        editor.add(WordList.PLACES, """Beth\w+""").shouldBeTrue()   // regex entry
        editor.contains(WordList.PLACES, """Beth\w+""").shouldBeTrue()
        editor.categoriesContaining("Moses") shouldContainExactly listOf(WordList.MEN)
    }

    "ValidationState records, resumes, and un-records (undo) reviewed forms" {
        val file = Files.createTempDirectory("vs").resolve("annotation-validation.tsv")
        val state = ValidationState.load(file)
        state.isDone("Moses").shouldBeFalse()
        state.record("Moses", "men")
        state.record("Selah", "none")
        ValidationState.load(file).doneForms shouldBe setOf("Moses", "Selah")

        state.remove("Moses") // undo
        ValidationState.load(file).doneForms shouldBe setOf("Selah")
    }

    "OverridesWriter append is idempotent, reports new rows, and remove reverses it" {
        val file = Files.createTempDirectory("ow").resolve("category-overrides.tsv")
        val writer = OverridesWriter(file)
        val row = CategoryOverride(VerseRef(Book.GEN, 1, 1), "Noah", "women")

        writer.append(listOf(row)) shouldHaveSize 1          // newly written
        writer.append(listOf(row)).shouldBeEmpty()           // already present → nothing added
        file.readText() shouldContain "Genesis 1:1\tNoah\twomen"

        writer.remove(listOf(row))                           // undo
        file.readText() shouldNotContain "Noah"
    }
})
