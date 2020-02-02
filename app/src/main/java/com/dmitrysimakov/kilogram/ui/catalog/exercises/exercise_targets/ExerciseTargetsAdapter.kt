package com.dmitrysimakov.kilogram.ui.catalog.exercises.exercise_targets

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.entity.ExerciseTarget
import com.dmitrysimakov.kilogram.data.local.entity.ExerciseTargetDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemExerciseTargetBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseTargetsAdapter(clickCallback: ((ExerciseTarget) -> Unit)? = null)
    : DataBoundListAdapter<ExerciseTarget, ItemExerciseTargetBinding>(clickCallback, ExerciseTargetDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemExerciseTargetBinding = ItemExerciseTargetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseTargetBinding, item: ExerciseTarget) {
        binding.target = item
    }
}