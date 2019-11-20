package com.dmitrysimakov.kilogram.ui.catalogs.exercises.exercise_targets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.ExerciseTarget
import com.dmitrysimakov.kilogram.databinding.ItemExerciseTargetBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MuscleListAdapter(clickCallback: ((ExerciseTarget) -> Unit)? = null)
    : DataBoundListAdapter<ExerciseTarget, ItemExerciseTargetBinding>(clickCallback, MuscleDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemExerciseTargetBinding = ItemExerciseTargetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseTargetBinding, item: ExerciseTarget) {
        binding.target = item
    }
}

private class MuscleDiffCallback : DiffUtil.ItemCallback<ExerciseTarget>() {
    override fun areItemsTheSame(oldItem: ExerciseTarget, newItem: ExerciseTarget) =
            oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: ExerciseTarget, newItem: ExerciseTarget) =
            oldItem == newItem
}