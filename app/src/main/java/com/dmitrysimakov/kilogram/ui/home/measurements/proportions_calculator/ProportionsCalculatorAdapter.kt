package com.dmitrysimakov.kilogram.ui.home.measurements.proportions_calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.local.relation.ProportionsCalculatorItem
import com.dmitrysimakov.kilogram.data.local.relation.ProportionsCalculatorItemDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemProportionsCalculatorBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import kotlin.math.absoluteValue

class ProportionsCalculatorAdapter(
        private val viewModel: ProportionsCalculatorViewModel,
        clickCallback: ((ProportionsCalculatorItem) -> Unit)? = null
) : DataBoundListAdapter<ProportionsCalculatorItem, ItemProportionsCalculatorBinding>(
        clickCallback,
        ProportionsCalculatorItemDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemProportionsCalculatorBinding = ItemProportionsCalculatorBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProportionsCalculatorBinding, item: ProportionsCalculatorItem) {
        binding.item = item
        binding.vm = viewModel
        
        item.percent?.let { percent ->
            val sign = if (percent > 0) "+" else ""
            binding.percent.text =  "$sign$percent%"
            
            val res = binding.root.context.resources
            val abs = percent.absoluteValue
            val color = when {
                abs < 1 -> R.color.green
                abs < 2 -> R.color.light_green
                abs < 3 -> R.color.lime
                abs < 4 -> R.color.yellow
                abs < 6 -> R.color.amber
                abs < 8 -> R.color.orange
                abs < 10 -> R.color.orange_deep
                abs < 15 -> R.color.red500
                else -> R.color.red800
            }
            binding.percent.setTextColor(res.getColor(color))
        }
    }
}