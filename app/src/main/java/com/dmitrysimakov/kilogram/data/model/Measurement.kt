package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

@Entity(indices = [Index(value = ["param"])],
        foreignKeys = [
            ForeignKey(
                    entity = MeasurementParam::class,
                    parentColumns = ["name"],
                    childColumns = ["param"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class Measurement(
        val param: String = "",
        val value: Double = 0.0,
        val date: Date = Date(),
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
)

class MeasurementDiffCallback : DiffUtil.ItemCallback<Measurement>() {
    override fun areItemsTheSame(oldItem: Measurement, newItem: Measurement) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Measurement, newItem: Measurement) =
            oldItem == newItem
}