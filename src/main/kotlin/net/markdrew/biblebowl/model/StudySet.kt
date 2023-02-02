package net.markdrew.biblebowl.model

import net.markdrew.biblebowl.model.Book.*

data class StudySet(val name: String, val chapterRanges: List<ChapterRange>) {

    init {
        require(chapterRanges.zipWithNext().all { (a, b) -> a.endInclusive < b.start }) {
            "Chapter ranges must be in ascending Biblical order!"
        }
    }

    constructor(name: String, vararg chapterRanges: ChapterRange) : this(name, chapterRanges.asList())

    constructor(book: Book) : this(book.fullName, book.allChapters())

    enum class StandardStudySet(val set: StudySet) {
        GENESIS(StudySet(GEN)),
        MATTHEW(StudySet(MAT)),
        LIFE_OF_MOSES(StudySet("Life of Moses",
            EXO.chapterRange(1, 20),
            EXO.chapterRange(32, 34),
            NUM.chapterRange(1, 3),
            NUM.chapterRange(10, 14),
            NUM.chapterRange(16, 17),
            NUM.chapterRange(20, 27),
            NUM.chapterRange(31, 36),
            DEU.chapterRange(31, 34),
        )),
        LUKE(StudySet(LUK)),
        JOSHUA_JUDGES_RUTH(StudySet("Joshua, Judges, and Ruth",
            JOS.allChapters(),
            JUD.allChapters(),
            RUT.allChapters()
        )),
        ACTS(StudySet(ACT)),
        I_SAMUEL(StudySet(SA1)),
        JOHN(StudySet(JOH)),
        II_SAMUEL(StudySet(SA2)),
        REVELATION(StudySet(REV)),
    }
}