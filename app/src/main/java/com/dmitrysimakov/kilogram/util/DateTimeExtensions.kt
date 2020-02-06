package com.dmitrysimakov.kilogram.util

import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE
import org.threeten.bp.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toOffsetDateTime() = ISO_OFFSET_DATE_TIME.parse(this, OffsetDateTime::from)
fun OffsetDateTime.toIsoString() = format(ISO_OFFSET_DATE_TIME)

fun String.toLocalDate() = ISO_LOCAL_DATE.parse(this, LocalDate::from)
fun LocalDate.toIsoString() = format(ISO_LOCAL_DATE)

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return try { dateFormat.parse(this) } catch (e: ParseException) { null }
}

fun Date.toIsoString(): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(this)
}