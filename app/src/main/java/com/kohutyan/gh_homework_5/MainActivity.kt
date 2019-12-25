package com.kohutyan.gh_homework_5

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kohutyan.gh_homework_5.recycler.MainAdapter
import com.kohutyan.gh_homework_5.retrofit.WeatherResponse
import com.kohutyan.gh_homework_5.retrofit.WeatherService
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.*

open class MainActivity : AppCompatActivity() {

    companion object {
        val weatherArray: ArrayList<Weather> = ArrayList()
        val name = "Cherkasy"
        val country = "UA"
        val API = "a48d2d9cf4888e7eddf74f7a21666708"
        val BaseUrl = "api.openweathermap.org/"
    }

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(weatherArray)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = adapter
        //weatherTask().execute()
    }

    fun GetCurrentData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(name, country, API)

        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {

                })
            }
            /*inner class weatherTask() : AsyncTask<String, Void, String>() {
            override fun onPreExecute() {
                super.onPreExecute()
                progressBar.visibility = View.VISIBLE
            }

            override fun doInBackground(vararg params: String?): String {
                var response: String?
                response = try {
                    //api.openweathermap.org/data/2.5/forecast/daily?q={CivicLocationKeys.CITY},&cnt=7,&units=metric,&appid=$API
                    URL("api.openweathermap.org/data/2.5/weather?q={$CITY},{$COUNTRY}&units=metric&appid=$API").readText(
                        Charsets.UTF_8
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                return response.toString()
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                try {
                    val jsonObj = JSONObject(result)
                    val sys = jsonObj.getJSONObject("sys")
                    val main = jsonObj.getJSONObject("main")
                    val wind = jsonObj.getJSONObject("wind")
                    val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                    val temp = main.getString("temp") + "°C"
                    val windSpeed = wind.getString("speed")
                    val weatherDescription = weather.getString("description")
                    val address = jsonObj.getString("name") + ", " + sys.getString("country")
                    val humidity = main.getString("humidity")

                    val weather2 = Weather(main.toString(), weatherDescription, temp)
                    val wind2 = Weather(wind.toString(), windSpeed, humidity)

                    weatherArray.add(weather2)
                    weatherArray.add(wind2)

                    progressBar.visibility = View.GONE

                    adapter.notifyDataSetChanged()

                } catch (e: java.lang.Exception) {
                    progressBar.visibility = View.GONE
                }

            }
        }*/

        }
    }
}