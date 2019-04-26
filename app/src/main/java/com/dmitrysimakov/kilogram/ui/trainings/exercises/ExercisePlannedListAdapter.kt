package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemExercisePlannedBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExercisePlannedListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((TrainingExerciseR) -> Unit)? = null
) : DataBoundListAdapter<TrainingExerciseR, ItemExercisePlannedBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<TrainingExerciseR>() {
            override fun areItemsTheSame(oldItem: TrainingExerciseR, newItem: TrainingExerciseR) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: TrainingExerciseR, newItem: TrainingExerciseR) =
                    oldItem == newItem
        }) {

    override fun createBinding(parent: ViewGroup) = ItemExercisePlannedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExercisePlannedBinding, item: TrainingExerciseR) {
        binding.exercise = item
    }
}