package com.dmitrysimakov.kilogram.ui.common

import android.view.LayoutInflater
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipGroupFilterAdapter(private val group: ChipGroup, private val callback: ((String, Boolean) -> Unit)) {
    
    fun submitList(newList: List<FilterParam>) {
        group.removeAllViews()
        for (item in newList) {
            val chip = LayoutInflater.from(group.context).inflate(R.layout.view_chip_filter, group, false) as Chip
            chip.text = item.name
            chip.isChecked = item.isActive
            chip.setOnCheckedChangeListener{ _, isChecked -> callback(item.name, isChecked) }
            group.addView(chip)
        }
    }
}