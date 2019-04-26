package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemExerciseRunningBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseRunningListAdapter(
        private val sessionTime: LiveData<Int?>,
        private val lifecycleOwner: LifecycleOwner,
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit),
        private val finishIconClickCallback: ((TrainingExerciseR) -> Unit)
) : DataBoundListAdapter<TrainingExerciseR, ItemExerciseRunningBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<TrainingExerciseR>() {
            override fun areItemsTheSame(oldItem: TrainingExerciseR, newItem: TrainingExerciseR) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: TrainingExerciseR, newItem: TrainingExerciseR) =
                    oldItem == newItem
        }) {
    
    override fun createBinding(parent: ViewGroup) = ItemExerciseRunningBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseRunningBinding, item: TrainingExerciseR) {
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