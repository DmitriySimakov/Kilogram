package com.dmitrysimakov.kilogram.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "training_exercise_set",
        foreignKeys = [
            ForeignKey(
                    entity = TrainingExercise::class,
                    parentColumns = ["_id"],
                    childColumns = ["training_exercise_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class TrainingExerciseSet(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val training_exercise_id: Long,
        val secs_since_start: Int? = null,
        val weight: Int? = null,
        val reps: Int? = null,
        val time: Int? = null,
        val distance: Int? = null
) : HasId