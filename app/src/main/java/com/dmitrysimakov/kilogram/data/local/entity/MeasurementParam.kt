package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MeasurementParam(
        @PrimaryKey val name: String,
        val instruction: String? = null,
        val coefficient: Double? = null
)