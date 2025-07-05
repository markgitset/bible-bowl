package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Duration

class PassageCacheTest {

    private lateinit var esvClient: EsvClient
    private lateinit var cache: PassageCache

    private val book = mock<Book>()
    private val chapterRef = mock<ChapterRef>()
    private val passage = mock<Passage>()

    @BeforeEach
    fun setup() {
        esvClient = mock()
    }

    @Test
    fun `returns cached passage when not forcing download`() {
        val cacheSpy = spy(PassageCache(false, esvClient, Duration.ZERO))
        whenever(cacheSpy["retrieveCachedPassage"](chapterRef)).thenReturn(passage)

        val result = cacheSpy.getPassage(chapterRef)
        assertEquals(passage, result)
        verify(cacheSpy, never()).updateCachedPassage(any(), any())
        verify(esvClient, never()).queryPassage(any())
    }

    @Test
    fun `downloads passage and updates cache when not cached`() {
        val cacheSpy = spy(PassageCache(false, esvClient, Duration.ZERO))
        whenever(cacheSpy["retrieveCachedPassage"](chapterRef)).thenReturn(null)
        whenever(esvClient.queryPassage(chapterRef)).thenReturn(passage)
        doNothing().whenever(cacheSpy)["updateCachedPassage"](chapterRef, passage)

        val result = cacheSpy.getPassage(chapterRef)
        assertEquals(passage, result)
        verify(cacheSpy).updateCachedPassage(chapterRef, passage)
        verify(esvClient).queryPassage(chapterRef)
    }

    @Test
    fun `forces download and updates cache when forceDownload is true`() {
        val cacheSpy = spy(PassageCache(true, esvClient, Duration.ZERO))
        whenever(esvClient.queryPassage(chapterRef)).thenReturn(passage)
        doNothing().whenever(cacheSpy)["updateCachedPassage"](chapterRef, passage)

        val result = cacheSpy.getPassage(chapterRef)
        assertEquals(passage, result)
        verify(cacheSpy).updateCachedPassage(chapterRef, passage)
        verify(cacheSpy, never()).retrieveCachedPassage(any())
        verify(esvClient).queryPassage(chapterRef)
    }
}