package com.dmitrysimakov.kilogram.ui.measurements.add_measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.MeasurementR
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class MeasurementsAdapter (
        appExecutors: AppExecutors,
        clickCallback: ((MeasurementR) -> Unit)?
) : DataBoundListAdapter<MeasurementR, ItemMeasurementBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemMeasurementBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { measurement?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ItemMeasurementBinding, item: MeasurementR) {
        binding.measurement = item
    }
}