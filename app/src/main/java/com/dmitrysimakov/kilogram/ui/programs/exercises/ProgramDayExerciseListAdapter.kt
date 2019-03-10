package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemProgramExerciseBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ProgramDayExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((ProgramExerciseR) -> Unit)? = null
) : DataBoundListAdapter<ProgramExerciseR, ItemProgramExerciseBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemProgramExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramExerciseBinding, item: ProgramExerciseR) {
        binding.exercise = item
    }
}