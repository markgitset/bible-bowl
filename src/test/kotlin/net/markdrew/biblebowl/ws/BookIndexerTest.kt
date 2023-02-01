package ws

import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.toVerseRef
import net.markdrew.biblebowl.ws.BookIndexer
import net.markdrew.biblebowl.ws.Passage
import net.markdrew.biblebowl.ws.PassageMeta
import net.markdrew.chupacabra.core.DisjointRangeMap
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BookIndexerTest {

    private val testMeta = PassageMeta(
        canonical = "Genesis 1:1–10",
        chapterStart = listOf(1001001, 1001031),
        chapterEnd = listOf(1001001, 1001031),
        prevVerse = null,
        nextVerse = 1001011,
        prevChapter = null,
        nextChapter = listOf(1002001, 1002025)
    )
    private val testPassage = Passage(
        canonical = "Genesis 1:1–10",
        range = 1001001..1001010,
        meta = testMeta,
        text = """
            _______________________________________________________
            The Creation of the World

            [1] In the beginning, God created the heavens and the earth. [2] The earth was without form and void, and darkness was over the face of the deep. And the Spirit of God was hovering over the face of the waters.

            [3] And God said, “Let there be light,” and there was light. [4] And God saw that the light was good. And God separated the light from the darkness. [5] God called the light Day, and the darkness he called Night. And there was evening and there was morning, the first day.

            [6] And God said, “Let there be an expanse(1) in the midst of the waters, and let it separate the waters from the waters.” [7] And God made(2) the expanse and separated the waters that were under the expanse from the waters that were above the expanse. And it was so. [8] And God called the expanse Heaven.(3) And there was evening and there was morning, the second day.

            [9] And God said, “Let the waters under the heavens be gathered together into one place, and let the dry land appear.” And it was so. [10] God called the dry land Earth,(4) and the waters that were gathered together he called Seas. And God saw that it was good.
            verse continuation(5) test 
            more(6) [11] verse(7) continuation test.

            [26] Then God said, “Let us make man(8) in our image, after our likeness. And let them have dominion over the fish of the sea and over the birds of the heavens and over the livestock and over all the earth and over every creeping thing that creeps on the earth.”
            
              [27] So God created man in his own image,
                  in the image of God he created him;
                  male and female he created them.
              
              
                [28] And God blessed them. And God said to them, “Be fruitful and multiply and fill the earth and subdue it, and have dominion over the fish of the sea and over the birds of the heavens and over every living thing that moves on the earth.”
            
            Footnotes

            (1) 1:6 Or *a canopy*; also verses 7, 8, 14, 15, 17, 20

            (2) 1:7 Or *fashioned*; also verse 16

            (3) 1:8 Or *Sky*; also verses 9, 14, 15, 17, 20, 26, 28, 30; 2:1

            (4) 1:10 Or *Land*; also verses 11, 12, 22, 24, 25, 26, 28, 30; 2:1
            (5) 1:10 test note 1
            (6) 1:10 test note 2
            (7) 1:11 test note 3
            
            (8) 1:26 The Hebrew word for *man* (*adam*) is the generic term for mankind and becomes the proper name *Adam*
        """.trimIndent())

    @Test
    fun indexChapter() {
        val indexer = BookIndexer(Book.GEN)
        with(indexer.indexBook(sequenceOf(testPassage))) {

            //println(text)
            assertEquals("""
                  In the beginning, God created the heavens and the earth. The earth was without form and void, and darkness was over the face of the deep. And the Spirit of God was hovering over the face of the waters.
                  And God said, “Let there be light,” and there was light. And God saw that the light was good. And God separated the light from the darkness. God called the light Day, and the darkness he called Night. And there was evening and there was morning, the first day.
                  And God said, “Let there be an expanse in the midst of the waters, and let it separate the waters from the waters.” And God made the expanse and separated the waters that were under the expanse from the waters that were above the expanse. And it was so. And God called the expanse Heaven. And there was evening and there was morning, the second day.
                  And God said, “Let the waters under the heavens be gathered together into one place, and let the dry land appear.” And it was so. God called the dry land Earth, and the waters that were gathered together he called Seas. And God saw that it was good.
                  verse continuation test 
                  more verse continuation test.
                  Then God said, “Let us make man in our image, after our likeness. And let them have dominion over the fish of the sea and over the birds of the heavens and over the livestock and over all the earth and over every creeping thing that creeps on the earth.”
                    So God created man in his own image,
                        in the image of God he created him;
                        male and female he created them.
                      And God blessed them. And God said to them, “Be fruitful and multiply and fill the earth and subdue it, and have dominion over the fish of the sea and over the birds of the heavens and over every living thing that moves on the earth.”
            """.trimIndent() + "\n", text)

            //println(verses)
            assertEquals(
                DisjointRangeMap(
                    0..55 to 1001001,
                    57..200 to 1001002,
                    202..257 to 1001003,
                    259..341 to 1001004,
                    343..461 to 1001005,
                    463..577 to 1001006,
                    579..715 to 1001007,
                    717..811 to 1001008,
                    813..941 to 1001009,
                    943..1091 to 1001010,
                    1093..1116 to 1001011,
                    1118..1371 to 1001026,
                    1375..1491 to 1001027,
                    1497..1730 to 1001028
                ).mapValues { (_, verseNum) -> verseNum.toVerseRef() },
                verses
            )

            //println(footnotes)
            assertEquals(
                mapOf(
                    501 to "Or *a canopy*; also verses 7, 8, 14, 15, 17, 20",
                    591 to "Or *fashioned*; also verse 16",
                    751 to "Or *Sky*; also verses 9, 14, 15, 17, 20, 26, 28, 30; 2:1",
                    973 to "Or *Land*; also verses 11, 12, 22, 24, 25, 26, 28, 30; 2:1",
                    1081 to "test note 1",
                    1092 to "test note 2",
                    1098 to "test note 3",
                    1149 to "The Hebrew word for *man* (*adam*) is the generic term for mankind and becomes the proper name *Adam*"
                ),
                footnotes
            )
        }
    }
}