package com.dmitrysimakov.kilogram.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.Exercise
import com.dmitrysimakov.kilogram.data.model.ExerciseDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemExerciseMiniBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExercisesHorizontalAdapter(
        clickCallback: ((Exercise) -> Unit)? = null
) : DataBoundListAdapter<Exercise, ItemExerciseMiniBinding>(clickCallback, ExerciseDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemExerciseMiniBinding = ItemExerciseMiniBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemExerciseMiniBinding, item: Exercise) {
        binding.exercise = item
    }
}