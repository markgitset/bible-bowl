package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.Excerpt
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FindNumbersKtTest {

    private val twoToNine =
        listOf("two", "three", "four", "five", "six", "seven", "eight", "nine")
    private val oneToNine = listOf("one") + twoToNine
    private val twoToTwelve =
        twoToNine + listOf("ten", "eleven", "twelve")
    private val teens =
        listOf("thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    private val tens =
        listOf("twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

    @Test
    fun `findNumbers finds simple numbers`() {
        assertFound(twoToTwelve)
    }

    @Test
    fun `findNumbers finds teen numbers`() {
        assertRegexMatch(TEENS, teens)
        assertFound(teens)
    }

    @Test
    fun `findNumbers finds hyphenated numbers`() {
        assertFound(tens.flatMap { ten -> oneToNine.map { "$ten-$it" } })
    }

    @Test
    fun `findNumbers finds hyphenated fractions`() {
        val fractions = listOf(
            "half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"
        ).map { "one-$it" }
        assertRegexMatch(BASE_FRACTIONS, listOf("half"))
        assertRegexMatch(FRACTIONS, listOf("half"))
        assertRegexMatch(FRACTIONS, listOf("one-half"))
        assertFound(fractions)
    }

    @Test
    fun `findNumbers finds non-hyphenated fractions`() {
        val fractions = listOf(
            "half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"
        ).map { "four ${it}s" }
        assertRegexMatch(MULTI_NUMBER_PATTERN, listOf("four"))
        assertRegexMatch(BASE_FRACTIONS, listOf("halfs"))
        assertRegexMatch(FRACTIONS, fractions)
        assertFound(fractions)
    }

    @Test
    fun `findNumbers finds multiple of 10 numbers`() {
        assertRegexMatch(TENS, tens)
        assertFound(tens)
    }

    @Test
    fun `findNumbers finds simple ordinals`() {
        val simpleOrdinals = listOf(
            "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
            "tenth", "eleventh", "twelfth"
        )
        assertRegexMatch(ORDINALS, simpleOrdinals)
        assertFound(simpleOrdinals)
        assertNotFound("first")
    }

    @Test
    fun `findNumbers finds teen ordinals`() {
        val teenOrdinals = teens.map { "${it}th" }
//        assertRegexMatch(TEEN_ORDINALS, teenOrdinals)
        assertRegexMatch(ORDINALS, teenOrdinals)
        assertFound(teenOrdinals)
    }

    @Test
    fun `findNumbers finds hyphenated ordinals`() {
        val ones = listOf("first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth")
        val hyphenatedOrdinals = tens.flatMap { t -> ones.map { "$t-$it" } }
        assertRegexMatch(ORDINALS, hyphenatedOrdinals)
        assertFound(hyphenatedOrdinals)
    }

    @Test
    fun `findNumbers finds multiple of 10 ordinals`() {
        assertFound(listOf(
            "twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth"
        ))
    }

    @Test
    fun `findNumbers finds plural numbers`() {
        assertFound(listOf("hundreds", "thousands", "ten thousands"))
    }

    @Test
    fun `findNumbers finds two word numbers`() {
        assertFound(listOf("six hundred", "five thousand", "ten thousands", "sixty-seven thousand"))
    }

    @Test
    fun `findNumbers finds weird combos`() {
        assertFound(listOf(
            "thousands of ten thousands",
            "six hundred and second",
            "six hundred and first"
        ))
    }

    @Test
    fun `findNumbers finds folds`() {
        assertFound(listOf(
            "sevenfold",
            "hundredfold",
            "seventy-sevenfold"
        ))
    }

    @Test
    fun `findNumbers does not find-gathered together into one place`() {
        assertNotFound(
            "gathered together into one place,",
            "brought of the firstborn of his flock",
            "at the first",
            "so that if one can",
            "one said",
            "is it not a little one",
            "Complete the week of this one",
            "Every one that is not speckled",
            "every one that had white",
            "But one day",
            "And one night",
            "he is the only one left",
            "take this one also from me",
            "from one end of Egypt",
            "your brothers one mountain slope",
            "on my behalf",
        )
    }

    private fun assertRegexMatch(regex: String, shouldMatch: List<String>) {
        val exp = regex.toRegex()
        for (s in shouldMatch) {
            assertTrue(exp.matches(s)) { "Expected '$s' to match regex: $regex"}
        }
    }

    private fun assertNoRegexMatch(regex: String, shouldNotMatch: List<String>) {
        val exp = regex.toRegex()
        for (s in shouldNotMatch) {
            assertFalse(exp.matches(s)) { "Expected '$s' to NOT match regex: $regex"}
        }
    }

    private fun assertFound(numbers: List<String>, prefix: String = "There were ", suffix: String = " dogs.") {
        for (number in numbers) {
            assertEquals(
                listOf(Excerpt(number, prefix.length until prefix.length + number.length)),
                findNumbers("$prefix$number$suffix").toList()
            )
        }
    }

    private fun assertNotFound(vararg noNumbers: String) {
        for (s in noNumbers) {
            val foundNumbers = findNumbers(s).toList()
            assertTrue(foundNumbers.isEmpty()) { "Found number(s) $foundNumbers in '$s'"}
        }
    }

}
