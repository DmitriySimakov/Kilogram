package com.dmitrysimakov.kilogram.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.model.ProgramDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemProgramBinding

class ProgramsAdapter(
        clickCallback: ((Program) -> Unit)? = null
) : DataBoundListAdapter<Program, ItemProgramBinding>(clickCallback, ProgramDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemProgramBinding = ItemProgramBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramBinding, item: Program) {
        binding.program = item
    }
}