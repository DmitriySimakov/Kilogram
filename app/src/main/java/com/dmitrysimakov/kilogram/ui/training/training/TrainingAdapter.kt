package com.dmitrysimakov.kilogram.ui.training.training

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.TrainingExerciseItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class TrainingAdapter(
        appExecutors: AppExecutors,
        private val clickCallback: ((TrainingExerciseR) -> Unit)
) : DataBoundListAdapter<TrainingExerciseR, TrainingExerciseItemBinding>(appExecutors, IdDiffCallback()) {

    override fun createBinding(parent: ViewGroup) = TrainingExerciseItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { exercise?.run { clickCallback.invoke(this) } }
    }

    override fun bind(binding: TrainingExerciseItemBinding, item: TrainingExerciseR) {
        binding.exercise = item
    }
}