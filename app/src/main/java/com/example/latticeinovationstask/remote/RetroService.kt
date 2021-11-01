package com.example.latticeinovationstask.remote

import com.example.latticeinovationstask.model.Article
import com.example.latticeinovationstask.remote.RetroInstance.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    //API to fetch top headlines in the country
    @GET("everything")
    fun dataFromAPI(
        @Query("q") topic: String = "bitcoin",
        @Query("pageSize") pageSize: Int = 100,
        @Query("language") lang: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<Article>


    // API to search articles
    @GET("everything")
    fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("pageSize") pageSize: Int = 100,
        @Query("language") lang: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<Article>


}