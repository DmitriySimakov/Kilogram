package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.databinding.ItemProgramBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChooseProgramAdapter(clickCallback: ((Program) -> Unit)? = null)
    : DataBoundListAdapter<Program, ItemProgramBinding>(clickCallback, ProgramDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemProgramBinding = ItemProgramBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramBinding, item: Program) {
        binding.program = item
    }
}

private class ProgramDiffCallback : DiffUtil.ItemCallback<Program>() {
    override fun areItemsTheSame(oldItem: Program, newItem: Program) =
            oldItem._id == newItem._id
    override fun areContentsTheSame(oldItem: Program, newItem: Program) =
            oldItem == newItem
}