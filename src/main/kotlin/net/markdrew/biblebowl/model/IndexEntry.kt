package net.markdrew.biblebowl.model

/** A key with its list of values, used as the row representation for indices and analysis */
data class IndexEntry<K, V>(val key: K, val values: List<V>)
