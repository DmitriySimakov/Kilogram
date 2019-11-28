package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "measurement",
        indices = [Index(value = ["param"])],
        foreignKeys = [
            ForeignKey(
                    entity = MeasurementParam::class,
                    parentColumns = ["name"],
                    childColumns = ["param"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class Measurement(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val date: LocalDate,
        val param: String,
        val value: Double
)