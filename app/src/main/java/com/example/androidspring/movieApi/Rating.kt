package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating (
    @SerializedName("watchGradeNm")
    var watchGradeNm: String?
) : Serializable {

}