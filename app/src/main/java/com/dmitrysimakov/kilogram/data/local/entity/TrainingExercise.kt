package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val trainingId: Long,
        val exercise: String,
        var indexNumber: Int,
        val rest: Int = 120,
        val strategy: String? = null,
        val state: Int = PLANNED
) {
    companion object {
        const val PLANNED = 0
        const val RUNNING = 1
        const val FINISHED = 2
    }
}