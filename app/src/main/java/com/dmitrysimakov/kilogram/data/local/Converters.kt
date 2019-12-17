package com.dmitrysimakov.kilogram.data.local

import androidx.room.TypeConverter
import com.dmitrysimakov.kilogram.util.toIsoString
import com.dmitrysimakov.kilogram.util.toLocalDate
import com.dmitrysimakov.kilogram.util.toOffsetDateTime
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime

class Converters {
    
    @TypeConverter fun toOffsetDateTime(value: String?) = value?.toOffsetDateTime()
    
    @TypeConverter fun fromOffsetDateTime(date: OffsetDateTime?) = date?.toIsoString()
    
    
    @TypeConverter fun toLocalDate(value: String?) = value?.toLocalDate()
    
    @TypeConverter fun fromLocalDate(date: LocalDate?) = date?.toIsoString()
}