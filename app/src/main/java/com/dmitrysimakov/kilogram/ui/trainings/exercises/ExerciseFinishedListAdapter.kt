package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemExerciseFinishedBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseFinishedListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit)? = null
) : DataBoundListAdapter<TrainingExerciseR, ItemExerciseFinishedBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<TrainingExerciseR>() {
            override fun areItemsTheSame(oldItem: TrainingExerciseR, newItem: TrainingExerciseR) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: TrainingExerciseR, newItem: TrainingExerciseR) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup) = ItemExerciseFinishedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseFinishedBinding, item: TrainingExerciseR) {
        binding.exercise = item
    }
}