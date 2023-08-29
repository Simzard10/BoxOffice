package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Actor (
    @SerializedName("peopleNm")
    var peopleNm: String?
) : Serializable {

}
