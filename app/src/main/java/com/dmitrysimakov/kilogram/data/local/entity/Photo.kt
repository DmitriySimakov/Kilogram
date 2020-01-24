package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
data class Photo(
        @PrimaryKey val uri: String,
        val dateTime: OffsetDateTime
)