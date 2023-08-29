package com.example.androidspring

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidspring.movieApi.BoxOfficeDto
import com.example.androidspring.movieApi.BoxOfficeResponse
import com.example.androidspring.movieApi.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var button4: Button


    companion object {
        const val KEY = "54b408a67510f640f635378b77536444"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button4 = findViewById(R.id.btn4)

        button4.setOnClickListener(this)


    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn4 -> {
                var cal: Calendar = Calendar.getInstance()
                var year = cal.get(Calendar.YEAR)
                var month = cal.get(Calendar.MONTH)
                var dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

                val textViewDate = findViewById<TextView>(R.id.textViewDate)
                val dateButton = findViewById<Button>(R.id.btn4)
                val datePicker = findViewById<DatePicker>(R.id.datePicker)


                textViewDate.text =
                    """
                        초기 설정된 날짜 [년도/월/일]
                        $year/${month+1}/$dayOfMonth
                        """.trimIndent()

                dateButton.setOnClickListener(this)


                if(v?.id == R.id.btn4) {
                    textViewDate.text = "\n" + datePicker.year.toString() + "/" +
                            (datePicker.month + 1).toString() + "/" + datePicker.dayOfMonth.toString()
                }

                year = datePicker.year
                month = datePicker.month
                dayOfMonth = datePicker.dayOfMonth


                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                val targetDt = dateFormat.format(cal.time)


                Log.d("targetDt", "$targetDt")
                val rankTitle = """$year/${month+1}/${dayOfMonth} 박스오피스 순위""".trimIndent()
                getResult(targetDt, rankTitle)



            }
        }

    }
    private fun getResult(targetDt: String, rankTitle: String){
        RetrofitBuilder.api
            .getMovieList(targetDt, KEY)
            .enqueue(object : Callback<BoxOfficeResponse> {
                override fun onFailure(call: Call<BoxOfficeResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BoxOfficeResponse>, response: Response<BoxOfficeResponse>) {
                    val movieResponse = response.body()
                    val list : List<BoxOfficeDto> = movieResponse!!.boxofficeResult!!.dailyBoxOfficeList
                    Log.d("MY", "$list")



                    val intent: Intent = Intent(this@MainActivity, RankActivity::class.java)
                    //bundle -> 보따리 느낌
                    val bundle = Bundle()
                    bundle.putSerializable(
                        "movieList",
                        (list as Serializable)
                    ) //list를 강제 형변환
                    //bundle.putString("name","홍길동")
                    //bundle.putInt("age",10)  이 두줄과 같이 put 뒤에 전달하려는 값의 자료형을 쓰면된다.
                    intent.putExtras(bundle)
                    intent.putExtra("rankTitle", rankTitle)
                    startActivity(intent)
                }
            })
    }

}