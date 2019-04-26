package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.databinding.ItemProgramExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayExerciseListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((ProgramExerciseR) -> Unit)? = null
) : DataBoundListAdapter<ProgramExerciseR, ItemProgramExerciseBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<ProgramExerciseR>() {
            override fun areItemsTheSame(oldItem: ProgramExerciseR, newItem: ProgramExerciseR) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: ProgramExerciseR, newItem: ProgramExerciseR) =
                    oldItem == newItem
        }) {

    override fun createBinding(parent: ViewGroup) = ItemProgramExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramExerciseBinding, item: ProgramExerciseR) {
        binding.exercise = item
    }
}