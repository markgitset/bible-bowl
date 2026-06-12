package net.markdrew.biblebowl.analysis

/**
 * Explicit, documented precedence for breaking **equal-length** category ties in [CategoryAnnotator]
 * (a span claimed by two categories with no override). Higher precedence — earlier in [order] — wins;
 * categories not listed rank lowest. This makes tie resolution deterministic and order-independent
 * rather than depending on the incidental order categories happen to be supplied in.
 *
 * Seeded to reproduce the historical highlight-palette tie-break (the palette listed men, places,
 * women, divine and the later one won, i.e. divine > women > places > men), so generated output is
 * unchanged. Extend deliberately when adding categories.
 */
object CategoryPrecedence {

    /** Highest precedence first; `other` (the catch-all name list) is lowest so specific lists win. */
    val order: List<String> = listOf(
        "divine", "women", "places", "men", "people-groups", "angels-demons",
        "numbers", "animals", "body-parts", "colors", "foods", "other",
    )

    /** Lower value = higher precedence; categories not in [order] rank last. */
    fun rank(category: String): Int = order.indexOf(category).let { if (it < 0) Int.MAX_VALUE else it }

    /** True if [candidate] should beat [incumbent] on an equal-length tie. */
    fun wins(candidate: String, incumbent: String): Boolean = rank(candidate) < rank(incumbent)
}
