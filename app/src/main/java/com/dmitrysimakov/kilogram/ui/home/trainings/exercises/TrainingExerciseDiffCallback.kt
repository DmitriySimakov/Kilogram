package com.dmitrysimakov.kilogram.ui.home.trainings.exercises

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.model.TrainingExercise

class TrainingExerciseDiffCallback : DiffUtil.ItemCallback<TrainingExercise>() {
    override fun areItemsTheSame(oldItem: TrainingExercise, newItem: TrainingExercise)
            = oldItem.id == newItem.id
    
    override fun areContentsTheSame(oldItem: TrainingExercise, newItem: TrainingExercise)
            = oldItem == newItem
}