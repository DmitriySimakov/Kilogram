package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.databinding.ItemProgramExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((ProgramDayExercise) -> Unit)? = null
) : DataBoundListAdapter<ProgramDayExercise, ItemProgramExerciseBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<ProgramDayExercise>() {
            override fun areItemsTheSame(oldItem: ProgramDayExercise, newItem: ProgramDayExercise) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: ProgramDayExercise, newItem: ProgramDayExercise) =
                    oldItem == newItem
        }) {

    override fun createBinding(parent: ViewGroup) = ItemProgramExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramExerciseBinding, item: ProgramDayExercise) {
        binding.exercise = item
    }
}