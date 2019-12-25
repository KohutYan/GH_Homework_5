package com.kohutyan.gh_homework_5.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("name") name: String?, @Query("country") country: String?, @Query(
            "APPID"
        ) app_id: String?
    ): Call<WeatherResponse?>?
}