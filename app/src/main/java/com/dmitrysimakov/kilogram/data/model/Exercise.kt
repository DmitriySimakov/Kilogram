package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [
            Index(value = ["target"]),
            Index(value = ["equipment"])
        ],
        foreignKeys = [
            ForeignKey(
                    entity = ExerciseTarget::class,
                    parentColumns = ["name"],
                    childColumns = ["target"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = Equipment::class,
                    parentColumns = ["name"],
                    childColumns = ["equipment"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
data class Exercise(
        @PrimaryKey val name: String,
        val target: String? = null,
        val isIsolated: Boolean? = null,
        val equipment: String? = null,
        val description: String = "",
        val executionsCount: Long = 0,
        var isFavorite: Boolean = false,
        val measuredInWeight: Boolean = true,
        val measuredInReps: Boolean = true,
        val measuredInTime: Boolean = false,
        val measuredInDistance: Boolean = false
)

class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem == newItem
}