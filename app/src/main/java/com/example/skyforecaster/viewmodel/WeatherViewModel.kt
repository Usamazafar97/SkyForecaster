package com.example.skyforecaster.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyforecaster.model.WeatherMapApiModel
import com.example.skyforecaster.network.NetworkResponse
import com.example.skyforecaster.retrofit.RetrofitInstance
import com.example.skyforecaster.utility.Constant
import kotlinx.coroutines.launch

class WeatherViewModel :ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherMapApiModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherMapApiModel>> = _weatherResult

    // Method to set weather data
    fun setWeatherData(data: WeatherMapApiModel) {
        _weatherResult.value  = NetworkResponse.Success(data)
    }

    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                val response = weatherApi.getWeather(Constant.apiKey,city)
                if(response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                    Log.d("WeatherViewModel1",response.message())
                }
            }
            catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
                Log.d("WeatherViewModel2",e.message.toString())
            }

        }
    }

}












