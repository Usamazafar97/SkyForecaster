package com.example.skyforecaster

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skyforecaster.model.WeatherMapApiModel
import com.example.skyforecaster.screen.PreviewDetailsPage
import com.example.skyforecaster.screen.PreviewHomePage
import com.example.skyforecaster.viewmodel.WeatherViewModel
import com.google.gson.Gson
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// Example test with mocked ViewModel using Mockito
@RunWith(AndroidJUnit4::class)
class InputTest {

    // Create a Compose Test Rule
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val weatherViewModel = WeatherViewModel()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    PreviewHomePage(weatherViewModel, navController)
                }

                composable("details/{weatherData}") { backStackEntry ->
                    val weatherJson = backStackEntry.arguments?.getString("weatherData")
                    val weatherData = Gson().fromJson(weatherJson, WeatherMapApiModel::class.java)
                    PreviewDetailsPage(navController, weatherData, LocalContext.current)
                }
            }
        }
    }


    @Test
    fun enterCityAndClickSearchTest() {

        // Enter a city and click the search button
        composeTestRule.onNodeWithTag("SearchField")
            .performTextInput("Berlin")

        composeTestRule.onNodeWithTag("OpenDetailsPage")
            .performClick()
            .assertExists()
    }

    @Test
    fun searchFieldIsEmptyOnStartTest() {

        // Check if the search field is empty on start
        composeTestRule.onNodeWithTag("SearchField")
            .assertTextEquals("")
    }

    @Test
    fun searchFieldDisplaysPlaceHolderTest() {

        // Check if the search field displays the placeholder text
        composeTestRule.onNodeWithText("Search for any location")
            .assertExists()
            .assertIsDisplayed()

    }

}