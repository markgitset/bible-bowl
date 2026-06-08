package net.markdrew.biblebowl.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class StandardStudySetTest : StringSpec({

    "parseOrNull matches an exact enum name, case-insensitively" {
        StandardStudySet.parseOrNull("acts") shouldBe StandardStudySet.ACTS.set
        StandardStudySet.parseOrNull("ACTS") shouldBe StandardStudySet.ACTS.set
    }

    "parseOrNull matches a simpleName" {
        StandardStudySet.parseOrNull("josh-judg-ruth") shouldBe StandardStudySet.JOSHUA_JUDGES_RUTH.set
    }

    "parseOrNull matches a prefix of the enum name" {
        StandardStudySet.parseOrNull("joshua") shouldBe StandardStudySet.JOSHUA_JUDGES_RUTH.set
    }

    "parseOrNull falls back to a single-book study set" {
        StandardStudySet.parseOrNull("Mark")?.simpleName shouldBe "mark"
    }

    "parseOrNull returns null when nothing matches" {
        StandardStudySet.parseOrNull("zzznotabook").shouldBeNull()
    }

    "parse returns the default for a null name" {
        StandardStudySet.parse(null) shouldBe StandardStudySet.DEFAULT
    }

    "parse falls back to the default for an unrecognized name" {
        StandardStudySet.parse("zzznotabook") shouldBe StandardStudySet.DEFAULT
    }
})
