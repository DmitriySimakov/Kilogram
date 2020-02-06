package com.dmitrysimakov.kilogram.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Photo(
        @PrimaryKey val uri: String = "",
        val dateTime: Date = Date(),
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false
)