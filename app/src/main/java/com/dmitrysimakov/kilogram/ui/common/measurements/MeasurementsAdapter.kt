package com.dmitrysimakov.kilogram.ui.common.measurements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.data.relation.MeasurementsWithPreviousResultsDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemMeasurementBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.millisToDays
import com.dmitrysimakov.kilogram.util.toSignedString

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
        
        if (item.value != null && item.prevValue != null && item.date != null && item.prevDate != null) {
            val res = binding.root.context.resources
            
            val valueDiff = item.value - item.prevValue
            val dateDiff =  millisToDays(item.date.time - item.prevDate.time)
            
            val color = when {
                valueDiff > 0.0 -> R.color.light_green
                valueDiff < 0.0 -> R.color.red600
                else -> R.color.black
            }
    
            binding.measureDifference.visibility = View.VISIBLE
            binding.measureDifference.text = "${valueDiff.toSignedString()} за $dateDiff дн."
            binding.measureDifference.setTextColor(res.getColor(color))
        } else {
            binding.measureDifference.visibility = View.INVISIBLE
        }
    }
}