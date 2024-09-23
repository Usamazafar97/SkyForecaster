package com.example.skyforecaster.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.skyforecaster.model.Item0
import com.example.skyforecaster.model.WeatherMapApiModel
import com.example.skyforecaster.storage.LocalStorage
import com.example.skyforecaster.utility.Constant.HOME_PAGE
import com.example.skyforecaster.utility.Util

@Composable
fun PreviewDetailsPage(
    navController: NavHostController,
    weatherData: WeatherMapApiModel,
    current: Context
) {
    MaterialTheme {
        DetailsPage(navController, weatherData, current)
    }
}

@Composable
fun DetailsPage(
    navController: NavController,
    weatherData: WeatherMapApiModel,
    current: Context
) {

    // Handle the back press to navigate back
    BackHandler {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.testTag("DetailsPage")
    ) {
        AddAndCancelButton(navController = navController, weatherData, current)
        MainWeatherInfo(weatherData)
        HourlyForcastInfo(weatherData)
        FiveDayForecastInfo(weatherData)
    }
}

@Composable
private fun AddAndCancelButton(
    navController: NavController,
    weatherData: WeatherMapApiModel,
    current: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.testTag("CancelButton"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Set background to transparent
                contentColor = Color.Black // Text color
            ),
            onClick = {
                navController.popBackStack(HOME_PAGE, inclusive = false)
            }) {
            Text(text = "Cancel", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        Button(
            modifier = Modifier.testTag("AddButton"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Set background to transparent
                contentColor = Color.Black // Text color
            ), onClick = {
                saveData(weatherData, current)
                navController.navigate(HOME_PAGE)
            }) {
            Text(
                text = "Add",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

fun saveData(weatherData: WeatherMapApiModel, current: Context) {
    val localStorage = LocalStorage(current)
    localStorage.saveWeatherMapDataList(weatherData)
}

@Composable
fun MainWeatherInfo(weatherData: WeatherMapApiModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = weatherData.city.name, fontSize = 28.sp, fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = " ${(weatherData.list[0].main.temp - 273.15).toInt()} °c",
            fontSize = 48.sp,
            fontWeight = FontWeight.Thin,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = weatherData.list[0].weather[0].main,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "H:${(weatherData.list[0].main.temp_max - 273.15).toInt()}° L:${(weatherData.list[0].main.temp_min - 273.15).toInt()}°",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )

    }

}

@Composable
private fun HourlyForcastInfo(weatherData: WeatherMapApiModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E).copy(alpha = 0.1f),
            contentColor = Color.Black
        )
    ) {
        // Title
        Text(
            text = "DAILY FORECAST",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Row for hourly weather data
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var indexForHourlyForecast = 1
                items(7) {

                    // Call the forecast item composable
                    HourlyForecastItem(
                        Util.convertTime(weatherData.list[indexForHourlyForecast].dt_txt),
                        " ${(weatherData.list[indexForHourlyForecast].main.temp - 273.15).toInt()} °",
                        "https://openweathermap.org/img/wn/${weatherData.list[indexForHourlyForecast].weather[0].icon}.png"
                    )
                    indexForHourlyForecast += 1
                }
            }
        }
    }
}

@Composable
fun HourlyForecastItem(time: String, temperature: String, icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Time
        Text(
            text = time,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            modifier = Modifier.size(60.dp),
            model = icon,
            contentDescription = "Condition icon"
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Temperature
        Text(
            text = temperature,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun FiveDayForecastInfo(weatherData: WeatherMapApiModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E).copy(alpha = 0.1f),
            contentColor = Color.Black
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(
                text = "5-DAY FORECAST",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            ForecastTableView(Util.get5DayForecast(weatherData.list))
        }
    }
}

@Composable
fun ForecastTableView(forecastList: List<Item0>) {
    Column(modifier = Modifier.padding(16.dp)) {

        // Forecast rows
        forecastList.forEach { forecast ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = Util.extractDayOfWeek(forecast.dt_txt), modifier = Modifier.weight(1f))
                Text(text = forecast.weather.first().main, modifier = Modifier.weight(1f))
                Text(
                    text = "H:${(forecast.main.temp_max - 273.15).toInt()}° \tL:${(forecast.main.temp_min - 273.15).toInt()}°",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}