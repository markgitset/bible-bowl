package net.markdrew.biblebowl.generate.text

import java.io.InputStream
import java.net.URI
import kotlin.io.path.toPath

val smallCapsNames = mapOf(
    "LORD" to "Lord",
    "I AM" to "I am",
    "I AM WHO I AM" to "I am who I am",
    "I am what I am" to "I am what I am",
    "I will be what I will be" to "I will be what I will be"
)

val divineNames = setOf("God", "Jesus", "Christ", "Holy Spirit", "Immanuel", "Father", "Spirit of God",
    "Son of God", "Son of Man", "Son of David", "Lord of the harvest", "Spirit of your Father", "Son",
    "God Almighty", "angel of the LORD", "Spirit",
    *smallCapsNames.keys.toTypedArray())

internal fun URI.resolveChild(childString: String): URI =
    toPath().resolve(childString).toUri()
internal fun URI.open(childString: String): InputStream =
    resolveChild(childString).toURL().openStream()