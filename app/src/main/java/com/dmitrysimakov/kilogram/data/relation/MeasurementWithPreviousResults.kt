package com.dmitrysimakov.kilogram.data.relation

import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class MeasurementWithPreviousResults(
        val id: String?,
        val param: String,
        val value: Double?,
        val date: Date?,
        val prevValue: Double? = null,
        val prevDate: Date? = null
)

class MeasurementsWithPreviousResultsDiffCallback : DiffUtil.ItemCallback<MeasurementWithPreviousResults>() {
    override fun areItemsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
            oldItem == newItem
}