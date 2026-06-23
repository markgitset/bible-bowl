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
        config.layout.fontSize shouldBe 10
        config.layout.twoColumns shouldBe true
        config.layout.useHeadingsForChapters shouldBe true
        config.layout.chapterEndLines shouldBe false
        config.layout.testDate shouldBe testDate
        config.features.underlineUniqueWords shouldBe false
        config.features.verseOnNewLine shouldBe false
    }

    "a null override field inherits the preset value; a non-null replaces it" {
        val config = Presets.marks.resolve(
            TextOverrides(fontSize = 14, twoColumns = false, verseOnNewLine = true, chapterEndLines = true),
            testDate,
        )
        config.layout.fontSize shouldBe 14          // replaced
        config.layout.twoColumns shouldBe false     // replaced (with false)
        config.layout.useHeadingsForChapters shouldBe true   // inherited from marks
        config.features.verseOnNewLine shouldBe true
        config.layout.chapterEndLines shouldBe true
    }

    "typographic overrides are resolved correctly" {
        val config = Presets.tbb.resolve(
            TextOverrides(mainFont = "Liberation Sans", chapterFontSize = 18, justified = true),
            testDate
        )
        config.layout.mainFont shouldBe "Liberation Sans"
        config.layout.chapterFontSize shouldBe 18
        config.layout.justified shouldBe true
    }

    "highlight is tri-state: true applies the full palette, false clears it, null inherits" {
        val on = Presets.plain.resolve(TextOverrides(highlight = true), testDate)
        on.features.customHighlights.entries.isNotEmpty().shouldBeTrue()

        val off = Presets.plain.resolve(TextOverrides(highlight = false), testDate)
        off.features.customHighlights.entries.isEmpty().shouldBeTrue()

        val inherited = Presets.plain.resolve(TextOverrides(), testDate)
        inherited.features.customHighlights shouldBe Presets.plain.features.customHighlights
    }

    "preset and style lookups are case-insensitive and return null for unknown tokens" {
        Presets.byName("MARKS") shouldBe Presets.marks
        Presets.byName("nope") shouldBe null
        StyleId.byToken("Tbb") shouldBe StyleId.TBB
        StyleId.byToken("nope") shouldBe null
    }
})
