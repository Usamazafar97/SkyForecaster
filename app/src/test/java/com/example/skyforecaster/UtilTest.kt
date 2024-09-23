package com.example.skyforecaster

import com.example.skyforecaster.model.Clouds
import com.example.skyforecaster.model.Item0
import com.example.skyforecaster.model.Main
import com.example.skyforecaster.model.Sys
import com.example.skyforecaster.model.Wind
import com.example.skyforecaster.utility.Util
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UtilTest {

    val mockMain1 = Main(
        feels_like = 20.0,
        grnd_level = 1000,
        humidity = 75,
        pressure = 1013,
        sea_level = 1013,
        temp = 22.0,
        temp_kf = 0.0,
        temp_max = 24.0,
        temp_min = 18.0
    )

    val mockMain2 = Main(
        feels_like = 21.0,
        grnd_level = 1005,
        humidity = 80,
        pressure = 1015,
        sea_level = 1015,
        temp = 23.0,
        temp_kf = 0.1,
        temp_max = 25.0,
        temp_min = 19.0
    )



    // Test for a valid date
    @Test
    fun `test convertTime with valid date`() {
        val inputData = "2023-09-27 12:00:00"
        val expectedOutput = "12 pm"

        val result = Util.convertTime(inputData)
        assertEquals(result, expectedOutput)

    }

    // Test for today
    @Test
    fun `test extractDayOfWeek for today`() {

        val todayDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val result = Util.extractDayOfWeek(todayDate)
        assertEquals("Today", result)
    }


    // Test for a past date
    @Test
    fun `test extractDayOfWeek for past date`() {
        val inputData = "2023-09-27 12:00:00"
        val expectedDay = "Wed"

        val result = Util.extractDayOfWeek(inputData)
        assertEquals(expectedDay, result)
    }

    @Test
    fun `test getCurrentTimeFormatted returns valid format`() {

        // Check if the format is correct
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val result = Util.getCurrentTimeFormatted()

            // Matches "h:mm AM" or "h:mm PM"
            val expectedFormat = Regex("\\d{1,2}:\\d{2} [AP]M")

            assertTrue(expectedFormat.matches(result))

        }
    }

    @Test
    fun `test getClosestTemperature finds correct item`() {

        val itemList = listOf(
            Item0(
                clouds = Clouds(10), dt = 1, dt_txt = "2023-09-19 12:00:00", main = mockMain1,
                pop = 0.1,
                sys = Sys(""),
                visibility = 1000,
                weather = listOf(),
                wind = Wind(1, 1.0, 1.0)
            ),
            Item0(
                clouds = Clouds(20),
                dt = 2,
                dt_txt = "2023-09-19 18:00:00",
                main = mockMain2,
                pop = 0.1,
                sys = Sys(""),
                visibility = 1000,
                weather = listOf(),
                wind = Wind(1, 1.0, 1.0)
            )
        )

        val result = Util.getClosestTemperature(itemList)

        assertNotNull(result)
        assertEquals(mockMain2.temp, result?.main?.temp)

    }

    @Test
    fun `test get5dayForecast returns first item for each day`(){

        val itemList = listOf(
            Item0(
                clouds = Clouds(10), dt = 1, dt_txt = "2023-09-19 12:00:00", main = mockMain1,
                pop = 0.1,
                sys = Sys(""),
                visibility = 1000,
                weather = listOf(),
                wind = Wind(1, 1.0, 1.0)
            ),
            Item0(
                clouds = Clouds(20),
                dt = 2,
                dt_txt = "2023-09-19 18:00:00",
                main = mockMain2,
                pop = 0.1,
                sys = Sys(""),
                visibility = 1000,
                weather = listOf(),
                wind = Wind(1, 1.0, 1.0)
            )
        )

        val result = Util.get5DayForecast(itemList)

        assertEquals(1, result.size)
        assertEquals("2023-09-19 12:00:00", result[0].dt_txt)

    }
}