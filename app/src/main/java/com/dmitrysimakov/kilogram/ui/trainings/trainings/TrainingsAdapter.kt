package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.databinding.TrainingItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class TrainingsAdapter(
        appExecutors: AppExecutors,
        private val clickCallback: ((Training) -> Unit)
) : DataBoundListAdapter<Training, TrainingItemBinding>(appExecutors, IdDiffCallback()) {

    override fun createBinding(parent: ViewGroup) = TrainingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { training?.run { clickCallback.invoke(this) } }
    }

    override fun bind(binding: TrainingItemBinding, item: Training) {
        binding.training = item
    }
}