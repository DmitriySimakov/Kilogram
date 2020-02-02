package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDiffCallback
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