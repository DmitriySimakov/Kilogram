package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.databinding.ItemExerciseBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExerciseListAdapter(
        appExecutors: AppExecutors,
        private val viewModel: ChooseExerciseViewModel,
        clickCallback: ((Exercise) -> Unit)? = null
) : DataBoundListAdapter<Exercise, ItemExerciseBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup) = ItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExerciseBinding, item: Exercise) {
        binding.exercise = item
        binding.vm = viewModel
    }
}