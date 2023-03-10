package com.example.listapi.retrofit

import com.example.listapi.model.DataAll
import com.example.listapi.model.DataAllItem
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET()
    suspend fun getData(@Url url: String): Response<DataAllItem>
    @GET()
    suspend fun getTags(@Url url:String): Response<DataAll>
    //Aquí posem les operacions GET,POST, PUT i DELETE vistes abans
    companion object {
        val BASE_URL = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}