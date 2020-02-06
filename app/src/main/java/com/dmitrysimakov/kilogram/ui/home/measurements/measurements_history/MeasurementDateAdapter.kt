package com.dmitrysimakov.kilogram.ui.home.measurements.measurements_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementDateBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import java.util.*

class MeasurementDateAdapter(clickCallback: ((Date) -> Unit)? = null)
    : DataBoundListAdapter<Date, ItemMeasurementDateBinding>(clickCallback, MeasurementDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementDateBinding = ItemMeasurementDateBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementDateBinding, item: Date) {
        binding.date = item
    }
}

private class MeasurementDiffCallback : DiffUtil.ItemCallback<Date>() {
    override fun areItemsTheSame(oldItem: Date, newItem: Date) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Date, newItem: Date) = oldItem == newItem
}