package net.markdrew.biblebowl.model

import java.lang.IllegalArgumentException

enum class Book(val fullName: String) {
    GEN("Genesis"),
    EXO("Exodus"),
    LEV("Leviticus"),
    NUM("Numbers"),
    DEU("Deuteronomy"),
    JOS("Joshua"),
    JDG("Judges"),
    RUT("Ruth"),
    SA1("1 Samuel"),
    SA2("2 Samuel"),
    KI1("1 Kings"),
    KI2("2 Kings"),
    CH1("1 Chronicles"),
    CH2("2 Chronicles"),
    EZR("Ezra"),
    NEH("Nehemiah"),
    EST("Esther"),
    JOB("Job"),
    PSA("Psalms"),
    PRO("Proverbs"),
    ECC("Ecclesiastes"),
    SOS("Song of Solomon"),
    ISA("Isaiah"),
    JER("Jeremiah"),
    LAM("Lamentations"),
    EZE("Ezekiel"),
    DAN("Daniel"),
    HOS("Hosea"),
    JOE("Joel"),
    AMO("Amos"),
    OBA("Obadiah"),
    JON("Jonah"),
    MIC("Micah"),
    NAH("Nahum"),
    HAB("Habakkuk"),
    ZEP("Zephaniah"),
    HAG("Haggai"),
    ZEC("Zechariah"),
    MAL("Malachi"),
    MAT("Matthew"),
    MAR("Mark"),
    LUK("Luke"),
    JOH("John"),
    ACT("Acts"),
    ROM("Romans"),
    CO1("1 Corinthians"),
    CO2("2 Corinthians"),
    GAL("Galatians"),
    EPH("Ephesians"),
    PHP("Philippians"),
    COL("Colossians"),
    TH1("1 Thessalonians"),
    TH2("2 Thessalonians"),
    TI1("1 Timothy"),
    TI2("2 Timothy"),
    TIT("Titus"),
    PHM("Philemon"),
    HEB("Hebrews"),
    JAM("James"),
    PE1("1 Peter"),
    PE2("2 Peter"),
    JO1("1 John"),
    JO2("2 John"),
    JO3("3 John"),
    JUD("Jude"),
    REV("Revelation");

    val number = ordinal + 1

    companion object {
        fun fromNumber(n: Int): Book = values()[n-1]
        fun parse(s: String?, default: Book): Book = if (s == null) default else try {
            valueOf(s.toUpperCase())
        } catch (e: IllegalArgumentException) {
            values().firstOrNull { it.fullName.lowercase().startsWith(s.lowercase()) } ?: default
        }
    }
}

fun main() {
    println(Book.REV.number)
}