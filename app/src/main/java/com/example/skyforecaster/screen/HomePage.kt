package com.example.skyforecaster.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skyforecaster.R
import com.example.skyforecaster.model.WeatherMapApiModel
import com.example.skyforecaster.network.NetworkResponse
import com.example.skyforecaster.storage.LocalStorage
import com.example.skyforecaster.utility.Constant.HOME_PAGE
import com.example.skyforecaster.utility.Constant.WEATHER_DATA
import com.example.skyforecaster.utility.Util
import com.example.skyforecaster.viewmodel.WeatherViewModel
import com.google.gson.Gson


@Composable
fun HomePageWithNavigation(weatherViewModel: WeatherViewModel) {
    // Initialize NavController
    val navController = rememberNavController()

    // Set up the navigation graph
    NavHost(navController = navController, startDestination = HOME_PAGE) {
        composable(HOME_PAGE) {
            PreviewHomePage(weatherViewModel, navController)
        }
        composable("detail/{weatherData}") { backStackEntry ->
            val weatherJson = backStackEntry.arguments?.getString(WEATHER_DATA)
            val weatherData = Gson().fromJson(weatherJson, WeatherMapApiModel::class.java)
            PreviewDetailsPage(navController, weatherData, LocalContext.current)
        }
    }
}


@Composable
fun PreviewHomePage(weatherViewModel: WeatherViewModel, navController: NavController) {
    MaterialTheme {
        Column {
            SearchBar(
                weatherViewModel,
                navController = navController
            )
            ShowFavourites(
                navController = navController
            )
        }
    }
}

@Composable
fun SearchBar(viewModel: WeatherViewModel, navController: NavController) {

    // State variables
    var city by remember {
        mutableStateOf("")
    }

    // Flag to redirect to detail page
    var redirect by remember {
        mutableStateOf(false)
    }

    // Observe the weather result
    val weatherResult = viewModel.weatherResult.observeAsState()

    // Keyboard Controller
    val keyboardController = LocalSoftwareKeyboardController.current

    Log.d("TAG", "SearchBar")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f)
                    .testTag("SearchField"),
                shape = MaterialTheme.shapes.medium,
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(text = "Search for any location")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (city.isNotEmpty()) {
                            redirect = true
                            viewModel.getData(city)
                            keyboardController?.hide()
                        }
                    },
                        modifier = Modifier.testTag("OpenDetailsPage")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search for any location"
                        )
                    }
                }
            )


        }

        // Handle different network response states
        when (val result = weatherResult.value) {

            // Handle error state
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            // Handle loading state
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            // Handle successful response state
            is NetworkResponse.Success -> {
                Log.d("TAG", "Redirecting to detail page")

                //
                if (redirect) {
                    navController.navigate("detail/${Gson().toJson(result.data)}")

                    // Reset redirect flag
                    redirect = false
                }

            }

            null -> {
            }
        }

    }
}


@Composable
fun ShowFavourites(navController: NavController) {

    // Retrieve the list of weather data from the local storage
    val localStorage = LocalStorage(LocalContext.current)
    val weatherMapDataList = localStorage.getWeatherMapDataList()

    // if no favourites are added
    if (weatherMapDataList.isEmpty()) {
        EmptyFavoritesScreen()
        return
    }
    // Display the list of favourites
    else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item(weatherMapDataList.size) {
                weatherMapDataList
                    .forEach { singleCityData ->
                        FavouritesItem(singleCityData, navController)
                    }
            }

        }
    }
}


@Composable
fun EmptyFavoritesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image or illustration
        Image(
            painter = painterResource(id = R.drawable.empty_list), // Add your illustration here
            contentDescription = "No Favorites",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )

        // Message text
        Text(
            text = "No Favorite Cities Yet!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Secondary text
        Text(
            text = "Start adding cities to your favorites list",
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}


@Composable
fun FavouritesItem(singleCityData: WeatherMapApiModel, navController: NavController) {

    // Get the current weather data according to the current time
    val currentDataToShow = Util.getClosestTemperature(singleCityData.list)

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("detail/${Gson().toJson(singleCityData)}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E).copy(0.1f),
            contentColor = Color.White
        )

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = singleCityData.city.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Text(
                    text = "${(currentDataToShow?.main?.temp?.minus(273.15))?.toInt()}°",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    text = Util.getCurrentTimeFormatted(),
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = currentDataToShow?.weather?.get(0)?.main ?: "",
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                }
                Text(
                    text = "H:${(currentDataToShow?.main?.temp_max?.minus(273.15))?.toInt()}° L:${
                        (currentDataToShow?.main?.temp_min?.minus(
                            273.15
                        ))?.toInt()
                    }°",

                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}