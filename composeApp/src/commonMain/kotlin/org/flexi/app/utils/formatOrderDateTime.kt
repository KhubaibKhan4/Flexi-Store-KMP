package org.flexi.app.utils

fun formatOrderDateTime(orderDateTime: String): Pair<String, String> {
    return try {
        val parts = orderDateTime.split(" ")
        val month = parts[1]
        val dayOfMonth = parts[2]
        val year = parts[5]
        val time = parts[3].substring(0, 5)
        val amPm = if (parts[3].substring(0, 2).toInt() < 12) "AM" else "PM"

        // Get the day name
        val dayName = parts[0]

        val formattedDate = "$dayName, $month $dayOfMonth, $year"
        val formattedTime = "$time $amPm"

        Pair(formattedDate, formattedTime)
    } catch (e: Throwable) {
        Pair("Unknown date", "Unknown time")
    }
}