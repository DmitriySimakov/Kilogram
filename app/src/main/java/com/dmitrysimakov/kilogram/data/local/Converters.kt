package com.dmitrysimakov.kilogram.data.local

import androidx.room.TypeConverter
import com.dmitrysimakov.kilogram.util.toDate
import com.dmitrysimakov.kilogram.util.toIsoString
import java.util.*

class Converters {
    
    @TypeConverter fun toDate(value: String?) = value?.toDate()
    
    @TypeConverter fun fromDate(date: Date?) = date?.toIsoString()
}