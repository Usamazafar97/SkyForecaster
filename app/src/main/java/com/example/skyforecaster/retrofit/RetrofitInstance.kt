package com.example.skyforecaster.retrofit

import com.example.skyforecaster.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL for the API endpoint
    private const val baseUrl = "https://api.openweathermap.org/";

    // Retrofit instance
    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Weather API instance
    val weatherApi : WeatherApi = getInstance().create(WeatherApi::class.java)

}