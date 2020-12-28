package net.markdrew.biblebowl.cram

fun normalizeWhitespace(s: String): String = s.replace("\\s+".toRegex(), " ").trim()
