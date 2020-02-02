package com.dmitrysimakov.kilogram.ui.common.measurements

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.data.local.relation.MeasurementsWithPreviousResultsDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MeasurementsAdapter (
        clickCallback: ((MeasurementWithPreviousResults) -> Unit)? = null
) : DataBoundListAdapter<MeasurementWithPreviousResults, ItemMeasurementBinding>(
        clickCallback,
        MeasurementsWithPreviousResultsDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemMeasurementBinding = ItemMeasurementBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementBinding, item: MeasurementWithPreviousResults) {
        binding.measurement = item
    }
}