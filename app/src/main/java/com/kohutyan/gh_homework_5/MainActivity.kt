package com.kohutyan.gh_homework_5

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL
import java.util.*

open class MainActivity : AppCompatActivity() {

    companion object {
        val weatherArray: ArrayList<Weather> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(weatherArray)
        //fetchJson()
        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String {
            var response: String?
            val CITY = "Cherkasy"
            val COUNTRY = "UA"
            val API = "a48d2d9cf4888e7eddf74f7a21666708"
            response = try {
                //api.openweathermap.org/data/2.5/forecast/daily?q={CivicLocationKeys.CITY},&cnt=7,&units=metric,&appid=$API
                URL("api.openweathermap.org/data/2.5/weather?q={$CITY},{$COUNTRY}&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            } catch (e: Exception) {
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

                val weather2 = Weather(main, weatherDescription, temp)
                val wind2 = Weather(wind ,windSpeed, humidity)

                weatherArray.add(weather2)
                weatherArray.add(wind2)


            } catch (e: java.lang.Exception) {
                progressBar.visibility = View.GONE
            }

        }
    }
}

/*
fun fetchJson() {
    println("Attempting fo fetch JSON")

    val url =
        "https://api.openweathermap.org/data/2.5/weather?q=Cherkasy,ua&appid=a48d2d9cf4888e7eddf74f7a21666708"

    val request = Request.Builder().url(url).build()

    val client = OkHttpClient()
    client.newCall(request).enqueue(object: Callback{
        override fun onFailure(call: Call, e: IOException) {
            println("Request failed")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response?.body?.string()
            println(body)

            val gson = GsonBuilder().create()

            val weather = gson.fromJson(body, weather::class.java)
        }
    })
}

class weather(val main: String)
class main(val temp:Double)
class wind(val speed: Double)*/

