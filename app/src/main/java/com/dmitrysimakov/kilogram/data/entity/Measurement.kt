package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "measurement",
        foreignKeys = [
            ForeignKey(
                    entity = MeasurementParam::class,
                    parentColumns = ["_id"],
                    childColumns = ["param_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class Measurement(
        val date: String,
        val param_id: Long,
        val value: Double
) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}