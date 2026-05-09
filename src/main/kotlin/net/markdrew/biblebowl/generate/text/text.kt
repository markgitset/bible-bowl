package net.markdrew.biblebowl.generate.text

import org.intellij.lang.annotations.Language
import java.io.InputStream
import java.net.URI
import kotlin.io.path.toPath

/** Default small-caps replacements: forms of the divine name and "I AM" sayings rendered in small caps. */
val smallCapsNames = mapOf(
    "GOD" to "God",
    "LORD" to "Lord",
    "I AM" to "I am",
    "I AM WHO I AM" to "I am who I am",
    "I am what I am" to "I am what I am",
    "I will be what I will be" to "I will be what I will be"
)

/**
 * Regex patterns matching common references to God, Jesus, Holy Spirit, angels, etc.
 *
 * Used by renderers to apply divine-name highlighting and by name-extraction code to exclude these from
 * the regular names index (so "God" / "Jesus" / "Christ" don't show up there by default).
 */
@Language("RegExp")
val divineNames: Set<String> = setOf(
    "(?:L(?:ORD|ord),? (?:your |the )?)?God(?: of Israel)?", "(Lord )?Jesus( Christ)?", "Christ( of God| the Lord)?",
    "Holy Spirit", "Immanuel",
    "(?<!(go to my father, and I will say to him|son said to him|said to his father), [‘“])Father(?! Abraham)",
    "Spirit of God", "Son of God", "Son of Man", "Son of David", "Lord of the harvest",
    "Spirit of your Father", "(?<![‘“])Son", "God Almighty", "angel of the LORD", "Spirit( of the Lord)?",
    "(?<=[Tt]he |O )Lord(’s Christ| your God)?", "(Son of the )?Most High( God)?", "(Holy|Chosen) One( of God)?",
    *smallCapsNames.keys.toTypedArray()
)

internal fun URI.resolveChild(childString: String): URI =
    toPath().resolve(childString).toUri()
internal fun URI.open(childString: String): InputStream =
    resolveChild(childString).toURL().openStream()