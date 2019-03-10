package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemExerciseFinishedBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ExerciseFinishedListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit)? = null
) : DataBoundListAdapter<TrainingExerciseR, ItemExerciseFinishedBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemExerciseFinishedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseFinishedBinding, item: TrainingExerciseR) {
        binding.exercise = item
    }
}