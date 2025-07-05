package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.ChapterRef
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class PassageCache(
    private val forceDownload: Boolean = false,
    private val esvClient: EsvClient = EsvClient(),
    private val timeBetweenChapters: Duration = 1.seconds,
    private val passageStore: PassageStore = PassageStore()
) {
    fun getPassage(chapterRef: ChapterRef): Passage {
        val cached: Passage? = if (forceDownload) null else passageStore.read(chapterRef)
        if (cached != null) {
            println("Using cached $chapterRef")
            return cached
        }
        Thread.sleep(timeBetweenChapters.inWholeMilliseconds)
        val passage: Passage = esvClient.queryPassage(chapterRef)
        updateCachedPassage(chapterRef, passage)
        return passage
    }

//    fun bookByChapters(studySet: StudySet): Sequence<Passage> =
//        studySet.chapterRanges.asSequence().flatMap { rangeByChapters(it) }

    private fun updateCachedPassage(chapterRef: ChapterRef, passage: Passage) {
        val existingSerialized = passageStore.mostRecentSerialized(chapterRef)
        if (existingSerialized != null && existingSerialized == passage.serialize()) {
            println("Content unchanged for $chapterRef, skipping cache update")
            return
        }
        println("Updating cache for $chapterRef...")
        passageStore.write(chapterRef, passage)
    }
}