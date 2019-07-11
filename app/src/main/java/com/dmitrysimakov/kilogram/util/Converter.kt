@file:JvmName("Converter")
package com.dmitrysimakov.kilogram.util

import android.text.format.DateUtils
import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.*

fun unbox(b: Boolean?): Boolean = b ?: false

@InverseMethod("unbox")
fun box(b: Boolean): Boolean? = b

fun intToString(value: Int?) = value?.toString() ?: ""

@InverseMethod("intToString")
fun stringToInt(string: String) = try { string.toInt() } catch (e: Exception) { null }

fun secondsToTimeFormat(seconds: Int?): String {
    if (seconds == null) return ""
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

fun dateToRelativeTimeSpan(date: Date?): String {
    return if (date == null) ""
    else DateUtils.getRelativeTimeSpanString(date.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString()
}

fun calendarToDateFormat(calendar: Calendar): String {
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.time)
}

fun calendarToTimeFormat(calendar: Calendar): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
}