package net.markdrew.biblebowl.ws

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Application keys must be included with each API request in an Authorization header.

fun main(args: Array<String>) {

    println("Bible Bowl!")

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory()
            .build()

    val service: EsvService = retrofit.create(EsvService::class.java)

//    val call: Call<PassageText> = service.text("Rev13,Rev13:1",
//    val call: Call<PassageText> = service.text("Jud-Rev1:12",
    val call: Call<PassageText> = service.text("Gen1-Gen2",
        includePassageReferences = false,
        includeShortCopyright = false,
        includePassageHorizontalLines = false
    )
    val passage: PassageText? = call.execute().body()
    passage?.passages?.forEach { println(it) }

}
