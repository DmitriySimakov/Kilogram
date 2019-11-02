package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.databinding.ItemExerciseFinishedBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseFinishedListAdapter(clickCallback: ((DetailedTrainingExercise) -> Unit)? = null)
    : DataBoundListAdapter<DetailedTrainingExercise, ItemExerciseFinishedBinding>(
        clickCallback,
        DetailedTrainingExerciseDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemExerciseFinishedBinding = ItemExerciseFinishedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseFinishedBinding, item: DetailedTrainingExercise) {
        binding.exercise = item
    }
}