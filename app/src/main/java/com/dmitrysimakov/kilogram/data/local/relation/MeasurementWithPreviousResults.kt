package com.dmitrysimakov.kilogram.data.local.relation

import androidx.recyclerview.widget.DiffUtil
import org.threeten.bp.LocalDate

data class MeasurementWithPreviousResults(
        val id: Long?,
        val param: String,
        val value: Double?,
        val date: LocalDate?,
        val prevValue: Double? = null,
        val prevDate: LocalDate? = null
)

class MeasurementsWithPreviousResultsDiffCallback : DiffUtil.ItemCallback<MeasurementWithPreviousResults>() {
    override fun areItemsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
            oldItem == newItem
}