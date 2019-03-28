package com.dmitrysimakov.kilogram.util

import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import com.dmitrysimakov.kilogram.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipGroupFilterAdapter(private val group: ChipGroup, callback: ((Int, Boolean) -> Unit)) {
    
    val notification = MutableLiveData<Boolean>()
    
    private val list = mutableListOf<Chip>()
    
    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener { view, isChecked ->
        list.findIndex {it.id == view.id}?.let { index ->
            callback.invoke(index + 1, isChecked)
        }
    }
    
    fun submitList(newList: List<HasName>) {
        for (item in newList) {
            val chip = LayoutInflater.from(group.context).inflate(R.layout.chip_filter, group, false) as Chip
            chip.text = item.name
            chip.setOnCheckedChangeListener(checkedChangeListener)
            list.add(chip)
            group.addView(chip)
        }
        notification.value = true
    }
    
    fun setChecked(id: Int, isChecked: Boolean) {
        if (id <= list.size) list[id - 1].isChecked = isChecked
    }
}