package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Excerpt

internal class FindNumbersKtTest : FunSpec({

    val twoToNine =
        listOf("two", "three", "four", "five", "six", "seven", "eight", "nine")
    val oneToNine = listOf("one") + twoToNine
    val twoToTwelve =
        twoToNine + listOf("ten", "eleven", "twelve")
    val teens =
        listOf("thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    val tens =
        listOf("twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

    fun assertRegexMatch(regex: String, shouldMatch: List<String>) {
        val exp = regex.toRegex()
        for (s in shouldMatch) {
            exp.matches(s).shouldBeTrue()
        }
    }

    fun assertNoRegexMatch(regex: String, shouldNotMatch: List<String>) {
        val exp = regex.toRegex()
        for (s in shouldNotMatch) {
            exp.matches(s).shouldBeFalse()
        }
    }

    fun assertFound(numbers: List<String>, prefix: String = "There were ", suffix: String = " dogs.") {
        for (number in numbers) {
            findNumbers("$prefix$number$suffix").toList() shouldBe listOf(Excerpt(number, prefix.length until prefix.length + number.length))
        }
    }

    /** Doesn't check found ranges, but does check that the expected number strings were found */
    fun assertFound(text: String, vararg expectedNumberStrings: String) {
        findNumbers(text).map { it.excerptText }.toList() shouldBe expectedNumberStrings.toList()
    }

    fun assertNotFound(vararg noNumbers: String) {
        for (s in noNumbers) {
            val foundNumbers = findNumbers(s).toList()
            foundNumbers.isEmpty().shouldBeTrue()
        }
    }

    test("findNumbers finds simple numbers") {
        assertFound(twoToTwelve)
    }

    test("findNumbers finds teen numbers") {
        assertRegexMatch(TEENS, teens)
        assertFound(teens)
    }

    test("findNumbers finds hyphenated numbers") {
        assertFound(tens.flatMap { ten -> oneToNine.map { "$ten-$it" } })
    }

    test("findNumbers finds hyphenated fractions") {
        val fractions = listOf(
            "half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"
        ).map { "one-$it" }
        assertRegexMatch(BASE_FRACTIONS, listOf("half"))
        assertRegexMatch(FRACTIONS, listOf("half"))
        assertRegexMatch(FRACTIONS, listOf("one-half"))
        assertFound(fractions)
    }

    test("findNumbers finds non-hyphenated fractions") {
        val fractions = listOf(
            "half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"
        ).map { "four ${it}s" }
        assertRegexMatch(MULTI_NUMBER_PATTERN, listOf("four"))
        assertRegexMatch(BASE_FRACTIONS, listOf("halfs"))
        assertRegexMatch(FRACTIONS, fractions)
        assertFound(fractions)
    }

    test("findNumbers finds multiple of 10 numbers") {
        assertRegexMatch(TENS, tens)
        assertFound(tens)
    }

    test("findNumbers finds simple ordinals") {
        val simpleOrdinals = listOf(
            "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
            "tenth", "eleventh", "twelfth"
        )
        assertRegexMatch(ORDINALS, simpleOrdinals)
        assertFound(simpleOrdinals)
        assertNotFound("first")
    }

    test("findNumbers finds teen ordinals") {
        val teenOrdinals = teens.map { "${it}th" }
//        assertRegexMatch(TEEN_ORDINALS, teenOrdinals)
        assertRegexMatch(ORDINALS, teenOrdinals)
        assertFound(teenOrdinals)
    }

    test("findNumbers finds hyphenated ordinals") {
        val ones = listOf("first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth")
        val hyphenatedOrdinals = tens.flatMap { t -> ones.map { "$t-$it" } }
        assertRegexMatch(ORDINALS, hyphenatedOrdinals)
        assertFound(hyphenatedOrdinals)
    }

    test("findNumbers finds multiple of 10 ordinals") {
        assertFound(listOf(
            "twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth"
        ))
    }

    test("findNumbers finds plural numbers") {
        assertFound(
            listOf(
                "hundreds", "thousands", "ten thousands", "twenties", "thirties", "forties", "fifties",
                "sixties", "seventies", "eighties", "nineties", "twos", "threes", "fours", "fives", "sixes", "sevens",
                "eights", "nines", "tens", "elevens", "twelves", "thirteens"
            )
        )
    }

    test("findNumbers finds two word numbers") {
        assertFound(listOf("six hundred", "five thousand", "ten thousands", "sixty-seven thousand"))
    }

    test("findNumbers finds weird combos") {
        assertFound(listOf(
            "thousands of ten thousands",
            "six hundred and second",
            "six hundred and first"
        ))
    }

    test("findNumbers finds one as a number") {
        assertFound("You shall not eat just one day, or two days, or five days", "one", "two", "five")
    }

    test("findNumbers finds first as an ordinal") {
        assertFound("the first month of the year", "first")
        assertFound("he said to the first, ‘How much do you owe my master?’", "first")
    }

    test("findNumbers finds folds") {
        assertFound(listOf(
            "sevenfold",
            "hundredfold",
            "seventy-sevenfold"
        ))
    }

    test("findNumbers does not find non-numeric ones") {
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
            "seeing no one, he struck down",
            "to your godly one, whom",
        )
    }

})
