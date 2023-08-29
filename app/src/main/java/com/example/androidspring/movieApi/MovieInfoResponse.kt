package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName

data class MovieInfoResponse(
    @SerializedName("movieInfoResult")
    var movieInfoResult: MovieInfoResult?
)