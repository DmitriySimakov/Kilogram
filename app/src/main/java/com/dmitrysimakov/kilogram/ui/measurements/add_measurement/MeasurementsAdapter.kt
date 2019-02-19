package com.dmitrysimakov.kilogram.ui.measurements.add_measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.MeasurementR
import com.dmitrysimakov.kilogram.databinding.MeasurementItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class MeasurementsAdapter (
        appExecutors: AppExecutors,
        clickCallback: ((MeasurementR) -> Unit)?
) : DataBoundListAdapter<MeasurementR, MeasurementItemBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = MeasurementItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { measurement?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: MeasurementItemBinding, item: MeasurementR) {
        binding.measurement = item
    }
}