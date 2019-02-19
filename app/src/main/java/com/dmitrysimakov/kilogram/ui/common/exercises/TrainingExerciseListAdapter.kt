package com.dmitrysimakov.kilogram.ui.common.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.TrainingExerciseItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class TrainingExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit)? = null
) : DataBoundListAdapter<TrainingExerciseR, TrainingExerciseItemBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = TrainingExerciseItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { exercise?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: TrainingExerciseItemBinding, item: TrainingExerciseR) {
        binding.exercise = item
    }
}