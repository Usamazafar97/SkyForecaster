package com.example.skyforecaster.utility


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.skyforecaster.model.Item0
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object Util {

    fun convertDate(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }

    fun convertTime(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh a", Locale.getDefault())

        val date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }

    fun convertToDayOfWeek(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateTime)

        // Get the current date without time
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Get the parsed date in calendar format without time
        val targetDate = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Check if the target date is today
        return if (currentDate == targetDate) {
            "Today"
        } else {
            // Format as a day of the week (e.g., Fri, Sat, Sun)
            val outputFormat = SimpleDateFormat("EEE", Locale.getDefault())
            outputFormat.format(date)
        }
    }

    fun extractDayOfWeek(dtTxt: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dtTxt)

        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val todayDate = Calendar.getInstance()
        val targetDate = Calendar.getInstance().apply { time = date }

        // Check if the date is today
        return if (todayDate.get(Calendar.YEAR) == targetDate.get(Calendar.YEAR) &&
            todayDate.get(Calendar.DAY_OF_YEAR) == targetDate.get(Calendar.DAY_OF_YEAR)) {
            "Today"
        } else {
            // Return day abbreviation (e.g., Fri, Sat)
            dayFormat.format(date)
        }
    }

    fun get5DayForecast(forecastList: List<Item0>): List<Item0> {
        val daysAdded = mutableSetOf<String>()  // Keep track of which days are already added
        val filteredForecast = mutableListOf<Item0>()

        for (item in forecastList) {
            val dayOfWeek = extractDayOfWeek(item.dt_txt)
            if (!daysAdded.contains(dayOfWeek)) {
                filteredForecast.add(item)  // Add only the first item for the day
                daysAdded.add(dayOfWeek)  // Mark the day as added
            }

            // Stop once we have 5 days
            if (filteredForecast.size == 5) break
        }

        return filteredForecast
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimeFormatted(): String {
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        return currentTime.format(formatter)
    }

    fun getClosestTemperature(itemList: List<Item0>): Item0? {
        // Define the date format that matches `dt_txt`
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Assuming UTC time zone for dt_txt

        // Get the current time
        val now = Date()

        // Find the closest time
        var closestItem: Item0? = null
        var minDifference = Long.MAX_VALUE

        for (item in itemList) {
            val itemDate = dateFormat.parse(item.dt_txt) ?: continue
            val timeDifference = Math.abs(now.time - itemDate.time)

            if (timeDifference < minDifference) {
                minDifference = timeDifference
                closestItem = item
            }
        }

        // Return the temperature of the closest time
        return closestItem
    }

}