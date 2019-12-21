package com.dmitrysimakov.kilogram.ui.home.measurements.measurements_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementDateBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import org.threeten.bp.LocalDate

class MeasurementDateAdapter(clickCallback: ((LocalDate) -> Unit)? = null)
    : DataBoundListAdapter<LocalDate, ItemMeasurementDateBinding>(clickCallback, MeasurementDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementDateBinding = ItemMeasurementDateBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementDateBinding, item: LocalDate) {
        binding.date = item
    }
}

private class MeasurementDiffCallback : DiffUtil.ItemCallback<LocalDate>() {
    override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate) = oldItem == newItem
    override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate) = oldItem == newItem
}