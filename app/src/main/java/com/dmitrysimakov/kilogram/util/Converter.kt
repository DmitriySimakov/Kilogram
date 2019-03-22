@file:JvmName("Converter")
package com.dmitrysimakov.kilogram.util

import java.text.SimpleDateFormat
import java.util.*

fun secondsToTimeFormat(seconds: Int): String {
    val millis = seconds * 1000L
    var pattern = "ss"
    if (seconds >= 60) pattern = "mm:$pattern"
    if (seconds >= 60*60) pattern = "HH:$pattern"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("GMT")
    return sdf.format(Date(millis))
}

fun millisToDateTimeFormat(milliseconds: Long): String {
    return SimpleDateFormat("EE. dd MMMM yyyy г. HH:mm", Locale.getDefault()).format(Date(milliseconds))
}

fun calendarToDateFormat(calendar: Calendar): String {
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.time)
}

fun calendarToTimeFormat(calendar: Calendar): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
}