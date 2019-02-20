package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ProgramExerciseItemBinding
import com.dmitrysimakov.kilogram.databinding.TrainingExerciseItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ProgramDayExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((ProgramExerciseR) -> Unit)? = null
) : DataBoundListAdapter<ProgramExerciseR, ProgramExerciseItemBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ProgramExerciseItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { exercise?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ProgramExerciseItemBinding, item: ProgramExerciseR) {
        binding.exercise = item
    }
}