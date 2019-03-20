@file:JvmName("Converter")
package com.dmitrysimakov.kilogram.util

import java.text.SimpleDateFormat
import java.util.*

fun secondsToTimeFormat(seconds: Int): String {
    return if (seconds < 60) {
        seconds.toString()
    } else {
        val min = seconds / 60
        val sec = seconds % 60
        String.format("%d:%02d", min, sec)
    }
}

fun millisToDateTimeFormat(milliseconds: Long): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(milliseconds))
}

fun calendarToDateFormat(calendar: Calendar): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
}

fun calendarToTimeFormat(calendar: Calendar): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
}