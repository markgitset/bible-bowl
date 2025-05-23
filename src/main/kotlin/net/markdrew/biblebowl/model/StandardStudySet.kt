package net.markdrew.biblebowl.model

enum class StandardStudySet(val set: StudySet) {
    GENESIS(StudySet(Book.GEN, "gen")),
    MATTHEW(StudySet(Book.MAT, "matt")),
    LIFE_OF_MOSES(
        StudySet(
            "Life of Moses", "moses", listOf(
                Book.EXO.chapterRange(1, 20),
                Book.EXO.chapterRange(32, 34),
                Book.NUM.chapterRange(1, 3),
                Book.NUM.chapterRange(10, 14),
                Book.NUM.chapterRange(16, 17),
                Book.NUM.chapterRange(20, 27),
                Book.NUM.chapterRange(31, 36),
                Book.DEU.chapterRange(31, 34),
            ),
            "the Life of Moses (Exo 1-20,32-34, Num 1-3,10-14,16-17,20-27,31-36, and Deut 31-34)"
        )
    ),
    LIFE_OF_MOSES_LTC(
        StudySet(
            "Life of Moses LTC", "moses-ltc",
            Book.EXO.chapterRange(1, 20),
            Book.NUM.chapterRange(10, 14),
            Book.NUM.chapterRange(20, 24),
            Book.DEU.chapterRange(31, 34),
        )
    ),
    LUKE(StudySet(Book.LUK, "luke")),
    JOSHUA_JUDGES_RUTH(
        StudySet(
            "Joshua, Judges, and Ruth", "josh-judg-ruth", listOf(
                Book.JOS.allChapters(),
                Book.JDG.allChapters(),
                Book.RUT.allChapters()
            ),
            "the books of Joshua, Judges, and Ruth"
        )
    ),
    ACTS(StudySet(Book.ACT, "acts")),
    I_SAMUEL(StudySet(Book.SA1, "1sam")),
    JOHN(StudySet(Book.JOH, "john")),
    II_SAMUEL(StudySet(Book.SA2, "2sam")),
    REVELATION(StudySet(Book.REV, "rev")),;

    companion object {
        val DEFAULT: StudySet = JOSHUA_JUDGES_RUTH.set

        // lenient parsing for user input, e.g.
        fun parse(queryName: String?, default: StudySet = DEFAULT): StudySet =
            if (queryName == null) {
                default
            } else {
                val studySet: StudySet? = try {
                    valueOf(queryName.uppercase())
                } catch (e: IllegalArgumentException) {
                    val lowerQueryName = queryName.lowercase()
                    entries.firstOrNull { sss ->
                        setOf(sss.name, sss.set.simpleName, sss.set.name).any {
                            it.lowercase().startsWith(lowerQueryName)
                        }
                    }
                }?.set
                studySet ?: Book.parse(queryName, null)?.let { StudySet(it) } ?: default
            }
    }
}