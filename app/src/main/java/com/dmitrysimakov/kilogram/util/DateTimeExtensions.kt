package com.dmitrysimakov.kilogram.util

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE
import org.threeten.bp.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

fun String.toOffsetDateTime() = ISO_OFFSET_DATE_TIME.parse(this, OffsetDateTime::from)
fun OffsetDateTime.toIsoString() = format(ISO_OFFSET_DATE_TIME)
fun OffsetDateTime.toDate() = DateTimeUtils.toDate(this.toInstant())

fun String.toLocalDate() = ISO_LOCAL_DATE.parse(this, LocalDate::from)
fun LocalDate.toIsoString() = format(ISO_LOCAL_DATE)
fun LocalDate.toDate() = DateTimeUtils.toDate(atStartOfDay().toInstant(ZoneOffset.UTC))