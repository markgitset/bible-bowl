package net.markdrew.biblebowl.analysis

import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import net.markdrew.biblebowl.model.Excerpt

class FindNumbersKtTest : StringSpec({
    val twoToNine = listOf("two", "three", "four", "five", "six", "seven", "eight", "nine")
    val oneToNine = listOf("one") + twoToNine
    val twoToTwelve = twoToNine + listOf("ten", "eleven", "twelve")
    val teens = listOf("thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    val tens = listOf("twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

    fun checkFound(number: String) {
        val prefix = "There were "
        findNumbers("$prefix$number dogs.").toList() shouldBe
            listOf(Excerpt(number, prefix.length until prefix.length + number.length))
    }

    withData(nameFn = { "finds simple number \"$it\"" }, twoToTwelve) { checkFound(it) }

    withData(nameFn = { "TEENS regex matches \"$it\"" }, teens) { teen ->
        TEENS.toRegex().matches(teen) shouldBe true
    }
    withData(nameFn = { "finds teen number \"$it\"" }, teens) { checkFound(it) }

    withData(
        nameFn = { "finds hyphenated number \"$it\"" },
        tens.flatMap { ten -> oneToNine.map { "$ten-$it" } }
    ) { checkFound(it) }

    "hyphenated-fraction regex sanity checks" {
        BASE_FRACTIONS.toRegex().matches("half") shouldBe true
        FRACTIONS.toRegex().matches("half") shouldBe true
        FRACTIONS.toRegex().matches("one-half") shouldBe true
    }
    withData(
        nameFn = { "finds hyphenated fraction \"$it\"" },
        listOf("half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth")
            .map { "one-$it" }
    ) { checkFound(it) }

    "non-hyphenated-fraction regex sanity checks" {
        MULTI_NUMBER_PATTERN.toRegex().matches("four") shouldBe true
        BASE_FRACTIONS.toRegex().matches("halfs") shouldBe true
    }
    withData(
        nameFn = { "finds non-hyphenated fraction \"$it\"" },
        listOf("half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth")
            .map { "four ${it}s" }
    ) { checkFound(it) }

    withData(nameFn = { "TENS regex matches \"$it\"" }, tens) { ten ->
        TENS.toRegex().matches(ten) shouldBe true
    }
    withData(nameFn = { "finds multiple of 10 \"$it\"" }, tens) { checkFound(it) }

    val simpleOrdinals = listOf(
        "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
        "tenth", "eleventh", "twelfth"
    )
    withData(nameFn = { "ORDINALS regex matches simple ordinal \"$it\"" }, simpleOrdinals) { ord ->
        ORDINALS.toRegex().matches(ord) shouldBe true
    }
    withData(nameFn = { "finds simple ordinal \"$it\"" }, simpleOrdinals) { checkFound(it) }
    "does not find \"first\" standalone" {
        findNumbers("first").toList() shouldBe emptyList()
    }

    val teenOrdinals = teens.map { "${it}th" }
    withData(nameFn = { "ORDINALS regex matches teen ordinal \"$it\"" }, teenOrdinals) { ord ->
        ORDINALS.toRegex().matches(ord) shouldBe true
    }
    withData(nameFn = { "finds teen ordinal \"$it\"" }, teenOrdinals) { checkFound(it) }

    val hyphenatedOrdinals = tens.flatMap { t ->
        listOf("first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth")
            .map { "$t-$it" }
    }
    withData(nameFn = { "ORDINALS regex matches hyphenated ordinal \"$it\"" }, hyphenatedOrdinals) { ord ->
        ORDINALS.toRegex().matches(ord) shouldBe true
    }
    withData(nameFn = { "finds hyphenated ordinal \"$it\"" }, hyphenatedOrdinals) { checkFound(it) }

    withData(
        nameFn = { "finds multiple-of-10 ordinal \"$it\"" },
        listOf("twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth")
    ) { checkFound(it) }

    withData(
        nameFn = { "finds plural number \"$it\"" },
        listOf(
            "hundreds", "thousands", "ten thousands", "twenties", "thirties", "forties", "fifties",
            "sixties", "seventies", "eighties", "nineties", "twos", "threes", "fours", "fives", "sixes",
            "sevens", "eights", "nines", "tens", "elevens", "twelves", "thirteens"
        )
    ) { checkFound(it) }

    withData(
        nameFn = { "finds two-word number \"$it\"" },
        listOf("six hundred", "five thousand", "ten thousands", "sixty-seven thousand")
    ) { checkFound(it) }

    withData(
        nameFn = { "finds complex number phrase \"$it\"" },
        listOf("thousands of ten thousands", "six hundred and second", "six hundred and first")
    ) { checkFound(it) }

    "finds multiple numbers in one sentence" {
        findNumbers("You shall not eat just one day, or two days, or five days")
            .map { it.excerptText }.toList() shouldBe listOf("one", "two", "five")
    }

    withData(
        nameFn = { "finds \"first\" as ordinal in: \"$it\"" },
        "the first month of the year",
        "he said to the first, 'How much do you owe my master?'",
    ) { text ->
        findNumbers(text).map { it.excerptText }.toList() shouldBe listOf("first")
    }

    withData(
        nameFn = { "finds fold \"$it\"" },
        "sevenfold", "hundredfold", "seventy-sevenfold"
    ) { checkFound(it) }

    withData(
        nameFn = { "finds no number in: \"$it\"" },
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
    ) { text ->
        findNumbers(text).toList() shouldBe emptyList()
    }
})
