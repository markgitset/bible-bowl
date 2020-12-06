package net.markdrew.biblebowl.ws

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Application keys must be included with each API request in an Authorization header.
// Authorization: Token 20e9b0330b3824603c4e2696c75d51c92529babc

fun main(args: Array<String>) {

    println("Bible Bowl!")

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory()
            .build()

    val service: EsvService = retrofit.create(EsvService::class.java)

    val call: Call<PassageText> = service.text("Rev13,Rev13:1",
            includePassageReferences = false,
            includeFootnotes = false,
            includeFootnoteBody = false,
            includeShortCopyright = false,
            includePassageHorizontalLines = true,
            includeHeadingHorizontalLines = true,
            horizontalLineLength = 40,
            includeHeadings = true,
            indentParagraphs = 0,
            indentPoetry = false,
            indentPoetryLines = 0,
            indentDeclares = 0,
            indentPsalmDoxology = 0,
            lineLength = 40)
    val passage: PassageText? = call.execute().body()
    println(passage)

}
