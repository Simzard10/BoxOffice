package com.example.androidspring

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidspring.movieApi.BoxOfficeDto
import com.example.androidspring.movieApi.BoxOfficeResponse
import com.example.androidspring.movieApi.MovieInfo
import com.example.androidspring.movieApi.MovieInfoResponse
import com.example.androidspring.movieApi.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class RankActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var list: List<BoxOfficeDto>
    //lateinit var movieInfoList: List<MovieDto>
    lateinit var textView2: TextView
    lateinit var movieCd: String

    companion object {
        const val KEY = "54b408a67510f640f635378b77536444"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        recyclerView = findViewById(R.id.rv_result)
        recyclerView.adapter = ResultRecyclerViewAdpater()
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val bundle = intent.extras
        list = bundle?.getSerializable("movieList") as List<BoxOfficeDto>
        Log.d("My", "RankActivity의 MovieList : $list")

        textView2 = findViewById(R.id.textView2)

        val rankTitle = intent.getStringExtra("rankTitle")
        textView2.text = rankTitle
    }

    inner class ResultRecyclerViewAdpater :
        RecyclerView.Adapter<ResultRecyclerViewAdpater.ResultViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ResultRecyclerViewAdpater.ResultViewHolder {
            //마지막 인자 -> parent에 합성(?)을 시킬지
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            return ResultViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(
            holder: ResultRecyclerViewAdpater.ResultViewHolder,
            position: Int
        ) {
            holder.apply {
                rankTextView.text = list[position].rank
                val rankInten = list[position].rankInten
                rankIntenTextView.text = rankInten
                movieNameTextView.text = list[position].movieNm
                //movieAudienceTextView.text = list[position].audiAcc

                movieCd = list[position].movieCd.toString()
                Log.d("movieCd1", movieCd)

                val movieAudienceCount = list[position].audiAcc?.toIntOrNull() ?: 0
                val formattedAudience: String = if (movieAudienceCount >= 10000) {
                    val audienceInTenThousand = movieAudienceCount / 10000
                    "$audienceInTenThousand 만명"
                } else {
                    "${movieAudienceCount}명"
                }
                movieAudienceTextView.text = formattedAudience

                if (rankInten?.toInt()!! < 0) {
                    rankIntenImageView.setImageResource(R.drawable.ic_sort_down_solid)
                    rankIntenTextView.setTextColor(R.color.red)
                }
            }
        }

        inner class ResultViewHolder(val view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {
            //위의 onCreateViewHolder에서 생성된 view를 가지고 실행함
            //클래스 안의 클래스
            //item_detail의 아이들을 가지고 옴 findviewbyid 로
            val rankTextView: TextView = view.findViewById(R.id.rank)
            val movieNameTextView: TextView = view.findViewById(R.id.movie_name)
            val rankIntenTextView: TextView = view.findViewById(R.id.tv_rankInten)
            val rankIntenImageView: ImageView = view.findViewById(R.id.iv_rankInten)
            val movieAudienceTextView: TextView = view.findViewById(R.id.movie_audience)

            init {
                movieNameTextView.setOnClickListener(this)
            }

            override fun onClick(v: View) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedMovie = list[position]
                    val movieCode = selectedMovie.movieCd
                    Log.d("movieCd2", movieCode.toString())

                    // API 호출을 통해 영화 상세 정보를 가져오고, 결과를 처리합니다.
                    RetrofitBuilder.api
                        .getMovieInfoList(movieCode, KEY)
                        .enqueue(object : Callback<MovieInfoResponse> {
                            override fun onFailure(call: Call<MovieInfoResponse>, t: Throwable) {
                                Toast.makeText(this@RankActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<MovieInfoResponse>, response: Response<MovieInfoResponse>) {
                                val movieInfoResponse = response.body()
                                val movieInfoList = movieInfoResponse!!.movieInfoResult!!.movieInfo!!
                                Log.d("상세정보", "$movieInfoList")

                                val intent: Intent = Intent(this@RankActivity, MovieActivity::class.java)
                                //bundle -> 보따리 느낌
                                val bundle = Bundle()
                                bundle.putSerializable(
                                    "movieInfoList",
                                    (movieInfoList as Serializable)
                                ) //list를 강제 형변환
                                //bundle.putString("name","홍길동")
                                //bundle.putInt("age",10)  이 두줄과 같이 put 뒤에 전달하려는 값의 자료형을 쓰면된다.
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }
                        })
                }
            }
        }
    }
}