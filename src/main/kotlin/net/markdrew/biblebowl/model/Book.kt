package net.markdrew.biblebowl.model

enum class Book(val fullName: String, val briefName: String = fullName, private val twoLetterCode: String? = null) {
    GEN("Genesis", "Gen"),
    EXO("Exodus", "Exo"),
    LEV("Leviticus", "Lev"),
    NUM("Numbers", "Num"),
    DEU("Deuteronomy", "Deut", "DT"),
    JOS("Joshua"),
    JDG("Judges", twoLetterCode = "JG"),
    RUT("Ruth"),
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

    val number = ordinal + 1
    val lastChapterRef = ChapterRef(this, BCV_FACTOR - 1)
    val twoLetter = (twoLetterCode ?: name.take(2)).uppercase()

    fun verseRef(chapter: Int, verse: Int): VerseRef = chapterRef(chapter).verse(verse)
    fun chapterRef(chapter: Int): ChapterRef = ChapterRef(this, chapter)
    fun chapterRange(first: Int, last: Int): ChapterRange = chapterRef(first)..chapterRef(last)
    fun allChapters(): ChapterRange = chapterRef(1)..lastChapterRef

    companion object {
        val DEFAULT = MAT

        fun fromNumber(n: Int): Book = values()[n-1]

        // lenient parsing for user input, e.g.
        fun parse(s: String?, default: Book = DEFAULT): Book = if (s == null) default else try {
            valueOf(s.uppercase())
        } catch (e: IllegalArgumentException) {
            values().firstOrNull { it.fullName.lowercase().startsWith(s.lowercase()) } ?: default
        }
    }
}

fun main() {
    println(Book.DEFAULT.number)
}