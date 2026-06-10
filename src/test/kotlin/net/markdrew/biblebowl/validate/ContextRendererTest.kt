package net.markdrew.biblebowl.validate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import net.markdrew.biblebowl.analysis.singleVerseStudyData

class ContextRendererTest : StringSpec({

    val data = singleVerseStudyData("the quick brown fox jumps over the lazy dog")
    val renderer = ContextRenderer(data)
    val fox = data.text.indexOf("fox")..(data.text.indexOf("fox") + 2)

    "word-window zoom isolates the instance with surrounding words" {
        val rc = renderer.render(fox, ContextZoom.WORDS, wordWindow = 2)
        rc.instance shouldBe "fox"
        rc.before shouldContain "brown"
        rc.after shouldContain "jumps"
        rc.location shouldBe "Genesis 1:1"
    }

    "verse zoom spans the whole verse" {
        val rc = renderer.render(fox, ContextZoom.VERSE)
        rc.before shouldContain "the quick"
        rc.after shouldContain "lazy dog"
        rc.instance shouldBe "fox"
    }
})
