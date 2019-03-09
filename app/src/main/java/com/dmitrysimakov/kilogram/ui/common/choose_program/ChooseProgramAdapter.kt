package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.databinding.ItemProgramBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ChooseProgramAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Program) -> Unit)? = null
) : DataBoundListAdapter<Program, ItemProgramBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemProgramBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { program?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ItemProgramBinding, item: Program) {
        binding.program = item
    }
}