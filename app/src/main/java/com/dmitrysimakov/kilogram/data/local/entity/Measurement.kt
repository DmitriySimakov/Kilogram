package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "measurement",
        indices = [Index(value = ["param_id"])],
        foreignKeys = [
            ForeignKey(
                    entity = MeasurementParam::class,
                    parentColumns = ["_id"],
                    childColumns = ["param_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class Measurement(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val date: String,
        val param_id: Long,
        val value: Double
)