package com.dmitrysimakov.kilogram.ui.programs.programs

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.databinding.ProgramItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class ProgramsAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Program) -> Unit)?
) : DataBoundListAdapter<Program, ProgramItemBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ProgramItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { program?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ProgramItemBinding, item: Program) {
        binding.program = item
    }
}