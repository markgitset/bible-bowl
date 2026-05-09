package net.markdrew.biblebowl.model

/**
 * The 66 books of the Protestant Bible canon, in canonical order
 *
 * Each book carries display names at three levels (full, brief, three-letter) and a positional [number] (1..66)
 * used to encode/decode packed [AbsoluteChapterNum] and [AbsoluteVerseNum] values.
 *
 * @param fullName the unabbreviated book name (e.g. "1 Corinthians")
 * @param briefName a short name for index/footer use; defaults to [fullName]
 * @param twoLetterCode optional override for the two-letter code; defaults to the first two letters of [name]
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

    /** 1-based canonical position (Genesis = 1, Revelation = 66) */
    val number = ordinal + 1

    /** Largest possible chapter ref for this book, useful as a sentinel "to end of book" upper bound */
    val lastChapterRef = ChapterRef(this, BCV_FACTOR - 1)

    /** Two-letter uppercase code for this book (e.g. "GE", "JG", "DT") */
    val twoLetter = (twoLetterCode ?: name.take(2)).uppercase()

    /** Returns the [VerseRef] at [chapter]:[verse] in this book. */
    fun verseRef(chapter: Int, verse: Int): VerseRef = chapterRef(chapter).verse(verse)

    /** Returns the [ChapterRef] for [chapter] in this book. */
    fun chapterRef(chapter: Int): ChapterRef = ChapterRef(this, chapter)

    /** Returns the inclusive [ChapterRange] from chapter [first] through [last] of this book. */
    fun chapterRange(first: Int, last: Int): ChapterRange = chapterRef(first)..chapterRef(last)

    /** Returns a sentinel [ChapterRange] covering every chapter of this book; the upper bound is [lastChapterRef]. */
    fun allChapters(): ChapterRange = chapterRef(1)..lastChapterRef

    companion object {
        val DEFAULT = MAT

        /** Returns the book at 1-based canonical position [n] (1 = Genesis, 66 = Revelation). */
        fun fromNumber(n: Int): Book = entries[n-1]

        /**
         * Lenient parser tolerating both the three-letter enum name (case-insensitive) and a prefix of [fullName].
         *
         * Returns [default] if [s] is null. If [s] is non-null and matches neither form, returns [default] as
         * well (i.e. unknown input falls back rather than throwing).
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
