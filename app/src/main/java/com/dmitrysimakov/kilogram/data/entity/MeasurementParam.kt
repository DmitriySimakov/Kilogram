package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement_param")
data class MeasurementParam(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val name: String,
        val image: String? = null,
        val instruction: String? = null,
        val coefficient: Double? = null
)