package com.example.skyforecaster.model

data class WeatherMapApiModel(
    val city: City,
    val cnt: String,
    val cod: String,
    val list: List<Item0>,
    val message: Int
)