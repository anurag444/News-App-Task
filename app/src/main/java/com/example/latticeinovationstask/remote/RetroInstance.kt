package com.example.latticeinovationstask.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val baseURL = "https://newsapi.org/v2/"
        //const val API_KEY = "325f96dc8cd340f2a43a001986e21efe"
        const val API_KEY = "e7695feef6d345138d56382e32fba52e"
        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}