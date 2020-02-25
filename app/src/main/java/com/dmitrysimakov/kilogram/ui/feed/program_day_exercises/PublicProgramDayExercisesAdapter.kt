package com.dmitrysimakov.kilogram.ui.feed.program_day_exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.model.ProgramDayExerciseDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemPublicProgramExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class PublicProgramDayExercisesAdapter(
        clickCallback: ((ProgramDayExercise) -> Unit)? = null
) : DataBoundListAdapter<ProgramDayExercise, ItemPublicProgramExerciseBinding>(clickCallback, ProgramDayExerciseDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemPublicProgramExerciseBinding = ItemPublicProgramExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemPublicProgramExerciseBinding, item: ProgramDayExercise) {
        binding.exercise = item
    }
}