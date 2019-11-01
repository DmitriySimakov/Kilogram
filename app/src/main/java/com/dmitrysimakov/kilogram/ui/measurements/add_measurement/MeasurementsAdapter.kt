package com.dmitrysimakov.kilogram.ui.measurements.add_measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MeasurementsAdapter (clickCallback: ((MeasurementWithPreviousResults) -> Unit)? = null)
    : DataBoundListAdapter<MeasurementWithPreviousResults, ItemMeasurementBinding>(clickCallback,
        object : DiffUtil.ItemCallback<MeasurementWithPreviousResults>() {
            override fun areItemsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementBinding = ItemMeasurementBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementBinding, item: MeasurementWithPreviousResults) {
        binding.measurement = item
    }
}