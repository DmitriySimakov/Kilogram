package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.databinding.ItemExercisePlannedBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ExercisePlannedListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((DetailedTrainingExercise) -> Unit)? = null
) : DataBoundListAdapter<DetailedTrainingExercise, ItemExercisePlannedBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<DetailedTrainingExercise>() {
            override fun areItemsTheSame(oldItem: DetailedTrainingExercise, newItem: DetailedTrainingExercise) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: DetailedTrainingExercise, newItem: DetailedTrainingExercise) =
                    oldItem == newItem
        }) {

    override fun createBinding(parent: ViewGroup) = ItemExercisePlannedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemExercisePlannedBinding, item: DetailedTrainingExercise) {
        binding.exercise = item
    }
}