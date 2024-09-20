package com.example.skyforecaster.network

//T refers to WeatherModel
sealed class NetworkResponse<out T> {

    // Success state with data
    data class Success<out T>(val data : T) : NetworkResponse<T>()

    // Error state with a message
    data class Error(val message : String) : NetworkResponse<Nothing>()

    // Loading state
    object Loading : NetworkResponse<Nothing>()
}