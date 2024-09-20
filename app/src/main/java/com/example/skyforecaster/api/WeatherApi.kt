package com.example.skyforecaster.api

import com.example.skyforecaster.model.WeatherMapApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // This function is used to get the weather data from the API
    @GET("/data/2.5/forecast")
    suspend fun getWeather(
        @Query("appid") apikey : String,
        @Query("q") city : String
    ) : Response<WeatherMapApiModel>

}