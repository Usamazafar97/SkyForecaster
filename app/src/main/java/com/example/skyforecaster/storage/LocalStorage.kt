package com.example.skyforecaster.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.skyforecaster.model.WeatherMapApiModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.skyforecaster.utility.Constant.WEATHER_DATA

class LocalStorage(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Save the list of WeatherMapApiModel objects
    fun saveWeatherMapDataList(newData: WeatherMapApiModel) {
        val existingList = getWeatherMapDataList().toMutableList() // Get the current list
        // Check if the newData already exists in the list
        if (existingList.any { it.city.id == newData.city.id }) {
            // If the same data already exists, don't save
            Log.d(WEATHER_DATA, "This Data is already stored")
            return
        }

        // Add the new data to the list
        existingList.add(newData)
        Log.d(WEATHER_DATA, "Data saved successfully")
        val editor = sharedPreferences.edit()
        val json = gson.toJson(existingList) // Serialize list to JSON
        editor.putString("weather_map_data_list", json)
        editor.apply()
    }

    // Get the list of WeatherMapApiModel objects
    fun getWeatherMapDataList(): List<WeatherMapApiModel> {
        val json = sharedPreferences.getString("weather_map_data_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<WeatherMapApiModel>>() {}.type
            gson.fromJson(json, type) // Deserialize JSON to list
        } else {
            emptyList() // Return an empty list if nothing is stored
        }
    }
}