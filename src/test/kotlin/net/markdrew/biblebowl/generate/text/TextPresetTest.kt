package net.markdrew.biblebowl.generate.text

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class TextPresetTest : StringSpec({

    val testDate = LocalDate.of(2027, 4, 3)

    "anySet is false for the empty override carrier" {
        TextOverrides().anySet().shouldBeFalse()
    }

    "anySet is true when any single field is set, including the 'false' value" {
        TextOverrides(verseOnNewLine = true).anySet().shouldBeTrue()
        // Explicitly turning something OFF is still an override, not 'inherit'.
        TextOverrides(twoColumns = false).anySet().shouldBeTrue()
        TextOverrides(fontSize = 11).anySet().shouldBeTrue()
        TextOverrides(chapterEndLines = false).anySet().shouldBeTrue()
    }

    "resolve with no overrides keeps preset values and stamps the testDate" {
        val config = Presets.marks.resolve(TextOverrides(), testDate)
        config.style shouldBe StyleId.MARKS
        config.fontSize shouldBe 10
        config.twoColumns shouldBe true
        config.useHeadingsForChapters shouldBe true
        config.chapterEndLines shouldBe false
        config.testDate shouldBe testDate
        config.underlineUniqueWords shouldBe false
        config.verseOnNewLine shouldBe false
    }

    "a null override field inherits the preset value; a non-null replaces it" {
        val config = Presets.marks.resolve(
            TextOverrides(fontSize = 14, twoColumns = false, verseOnNewLine = true, chapterEndLines = true),
            testDate,
        )
        config.fontSize shouldBe 14          // replaced
        config.twoColumns shouldBe false     // replaced (with false)
        config.useHeadingsForChapters shouldBe true   // inherited from marks
        config.verseOnNewLine shouldBe true
        config.chapterEndLines shouldBe true
    }

    "typographic overrides are resolved correctly" {
        val config = Presets.tbb.resolve(
            TextOverrides(mainFont = "Liberation Sans", chapterFontSize = 18, justified = true),
            testDate
        )
        config.mainFont shouldBe "Liberation Sans"
        config.chapterFontSize shouldBe 18
        config.justified shouldBe true
    }

    "highlight is tri-state: true applies the full palette, false clears it, null inherits" {
        val on = Presets.plain.resolve(TextOverrides(highlight = true), testDate)
        on.customHighlights.entries.isNotEmpty().shouldBeTrue()

        val off = Presets.plain.resolve(TextOverrides(highlight = false), testDate)
        off.customHighlights.entries.isEmpty().shouldBeTrue()

        val inherited = Presets.plain.resolve(TextOverrides(), testDate)
        inherited.customHighlights shouldBe Presets.plain.options.customHighlights
    }

    "preset and style lookups are case-insensitive and return null for unknown tokens" {
        Presets.byName("MARKS") shouldBe Presets.marks
        Presets.byName("nope") shouldBe null
        StyleId.byToken("Tbb") shouldBe StyleId.TBB
        StyleId.byToken("nope") shouldBe null
    }
})
