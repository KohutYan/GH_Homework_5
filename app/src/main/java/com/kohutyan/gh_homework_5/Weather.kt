package com.kohutyan.gh_homework_5


data class Weather(
    val speed: String,
    val desc: String,
    val temp: String
)

data class Result (val results: List<Weather>)