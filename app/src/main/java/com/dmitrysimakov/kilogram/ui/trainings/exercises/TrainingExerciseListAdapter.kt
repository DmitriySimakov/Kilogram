package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemTrainingExerciseBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class TrainingExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit)? = null
) : DataBoundListAdapter<TrainingExerciseR, ItemTrainingExerciseBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemTrainingExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { exercise?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ItemTrainingExerciseBinding, item: TrainingExerciseR) {
        binding.exercise = item
    }
}