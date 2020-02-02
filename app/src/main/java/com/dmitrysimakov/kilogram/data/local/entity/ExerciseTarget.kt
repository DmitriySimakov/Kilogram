package com.dmitrysimakov.kilogram.data.local.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseTarget(@PrimaryKey val name: String)

class ExerciseTargetDiffCallback : DiffUtil.ItemCallback<ExerciseTarget>() {
    override fun areItemsTheSame(oldItem: ExerciseTarget, newItem: ExerciseTarget) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: ExerciseTarget, newItem: ExerciseTarget) = oldItem == newItem
}