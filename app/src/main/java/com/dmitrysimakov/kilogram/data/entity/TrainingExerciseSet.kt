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
        val training_exercise_id: Long,
        val secs_since_start: Int? = null,
        val weight: Int? = null,
        val reps: Int? = null,
        val time: Int? = null,
        val distance: Int? = null
) : HasId {
    @PrimaryKey(autoGenerate = true) override var _id: Long = 0
}