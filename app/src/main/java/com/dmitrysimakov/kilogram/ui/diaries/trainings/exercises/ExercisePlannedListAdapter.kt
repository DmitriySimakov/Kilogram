package com.dmitrysimakov.kilogram.ui.diaries.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.databinding.ItemExercisePlannedBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExercisePlannedListAdapter(clickCallback: ((DetailedTrainingExercise) -> Unit)? = null)
    : DataBoundListAdapter<DetailedTrainingExercise, ItemExercisePlannedBinding>(
        clickCallback,
        DetailedTrainingExerciseDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemExercisePlannedBinding = ItemExercisePlannedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExercisePlannedBinding, item: DetailedTrainingExercise) {
        binding.exercise = item
    }
}