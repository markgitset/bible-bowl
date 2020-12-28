package net.markdrew.biblebowl.ws

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EsvService {

    /**
     * @param query (required) Passage reference query. Example references: Jn11.35; 43011035; Genesis 1-11;
     * 01001001-01011032; Ps1,3; 19001001-19001006,19003001-19003008
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
    @Headers("Authorization: Token 20e9b0330b3824603c4e2696c75d51c92529babc")
    @GET("v3/passage/text")
    fun text(@Query("q") query: String,
             @Query("include-passage-references") includePassageReferences: Boolean = true,
             @Query("include-first-verse-numbers") includeFirstVerseNumbers: Boolean = true,
             @Query("include-verse-numbers") includeVerseNumbers: Boolean = true,
             @Query("include-footnotes") includeFootnotes: Boolean = true,
             @Query("include-footnote-body") includeFootnoteBody: Boolean = true,
             @Query("include-short-copyright") includeShortCopyright: Boolean = true,
             @Query("include-copyright") includeCopyright: Boolean = false,
             @Query("include-passage-horizontal-lines") includePassageHorizontalLines: Boolean = true,
             @Query("include-heading-horizontal-lines") includeHeadingHorizontalLines: Boolean = true,
             @Query("horizontal-line-length") horizontalLineLength: Int = 55,
             @Query("include-headings") includeHeadings: Boolean = true,
             @Query("include-selahs") includeSelahs: Boolean = true,
             @Query("indent-using") indentUsing: String = "space",
             @Query("indent-paragraphs") indentParagraphs: Int = 2,
             @Query("indent-poetry") indentPoetry: Boolean = true,
             @Query("indent-poetry-lines") indentPoetryLines: Int = 4,
             @Query("indent-declares") indentDeclares: Int = 40,
             @Query("indent-psalm-doxology") indentPsalmDoxology: Int = 30,
             @Query("line-length") lineLength: Int = 0): Call<PassageText>

    companion object {

        fun create(): EsvService = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EsvService::class.java)

    }

}