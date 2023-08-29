package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName

data class MovieInfoResult(
    @SerializedName("movieInfo")
    var movieInfo: MovieInfo
)
