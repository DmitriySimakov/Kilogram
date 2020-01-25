package com.dmitrysimakov.kilogram.ui.home.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.databinding.ItemTrainingExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingExercisesAdapter(
        clickCallback: ((TrainingExercise) -> Unit),
        private val finishIconClickCallback: ((TrainingExercise) -> Unit)? = null
) : DataBoundListAdapter<TrainingExercise, ItemTrainingExerciseBinding>(
        clickCallback,
        TrainingExerciseDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemTrainingExerciseBinding = ItemTrainingExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingExerciseBinding, item: TrainingExercise) {
        binding.exercise = item
        binding.dragFinishIcon.setOnClickListener { finishIconClickCallback?.invoke(item) }
    }
}