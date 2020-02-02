package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.relation.NewMeasurement
import com.dmitrysimakov.kilogram.data.local.relation.NewMeasurementDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementInputBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MeasurementInputAdapter(
        clickCallback: ((NewMeasurement) -> Unit)? = null
) : DataBoundListAdapter<NewMeasurement, ItemMeasurementInputBinding>(clickCallback, NewMeasurementDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementInputBinding = ItemMeasurementInputBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementInputBinding, item: NewMeasurement) {
        binding.measurement = item
    }
}