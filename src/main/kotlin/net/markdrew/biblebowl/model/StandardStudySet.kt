package net.markdrew.biblebowl.model

enum class StandardStudySet(val set: StudySet) {
    GENESIS(StudySet(Book.GEN, "gen")),
    MATTHEW(StudySet(Book.MAT, "matt")),
    LIFE_OF_MOSES(
        StudySet(
            "Life of Moses", "moses",
            Book.EXO.chapterRange(1, 20),
            Book.EXO.chapterRange(32, 34),
            Book.NUM.chapterRange(1, 3),
            Book.NUM.chapterRange(10, 14),
            Book.NUM.chapterRange(16, 17),
            Book.NUM.chapterRange(20, 27),
            Book.NUM.chapterRange(31, 36),
            Book.DEU.chapterRange(31, 34),
        )
    ),
    LIFE_OF_MOSES_LTC(
        StudySet(
            "Life of Moses LTC", "ltc",
            Book.EXO.chapterRange(1, 20),
//            Book.EXO.chapterRange(32, 34),
//            Book.NUM.chapterRange(1, 3),
            Book.NUM.chapterRange(10, 14),
//            Book.NUM.chapterRange(16, 17),
            Book.NUM.chapterRange(20, 24),
//            Book.NUM.chapterRange(20, 27),
//            Book.NUM.chapterRange(31, 36),
            Book.DEU.chapterRange(31, 34),
        )
    ),
    LUKE(StudySet(Book.LUK, "luke")),
    JOSHUA_JUDGES_RUTH(
        StudySet(
            "Joshua, Judges, and Ruth", "josh-judg-ruth",
            Book.JOS.allChapters(),
            Book.JDG.allChapters(),
            Book.RUT.allChapters()
        )
    ),
    ACTS(StudySet(Book.ACT, "acts")),
    I_SAMUEL(StudySet(Book.SA1, "1sam")),
    JOHN(StudySet(Book.JOH, "john")),
    II_SAMUEL(StudySet(Book.SA2, "2sam")),
    REVELATION(StudySet(Book.REV, "rev")),;

    companion object {
        val DEFAULT: StudySet = MATTHEW.set

        // lenient parsing for user input, e.g.
        fun parse(queryName: String?, default: StudySet = DEFAULT): StudySet =
            if (queryName == null) {
                default
            } else {
                val standardStudySet: StandardStudySet? = try {
                    StandardStudySet.valueOf(queryName.uppercase())
                } catch (e: IllegalArgumentException) {
                    val lowerQueryName = queryName.lowercase()
                    StandardStudySet.values().firstOrNull { sss ->
                        setOf(sss.name, sss.set.simpleName, sss.set.name).any {
                            it.lowercase().startsWith(lowerQueryName)
                        }
                    }
                }
                standardStudySet?.set ?: default
            }
    }
}