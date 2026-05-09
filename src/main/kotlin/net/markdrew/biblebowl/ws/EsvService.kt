package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.INDENT_POETRY_LINES
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for the ESV "passage/text" endpoint
 *
 * The full set of formatting parameters is exposed verbatim so callers can fine-tune the rendered text. All
 * parameters mirror the ones documented at <https://api.esv.org/docs/passage-text/>; defaults here match the
 * defaults [EsvIndexer] expects when parsing the response.
 */
interface EsvService {

    /**
     * Fetches the rendered text for [query]
     *
     * @param query passage reference (e.g. "Jn11.35", "43011035", "Genesis 1-11", or
     *   "01001001-01011032,19001001-19001006")
     * @param includePassageReferences include a leading line with the passage reference
     * @param includeFirstVerseNumbers display the verse number at the start of each chapter, e.g. `[23:1]`
     * @param includeVerseNumbers display verse numbers in brackets, e.g. `[4]`
     * @param includeFootnotes display callouts to footnotes inline in the text
     * @param includeFootnoteBody display footnote bodies at the end of the passage
     * @param includeShortCopyright include "(ESV)" at the end of the text
     * @param includeCopyright include the longer copyright at the end; mutually exclusive with
     *   [includeShortCopyright]
     * @param includePassageHorizontalLines draw a line of equal signs above each passage
     * @param includeHeadingHorizontalLines draw a line of underscores above each section heading
     * @param horizontalLineLength character width of the horizontal lines
     * @param includeHeadings include extra-textual section headings
     * @param includeSelahs include "Selah" in Psalms where it appears
     * @param indentUsing `"space"` or `"tab"`
     * @param indentParagraphs indent characters at the start of each paragraph
     * @param indentPoetry whether to indent poetry lines at all
     * @param indentPoetryLines indent characters per poetry indentation level
     * @param indentDeclares indent characters for "Declares the LORD" lines in the prophets
     * @param indentPsalmDoxology indent characters for Psalm doxologies
     * @param lineLength wrap column; 0 means unlimited
     */
    @GET("v3/passage/text")
    fun text(@Query("q") query: String,
             @Query("include-passage-references") includePassageReferences: Boolean = false,
             @Query("include-first-verse-numbers") includeFirstVerseNumbers: Boolean = true,
             @Query("include-verse-numbers") includeVerseNumbers: Boolean = true,
             @Query("include-footnotes") includeFootnotes: Boolean = true,
             @Query("include-footnote-body") includeFootnoteBody: Boolean = true,
             @Query("include-short-copyright") includeShortCopyright: Boolean = false,
             @Query("include-copyright") includeCopyright: Boolean = false,
             @Query("include-passage-horizontal-lines") includePassageHorizontalLines: Boolean = false,
             @Query("include-heading-horizontal-lines") includeHeadingHorizontalLines: Boolean = true,
             @Query("horizontal-line-length") horizontalLineLength: Int = 55,
             @Query("include-headings") includeHeadings: Boolean = true,
             @Query("include-selahs") includeSelahs: Boolean = true,
             @Query("indent-using") indentUsing: String = "space",
             @Query("indent-paragraphs") indentParagraphs: Int = 2,
             @Query("indent-poetry") indentPoetry: Boolean = true,
             @Query("indent-poetry-lines") indentPoetryLines: Int = INDENT_POETRY_LINES,
             @Query("indent-declares") indentDeclares: Int = 40,
             @Query("indent-psalm-doxology") indentPsalmDoxology: Int = 30,
             @Query("line-length") lineLength: Int = 0): Call<PassageText>

    companion object {

        /**
         * Builds an [EsvService] talking to api.esv.org
         *
         * If [token] is non-null, every outgoing request gets an `Authorization: Token <token>` header. The
         * ESV API will reject unauthenticated requests, so for live use [token] must come from
         * `ESV_API_TOKEN` (or be passed explicitly).
         */
        fun create(token: String? = null): EsvService {
            val builder = Retrofit.Builder()
                .baseUrl("https://api.esv.org/")
                .addConverterFactory(GsonConverterFactory.create())

            if (token != null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "Token $token")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
                builder.client(client)
            }

            return builder.build().create(EsvService::class.java)
        }

    }

}
