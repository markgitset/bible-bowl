package net.markdrew.biblebowl.ws

import net.markdrew.biblebowl.BANNER
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Application keys must be included with each API request in an Authorization header.

fun main() {

    println(BANNER)

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.esv.org/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory()
            .build()

    val service: EsvService = retrofit.create(EsvService::class.java)

//    val call: Call<PassageText> = service.text("Rev13,Rev13:1",
//    val call: Call<PassageText> = service.text("Jud-Rev1:12",
    val call: Call<PassageText> = service.text("1 Samuel2")
    val passage: PassageText? = call.execute().body()
    passage?.passages?.forEach { println(it) }

}
