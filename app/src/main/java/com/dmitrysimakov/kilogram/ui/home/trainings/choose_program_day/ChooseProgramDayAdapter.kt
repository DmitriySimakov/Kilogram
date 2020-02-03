package com.dmitrysimakov.kilogram.ui.home.trainings.choose_program_day

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemProgramDayBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChooseProgramDayAdapter(
        clickCallback: ((ProgramDay) -> Unit)? = null
) : DataBoundListAdapter<ProgramDay, ItemProgramDayBinding>(clickCallback, ProgramDayDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemProgramDayBinding = ItemProgramDayBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramDayBinding, item: ProgramDay) {
        binding.day = item
    }
}