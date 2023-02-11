package net.markdrew.biblebowl.model

typealias BookFormat = (Book) -> String

val THREE_LETTER_BOOK_FORMAT: BookFormat = Book::name
val BRIEF_BOOK_FORMAT: BookFormat = Book::briefName
val FULL_BOOK_FORMAT: BookFormat = Book::fullName
val NO_BOOK_FORMAT: BookFormat = { "" }
