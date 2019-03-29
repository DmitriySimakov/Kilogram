package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipGroupFilterAdapter(private val group: ChipGroup, private val callback: ((Long, Boolean) -> Unit)) {
    
    val dataWasChanged = MutableLiveData<Boolean>()
    
    private val list = mutableListOf<Chip>()
    
    fun submitList(newList: List<FilterParam>) {
        group.removeAllViews()
        for (item in newList) {
            val chip = LayoutInflater.from(group.context).inflate(R.layout.chip_filter, group, false) as Chip
            chip.text = item.name
            chip.isChecked = item.isActive
            chip.setOnCheckedChangeListener{ _, isChecked -> callback(item._id, isChecked) }
            list.add(chip)
            group.addView(chip)
        }
        dataWasChanged.value = true
    }
    
    fun setChecked(id: Int, isChecked: Boolean) {
        if (id <= list.size) list[id - 1].isChecked = isChecked
    }
}