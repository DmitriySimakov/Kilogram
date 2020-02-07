package com.dmitrysimakov.kilogram.data.relation

import androidx.recyclerview.widget.DiffUtil

data class ProportionsCalculatorItem(
        val param: String,
        val coefficient: Double,
        val value: Double? = null,
        val recommendedValue: Double? = null,
        val percent: Double? = null
)

class ProportionsCalculatorItemDiffCallback : DiffUtil.ItemCallback<ProportionsCalculatorItem>() {
    override fun areItemsTheSame(oldItem: ProportionsCalculatorItem, newItem: ProportionsCalculatorItem) =
            oldItem.param == newItem.param
    override fun areContentsTheSame(oldItem: ProportionsCalculatorItem, newItem: ProportionsCalculatorItem) =
            oldItem == newItem
}