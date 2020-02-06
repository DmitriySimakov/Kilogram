package com.dmitrysimakov.kilogram.util

import android.annotation.SuppressLint
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE
import org.threeten.bp.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import java.text.SimpleDateFormat
import java.util.*

fun String.toOffsetDateTime() = ISO_OFFSET_DATE_TIME.parse(this, OffsetDateTime::from)
fun OffsetDateTime.toIsoString() = format(ISO_OFFSET_DATE_TIME)

fun String.toLocalDate() = ISO_LOCAL_DATE.parse(this, LocalDate::from)
fun LocalDate.toIsoString() = format(ISO_LOCAL_DATE)

@SuppressLint("SimpleDateFormat")
private val iso8601DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
            .apply { timeZone = TimeZone.getTimeZone("UTC") }

fun String.toDate() = try { iso8601DateFormat.parse(this) } catch (e: Exception) { null }
fun Date.toIsoString(): String = iso8601DateFormat.format(this)