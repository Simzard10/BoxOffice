package com.example.androidspring

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidspring.movieApi.MovieInfo
import org.w3c.dom.Text

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val selectedMovie = intent.getSerializableExtra("movieInfoList") as? MovieInfo

        if (selectedMovie != null) {
            val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
            val textViewReleaseDate = findViewById<TextView>(R.id.textViewReleaseDate)
            val textViewMovieRating = findViewById<TextView>(R.id.textViewMovieRating)
            val textViewRunningTime = findViewById<TextView>(R.id.textViewRunningTime)
            val textViewDirector = findViewById<TextView>(R.id.textViewDirector)
            val textViewActors = findViewById<TextView>(R.id.textViewActors)
            //val textViewRating = findViewById<TextView>(R.id.textViewMovieRating)
            // ... 다른 TextView들도 가져오기
            // 관람등급 처리
            val audits = selectedMovie.audits.joinToString(", ") { it.watchGradeNm ?: "" }
            textViewMovieRating.text = "관람등급: ${audits}"

            // 감독 처리
            val directors = selectedMovie.directors.joinToString(", ") { it.peopleNm ?: "" }
            textViewDirector.text = "감독: ${directors}"

            // 배우 처리
            val actors = selectedMovie.actors.take(10).joinToString(", ") { it.peopleNm ?: "" }
            textViewActors.text = "배우: ${actors}"

            textViewTitle.text = selectedMovie.movieNm


            val openDt = selectedMovie.openDt
            val year = openDt?.substring(0, 4) // "2023"
            val month = openDt?.substring(4, 6) // "08"
            val day = openDt?.substring(6, 8) // "29"

            val formattedOpenDt = "${year}년 ${month}월 ${day}일"
            textViewReleaseDate.text = "개봉일: ${formattedOpenDt}"
            textViewRunningTime.text = "상영시간: ${selectedMovie.showTm}분"
            // ... 다른 TextView들 설정
        } else {
            Log.v("오류", "오류 발생")
            // selectedMovie가 null인 경우 처리
            // 예를 들어 오류 메시지 표시 등
        }
    }
}
