package com.dmitrysimakov.kilogram.ui.home.programs.program_days

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.model.ProgramDayDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemProgramDayDraggableBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ProgramDaysAdapter(
        clickCallback: ((ProgramDay) -> Unit)? = null
) : DataBoundListAdapter<ProgramDay, ItemProgramDayDraggableBinding>(clickCallback, ProgramDayDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemProgramDayDraggableBinding = ItemProgramDayDraggableBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramDayDraggableBinding, item: ProgramDay) {
        binding.day = item
    }
}