package com.example.androidspring.movieApi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BoxOfficeDto(
    //@SerialzedName : JSON에서 데이터에 매칭되는 이름 명시
    //@Expose : 해당값이 null일경우 json으로 만들 필드를 자동 생략하겠다! 는 명령어
    @SerializedName("movieNm")
    var movieNm: String?,
    @SerializedName("rankInten")
    var rankInten: String?,
    @SerializedName("rank")
    var rank: String?,
    @SerializedName("audiAcc")
    var audiAcc: String?,
    @SerializedName("movieCd")
    var movieCd: String?
) : Serializable {

}