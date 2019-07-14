package com.dmitrysimakov.kilogram.ui.measurements.add_measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.MeasurementR
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MeasurementsAdapter (
        appExecutors: AppExecutors,
        clickCallback: ((MeasurementR) -> Unit)? = null
) : DataBoundListAdapter<MeasurementR, ItemMeasurementBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<MeasurementR>() {
            override fun areItemsTheSame(oldItem: MeasurementR, newItem: MeasurementR) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: MeasurementR, newItem: MeasurementR) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup) = ItemMeasurementBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemMeasurementBinding, item: MeasurementR) {
        binding.measurement = item
    }
}