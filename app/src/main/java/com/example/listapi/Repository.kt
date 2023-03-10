package com.example.listapi

import com.example.listapi.retrofit.ApiInterface
import retrofit2.http.Url

class Repository {
    private val apiInterface = ApiInterface.create()
    suspend fun getTags(url: String) = apiInterface.getTags(url)
    suspend fun getHero(url: String) = apiInterface.getData(url)

}