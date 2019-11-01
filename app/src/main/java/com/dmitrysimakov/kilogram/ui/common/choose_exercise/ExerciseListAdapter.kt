package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.databinding.ItemExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseListAdapter(
        private val viewModel: ChooseExerciseViewModel,
        clickCallback: ((Exercise) -> Unit)? = null
) : DataBoundListAdapter<Exercise, ItemExerciseBinding>(clickCallback,
        object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
                    oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup): ItemExerciseBinding = ItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseBinding, item: Exercise) {
        binding.exercise = item
        binding.vm = viewModel
    }
}