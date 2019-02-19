package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.databinding.ExerciseItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class ExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Exercise) -> Unit)? = null
) : DataBoundListAdapter<Exercise, ExerciseItemBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ExerciseItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { exercise?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ExerciseItemBinding, item: Exercise) {
        binding.exercise = item
    }
}