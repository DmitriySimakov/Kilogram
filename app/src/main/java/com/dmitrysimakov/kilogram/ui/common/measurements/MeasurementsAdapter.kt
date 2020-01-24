package com.dmitrysimakov.kilogram.ui.common.measurements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MeasurementsAdapter (clickCallback: ((MeasurementWithPreviousResults) -> Unit)? = null)
    : DataBoundListAdapter<MeasurementWithPreviousResults, ItemMeasurementBinding>(
        clickCallback,
        MeasurementsWithPreviousResultsDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementBinding = ItemMeasurementBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementBinding, item: MeasurementWithPreviousResults) {
        binding.measurement = item
    }
}

private class MeasurementsWithPreviousResultsDiffCallback : DiffUtil.ItemCallback<MeasurementWithPreviousResults>() {
    override fun areItemsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MeasurementWithPreviousResults, newItem: MeasurementWithPreviousResults) =
            oldItem == newItem
}