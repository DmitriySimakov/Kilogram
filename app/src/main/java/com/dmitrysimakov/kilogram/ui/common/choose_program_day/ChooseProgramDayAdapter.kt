package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.databinding.ItemProgramDayBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChooseProgramDayAdapter(clickCallback: ((ProgramDay) -> Unit)? = null)
    : DataBoundListAdapter<ProgramDay, ItemProgramDayBinding>(
        clickCallback,
        ProgramDayDiffCallback()
) {
 
    
    override fun createBinding(parent: ViewGroup): ItemProgramDayBinding = ItemProgramDayBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramDayBinding, item: ProgramDay) {
        binding.day = item
    }
}

private class ProgramDayDiffCallback : DiffUtil.ItemCallback<ProgramDay>() {
    override fun areItemsTheSame(oldItem: ProgramDay, newItem: ProgramDay) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ProgramDay, newItem: ProgramDay) =
            oldItem == newItem
}