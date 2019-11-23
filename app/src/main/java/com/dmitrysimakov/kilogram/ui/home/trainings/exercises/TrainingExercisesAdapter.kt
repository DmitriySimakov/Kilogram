package com.dmitrysimakov.kilogram.ui.home.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.databinding.ItemTrainingExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingExercisesAdapter(
        clickCallback: ((DetailedTrainingExercise) -> Unit),
        private val finishIconClickCallback: ((DetailedTrainingExercise) -> Unit)? = null
) : DataBoundListAdapter<DetailedTrainingExercise, ItemTrainingExerciseBinding>(
        clickCallback,
        DetailedTrainingExerciseDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemTrainingExerciseBinding = ItemTrainingExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingExerciseBinding, item: DetailedTrainingExercise) {
        binding.exercise = item
        binding.dragFinishIcon.setOnClickListener { finishIconClickCallback?.invoke(item) }
    }
}