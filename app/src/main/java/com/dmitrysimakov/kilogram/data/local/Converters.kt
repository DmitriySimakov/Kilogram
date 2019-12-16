package com.dmitrysimakov.kilogram.data.local

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {
    
    private val offsetDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    
    @TypeConverter fun toOffsetDateTime(value: String?) = value?.let {
        offsetDateTimeFormatter.parse(value, OffsetDateTime::from)
    }
    
    @TypeConverter fun fromOffsetDateTime(date: OffsetDateTime?) = date?.format(offsetDateTimeFormatter)
    
    
    private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    
    @TypeConverter fun toLocalDate(value: String?) = value?.let {
        localDateFormatter.parse(value, LocalDate::from)
    }
    
    @TypeConverter fun fromLocalDate(date: LocalDate?) = date?.format(localDateFormatter)
}