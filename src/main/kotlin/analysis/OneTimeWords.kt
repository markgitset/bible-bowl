package net.markdrew.biblebowl.analysis

import net.markdrew.biblebowl.model.BookData

fun oneTimeWords(bookData: BookData): List<IntRange> = bookData.words
    .groupBy { bookData.text.substring(it).lowercase() }
    .filterValues { it.size == 1 }.values.flatten()
