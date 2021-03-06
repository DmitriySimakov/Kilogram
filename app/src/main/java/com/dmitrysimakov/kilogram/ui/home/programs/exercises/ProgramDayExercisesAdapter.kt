package com.dmitrysimakov.kilogram.ui.home.programs.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.model.ProgramDayExerciseDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemProgramExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ProgramDayExercisesAdapter(
        clickCallback: ((ProgramDayExercise) -> Unit)? = null
) : DataBoundListAdapter<ProgramDayExercise, ItemProgramExerciseBinding>(clickCallback, ProgramDayExerciseDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemProgramExerciseBinding = ItemProgramExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramExerciseBinding, item: ProgramDayExercise) {
        binding.exercise = item
    }
}