package com.dmitrysimakov.kilogram.ui.common.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.Exercise
import com.dmitrysimakov.kilogram.data.model.ExerciseDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseListAdapter(
        private val viewModel: ExercisesViewModel,
        clickCallback: ((Exercise) -> Unit)? = null
) : DataBoundListAdapter<Exercise, ItemExerciseBinding>(clickCallback, ExerciseDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemExerciseBinding = ItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseBinding, item: Exercise) {
        binding.exercise = item
        binding.vm = viewModel
    }
}