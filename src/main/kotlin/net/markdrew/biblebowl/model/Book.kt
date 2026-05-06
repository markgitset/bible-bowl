package net.markdrew.biblebowl.model

/**
 * Represents a book of the Bible, along with its standard abbreviations and order.
 *
 * @property fullName The full name of the book (e.g., "Genesis").
 * @property briefName A standard abbreviation for the book (e.g., "Gen").
 * @property twoLetterCode An optional two-letter code for the book.
 */
enum class Book(val fullName: String, val briefName: String = fullName, private val twoLetterCode: String? = null) {
    GEN("Genesis", "Gen"),
    EXO("Exodus", "Exo"),
    LEV("Leviticus", "Lev"),
    NUM("Numbers", "Num"),
    DEU("Deuteronomy", "Deut", "DT"),
    JOS("Joshua", "Jos"),
    JDG("Judges", "Jdg", twoLetterCode = "JG"),
    RUT("Ruth", "Ru"),
    SA1("1 Samuel", "1 Sam"),
    SA2("2 Samuel", "2 Sam"),
    KI1("1 Kings"),
    KI2("2 Kings"),
    CH1("1 Chronicles", "1 Chron"),
    CH2("2 Chronicles", "2 Chron"),
    EZR("Ezra"),
    NEH("Nehemiah", "Neh"),
    EST("Esther"),
    JOB("Job"),
    PSA("Psalms"),
    PRO("Proverbs", "Prov"),
    ECC("Ecclesiastes", "Eccl"),
    SOS("Song of Solomon", "Song"),
    ISA("Isaiah"),
    JER("Jeremiah", "Jer"),
    LAM("Lamentations", "Lam"),
    EZE("Ezekiel", "Eze"),
    DAN("Daniel"),
    HOS("Hosea"),
    JOE("Joel"),
    AMO("Amos"),
    OBA("Obadiah", "Oba"),
    JON("Jonah"),
    MIC("Micah"),
    NAH("Nahum"),
    HAB("Habakkuk", "Hab"),
    ZEP("Zephaniah", "Zeph"),
    HAG("Haggai"),
    ZEC("Zechariah", "Zech"),
    MAL("Malachi", "Mal"),
    MAT("Matthew", "Matt"),
    MAR("Mark"),
    LUK("Luke"),
    JOH("John"),
    ACT("Acts"),
    ROM("Romans"),
    CO1("1 Corinthians", "1 Cor"),
    CO2("2 Corinthians", "2 Cor"),
    GAL("Galatians", "Gal"),
    EPH("Ephesians", "Eph"),
    PHP("Philippians", "Phil"),
    COL("Colossians", "Col"),
    TH1("1 Thessalonians", "1 Thess"),
    TH2("2 Thessalonians", "2 Thess"),
    TI1("1 Timothy", "1 Tim"),
    TI2("2 Timothy", "2 Tim"),
    TIT("Titus"),
    PHM("Philemon", "Phm"),
    HEB("Hebrews", "Heb"),
    JAM("James"),
    PE1("1 Peter", "1 Pe"),
    PE2("2 Peter", "2 Pe"),
    JO1("1 John", "1 Jo"),
    JO2("2 John", "2 Jo"),
    JO3("3 John", "3 Jo"),
    JUD("Jude"),
    REV("Revelation", "Rev");

    /**
     * The 1-based order of the book in the standard biblical canon.
     */
    val number = ordinal + 1

    /**
     * The last theoretical chapter reference for this book, bounded by the system's maximum factor.
     */
    val lastChapterRef = ChapterRef(this, BCV_FACTOR - 1)

    /**
     * A two-letter representation of the book.
     */
    val twoLetter = (twoLetterCode ?: name.take(2)).uppercase()

    /**
     * Creates a reference to a specific verse within this book.
     *
     * @param chapter The chapter number.
     * @param verse The verse number.
     * @return A [VerseRef] representing the specified verse.
     */
    fun verseRef(chapter: Int, verse: Int): VerseRef = chapterRef(chapter).verse(verse)

    /**
     * Creates a reference to a specific chapter within this book.
     *
     * @param chapter The chapter number.
     * @return A [ChapterRef] representing the specified chapter.
     */
    fun chapterRef(chapter: Int): ChapterRef = ChapterRef(this, chapter)

    /**
     * Creates a range of chapters within this book.
     *
     * @param first The starting chapter number.
     * @param last The ending chapter number.
     * @return A [ChapterRange] covering the given chapters.
     */
    fun chapterRange(first: Int, last: Int): ChapterRange = chapterRef(first)..chapterRef(last)

    /**
     * Returns a range covering all chapters in the book (up to [lastChapterRef]).
     *
     * @return A [ChapterRange] from chapter 1 to the last theoretical chapter.
     */
    fun allChapters(): ChapterRange = chapterRef(1)..lastChapterRef

    companion object {
        /**
         * The default book used when parsing fails or no input is provided.
         */
        val DEFAULT = MAT

        /**
         * Retrieves a book based on its 1-based index in the canon.
         *
         * @param n The 1-based index.
         * @return The corresponding [Book].
         */
        fun fromNumber(n: Int): Book = entries[n-1]

        /**
         * Parses a string into a [Book], using lenient matching rules.
         *
         * Attempt to find an exact match by enum name, and falls back to prefix matching on [fullName].
         *
         * @param s The string to parse.
         * @param default The default book to return if parsing fails.
         * @return The matched [Book] or the default value.
         */
        fun parse(s: String?, default: Book? = DEFAULT): Book? = if (s == null) default else try {
            valueOf(s.uppercase())
        } catch (e: IllegalArgumentException) {
            entries.firstOrNull { it.fullName.lowercase().startsWith(s.lowercase()) } ?: default
        }
    }
}

fun main() {
    println(Book.DEFAULT.number)
}