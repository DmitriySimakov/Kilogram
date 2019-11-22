package com.dmitrysimakov.kilogram.ui.home.trainings.exercises

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTrainingExercise

class DetailedTrainingExerciseDiffCallback : DiffUtil.ItemCallback<DetailedTrainingExercise>() {
    override fun areItemsTheSame(
            oldItem: DetailedTrainingExercise,
            newItem: DetailedTrainingExercise
    ) = oldItem._id == newItem._id
    
    override fun areContentsTheSame(
            oldItem: DetailedTrainingExercise,
            newItem: DetailedTrainingExercise
    ) = oldItem == newItem
}