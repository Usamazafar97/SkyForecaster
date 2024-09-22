package com.example.skyforecaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.skyforecaster.screen.HomePageWithNavigation
import com.example.skyforecaster.ui.theme.SkyForecasterTheme
import com.example.skyforecaster.viewmodel.WeatherViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        // Initialize ViewModel
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        setContent {

            // Remember SystemUiController
            val systemUiController = rememberSystemUiController()

            // Set the status bar color to a custom color
            systemUiController.setStatusBarColor(
                color = Color(0xFF000000),
                darkIcons = false // Change to true in case of  dark icons
            )

            SkyForecasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        HomePageWithNavigation(weatherViewModel)
//                        FavouritesPage()
                    }
                }
            }
        }
    }
}

