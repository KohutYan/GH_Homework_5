package com.kohutyan.gh_homework_5

import android.net.wifi.rtt.CivicLocationKeys
import android.net.wifi.rtt.CivicLocationKeys.CITY
import android.opengl.Visibility
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.net.URL
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import android.widget.ProgressBar as ProgressBar

class MainActivity : AppCompatActivity() {

    val CITY: String = "Cherkasy,ua"
    val API: String = "a48d2d9cf4888e7eddf74f7a21666708"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter()

        //fetchJson()
        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String {
            var response: String?
            response = try {
                //api.openweathermap.org/data/2.5/forecast/daily?q={CivicLocationKeys.CITY},&cnt=7,&units=metric,&appid=$API
                URL("https://api.openweathermap.org/data/2.5/weather?q=${CivicLocationKeys.CITY}&units=metric&appid=$API").readText(
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
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val sys = jsonObj.getJSONObject("sys")
                val main = jsonObj.getJSONObject("main")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getString("temp") + "°C"
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                //присваиваем
                findViewById<TextView>(R.id.mainWeather).text = weatherDescription
                findViewById<TextView>(R.id.temperature).text = temp
                findViewById<TextView>(R.id.wind).text = windSpeed

                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
            }catch (e:java.lang.Exception){
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
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

