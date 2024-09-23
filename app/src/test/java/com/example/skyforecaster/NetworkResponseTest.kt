package com.example.skyforecaster

import com.example.skyforecaster.model.City
import com.example.skyforecaster.model.Coord
import com.example.skyforecaster.network.NetworkResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class NetworkResponseTest {
    @Test
    fun `test NetworkResponse success holds correct data`() {

        // Create a mock City object
        val mockCity = City(
            coord = Coord(lat = 52.5200, lon = 13.4050),  // Example coordinates for Berlin
            country = "DE",
            id = 1234,
            name = "Berlin",
            population = 3769000,
            sunrise = 1629874800,
            sunset = 1629925200,
            timezone = 7200
        )

        val successResponse = NetworkResponse.Success(mockCity)

        // Check if the response is a success
        assertTrue(successResponse is NetworkResponse.Success)
        assert(successResponse.data == mockCity)

        // Check if the data in the success response is correct
        assertEquals(successResponse.data.name,"Berlin")
        assertEquals(successResponse.data.coord.lat,52.5200)
        assertEquals(successResponse.data.coord.lon,13.4050)

    }

    @Test
    fun `test NetworkResponse error holds correct message`() {

        // Create a mock error message
        val errorMessage = "Failed to load data"
        val errorResponse = NetworkResponse.Error(errorMessage)

        // Check if the response is an error
        assertTrue(errorResponse is NetworkResponse.Error)
        assert(errorResponse.message == errorMessage)

    }

    @Test
    fun `test NetworkResponse loading state`() {

        // Create a loading response
        val loadingResponse = NetworkResponse.Loading

        // Check if the response is loading
        assertTrue(loadingResponse is NetworkResponse.Loading)
    }
}