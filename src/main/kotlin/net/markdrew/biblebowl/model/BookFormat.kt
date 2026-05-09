package net.markdrew.biblebowl.model

/** Strategy for rendering a [Book] as a string in references like "John 3:16" or "Joh 3:16" */
typealias BookFormat = (Book) -> String

/** Renders the three-letter enum code (e.g. "JOH"). */
val THREE_LETTER_BOOK_FORMAT: BookFormat = Book::name

/** Renders the brief abbreviated name (e.g. "Joh", "1 Sam"). */
val BRIEF_BOOK_FORMAT: BookFormat = Book::briefName

/** Renders the full book name (e.g. "John", "1 Samuel"). */
val FULL_BOOK_FORMAT: BookFormat = Book::fullName

/** Omits the book name entirely; use only when the book is unambiguous from context. */
val NO_BOOK_FORMAT: BookFormat = { "" }
