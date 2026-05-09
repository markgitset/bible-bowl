package net.markdrew.biblebowl.flashcards

/**
 * A two-sided flashcard with an optional hint
 *
 * @param front the question or prompt side
 * @param back the answer side
 * @param hint optional small hint shown alongside the prompt
 */
data class Card(val front: String, val back: String, val hint: String? = null)
