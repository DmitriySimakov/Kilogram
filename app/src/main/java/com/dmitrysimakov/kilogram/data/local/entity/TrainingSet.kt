package com.dmitrysimakov.kilogram.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["trainingExerciseId"])],
        foreignKeys = [
            ForeignKey(
                    entity = TrainingExercise::class,
                    parentColumns = ["id"],
                    childColumns = ["trainingExerciseId"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
class TrainingSet(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val trainingExerciseId: Long,
        val weight: Int? = null,
        val reps: Int? = null,
        val time: Int? = null,
        val distance: Int? = null
)