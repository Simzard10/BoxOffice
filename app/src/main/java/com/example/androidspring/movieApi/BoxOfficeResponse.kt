package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName

data class BoxOfficeResponse(
    @SerializedName("boxOfficeResult")
    var boxofficeResult: BoxOfficeResult?
)