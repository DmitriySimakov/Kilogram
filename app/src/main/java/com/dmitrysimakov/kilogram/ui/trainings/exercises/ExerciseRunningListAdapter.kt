package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.databinding.ItemExerciseRunningBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseRunningListAdapter(
        private val sessionTime: LiveData<Int?>,
        private val lifecycleOwner: LifecycleOwner,
        clickCallback: ((DetailedTrainingExercise) -> Unit),
        private val finishIconClickCallback: ((DetailedTrainingExercise) -> Unit)
) : DataBoundListAdapter<DetailedTrainingExercise, ItemExerciseRunningBinding>(
        clickCallback,
        DetailedTrainingExerciseDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemExerciseRunningBinding = ItemExerciseRunningBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseRunningBinding, item: DetailedTrainingExercise) {
        sessionTime.observe(lifecycleOwner, Observer {
            if (it != null) {
                val rest = it - item.secs_since_start
                binding.rest = if (rest > 0) rest else 0
            }
        })
        binding.lifecycleOwner = lifecycleOwner
        binding.exercise = item
        binding.finishIcon.setOnClickListener { finishIconClickCallback.invoke(item) }
    }
}