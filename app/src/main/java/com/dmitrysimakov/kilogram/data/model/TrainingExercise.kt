package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

@Entity(indices = [Index(value = ["trainingId"]), Index(value = ["exercise"])],
        foreignKeys = [
            ForeignKey(
                    entity = Training::class,
                    parentColumns = ["id"],
                    childColumns = ["trainingId"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(
                    entity = Exercise::class,
                    parentColumns = ["name"],
                    childColumns = ["exercise"],
                    onDelete = ForeignKey.CASCADE)
        ])
data class TrainingExercise(
        val trainingId: String = "",
        val exercise: String = "",
        val indexNumber: Int = 0,
        val rest: Int = 120,
        val strategy: String? = null,
        val state: Int = PLANNED,
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
) {
    companion object {
        const val PLANNED = 0
        const val RUNNING = 1
        const val FINISHED = 2
    }
}

class TrainingExerciseDiffCallback : DiffUtil.ItemCallback<TrainingExercise>() {
    override fun areItemsTheSame(oldItem: TrainingExercise, newItem: TrainingExercise)
            = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TrainingExercise, newItem: TrainingExercise)
            = oldItem == newItem
}