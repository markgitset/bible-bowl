package net.markdrew.biblebowl.generate.text

import java.io.InputStream
import java.net.URI
import kotlin.io.path.toPath

val divineNames = setOf("God", "Jesus", "Christ", "Holy Spirit", "Immanuel", "Father", "Spirit of God",
    "Son of God", "Son of Man", "Son of David", "Lord of the harvest", "Spirit of your Father", "Son")

internal fun URI.resolveChild(childString: String): URI =
    toPath().resolve(childString).toUri()
internal fun URI.open(childString: String): InputStream =
    resolveChild(childString).toURL().openStream()