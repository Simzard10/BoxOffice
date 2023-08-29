package com.example.androidspring.movieApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var baseUrl: String = "http://www.kobis.or.kr"
    var api: RetrofitMovieAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RetrofitMovieAPI::class.java)
    }
}