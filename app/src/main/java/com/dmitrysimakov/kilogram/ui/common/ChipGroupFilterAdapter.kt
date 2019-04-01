package com.dmitrysimakov.kilogram.ui.common

import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipGroupFilterAdapter(private val group: ChipGroup, private val callback: ((Long, Boolean) -> Unit)) {
    
    fun submitList(newList: List<FilterParam>) {
        group.removeAllViews()
        for (item in newList) {
            val chip = LayoutInflater.from(group.context).inflate(R.layout.chip_filter, group, false) as Chip
            chip.text = item.name
            chip.isChecked = item.is_active
            chip.setOnCheckedChangeListener{ _, isChecked -> callback(item._id, isChecked) }
            group.addView(chip)
        }
    }
}