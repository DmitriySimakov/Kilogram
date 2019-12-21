package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.relation.NewMeasurement
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementInputBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MeasurementInputAdapter(clickCallback: ((NewMeasurement) -> Unit)? = null)
    : DataBoundListAdapter<NewMeasurement, ItemMeasurementInputBinding>(clickCallback, MeasurementDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementInputBinding = ItemMeasurementInputBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementInputBinding, item: NewMeasurement) {
        binding.measurement = item
    }
}

private class MeasurementDiffCallback : DiffUtil.ItemCallback<NewMeasurement>() {
    override fun areItemsTheSame(oldItem: NewMeasurement, newItem: NewMeasurement) =
            oldItem.param == newItem.param
    override fun areContentsTheSame(oldItem: NewMeasurement, newItem: NewMeasurement) =
            oldItem == newItem
}