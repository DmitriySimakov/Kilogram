package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemExerciseRunningBinding
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ExerciseRunningListAdapter(
        private val viewModel: MainViewModel,
        private val lifecycleOwner: LifecycleOwner,
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit),
        private val finishIconClickCallback: ((TrainingExerciseR) -> Unit)
) : DataBoundListAdapter<TrainingExerciseR, ItemExerciseRunningBinding>(appExecutors, clickCallback) {
    
    override fun createBinding(parent: ViewGroup) = ItemExerciseRunningBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseRunningBinding, item: TrainingExerciseR) {
        binding.vm = viewModel
        binding.lifecycleOwner = lifecycleOwner
        binding.exercise = item
        binding.finishIcon.setOnClickListener { finishIconClickCallback.invoke(item) }
    }
}