package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.biblebowl.model.toVerseRef
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * @param includePassageReferences Include a line at the top displaying the reference of the passage.
 * Must be true or false. Default true.
 * @param includeFirstVerseNumbers Display the verse number at the beginnings of chapters, e.g. [23:1].
 * Must be true or false. Default true.
 * @param includeVerseNumbers Display verse numbers in brackets, e.g. [4]. Must be true or false. Default true.
 * @param includeFootnotes Display callouts to footnotes in the text. Must be true or false. Default true.
 * @param includeFootnoteBody Display footnotes at the end of the text. Must be true or false. Default true.
 * @param includeShortCopyright Include (ESV) at the end of the text. This fulfills your copyright display
 * requirements. Must be true or false. Default true.
 * @param includeCopyright Include a longer copyright at the end of the text. Mutually exclusive with
 * include_short_copyright. This fulfills your copyright display requirements. Must be true or false. Default false.
 * @param includePassageHorizontalLines Include a visual line of equal signs (====) above the beginning of each
 * passage. Must be true or false. Default true.
 * @param includeHeadingHorizontalLines Include a visual line of underscores (____) above each section heading.
 * Must be true or false. Default true.
 * @param horizontalLineLength Control the length of the line for include_passage_horizontal_lines and
 * include_heading_horizontal_lines. Must be an integer. Default 55.
 * @param includeHeadings Include extra-textual section headings, e.g. The Creation of the World.
 * Must be true or false. Default true.
 * @param includeSelahs Include Selah in certain Psalms. Must be true or false. Default true.
 * @param indentUsing Should indentation use spaces or tabs? Must be 'space' or 'tab'. Default 'space'.
 * @param indentParagraphs How many indentation characters to begin a paragraph with. Must be an integer.
 * Default 2.
 * @param indentPoetry Should poetry lines be indented? Must be true or false. Default true.
 * @param indentPoetryLines How many indentation characters to use for a poetry indentation level.
 * Must be an integer. Default 4.
 * @param indentDeclares How many indentation characters to use for Declares the LORD in some of the prophets.
 * Must be an integer. Default 40.
 * @param indentPsalmDoxology How many indentation characters to use for Psalm doxologies. Must be an integer.
 * Default 30.
 * @param lineLength How long may a line be before it is wrapped? Use 0 for unlimited line lengths.
 * Must be an integer. Default 0.
 */
class EsvClient(val includePassageReferences: Boolean = true,
                val includeFirstVerseNumbers: Boolean = true,
                val includeVerseNumbers: Boolean = true,
                val includeFootnotes: Boolean = true,
                val includeFootnoteBody: Boolean = true,
                val includeShortCopyright: Boolean = true,
                val includeCopyright: Boolean = false,
                val includePassageHorizontalLines: Boolean = true,
                val includeHeadingHorizontalLines: Boolean = true,
                val horizontalLineLength: Int = 55,
                val includeHeadings: Boolean = true,
                val includeSelahs: Boolean = true,
                val indentUsing: String = "space",
                val indentParagraphs: Int = 2,
                val indentPoetry: Boolean = true,
                val indentPoetryLines: Int = INDENT_POETRY_LINES,
                val indentDeclares: Int = 40,
                val indentPsalmDoxology: Int = 30,
                val lineLength: Int = 0,
                val esvService: EsvService = EsvService.create()) {

    fun query(query: String): Call<PassageText> = esvService.text(query,
        includePassageReferences,
        includeFirstVerseNumbers,
        includeVerseNumbers,
        includeFootnotes,
        includeFootnoteBody,
        includeShortCopyright,
        includeCopyright,
        includePassageHorizontalLines,
        includeHeadingHorizontalLines,
        horizontalLineLength,
        includeHeadings,
        includeSelahs,
        indentUsing,
        indentParagraphs,
        indentPoetry,
        indentPoetryLines,
        indentDeclares,
        indentPsalmDoxology,
        lineLength
    )

    fun queryPassage(singleQuery: String): Passage {
        require(',' !in singleQuery) { "singleQuery may not contain a comma!" }
        println("query = $singleQuery")
        val call: Call<PassageText> = query(singleQuery)
        val response: Response<PassageText> = call.execute()
        return response.body()?.single() ?: throw Exception(response.errorBody()?.string())
    }

    fun bookByChapters(book: Book): Sequence<Passage> {
        var chapterNum: Int? = 1
        return generateSequence {
            if (chapterNum == null) null
            else {
                val chapter = queryPassage("$book$chapterNum")
                val nextChapterV1: VerseRef? = chapter.meta.nextChapter?.first()?.toVerseRef()
                chapterNum = if (nextChapterV1?.book == book) nextChapterV1.chapter else null
                chapter
            }
        }
    }

    companion object {

        fun create(): EsvClient = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EsvClient::class.java)

    }

}