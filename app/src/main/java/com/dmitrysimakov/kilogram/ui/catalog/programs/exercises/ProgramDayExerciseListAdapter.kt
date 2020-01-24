package com.dmitrysimakov.kilogram.ui.catalog.programs.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.databinding.ItemProgramExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ProgramDayExerciseListAdapter(clickCallback: ((ProgramDayExercise) -> Unit)? = null)
    : DataBoundListAdapter<ProgramDayExercise, ItemProgramExerciseBinding>(
        clickCallback,
        ProgramDayExerciseDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemProgramExerciseBinding = ItemProgramExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramExerciseBinding, item: ProgramDayExercise) {
        binding.exercise = item
    }
}

private class ProgramDayExerciseDiffCallback : DiffUtil.ItemCallback<ProgramDayExercise>() {
    override fun areItemsTheSame(oldItem: ProgramDayExercise, newItem: ProgramDayExercise) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ProgramDayExercise, newItem: ProgramDayExercise) =
            oldItem == newItem
}