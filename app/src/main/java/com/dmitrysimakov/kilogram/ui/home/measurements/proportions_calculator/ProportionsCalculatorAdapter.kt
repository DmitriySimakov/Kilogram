package com.dmitrysimakov.kilogram.ui.home.measurements.proportions_calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.relation.ProportionsCalculatorItem
import com.dmitrysimakov.kilogram.databinding.ItemProportionsCalculatorBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.round

class ProportionsCalculatorAdapter(
        private val viewModel: ProportionsCalculatorViewModel,
        clickCallback: ((ProportionsCalculatorItem) -> Unit)? = null
) : DataBoundListAdapter<ProportionsCalculatorItem, ItemProportionsCalculatorBinding>(clickCallback, MeasurementDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemProportionsCalculatorBinding = ItemProportionsCalculatorBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProportionsCalculatorBinding, item: ProportionsCalculatorItem) {
        binding.item = item
        binding.vm = viewModel
        val recommendedValue = item.recommendedValue
        val value = item.value
        if (recommendedValue != null && value != null) {
            val sign = if (recommendedValue > value) "+" else ""
            val percent = ((recommendedValue / value - 1) * 100).round(1)
            binding.percent.text =  "$sign$percent%"
        }
    }
}

private class MeasurementDiffCallback : DiffUtil.ItemCallback<ProportionsCalculatorItem>() {
    override fun areItemsTheSame(oldItem: ProportionsCalculatorItem, newItem: ProportionsCalculatorItem) =
            oldItem.param == newItem.param
    override fun areContentsTheSame(oldItem: ProportionsCalculatorItem, newItem: ProportionsCalculatorItem) =
            oldItem == newItem
}