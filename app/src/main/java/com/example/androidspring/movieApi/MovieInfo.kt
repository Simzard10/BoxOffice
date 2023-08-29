package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieInfo(
    //@SerialzedName : JSON에서 데이터에 매칭되는 이름 명시
    //@Expose : 해당값이 null일경우 json으로 만들 필드를 자동 생략하겠다! 는 명령어
    @SerializedName("movieNm")
    var movieNm: String?,
    @SerializedName("openDt")
    var openDt: String?,
    @SerializedName("showTm")
    var showTm: String?,

    @SerializedName("directors")
    var directors: List<Director> = arrayListOf(),

    @SerializedName("audits")
    var audits: List<Rating> = arrayListOf(),

    @SerializedName("actors")
    var actors: List<Actor> = arrayListOf()


) : Serializable {

}