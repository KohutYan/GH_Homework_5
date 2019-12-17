package com.kohutyan.gh_homework_5

import org.json.JSONObject

data class Weather(
    val speed: JSONObject,
    val desc: String,
    val temp: String
)
