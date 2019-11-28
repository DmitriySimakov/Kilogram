package com.dmitrysimakov.kilogram.data.local

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {
    
    private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    
    @TypeConverter fun toOffsetDateTime(value: String?) = value?.let {
        dateTimeFormatter.parse(value, OffsetDateTime::from)
    }
    
    @TypeConverter fun fromOffsetDateTime(date: OffsetDateTime?) = date?.format(dateTimeFormatter)
    
    
    @TypeConverter fun toLocalDate(value: String?) = value?.let {
        dateTimeFormatter.parse(value, LocalDate::from)
    }
    
    @TypeConverter fun fromLocalDate(date: LocalDate?) = date?.format(dateTimeFormatter)
}