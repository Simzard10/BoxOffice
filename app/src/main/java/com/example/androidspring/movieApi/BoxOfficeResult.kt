package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName

data class BoxOfficeResult(
    @SerializedName("dailyBoxOfficeList")
    var dailyBoxOfficeList: List<BoxOfficeDto> = arrayListOf()
    //받아온 결과를 MovieDto list 형태로 만든다.
)