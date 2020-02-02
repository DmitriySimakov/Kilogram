package com.dmitrysimakov.kilogram.data.local.relation

import androidx.recyclerview.widget.DiffUtil

data class NewMeasurement(val param: String, var value: Double? = null)

class NewMeasurementDiffCallback : DiffUtil.ItemCallback<NewMeasurement>() {
    override fun areItemsTheSame(oldItem: NewMeasurement, newItem: NewMeasurement) =
            oldItem.param == newItem.param
    override fun areContentsTheSame(oldItem: NewMeasurement, newItem: NewMeasurement) =
            oldItem == newItem
}