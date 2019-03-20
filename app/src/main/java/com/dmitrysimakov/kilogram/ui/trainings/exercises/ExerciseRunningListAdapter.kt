package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemExerciseRunningBinding
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ExerciseRunningListAdapter(
        private val restTime: LiveData<Int>,
        private val lifecycleOwner: LifecycleOwner,
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit),
        private val finishIconClickCallback: ((TrainingExerciseR) -> Unit)
) : DataBoundListAdapter<TrainingExerciseR, ItemExerciseRunningBinding>(appExecutors, clickCallback) {
    
    override fun createBinding(parent: ViewGroup) = ItemExerciseRunningBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseRunningBinding, item: TrainingExerciseR) {
        restTime.observe(lifecycleOwner, Observer {
            val rest = item.rest - it
            binding.rest = if (rest > 0) rest else 0
        })
        binding.lifecycleOwner = lifecycleOwner
        binding.exercise = item
        binding.finishIcon.setOnClickListener { finishIconClickCallback.invoke(item) }
    }
}