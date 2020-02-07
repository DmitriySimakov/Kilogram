package com.dmitrysimakov.kilogram.util

import android.annotation.SuppressLint
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.text.SimpleDateFormat
import java.util.*

fun String.toLocalDateTime(): LocalDateTime? = try {
    LocalDateTime.from(ISO_LOCAL_DATE_TIME.parse(this))
} catch (e: Exception) { null }
fun LocalDateTime.toIsoString(): String = format(ISO_LOCAL_DATE_TIME)

@SuppressLint("SimpleDateFormat")
private val iso8601DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

fun String.toDate() = try {
    iso8601DateFormat.parse(this)
} catch (e: Exception) { null }
fun Date.toIsoString(): String = iso8601DateFormat.format(this)

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
fun LocalDateTime.toDate() = Date(toInstant(ZoneOffset.UTC).toEpochMilli())
